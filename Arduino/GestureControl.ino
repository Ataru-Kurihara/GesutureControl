#include <Arduino.h>
#include <M5Core2.h>
#include "TCA9548A.h"
#include <DFRobot_PAJ7620U2.h>
#include <Wire.h>
#include <WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
// #include "esp_wpa2.h" //wpa2 library for connections to Enterprise networks
#define EAP_IDENTITY "20FI035" //学籍番号
#define EAP_USERNAME "20FI035" //学籍番号
#define EAP_PASSWORD "Ataru_4101826514" //大学パスワード

TCA9548A I2CMux;                  // Address can be passed into the constructor
DFRobot_PAJ7620U2 sensor;

// #define VL53L0X_REG_IDENTIFICATION_MODEL_ID         0xc0
// #define VL53L0X_REG_IDENTIFICATION_REVISION_ID      0xc2
// #define VL53L0X_REG_PRE_RANGE_CONFIG_VCSEL_PERIOD   0x50
// #define VL53L0X_REG_FINAL_RANGE_CONFIG_VCSEL_PERIOD 0x70

#define VL53L0X_REG_SYSRANGE_START                  0x00
// #define VL53L0X_REG_RESULT_INTERRUPT_STATUS         0x13
#define VL53L0X_REG_RESULT_RANGE_STATUS             0x14
#define address_tof                                 0x29  
#define PAJ7620_ADDR 0x73

byte gbuf[16];

// const char ssid[] = "JCOM_JQVH_EXT";
// const char password[] = "103337022479";
// const char* mqttHost = "192.168.0.5";
// const int mqttPort = 1883;
const char ssid[] = "CPSLAB_WLX";
const char password[] = "6bepa8ideapbu";
// const char* mqttHost = "172.16.1.27";
const char* mqttHost = "nodered-sandbox.cps.private";
const int mqttPort = 1883;
// const char ssid[] = "TDU_MRCL_WLAN_DOT1X";
// const char password[] = "Ataru_4101826514";
// const char* mqttHost = "133.14.205.70";
// const int mqttPort = 1883;

const char* topic = "/test";
char* payload;
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);
int counter = 0;


uint16_t bswap(byte b[]) {
    // Big Endian unsigned short to little endian unsigned short
    uint16_t val = ((b[0] << 8) & b[1]);
    return val;
}

uint16_t makeuint16(int lsb, int msb) {
    return ((msb & 0xFF) << 8) | (lsb & 0xFF);
}

void write_byte_data(byte data) {
    Wire.beginTransmission(address_tof);
    Wire.write(data);
    Wire.endTransmission();
}

void write_byte_data_at(byte reg, byte data) {
    // write data word at address and register
    Wire.beginTransmission(address_tof);
    Wire.write(reg);
    Wire.write(data);
    Wire.endTransmission();
}

void write_word_data_at(byte reg, uint16_t data) {
    // write data word at address and register
    byte b0 = (data & 0xFF);
    byte b1 = ((data >> 8) && 0xFF);

    Wire.beginTransmission(address_tof);
    Wire.write(reg);
    Wire.write(b0);
    Wire.write(b1);
    Wire.endTransmission();
}

byte read_byte_data() {
    Wire.requestFrom(address_tof, 1);
    while (Wire.available() < 1) delay(1);
    byte b = Wire.read();
    return b;
}

byte read_byte_data_at(byte reg) {
    // write_byte_data((byte)0x00);
    write_byte_data(reg);
    Wire.requestFrom(address_tof, 1);
    while (Wire.available() < 1) delay(1);
    byte b = Wire.read();
    return b;
}

uint16_t read_word_data_at(byte reg) {
    write_byte_data(reg);
    Wire.requestFrom(address_tof, 2);
    while (Wire.available() < 2) delay(1);
    gbuf[0] = Wire.read();
    gbuf[1] = Wire.read();
    return bswap(gbuf);
}

void read_block_data_at(byte reg, int sz) {
    int i = 0;
    write_byte_data(reg);
    Wire.requestFrom(address_tof, sz);
    for (i = 0; i < sz; i++) {
        while (Wire.available() < 1) delay(1);
        gbuf[i] = Wire.read();
    }
}

uint16_t VL53L0X_decode_vcsel_period(short vcsel_period_reg) {
    // Converts the encoded VCSEL period register value into the real
    // period in PLL clocks
    uint16_t vcsel_period_pclks = (vcsel_period_reg + 1) << 1;
    return vcsel_period_pclks;
}

void setup() {
  M5.begin();
  Wire.begin();
  
  Serial.begin(115200);
  delay(300);
  // ジェスチャーユニット起動確認
  Serial.println("PAJ7620U2 Init");
  while (sensor.begin() != 0) {
    Serial.print("initial PAJ7620U2 failure!");
    delay(500);
  }
  Serial.println("PAJ7620U2 Init Success");
  sensor.setGestureHighRate(true);

  connectWiFi();
  mqttClient.setServer(mqttHost, mqttPort);
  connectMqtt();
}

void loop() {
  getGesture();
  getDistance();
}

void createJson() {

}

void getDistance() {
    write_byte_data_at(VL53L0X_REG_SYSRANGE_START, 0x01);

    byte val = 0;
    int cnt  = 0;
    
    while (cnt < 100) {  // 1 second waiting time max
      delay(10);
      val = read_byte_data_at(VL53L0X_REG_RESULT_RANGE_STATUS);
      if (val & 0x01) break;
      cnt++;
    }
    if (val & 0x01)
       Serial.println("ready");
    else
      Serial.println("not ready");

    read_block_data_at(0x14, 12);
    uint16_t acnt                  = makeuint16(gbuf[7], gbuf[6]);
    uint16_t scnt                  = makeuint16(gbuf[9], gbuf[8]);
    uint16_t dist                  = makeuint16(gbuf[11], gbuf[10]);
    byte DeviceRangeStatusInternal = ((gbuf[0] & 0x78) >> 3);
    char buffer [sizeof(unsigned int)*8+1];
    char* d = (char*) utoa(dist,buffer,10);
    M5.Lcd.fillRect(0, 35, 319, 239, BLACK);
    M5.Lcd.setCursor(0, 35, 4);

    M5.Lcd.print("distance: ");
    M5.Lcd.println(dist/10);
    M5.Lcd.print("status: ");
    M5.Lcd.println(DeviceRangeStatusInternal);
    
    char json[200];
    const size_t capacity = JSON_OBJECT_SIZE(5);
    StaticJsonDocument<capacity> doc;
    doc["type"] = "distance";
    doc["value"] = dist;
    serializeJson(doc, json); 
    mqttClient.publish(topic, json);
    delay(1000);
    if (WiFi.status() == WL_DISCONNECTED) {
      connectWiFi();
    }
    mqttloop();
    delay(100);
}
void getGesture() {
    DFRobot_PAJ7620U2::eGesture_t gesture = sensor.getGesture();
    M5.Lcd.fillRect(0, 100, 319, 239, BLACK);
    M5.Lcd.setCursor(0, 100, 4);
    if (gesture != sensor.eGestureNone) {
        String description = sensor.gestureDescription(gesture);
        Serial.println(description);

        M5.Lcd.print("description: ");
        M5.Lcd.println(description);
        char json[200];
        const size_t capacity = JSON_OBJECT_SIZE(5);
        StaticJsonDocument<capacity> doc;
        doc["type"] = "gesture";
        doc["value"] = description;
        serializeJson(doc, json); 
        mqttClient.publish(topic, json);
            delay(1000);
        if (WiFi.status() == WL_DISCONNECTED) {
            connectWiFi();
        }
        mqttloop();
        delay(100);
    }  
}

// void connectTDUWiFi() {
//   Serial.println();
//   Serial.print("Connecting to network: ");
//   Serial.println(ssid);
//   WiFi.disconnect(true);  //disconnect form wifi to set new wifi connection
//   WiFi.mode(WIFI_STA); //init wifi mode
//     // Example1 (most common): a cert-file-free eduroam with PEAP (or TTLS)
//   WiFi.begin(ssid, WPA2_AUTH_PEAP, EAP_IDENTITY, EAP_USERNAME, EAP_PASSWORD);
  
//   while (WiFi.status() != WL_CONNECTED) {
//     delay(500);
//     Serial.print(".");
//     counter++;
//     if(counter>=60){ //after 30 seconds timeout - reset board
//       ESP.restart();
//     }
//   }
//   Serial.println("");
//   Serial.println("WiFi connected");
//   Serial.println("IP address set: "); 
//   Serial.println(WiFi.localIP()); //print LAN
// }

void connectWiFi() {
  WiFi.begin(ssid, password);
  Serial.print("connecting wifi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
  }
  Serial.println("connected.");
}

void connectMqtt() {
  while (!mqttClient.connected()) {
    Serial.println("connecting to mqtt");
    String clientID = "M5stack-" + String(random(0xffff), HEX);
    if (mqttClient.connect(clientID.c_str())) {
      Serial.println("connected");
    }
    delay(1000);
    randomSeed(micros());
  }
}

void mqttloop() {
  // MQTT
  if (!mqttClient.connected()) {
    connectMqtt();
  }
  mqttClient.loop();
}



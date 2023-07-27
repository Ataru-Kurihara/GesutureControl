package com.iwai.cpslab_plugin.Utils;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpReq {
//    public static String Get(String sUrl) {
//        HttpURLConnection conn = null;
//        InputStream in = null;
//        BufferedReader reader = null;
//        try {
//            URL url = new URL(sUrl);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            int status = conn.getResponseCode();
//            StringBuilder output = new StringBuilder();
//            if (status == HttpURLConnection.HTTP_OK) {
//                in = conn.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line);
//                }
//            }
//            return output.toString();
//        } catch (IOException err) {
//            err.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) reader.close();
//                if (in != null) in.close();
//                if (conn != null) conn.disconnect();
//            } catch (IOException err) {
//                err.printStackTrace();
//            }
//        }
//        return "";
//    }
//
//    public static String post(String sUrl, Map hashMap) {
//        Gson gson = new Gson();
//        String json = gson.toJson(hashMap);
//        HttpURLConnection conn = null;
//        InputStream in = null;
//        BufferedReader reader = null;
//        try {
//            URL url = new URL(sUrl);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Content-Type","application/json");
//            conn.setRequestProperty("Connten-Length", String.valueOf(json.length()));
//            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//            out.write(json);
//            out.flush();
//            conn.connect();
//
//            int status = conn.getResponseCode();
//            StringBuilder output = new StringBuilder();
//            if (status == HttpURLConnection.HTTP_OK) {
//                in = conn.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line);
//                }
//            } else {
//                System.out.println("status: " + status);
//                in = conn.getErrorStream();
//                reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line);
//                }
//                System.out.println("output: " + output.toString());
//            }
//            return output.toString();
//        } catch (IOException err) {
//            err.printStackTrace();
//            return err.toString();
//        } finally {
//            try {
//                if (reader != null) reader.close();
//                if (in != null) in.close();
//                if (conn != null) conn.disconnect();
//            } catch (IOException err) {
//                err.printStackTrace();
//            }
//        }
//    }
}

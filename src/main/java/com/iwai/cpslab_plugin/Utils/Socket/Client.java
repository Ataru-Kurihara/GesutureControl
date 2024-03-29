package com.iwai.cpslab_plugin.Utils.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * ソケット通信(クライアント側)
 */
public class Client {
    public void runSample() {

        Socket cSocket = null;
        BufferedReader csInput = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try{
            //IPアドレスとポート番号を指定してクライアント側のソケットを作成
            cSocket = new Socket("c", 8765);

            //クライアント側での入力用
            csInput = new BufferedReader
                    (new InputStreamReader(System.in));

            //クライアント側からサーバへの送信用
            writer = new PrintWriter
                    (cSocket.getOutputStream(), true);

            //サーバ側からの受取用
            reader = new BufferedReader
                    (new InputStreamReader
                            (cSocket.getInputStream()));

            //無限ループ　byeの入力でループを抜ける
            String line = null;
            while (true) {
                System.out.println("-------------------");
                System.out.println("偶数の数値を入力して下さい");
                System.out.println("-------------------");

                line = csInput.readLine();

                //送信用の文字を送信
                writer.println(line);

                //byeの入力でループを抜ける
                if (line.equals("bye")) {
                    break;
                }

                //サーバ側からの受取の結果を表示
                System.out.println
                        ("サーバーからの回答：" + reader.readLine());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (csInput != null) {
                    csInput.close();
                }
                if (cSocket != null) {
                    cSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("クライアント側終了です");
        }
    }

    public void runSample2() {

        Socket cSocket = null;
        BufferedReader csInput = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try{
            System.out.println(InetAddress.getLocalHost());
            //IPアドレスとポート番号を指定してクライアント側のソケットを作成
            cSocket = new Socket("172.18.0.55", 8766);
//            cSocket = new Socket("localhost", 8766);
            //クライアント側での入力用
            csInput = new BufferedReader
                    (new InputStreamReader(System.in));

            //クライアント側からサーバへの送信用
            writer = new PrintWriter
                    (cSocket.getOutputStream(), true);

            //サーバ側からの受取用
            reader = new BufferedReader
                    (new InputStreamReader
                            (cSocket.getInputStream()));

            //無限ループ　byeの入力でループを抜ける
            String line = null;

            line = "2";

            //送信用の文字を送信
            writer.println(line);

            //サーバ側からの受取の結果を表示
            System.out.println("サーバーからの回答：" + reader.readLine());

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (csInput != null) {
                    csInput.close();
                }
                if (cSocket != null) {
                    cSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("クライアント側終了です");
        }
    }
}


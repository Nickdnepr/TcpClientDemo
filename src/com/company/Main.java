package com.company;

import java.io.*;
import java.net.Socket;

public class Main {

    public static void main(String argv[]) throws Exception {
        String sentence = "";
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   try {
                       String msg;
                       if ((msg = inFromServer.readLine())!=null){
                           System.out.println(msg);
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            }
        }).start();

        while (!sentence.equals("end")){
            sentence = inFromUser.readLine();
            out.println(sentence);
            out.flush();
        }

        clientSocket.close();
    }
}

package br.com.lucaophp.passaslide;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private static String lerArquivo(String path){
        String conteudo="";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = "";
            while((linha = bufferedReader.readLine())!=null){
                conteudo+=linha+"\n";

            }
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conteudo;
    }

    public static void send(String msg, String host) throws IOException {
        try {
            Socket clientSocket = new Socket(host, 60010);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(String.format("%s\n", msg));

        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }
}

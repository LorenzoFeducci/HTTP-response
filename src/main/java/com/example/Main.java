package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ServerSocket ss = new ServerSocket(8080);
        while(true){
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String[] firstLine = in.readLine().split(" ");
            String method = firstLine[0];
            String resource = firstLine[1];
            String version = firstLine[2];

            String header;
            do{
                header = in.readLine();
                System.out.println(header);
            }while(!header.isEmpty());
            System.out.println("fine");

            if(resource.equals("/index.html") || resource.equals("/")){

                File file = new File("htdocs/index.html");
                out.writeBytes("HTTP/1.1 200 OK\n");
                out.writeBytes("Content-Length: " + file.length() + "\n");
                out.writeBytes("Content-Type: "+ getContentType(file) +"\n");
                out.writeBytes("\n");

                // legge un file, buttando fuori il contenuto di un file a blocchi di 8k
                InputStream input = new FileInputStream(file);
                byte[] buf = new byte[8192];
                int n;
                while((n = input.read(buf)) != -1){
                    out.write(buf, 0, n);
                }
                input.close();

            }else if(resource.equals("/1.png")){

                File file = new File("htdocs/1.png");
                out.writeBytes("HTTP/1.1 200 OK\n");
                out.writeBytes("Content-Length: " + file.length() + "\n");
                out.writeBytes("Content-Type: "+ getContentType(file) +"\n");
                out.writeBytes("\n");

                // legge un file, buttando fuori il contenuto di un file a blocchi di 8k
                InputStream input = new FileInputStream(file);
                byte[] buf = new byte[8192];
                int n;
                while((n = input.read(buf)) != -1){
                    out.write(buf, 0, n);
                }
                input.close();
                
            }else if(resource.equals("/style.css")){

                File file = new File("htdocs/style.css");
                out.writeBytes("HTTP/1.1 200 OK\n");
                out.writeBytes("Content-Length: " + file.length() + "\n");
                out.writeBytes("Content-Type: "+ getContentType(file) +" \n");
                out.writeBytes("\n");

                // legge un file, buttando fuori il contenuto di un file a blocchi di 8k
                InputStream input = new FileInputStream(file);
                byte[] buf = new byte[8192];
                int n;
                while((n = input.read(buf)) != -1){
                    out.write(buf, 0, n);
                }
                input.close();
            }else{
                String response;
                response = "Pagina non trovata";
                out.writeBytes("HTTP/1.1 404 Not found\n");
                out.writeBytes("Content-Type: text/plain\n");
                out.writeBytes("Content-Length: "+ response.length() +"\n");
                out.writeBytes("\n");
                out.writeBytes(response);
            }
            s.close();
        }
    }

    private static String getContentType(File file) {
        String[] s = file.getName().split("\\.");
        String ext = s[s.length - 1];
        switch(ext) {
            case "html":
            case "htm":
                return "text/html";
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "css":
                return "text/css";
            case "js":
                return "application/javascript";
            default:
                return " ";
        }
    }
}
package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Server extends Thread {
    @Override
    public void run() {
        Random rand = new Random();
        String[] names = { "biton", "kitzi", "meow" };

        ServerSocket server = null;
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            server = new ServerSocket(5635);
            socket = server.accept();
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("[Server] Could not create the server socket");
        }
        boolean hasFinished = false;
        while (!hasFinished) {
            String message = "";
            try {
                message = reader.readLine();
            } catch (IOException e) {
                System.out.println("[Server] Couldn't read");
            }
            String returnMessage = "";
            switch (message) {
            case "random":
                returnMessage += Integer.toString(rand.nextInt(10) + 1);
                break;
            case "time":
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                returnMessage += format.format(LocalDateTime.now());
                break;
            case "mitzi":
                returnMessage += names[rand.nextInt(3)];
                break;
            case "close":
                writer.close();
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("[Server] Could not close the reader");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("[Server] Could not close the socket");
                }
                try {
                    server.close();
                } catch (IOException e) {
                    System.out.println("[Server] Could not close the server socket");
                }
                hasFinished = true;
                break;
            }
            if (message != "close" && returnMessage != "") {
                try {
                    writer.println(returnMessage);  
                } catch (Exception e) {
                    System.out.println("[Server] Could not write");
                }
            }
        }
    }
}
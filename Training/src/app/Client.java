package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
    @Override
    public void run() {
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try{
            socket = new Socket("127.0.0.1", 5635);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(UnknownHostException e){
            System.out.println("[Client] Unknown Host");
        }
        catch(IOException e){
            System.out.println("[Client] Connection not succesful");
        }
        boolean hasFinished = false;
        while(!hasFinished){
            String message = System.console().readLine("Enter message: ");
            try {
                writer.println(message);
            } catch (Exception e) {
                System.out.println("[Client] Could not write");
            }
            if(message == "close"){
                writer.close();
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("[Client] Could not close the reader");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("[Client] Could not close the socket");
                }
                hasFinished = true;
            }
            else {
                try {
                    System.out.println(reader.readLine());
                } catch (IOException e) {
                    System.out.println("[Client] Could not read from the server");
                }
            }
        }
    }
}
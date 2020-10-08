package app;

public class App {
    public static void main(String[] args) {
        Client c = new Client();
        Server s = new Server();

        s.start();
        c.start();

        System.out.println("Hello");
        System.out.println("Whatsapp");
        System.out.println("Meowmeow");
    }
}




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    
    public static void main(String[] args) throws IOException {
        final int PORT = 2788;
        final int TIME = 100;
        System.out.println("Attempting connection...");
        Socket s = new Socket("127.0.0.1", PORT); // create new socket
        System.out.println("Connection successful.");
        
        PrintWriter out = new PrintWriter(s.getOutputStream()); // create output stream to output messages from client to server
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); // create input steam to listen for messages from the server
        Scanner userInput = new Scanner(System.in); // Scanner to listen to user's inputs from keyboard
        // create new thread for continously listening to the server for messages
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (in.ready()) {
                            String response = in.readLine();
                            System.out.println(response);
                            Thread.sleep(TIME);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();
        
        // create new thread for continously sending messages to the server
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        
                        String msg = userInput.nextLine();
                        out.println(msg);
                        out.flush();
                        if (msg.equals("/QUIT") || msg.equals("/quit") || msg.equals("/LOGOUT") || msg.equals("/logout")) {
                            System.exit(0);
                            in.close();
                            out.close();
                            s.close();
                        }
                        Thread.sleep(TIME);
                        
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();
    }
}

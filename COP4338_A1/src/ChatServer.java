
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer
{
    public static void main (String[] args) throws IOException
    {
        final int PORT = 2788;
        ServerSocket server = new ServerSocket(PORT);
        ArrayList<Integer> idNums = new ArrayList<Integer>();
        ArrayList<PrintWriter> pw = new ArrayList<PrintWriter>();
        ArrayList<String> names = new ArrayList<String>();
        System.out.println("Waiting for clients to connect...");
        Integer id = 0;
        
        // continously checking for new clients
        while (true)
        {
            Socket s = server.accept(); // accept client's connection
            System.out.println("Client connected.");
            ChatService service = new ChatService(s, idNums, pw, names, id); // start new chat service with passed references
            Thread t = new Thread(service); // create new thread with chatservice runnable
            t.start(); // start/spawn new thread
            id = Integer.valueOf(id.intValue() + 1); // update id for next usable unique id
            System.out.println("Next unique id: " + id.toString());
            
        }
    }
}
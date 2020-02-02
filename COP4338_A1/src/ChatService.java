
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatService implements Runnable {

    Socket s;
    ArrayList<Integer> idNum;
    ArrayList<PrintWriter> pw;
    ArrayList<String> listNames;
    BufferedReader in;

    int id;
    //Scanner in;
    String name;
    boolean ifNameEntered;

    public ChatService(Socket st, ArrayList<Integer> num, ArrayList<PrintWriter> p, ArrayList<String> names, Integer uid) {
        id = uid;
        ifNameEntered = false;
        name = "";
        s = st;
        idNum = num;
        pw = p;
        listNames = names;
    }

    public void run() {
        try {
            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream())); // create inputstream to take in client input
                pw.add(new PrintWriter(s.getOutputStream())); // create output stream to output to client
                idNum.add(id); // add unique id for the user to the list
                doService();
            } finally {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doService() throws IOException {
        // continously listening to client's inputs
        while (true) {
            if (ifNameEntered == false) {
                pw.get(idNum.indexOf(id)).println("Please enter your username:");
                pw.get(idNum.indexOf(id)).flush();
               String input = in.readLine();
               if (listNames.contains(input)){ // if name already exists as a active user in server, deny name entry and prompt to enter a new name
                   pw.get(idNum.indexOf(id)).println("Error: this name is already in use. Please enter a different name.");
                   pw.get(idNum.indexOf(id)).flush();
               }
               else
               {
                name = input;
                listNames.add(name);
                for (PrintWriter p : pw) {
                    p.println("Welcome to the server, " + name + ".");
                    p.flush();
                }

                ifNameEntered = true;
               }
            } 
            else {
                pw.get(idNum.indexOf(id)).println("Enter a message: ");
                pw.get(idNum.indexOf(id)).flush();
                String message = in.readLine();
                
                if (message.equals("/QUIT") || message.equals("/quit") || message.equals("/LOGOUT") || message.equals("/logout")) {
                    for (PrintWriter p : pw) {
                        p.println(name + " has left the server.");
                        p.flush();
                        
                    }
                    // remove user from the PrintWriter list via index of unique ID from the id list; allows users to be removed correctly
                    pw.remove(idNum.indexOf(id));
                    listNames.remove(idNum.indexOf(id));
                    idNum.remove(idNum.indexOf(id));
                    return;
                }
                // prints list of users to the client calling the command
                else if (message.equals("/users") || message.equals("/USERS")) {
                    pw.get(idNum.indexOf(id)).println();
                    for (int i = 0; i < listNames.size(); i++) {
                        pw.get(idNum.indexOf(id)).println(listNames.get(i));
                        pw.get(idNum.indexOf(id)).flush();
                    }

                }
                // returns list of available commands to the client calling the command
                else if (message.equals("/help") || message.equals("/HELP")) {
                    pw.get(idNum.indexOf(id)).println("List of commands:\n"
                            + "/users or /USERS - get name list of all users in chat room\n"
                            + "/logout or /LOGOUT or /quit or /QUIT - Logout of the chatroom\n"
                            + "/help - return information on avaiable commands.");
                    pw.get(idNum.indexOf(id)).flush();
                } 
                else { // if nothing has been entered, ignore
                    if (message.equals("")) {
                        continue;
                    }
                    broadcast(message); // if no commands entered, message is broadcasted to all clients in the server
                }
            }
        }
        
        
    }

    public void broadcast(String msg) {
        // broadcast passed message to all clients in the server
        for (PrintWriter p : pw) {
            p.println(name + ": " + msg);
            p.flush();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alba.neagra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stfn
 */
public class Server {
    
    private static final int PORT = 9001;
    public static ArrayList<Socket> connections = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static int nrUsers = 0;
    
    public static void main (String[] args) throws IOException {
        
       System.out.println("UP");
       ServerSocket listener = new ServerSocket(PORT);
       
       while (true) {
           new Handler(listener.accept()).start();
       }
       
    }
    
    private static class Handler extends Thread {
        public String username;
        public Socket socket;
        OutputStream os;
        InputStream is;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        
        public Handler (Socket socket) {
            this.socket = socket;
        }
        
        public void run() {
            try {
                //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //out = new PrintWriter(socket.getOutputStream(), true);
                os = socket.getOutputStream();
                oos = new ObjectOutputStream(os);
                is = socket.getInputStream();
                ois = new ObjectInputStream(is);
                
                
               
                oos.writeObject("!SUBMITNAME!");
                username = (String) ois.readObject();
                 
                    
                User user = new User(username);
                
                
                    
                synchronized (users) {
                    for (int i = 0; i < users.size(); i++) {                
                        if (users.get(i).getName() == username) {         
                            //oos.writeObject("!USERS!");
                            //oos.writeObject(users);
                            oos.writeObject("!NAMEINUSE!");
                            break;
                        }
                    }
                    users.add(user);
                    nrUsers++;
                    oos.writeObject("!USERS!");
                    oos.writeObject(users);
                    
                }
                    
                    
                    
                
                
                //oos.writeObject("!NAMEACCEPTED!");
                
                System.out.println(socket.getLocalAddress() + " CONNECTED");
                
                while (true) {
                    if (nrUsers != users.size()) {
                        oos.writeObject("!USERS!");
                        oos.writeObject(users);
                        
                    }
                    
                    //System.out.println(users.get(0).getName());
                    //System.out.println(users.get(0).getName());
                    //String input = in.readLine();
                    //TODO:Reactioneaza cand primeste mesaje legate de joc:provocare la joc, accept/refuz provocare,
                    //asezare bila, alegere cupa, numarul de victorii consecutive ale jucatorilor
                    
                }
                
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
   /* public static void AddUser(Socket SOCK) throws IOException {
        Scanner in = new Scanner(SOCK.getInputStream());
        
        String user = in.nextLine();
        users.add(user);
        
        for (int i = 0; i < Server.connections.size(); i++) {
            PrintWriter out = new PrintWriter(Server.connections.get(i).getOutputStream());
            out.println("#USERS: " + users);
            out.flush();
        }
    }*/
    
}
        
    
    


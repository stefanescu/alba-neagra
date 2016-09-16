/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alba.neagra;

import static alba.neagra.GUI.jList1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author stfn
 */
 public class Client implements Runnable {
    
    //private static final int PORT = 9001;
    private Socket socket;
    //private BufferedReader in;
    //private PrintWriter out;
    private OutputStream os;
    private InputStream is;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    private int pozBila = 0;
    private int wins = 0;
    private int losses = 0;
    
    private String username = "Anon";
    public static ArrayList<User> users = new ArrayList<>();
    
    public Client (Socket socket) throws Exception {
       
        this.socket = socket;
        //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //out = new PrintWriter(socket.getOutputStream(), true);
        os = socket.getOutputStream();
        is = socket.getInputStream();
        oos = new ObjectOutputStream(os);
        ois = new ObjectInputStream(is);
        
        
        //GameClientGUI GUI = new GameClientGUI();
        //GUI.setVisible(true);
        
    }
    
     public String getUsername () {
        return JOptionPane.showInputDialog(null, "Enter a nickname", "Screen name selection", JOptionPane.PLAIN_MESSAGE);
    }
     
     public static String getServerAddress () {
        return  JOptionPane.showInputDialog(null, "Server IP:", "Server connection", JOptionPane.PLAIN_MESSAGE);
    }
    
    public int getWins () {
        return wins;
    }
    
    public int getLosses () {
        return losses;
    }
    
    public void win () {
        wins++;
    }
    
    public void resetWins () {
        wins = 0;
    }
    
    public int getPozBila () {
        return pozBila;
    }
 
    @Override
    public void run () {
        try {
            

            while (true) {
                String servLine = (String) ois.readObject();
                if (servLine.startsWith("!SUBMITNAME!")) {
                    username = getUsername();
                    if (username == null) {
                        System.exit(0);
                    }
                    oos.writeObject(username);
                    
                }
                else if (servLine.startsWith("!USERS!")) {
                    users = (ArrayList) ois.readObject();
                   
                    updateUserList();
                    //System.out.println(users.get(0).getName());
                    //JOptionPane.showInputDialog(null, users.get(0), "USERLIST", JOptionPane.INFORMATION_MESSAGE);
                    
                    
   
                }
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        
    }
    
    public void provoacaJoc(String challangedUser) throws IOException, ClassNotFoundException {
        boolean gameOver = false;
        
        oos.writeObject("!CHALLANGE! " + username + "" + challangedUser);
        String servLine = (String) ois.readObject();
        if (servLine == "!ACCEPT!") {
            
        }
        else if (servLine == "!DECLINE!") {
            
        }
        
    }
    
     public  void updateUserList() {
        /*for (int i = 0; i < users.size(); i++) {
            jList1.addElement(users.get(i));
        }*/
        //for (int i = 0; i < users.size(); i++) {
            //tempList.addElement(users.get(i));
            //jList1.add(users.get(i));
        //}
       
        jList1.removeAll();
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < users.size(); i++) {
            model.addElement(users.get(i).getName());
            
            
        }
        jList1.setModel(model);
        System.out.println(jList1.getModel());
        
        //jList1 = users.toArray();
        //jList1.setModel(tempList);
         
         
        
    }
    
     public void Disconnect() {
         //TODO:disconnect message, disable buttons, etc
         System.exit(0);
     }
     
   
}

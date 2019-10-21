/*
* @Author: Puffrora
* @Date:   2019-09-20 15:35:02
* @Last Modified by:   Puffrora
* @Last Modified time: 2019-10-01 15:25:26
*/
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.net.ServerSocketFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Server {
    static int port = 9090;
    static int counter = 0;
    static SessionManagerPanel sessPanel;
    volatile static ArrayList<WhiteBoard.drawings> sumDraw = new ArrayList<WhiteBoard.drawings>();
    static ArrayList<User> userList= new ArrayList<User>();
    static ArrayList<Socket> clientList = new ArrayList<Socket>();
    
    
    //class-level scope
    static WhiteBoard newPad;

    public static void main(String args[]) throws SocketException {
        userList.clear();
        
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        
        }
        

        newPad = new WhiteBoard("Manager");
        newPad.setTitle("Manager");
        newPad.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
        
        //SessionManger
        System.out.println("Session Manager");
        sessPanel= new SessionManagerPanel(port);
        sessPanel.setVisible(true);
        
        //Server setup
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try (ServerSocket server = factory.createServerSocket(port)) {
            System.out.println("Waiting for client connection to port number: " + port);

          
            while (true) {
                Socket client = null;
                client = server.accept();
                User newUser= new User(counter,"Guest",client);
                counter++;
                System.out.println("Client " + counter + ": Applying for connection! in port num: " + client.getPort());

                // Start a new thread for a connection
                ServerThread t = new ServerThread(newUser);
                t.start();
            }

        } catch (IOException e) {
            System.out.println("Unable to setup server, try another port.");
            System.exit(1);
        }
    }

    static class ServerThread extends Thread {
        // Client sends the query here and this thread will produce the responses to the client. In this case, client sends the drawings here
        // And the drawings will be combined with other drawings then send back to the client.
        User user;
        Socket client;
        DataInputStream is;
        DataOutputStream os;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        BufferedReader in;
        BufferedWriter out;

        ServerThread(User newuser) throws IOException {
        	this.client=newuser.socket;
        	this.user=newuser;
        	
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            user.setName(in.readLine());
            System.out.println(user.getName());
            
            
            
            //COPIED
            int response = JOptionPane.showConfirmDialog(null, user.getName()+" want to join in?", "allow",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                  System.out.println(user.getName()+"quit!");
                } else if (response == JOptionPane.YES_OPTION) {
                  System.out.println(user.getName()+"join!");
                } else if (response == JOptionPane.CLOSED_OPTION) {
                  System.out.println(user.getName()+"join!");
                }
                //this.id = count++;
               // out.write(new Integer(id).toString()); 
                //out.newLine();
                //out.flush();
            System.out.println("login success: id = " + " , " + this.user.socket);
            
            //userList.add(user);
            sessPanel.update(user);
            clientList.add(user.socket);
        }

        public void run() {
            String clientDrawing;
           
            
            try {
            	 os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
                 // os = new DataOutputStream(client.getOutputStream());
                 oos = new ObjectOutputStream(client.getOutputStream());
                 is = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                 ois = new ObjectInputStream(client.getInputStream());
                int count = 0;
                Graphics g = newPad.getGraphics();
                
                while (is.available()>0) {
                	int x1 = is.readInt();
                	int y1 = is.readInt();
                	int x2 = is.readInt();
                	int y2 = is.readInt();
                	g.drawLine(y1, x1, y2, x2);
                	
                	//added-Zoran's sync of client and server
                	for(Socket client:clientList) {
                		os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
                		os.writeInt(x1);
                		os.writeInt(y1);
                		os.writeInt(x2);
                		os.writeInt(y2);
                		os.flush();
                	}
//                    WhiteBoard.drawings draw = (WhiteBoard.drawings) ois.readObject();
//                    sumDraw.add(draw);
//                    oos.writeObject(sumDraw);
                	
                	
                	
                	
                	
                	
//                    if (newOb != null) {
//
//                        System.out.println(newOb.x1 + " " + newOb.y2 + " " + newOb.x2 + " " + newOb.y2);
//                        System.out.println(clientSocket.getPort() + "cacacaa" + clientSocket.getLocalPort());
//                        System.out.println(number);
//
//                        ArrayList<Integer> coordinate = new ArrayList<Integer>();
//
//    //                            oss.writeObject(newOb);
//                        coordinate.add(newOb.x1);
//                        coordinate.add(newOb.y1);
//                        coordinate.add(newOb.x2);
//                        coordinate.add(newOb.y2);
//                        for (int i = 0; i < 4; i++) {
//                            os.writeInt(coordinate.get(i));
//                            System.out.println("wrote " + i);
//                        }
//                        count += 1;
//                        os.flush();
//    //                        if(count == 20) {
//    //                            os.flush();
//    //                            count = 0;
//    //                        }
//
//                        newOb = null;
//    //                        int x1, x2, y1, y2;
//    //                        x1=is.readInt();
//    //                        y1=is.readInt();
//    //                        x2=is.readInt();
//    //                        y2=is.readInt();
//    //                        Graphics g = this.getGraphics();
//    //                        g.drawLine(x1, y1, x2, y2);
//                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
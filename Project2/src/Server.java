import whiteboard.*;
import whiteboard.WhiteBoard.drawings;

import java.awt.*;
import java.awt.event.*;
import javax.net.ServerSocketFactory;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static int port = 9090;
    static int counter = 1;
    
    static String userName = "Manager";
    volatile static ArrayList<drawings> sumDraw = new ArrayList<drawings>();
    static SessionManagerPanel sessPanel;
    static ArrayList<User> userList= new ArrayList<User>();
    static ArrayList<Socket> clientList = new ArrayList<Socket>();
    static WhiteBoard newPad;

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        newPad = new WhiteBoard(userName);
        newPad.setTitle(userName);
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
        
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try (ServerSocket server = factory.createServerSocket(port)) {
            System.out.println("Waiting for client connection to port number: " + port);

            // Wait for connections.
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

        ServerThread(User newuser) {
        	this.client=newuser.socket;
        	this.user=newuser;
            clientList.add(client);
            
            
        }
        
      //COPIED
       
        
        public void run() {
            String clientDrawing;
            try {
                //è¿žæŽ¥æˆ�åŠŸå�Žå¾—åˆ°æ•°æ�®è¾“å‡ºæµ�
                os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
//                os = new DataOutputStream(client.getOutputStream());
                //oos = new ObjectOutputStream(client.getOutputStream());
                is = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                ois = new ObjectInputStream(client.getInputStream());
                out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                user.setName(in.readLine());
                System.out.println(user.getName());
                
                int response = JOptionPane.showConfirmDialog(null, user.getName()+" want to join your session?", "Do you authorise", 
                		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); 
                //copied
                if (response == JOptionPane.NO_OPTION) {
                  System.out.println(user.getName()+" quit!");
                } else if (response == JOptionPane.YES_OPTION) {
                  System.out.println(user.getName()+"join!");
                  System.out.println("login success: id = " + " , " + this.user.socket);
           
                  sessPanel.update(user);
                  clientList.add(user.socket);

                } else if (response == JOptionPane.CLOSED_OPTION) {
                  System.out.println(user.getName()+"join!");
                }
                //this.id = count++;
               // out.write(new Integer(id).toString()); 
                //out.newLine();
                //out.flush();
                
                

                //x1,y1ä¸ºèµ·å§‹ç‚¹å��æ ‡ï¼Œx2,y2ä¸ºç»ˆç‚¹å��æ ‡ã€‚å››ä¸ªç‚¹çš„åˆ�å§‹å€¼è®¾ä¸º0

                
                int count = 0;
                Graphics g = newPad.getGraphics();
                while (true) {
                	int x1 = is.readInt();
                	if(x1 < -10000) {
                		x1 = is.readInt();
                	}
                	int y1 = is.readInt();
                	int x2 = is.readInt();
                	int y2 = is.readInt();
                	System.out.println("x1 = " + x1);
                	System.out.println("y1 = " + y1);
                	System.out.println("x2 = " + x2);
                	System.out.println("y2 = " + y2);
                	g.drawLine(x1, y1, x2, y2);
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
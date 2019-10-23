import java.awt.Graphics;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import whiteboard.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Client {
    int x1,x2,y1,y2,curchoice;
    DataInputStream is;
    DataOutputStream os;
    ObjectInputStream iss;
    private BufferedReader in; 
    public BufferedWriter out;
    Graphics g;
    WhiteBoard newPad;
    WhiteBoard.drawings nb;
    String userName = "Client";
    Socket client;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        Client CP = new Client();
        CP.creat();
        CP.welcomeFrame();
        CP.ShowUI();
    }
    
    //äº§ç”Ÿä¸€ä¸ªSocketç±»ç”¨äºŽè¿žæŽ¥æœ�åŠ¡å™¨ï¼Œå¹¶å¾—åˆ°è¾“å…¥æµ�
    public void creat() {
        try {
        	//the oos and iss caused problems and is commented out.
            client =new Socket("localhost", 9090);
//            is = new DataInputStream(new BufferedInputStream(client.getInputStream()));
           //is = new DataInputStream(client.getInputStream());
            //iss = new ObjectInputStream(client.getInputStream());
           out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream())); 
           os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void welcomeFrame() throws IOException, ClassNotFoundException{

        JFrame welcomeFrame = new JFrame("Welcome to PowerPuff Paint");
        // create a object of JTextField with 16 columns and a given initial text
        JTextField welcomeText = new JTextField("Enter username",16);
       
        // create a new button
        JButton welcomeButton = new JButton("Submit");
            welcomeButton.addActionListener(
                    new ActionListener() {
                public void actionPerformed(ActionEvent e)  {

                    try{
                        //if (!adminServer.userArrayList.contains(welcomeText.getText())) {
                        userName = welcomeText.getText();
                        
                        welcomeFrame.dispose();
                        System.out.println("Welcome");
                        System.out.println("Creating sockets");
                        out.write(userName);
            			out.newLine();
            			out.flush();
            			
                        
                        //if(Server.counter==1){
                           
                        //}



                    }catch (Exception w){
                        w.printStackTrace();
                    }
                    

                    // set the text of field to blank
                    //welcomeText.setText("");
                }
            });
            
            welcomeFrame.addWindowListener(
                    new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {

                            System.exit(0);
                        }
                    });
        // add buttons and textfield to panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(welcomeButton);
        welcomePanel.add(welcomeText);

        // add panel to frame
        welcomeFrame.add(welcomePanel);

        // set the size of frame
        welcomeFrame.setSize(300, 200);
        welcomeFrame.setVisible(true);

    }



    //æž„é€ å®¢æˆ·ç«¯ç•Œé�¢å¹¶å�¯åŠ¨çº¿ç¨‹
    public void ShowUI() throws IOException, ClassNotFoundException {
        System.out.println("1 ininini");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("2 ininini");
        } catch (Exception e) {
            System.out.println("3 badbad");
            e.printStackTrace();
        }
        newPad = new WhiteBoard(userName, client);
        //it doesn't go past this
        System.out.println("Whiteboard created");
     
        System.out.println("4 ininini");
        newPad.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {

                    System.exit(0);
                }
            });
        System.out.println("5 ininini");
        g = newPad.getGraphics();
//        while (true) {
//            try {
////                nb = (WhiteBoard.drawings)iss.readObject();
//                System.out.println("Get ininini");
//                ArrayList<Integer> coordinate = new ArrayList<Integer>();
//                for(int i = 0; i < 4; i++) {
//                    coordinate.add(is.readInt());
//                }
//                x1=is.readInt();
//                y1=is.readInt();
//                x2=is.readInt();
//                y2=is.readInt();
//                x1=coordinate.get(0);
//                y1=coordinate.get(1);
//                x2=coordinate.get(2);
//                y2=coordinate.get(3);
//
//                System.out.println("the coordinates are: " + x1 + x2 + y1 + y2);
//                g.drawLine(x1, y1, x2, y2);
//
////                os.writeInt(newPad.getNewOb().x1);
////                os.writeInt(newPad.getNewOb().y1);
////                os.writeInt(newPad.getNewOb().x2);
////                os.writeInt(newPad.getNewOb().y2);
////                os.flush();
//                    
//            } catch (IOException e) {
//            	e.printStackTrace();
//            }
//        catch (ClassNotFoundException ee) {
//            ee.printStackTrace();
//        }
        }

    }
    //å°†isè¾“å…¥æµ�ç»ˆä¸­çš„å��æ ‡å¾—åˆ°ï¼Œå¹¶æ ¹æ�®å��æ ‡ä¿¡æ�¯ç”»å‡ºç›¸åº”çš„çº¿æ®µã€‚
//    @Override
//    public void run() {
//        while (true) {
//            try {
////                    nb = (WhiteBoard.drawings)iss.readObject();
////                    System.out.println("Get suc");
//
//                    x1=is.read();
//                    y1=is.read();
//                    x2=is.read();
//                    y2=is.read();
//                    g.drawLine(x1, y1, x2, y2);
//
//            } catch (IOException e) {
//            e.printStackTrace();
//        }
////        catch (ClassNotFoundException ee) {
////            ee.printStackTrace();
////        }
//        }
//    }

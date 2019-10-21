import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Client extends JFrame {
    int x1,x2,y1,y2,curchoice;
    private int id;
    private BufferedReader in; 
    public BufferedWriter out;
    DataInputStream is;
    DataOutputStream os;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Graphics g;
    Server adminServer;
    WhiteBoard newPad;
    WhiteBoard.drawings nb;
    String userName = "Client";
    Socket client;
    SessionManagerPanel servermanager;

    public static void main(String args[]) throws IOException {
    	
				Client CP = new Client();
				
	}
    
    public Client() {
    	try {
    		
			welcomeFrame();
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    //Why is there no while loop
    public void create() {
        try {
        	
        	System.out.println("Entered socket");
            client =new Socket("localhost", 9090);
            System.out.println("Entered socket 1");
//            is = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            //is = new DataInputStream(client.getInputStream());
            
            //ois = new ObjectInputStream(client.getInputStream());
            System.out.println("Entered socket 2");
           // os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
            System.out.println("Entered socket 3");
            //oos = new ObjectOutputStream((new BufferedOutputStream(client.getOutputStream())));
            System.out.println("Entered socket 4");
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Entered socket 5");
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            System.out.println("Entered socket 6");
            System.out.println("Sockets created");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
        newPad.setTitle(userName);
        System.out.println("4 ininini");
        newPad.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {

                    System.exit(0);
                }
            });
        System.out.println("5 ininini");
        g = newPad.getGraphics();

        }
    	
    	
    	//UserList Manager
    	
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
                            
                            create();
                            System.out.println("Creating sockets");
                            out.write(userName);
                			out.newLine();
                			out.flush();
                            ShowUI();
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


    }

	
    //Ã¥Â°â€ isÃ¨Â¾â€œÃ¥â€¦Â¥Ã¦Âµï¿½Ã§Â»Ë†Ã¤Â¸Â­Ã§Å¡â€žÃ¥ï¿½ï¿½Ã¦Â â€¡Ã¥Â¾â€”Ã¥Ë†Â°Ã¯Â¼Å’Ã¥Â¹Â¶Ã¦Â Â¹Ã¦ï¿½Â®Ã¥ï¿½ï¿½Ã¦Â â€¡Ã¤Â¿Â¡Ã¦ï¿½Â¯Ã§â€�Â»Ã¥â€¡ÂºÃ§â€ºÂ¸Ã¥Âºâ€�Ã§Å¡â€žÃ§ÂºÂ¿Ã¦Â®ÂµÃ£â‚¬â€š
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

package SecureVoting;


import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class voter {
	
	public static JFrame frame;

    private static SecureRandom random = new SecureRandom();

    private static String nextSessionID(){
        return new BigInteger(130, random).toString(32);
    }

    private static void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("User:");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		
		loginButton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed



                String validID = CLAconn(new voterID(userText.getText(), new String(passwordText.getPassword())));
                System.out.println("THIS IS WHAT CAME BACK" + validID);

                if(validID.equals("USER HAS ALREADY REQUESTED VALIDATION NUMBER")){
                    System.out.println("USER HAS ALREADY REQUESTED VALIDATION NUMBER");
                    //print in GUI

                }
                else if(validID.equals("INVALID USERNAME/PASSWORD")){
                    System.out.println("INVALID USERNAME/PASSWORD");
                    //print in GUI
                }
                else if(!validID.equals("00000000")){
                    System.out.println("LOGGED IN");
                    String idnum = nextSessionID();
                    JFrame frame2 = new loggedin("ohyeah", validID, idnum);
                    frame2.setSize(300, 150);
                    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    frame.setVisible(false);
                    frame2.setVisible(true);
                }
                else{
                    System.out.println("VALIDATION FAILED");
                }

            }
        });  
		
	}
    
    
    public static String CLAconn(voterID voterID){
        String validation = "00000000";

        try{
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 25640);

            InputStream inputstream = sslsocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            ObjectOutputStream objectOut = new ObjectOutputStream(sslsocket.getOutputStream());
            objectOut.writeObject(voterID);

            System.out.println("wtf");
            validation = bufferedreader.readLine();
            System.out.println("HERE IS VALIDATION NUMBER "+ validation);
            sslsocket.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return validation;
    }

    public static String CTFconn(String voted, String validation, String idnum, int option){

        try{
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 23333);


            if(option == 1){
                OutputStream outputstream = sslsocket.getOutputStream();
                OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
                BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

                bufferedwriter.write(idnum + " " + validation + " " + voted);
                bufferedwriter.flush();
            }

            if(option == 2){
                ObjectInputStream objectIn = new ObjectInputStream(sslsocket.getInputStream());




            }



        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                frame = new JFrame("Demo application");
                frame.setSize(300, 150);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel panel = new JPanel();
                frame.add(panel);
                placeComponents(panel);


                frame.setVisible(true);

            }
        });

    }


}
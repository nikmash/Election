package SecureVoting;


import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

//Initial voter J-Frame and also execution of voter connection to the CLA then the CTF
//Threads are not needed because connections are temporary.

public class voter {
	
	public static JFrame frame;

    public static HashMap<String, String> results;
    public static HashMap<String, String> validID;

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

            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed


                String validID = CLAconn(new voterID(userText.getText(), new String(passwordText.getPassword())));
                System.out.println("THIS IS WHAT CAME BACK " + validID);

                if (validID.equals("USER HAS ALREADY REQUESTED VALIDATION NUMBER")) {
                    System.out.println("USER HAS ALREADY REQUESTED VALIDATION NUMBER");
                    JOptionPane.showMessageDialog(frame, "USER HAS ALREADY REQUESTED VALIDATION NUMBER");
                    //print in GUI

                } else if (validID.equals("INVALID USERNAME/PASSWORD")) {

                    JOptionPane.showMessageDialog(frame, "INVALID USERNAME/PASSWORD");
                    //print in GUI
                } else if (!validID.equals("00000000")) {
                    System.out.println("LOGGED IN");
                    JOptionPane.showMessageDialog(frame, "LOGGED IN");
                    String idnum = nextSessionID();
                    JFrame frame2 = new loggedin("ohyeah", idnum, validID);
                    frame2.setSize(300, 150);
                    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    frame.setVisible(false);
                    frame2.setVisible(true);
                } else {
                    System.out.println("VALIDATION FAILED");
                    JOptionPane.showMessageDialog(frame, "VALIDATION FAILED");

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

                System.out.println(idnum + " " + validation + " " + voted);
                JOptionPane.showMessageDialog(frame, "This is your id number: " + idnum);

                bufferedwriter.write(idnum + " " + validation + " " + voted + '\n');
                bufferedwriter.flush();
            }

            if(option == 2){

                OutputStream outputstream = sslsocket.getOutputStream();
                OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
                BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

                bufferedwriter.write("SENDTABLE" + '\n');
                bufferedwriter.flush();

                ObjectInputStream objectIn = new ObjectInputStream(sslsocket.getInputStream());
                results = (HashMap <String, String>) objectIn.readObject();
                validID = (HashMap<String, String>) objectIn.readObject();



                //HERE IS THE RESULTS

            }
            sslsocket.close();


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
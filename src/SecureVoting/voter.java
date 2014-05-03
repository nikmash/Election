package SecureVoting;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.io.*;


public class voter {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new MainFrame("Election Voting");
                frame.setSize(500, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
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

            ObjectOutputStream objectout = new ObjectOutputStream(sslsocket.getOutputStream());
            objectout.writeObject(voterID);

            validation = bufferedreader.readLine();

        } catch (Exception e){
            e.printStackTrace();
        }

        return validation;
    }

}
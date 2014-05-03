package SecureVoting;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;


public class CLA {
    private static SecureRandom random = new SecureRandom();

    private static String nextSessionID(){
        return new BigInteger(130, random).toString(32);
    }

    private static SSLSocket initiateServerConnection(int port) throws Exception{
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
        SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
        return sslsocket;
    }

    public static void main (String[] args){
        HashMap<voterID, String> validnumbers = new HashMap<voterID, String>();
        HashMap<String, String> trusteduser = new HashMap<String, String>();


        try{

            SSLSocket serversocketVoter = initiateServerConnection(25640);

            ObjectInputStream objectin = new ObjectInputStream(serversocketVoter.getInputStream());

            OutputStream outputstream = serversocketVoter.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

            voterID voterID = (voterID)objectin.readObject();

            if(validnumbers.get(voterID) == null){
                String validation = nextSessionID();
                validnumbers.put(voterID, validation);
                bufferedwriter.write(validation);
                bufferedwriter.flush();

                SSLSocket socketCTF = initiateServerConnection(24560);

                OutputStream outputCTF = socketCTF.getOutputStream();
                OutputStreamWriter outputCTFwriter = new OutputStreamWriter(outputCTF);
                BufferedWriter CTFwriter = new BufferedWriter(outputCTFwriter);



            }

            else{
                bufferedwriter.write("You have already voted.");
                bufferedwriter.flush();
            }


        } catch (Exception e){
            e.printStackTrace();
        }

    }
}


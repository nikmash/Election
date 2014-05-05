package SecureVoting;

import jBCrypt.BCrypt;

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

        String user = "Nikhil";
        String pass = "123456";

        String hashed = BCrypt.hashpw(pass, BCrypt.gensalt());

        trusteduser.put(user, hashed);

        try{

            SSLSocket socketCTF = initiateServerConnection(24560);
            SSLSocket serversocketVoter = initiateServerConnection(25640);

            ObjectInputStream objectin = new ObjectInputStream(serversocketVoter.getInputStream());

            OutputStream outputstream = serversocketVoter.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

            voterID voterID = null;
            while((voterID = (voterID)objectin.readObject()) != null){

                System.out.println(voterID.user);
                System.out.println(voterID.pass);

                if(BCrypt.checkpw(voterID.pass, trusteduser.get(voterID.user))){
                    if(validnumbers.containsKey(voterID)){
                            bufferedwriter.write("USER HAS ALREADY REQUESTED VALIDATION NUMBER");
                            bufferedwriter.flush();

                    }
                    else{
                        System.out.println("FUCK YEAH");
                        String validation = nextSessionID();
                        validnumbers.put(voterID, validation);
                        System.out.println("VALIDAITON NUMBER " + validation);
                        bufferedwriter.write(validation + '\n');
                        bufferedwriter.flush();



                        OutputStream outputCTF = socketCTF.getOutputStream();
                        OutputStreamWriter outputCTFwriter = new OutputStreamWriter(outputCTF);
                        BufferedWriter CTFwriter = new BufferedWriter(outputCTFwriter);
                        CTFwriter.write(validation);
                        CTFwriter.flush();

                        System.out.println("FUCK YEAH 2");
                    }

                }
                else{
                    bufferedwriter.write("INVALID USERNAME/PASSWORD");
                    bufferedwriter.flush();
                }
            System.out.println("FUCK YEAH 3");


            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}


package SecureVoting;

import jBCrypt.BCrypt;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.util.HashMap;

public class CLA {
    public static HashMap<String, String> validnumbers = new HashMap<String, String>();
    public static HashMap<String, String> trusteduser = new HashMap<String, String>();

    public static void main (String[] args){
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serversocketCTF = null;
        SSLServerSocket serversocketVoter = null;

        String user = "Nikhil";
        String pass = "123456";

        String hashed = BCrypt.hashpw(pass, BCrypt.gensalt());

        trusteduser.put(user, hashed);

        try{
            serversocketCTF = (SSLServerSocket) sslserversocketfactory.createServerSocket(24560);
            SSLSocket socketCTF = (SSLSocket) serversocketCTF.accept();
            serversocketVoter = (SSLServerSocket) sslserversocketfactory.createServerSocket(25640);

            while(true){
                SSLSocket socketVoter = (SSLSocket) serversocketVoter.accept();
                thread2 thread = new thread2(socketCTF, socketVoter);
                thread.start();
            }

        } catch(Exception e){
            e.printStackTrace();
        }


    }
}
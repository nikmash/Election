package SecureVoting;

import jBCrypt.BCrypt;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.util.HashMap;

//CLA program authenticates users and provide them with a random validation number.

public class CLA {
    public static HashMap<String, String> validnumbers = new HashMap<String, String>();
    public static HashMap<String, String> trusteduser = new HashMap<String, String>();

    public static void main (String[] args){
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serversocketCTF = null;
        SSLServerSocket serversocketVoter = null;

        String user = "Nikhil";
        String pass = "123456";
        String user1 = "James";
        String pass1 = "123456";
        String user2 = "Bob";
        String pass2 = "123456";
        String user3 = "Roger";
        String pass3 = "123456";
        String user4 = "Kevin";
        String pass4 = "123456";
        String user5 = "Jake";
        String pass5 = "123456";
        String user6 = "Peter";
        String pass6 = "123456";

        String hashed = BCrypt.hashpw(pass, BCrypt.gensalt());
        String hashed1 = BCrypt.hashpw(pass1, BCrypt.gensalt());
        String hashed2 = BCrypt.hashpw(pass2, BCrypt.gensalt());
        String hashed3 = BCrypt.hashpw(pass3, BCrypt.gensalt());
        String hashed4 = BCrypt.hashpw(pass4, BCrypt.gensalt());
        String hashed5 = BCrypt.hashpw(pass5, BCrypt.gensalt());
        String hashed6 = BCrypt.hashpw(pass6, BCrypt.gensalt());


        trusteduser.put(user, hashed);
        trusteduser.put(user1,hashed1);
        trusteduser.put(user2,hashed2);
        trusteduser.put(user3,hashed3);
        trusteduser.put(user4,hashed4);
        trusteduser.put(user5,hashed5);
        trusteduser.put(user6,hashed6);

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
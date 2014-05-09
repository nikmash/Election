package SecureVoting;


import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.util.HashMap;

//CTF program which uses thread1and voterThread in order to connect to CLA and keep a connection open
//and also keeps opening connections for voters to connect to the CTF

public class CTF {

    public static HashMap<String, String> validation = new HashMap<String, String>();
    public static HashMap<String, String> results = new HashMap<String, String>();


    public static void main(String[] args){

        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serversocketVoter = null;


        try{


            //System.setProperty("Djavax.net.ssl.keyStore", "myserverkeystore");
            //System.setProperty("Djavax.net.ssl.keyStorePassword", "123456");
            serversocketVoter = (SSLServerSocket) sslserversocketfactory.createServerSocket(23333);

            while(true){
                thread1 thread = new thread1();
                thread.start();
                SSLSocket socketVoter = (SSLSocket) serversocketVoter.accept();
                voterThread voterthread = new voterThread(socketVoter);
                voterthread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }




        //CLAconn(24560, validation);
        //voter(23333, validation, results);

    }


}

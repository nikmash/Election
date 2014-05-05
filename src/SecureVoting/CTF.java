package SecureVoting;


import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CTF {

    public static HashMap<String, String> validation = new HashMap<String, String>();
    public static HashMap<String, String> results = new HashMap<String, String>();



    private static SSLSocket initiateServerSocket(int port) throws Exception{
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
        SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
        return sslsocket;
    }

    private static SSLSocket initiateClientSocket(int port) throws Exception{
        System.setProperty("Djavax.net.ssl.trustStore", "myserverkeystore");
        System.setProperty("Djavax.net.ssl.trustStorePassword", "123456");
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
        return sslsocket;
    }

    private static void CLAconn(int port, HashMap<String, String> validation){
        try{
            SSLSocket clientsocketCLA = initiateClientSocket(port);

            InputStream inputstream = clientsocketCLA.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            String validnum = null;
            while((validnum = bufferedreader.readLine()) != null){
                System.out.println(validation);
                validation.put(validnum, null);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void voter(int port, HashMap<String, String> validation, HashMap<String, String> results){
        try{
            SSLSocket serversocketVoter = initiateServerSocket(port);

            InputStream inputstream = serversocketVoter.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            String string = null;
            while((string = bufferedreader.readLine()) != null){
                 if(string.equals("SENDTABLE")){
                     ObjectOutputStream objectout = new ObjectOutputStream(serversocketVoter.getOutputStream());
                     objectout.writeObject(results);

                 }

                 else{
                     StringTokenizer st = new StringTokenizer(string);
                     String idnum = st.nextToken();
                     String validnum = st.nextToken();
                     String voted = st.nextToken();

                     if(validation.containsKey(validnum)){
                         if(validation.get(validnum) == null){
                             validation.put(validnum, idnum);
                             results.put(idnum, voted);
                             System.out.println(idnum + " " + voted);
                         }
                         else{

                             System.out.println("YOU ALREADY VOTED");
                         }
                     }
                     else{
                         System.out.println("INVALID VALIDATION NUMBER");
                     }
                 }

            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

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
                System.out.println("FUCK YEAH BLAH");
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

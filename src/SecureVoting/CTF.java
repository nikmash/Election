package SecureVoting;


import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CTF {

    private static SSLSocket initiateServerSocket(int port) throws Exception{
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
        SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
        return sslsocket;
    }

    private static SSLSocket initiateClientSocket(int port) throws Exception{
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
            validnum = bufferedreader.readLine();
            validation.put(validnum, null);


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
        HashMap<String, String> validation = new HashMap<String, String>();
        HashMap<String, String> results = new HashMap<String, String>();

        CLAconn(24560, validation);
        voter(23333, validation, results);

    }


}

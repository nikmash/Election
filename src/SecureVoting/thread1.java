package SecureVoting;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class thread1 extends Thread{
    private SSLSocket clientsocketCLA;

	private static SSLSocket initiateClientSocket(int port) throws Exception{
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);
        return sslsocket;
    }
	
	
	public void run(){

		try{
            SSLSocket clientsocketCLA = initiateClientSocket(24560);

            InputStream inputstream = clientsocketCLA.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            String validnum = null;

            while((validnum = bufferedreader.readLine()) != null){
                System.out.println("COME HERE");
                System.out.println("VALIDATION NUMBER " + validnum);
                CTF.validation.put(validnum, null);
            }
            clientsocketCLA.close();

        } catch(Exception e){
            e.printStackTrace();
        }
	}
	
	
}

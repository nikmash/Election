package SecureVoting;


import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

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



    public static void main(String[] args){
        try{
            SSLSocket clientsocketCLA = initiateClientSocket(24560);


        } catch(Exception e){
            e.printStackTrace();
        }
    }


}

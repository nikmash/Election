package SecureVoting;

import jBCrypt.BCrypt;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

//Thread for CLA starting connections between voter and CTF. All ServerSocket SSL connections.

public class thread2 extends Thread{

	private static SecureRandom random = new SecureRandom();

    private SSLSocket socketVoter;
    private SSLSocket socketCTF;

    public thread2(SSLSocket socketCTF, SSLSocket socketVoter) throws IOException {
        this.socketVoter = socketVoter;
        this.socketCTF = socketCTF;
    }
	
    private static String nextSessionID(){
        return new BigInteger(130, random).toString(32);
    }
    
	public void run(){


        try{
            BufferedWriter CTFout = new BufferedWriter(new OutputStreamWriter(socketCTF.getOutputStream()));
            ObjectInputStream objectin = new ObjectInputStream(socketVoter.getInputStream());

            OutputStream outputstream = socketVoter.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

            voterID voterID = null;
            if((voterID = (voterID)objectin.readObject()) != null){

                System.out.println(voterID.user);
                System.out.println(voterID.pass);

                if(BCrypt.checkpw(voterID.pass, CLA.trusteduser.get(voterID.user))){
                    if(CLA.validnumbers.containsKey(voterID.user)){
                            bufferedwriter.write("USER HAS ALREADY REQUESTED VALIDATION NUMBER" + '\n');
                            bufferedwriter.flush();

                    }
                    else{
                        System.out.println("FUCK YEAH");
                        String validation = nextSessionID();
                        CLA.validnumbers.put(voterID.user, validation);
                        System.out.println("VALIDAITON NUMBER " + validation);
                        bufferedwriter.write(validation + '\n');
                        bufferedwriter.flush();

                       // OutputStream outputCTF = socketCTF.getOutputStream();
                       // OutputStreamWriter outputCTFwriter = new OutputStreamWriter(outputCTF);
                       // BufferedWriter CTFwriter = new BufferedWriter(outputCTFwriter);

                        CTFout.write(validation + '\n');
                        CTFout.flush();

                        System.out.println("FUCK YEAH 2");
                    }

                }
                else{
                    bufferedwriter.write("INVALID USERNAME/PASSWORD" + '\n');
                    bufferedwriter.flush();
                }
            System.out.println("FUCK YEAH 3");


            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
	
}

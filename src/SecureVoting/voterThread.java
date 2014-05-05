package SecureVoting;


import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class voterThread extends Thread{
    private SSLSocket socket;

    public voterThread(SSLSocket socket){
        this.socket = socket;
    }

    public void run(){
         try{
             BufferedReader Voterin = new BufferedReader(new InputStreamReader(socket.getInputStream()));

             String string = null;

             System.out.println("FUCK YEAH");
             if((string = Voterin.readLine()) != null){
                System.out.println("FUCK YEAH 2");
                if(string.equals("SENDTABLE")){
                    //SEND TABLE
                }
                else{
                    StringTokenizer st = new StringTokenizer(string);
                    String idnum = st.nextToken();
                    String validnum = st.nextToken();
                    String voted = st.nextToken();

                    if(CTF.validation.containsKey(validnum)){
                        if(CTF.validation.get(validnum) == null){
                            CTF.validation.put(validnum, idnum);
                            CTF.results.put(idnum, voted);
                            System.out.println("Your id is " + idnum + " you voted for " + voted);
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
         }catch(Exception e){
             e.printStackTrace();
         }
    }


}

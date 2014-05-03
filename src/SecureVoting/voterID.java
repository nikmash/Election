package SecureVoting;

import java.io.Serializable;

public class voterID implements Serializable {
    String user;
    String pass;
    String ID;
    public voterID(String name){
        user = name;
        ID = null;
    }
}

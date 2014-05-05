package SecureVoting;

import java.io.Serializable;

public class voterID implements Serializable {
    String user;
    String pass;
    String ID;
    public voterID(String name, String pass){
        user = name;
        this.pass = pass;
        ID = null;
    }
}

package SecureVoting;

import java.io.Serializable;

//Voter Identification Object Stores User, Pass, and validation number.

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

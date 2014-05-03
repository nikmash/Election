package SecureVoting;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        super(title);

        //Set layout manager
        setLayout(new SpringLayout());

        //Create Swing component
        final JTextField textField = new JTextField("User Name", 20);
        final JPasswordField passwordField = new JPasswordField("Password", 20);
        JButton button = new JButton("Login");

        //Add Swing components to content pane
        Container container = getContentPane();
        container.add(textField, SpringLayout.NORTH);
        container.add(passwordField, SpringLayout.SOUTH);

        //Add Behavior

        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                voterID voterID = new voterID("Nikhil");
                voterID.ID = voter.CLAconn(voterID);
            }
        });


    }
}
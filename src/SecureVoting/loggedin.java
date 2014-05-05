package SecureVoting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static SecureVoting.voter.CTFconn;

public class loggedin extends JFrame{

	public static JFrame frame;
	

    public loggedin(String title, final String idnum, final String validation){
        super(title);

		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		this.add(panel);
		
		panel.setLayout(null);

		JLabel passwordLabel = new JLabel("Candidates");
		passwordLabel.setBounds(10, 10, 80, 25);
		panel.add(passwordLabel);

		String[] options = { "Option1", "Option2", "Option3", "Option4", "Option15" };
        final JComboBox comboBox = new JComboBox(options);
		comboBox.setBounds(100, 10, 160, 25);
		panel.add(comboBox);

		JButton votebutton = new JButton("Vote");
		votebutton.setBounds(10, 80, 80, 25);
		panel.add(votebutton);
		votebutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                CTFconn(comboBox.getSelectedItem().toString(), validation, idnum, 1);
            	
            }
        });  
		
		
		JButton logoutbutton = new JButton("Logout");
		logoutbutton.setBounds(180, 80, 80, 25);
		panel.add(logoutbutton);
		logoutbutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	
            }
        });  


    }
	
}

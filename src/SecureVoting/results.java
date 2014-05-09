package SecureVoting;

import javax.swing.*;

//J - Frame Pane for voting results

public class results extends JFrame{

	public static JFrame frame;
	

    public results(String title){
        super(title);

		this.setSize(400, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		this.add(panel);
		
		panel.setLayout(null);

		JLabel userLabel = new JLabel("Results:");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		final JTextArea userText = new JTextArea();
		userText.setBounds(100, 10, 300, 750);
		panel.add(userText);




        int option1=0;
        int option2=0;
        int option3=0;
        int option4=0;
        int option15=0;

		for(String string: voter.results.keySet()){
               String key = string;
               String value = voter.results.get(string);
               if (value.equals("Option1")){
                   option1++;
               }
                if (value.equals("Option2")){
                   option2++;
                }
                if (value.equals("Option3")){
                   option3++;
                }
                if (value.equals("Option4")){
                   option4++;
                }
                if (value.equals("Option15")){
                   option15++;
                }
        }
        userText.append("Option 1: " + option1 + "\n");
        userText.append("Option 2: " + option2 + "\n");
        userText.append("Option 3: " + option3 + "\n");
        userText.append("Option 4: " + option4 + "\n");
        userText.append("Option 15: " + option15 + "\n \n");
        userText.append("Users who voted: \n \n");


        for(String string: voter.results.keySet()){
            String key = string;
            String value = voter.results.get(string);
            if (!value.equals(null)){
                userText.append(key + " " + value + "\n");
            }
        }

        userText.append("\n");

        userText.append("Users who registered but did not vote: \n \n");


        for(String string: voter.validID.keySet()){
            String key = string;
            String value = voter.validID.get(string);
            if (value==null){
                userText.append(key + "\n");
            }
        }



    }
	
}
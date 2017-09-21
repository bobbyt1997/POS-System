import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;

public class LoginWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a login window
	 */
	public LoginWindow(){
		JFrame frame = new JFrame("Nail Tech POS System");
		
		//create elements that will go into the login window
		JButton loginBtn = new JButton("Log In");
		JTextField usernameField = new JTextField(25);
		JPasswordField passwordField = new JPasswordField(25);
		JLabel usernameLbl = new JLabel("Username: ");
		JLabel passwordLbl = new JLabel("Password: ");
		
		frame.setVisible(true);
		frame.setSize(500, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		//Create a username and password textbox with a login button at the bottom
		frame.add(usernameLbl);
		frame.add(usernameField);
		frame.add(passwordLbl);
		frame.add(passwordField);
		frame.add(loginBtn);
		
		//position and size each element
		usernameLbl.setBounds(140, 70, 80, 30);
		usernameField.setBounds(210, 70, 150, 30);
		passwordLbl.setBounds(140, 110, 80, 30);
		passwordField.setBounds(210, 110, 150, 30);
		loginBtn.setBounds(210, 150, 100, 30);
		
		//create a listener that does something when the button is clicked
		loginBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//check if credentials entered matches any data in the database
				if ( checkCredentials(usernameField.getText(), passwordField.getPassword()) == true ){
					SwingUtilities.invokeLater(new Runnable() {
			            @Override
			            public void run() {
			            	//Start a new instance of POS and dispose of the login window
			                new POS();
			                dispose();
			            }
			        });
				}
				else{
					//if either usename or password is incorrect, notify the user
					JOptionPane.showMessageDialog(frame, "Username or Password INCORRECT. Please try again.");
				}
			}
        });
		
		
	}
	
	/**
	 * 
	 * @param username username entered by the user
	 * @param password password entered by the user retrieved as an array of chars
	 * @return true if credentials matches the data in the database and false otherwise
	 */
	public static boolean checkCredentials(String username, char[] password){
		String correctPass = "dude";
		String correctUser = "bobby";
		
		if (username.equals(correctUser)){
			if (Arrays.equals(password, correctPass.toCharArray())){
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginWindow();
            }
        });
		
	}

}

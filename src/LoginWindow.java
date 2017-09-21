
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class LoginWindow extends JFrame{

	private static final long serialVersionUID = 1L;

	public LoginWindow(){
		JFrame frame = new JFrame("Nail Tech POS System");
		
		//create elements that will go into the login window
		JButton loginBtn = new JButton("Log In");
		JTextField usernameField = new JTextField(25);
		JTextField passwordField = new JTextField(25);
		JLabel usernameLbl = new JLabel("Username: ");
		JLabel passwordLbl = new JLabel("Password: ");
		
		frame.setVisible(true);
		frame.setSize(500, 200);
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
		usernameLbl.setBounds(140, 20, 80, 30);
		usernameField.setBounds(210, 20, 150, 30);
		passwordLbl.setBounds(140, 60, 80, 30);
		passwordField.setBounds(210, 60, 150, 30);
		loginBtn.setBounds(210, 100, 100, 30);
		
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

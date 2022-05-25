package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color; 

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import backend.PasswordHashing;
import backend.Queries;

public class LoginWindow extends JDialog implements ActionListener{

	boolean authorized; 
	String username, password; 
	JTextArea nameArea; 
	JPasswordField passwordField; 
	
	LoginWindow()
	{
		authorized = false; 
		

		setSize(new Dimension(300,150)); 
		setModalityType(JDialog.DEFAULT_MODALITY_TYPE); 
		setLayout(null); 
		setResizable(false); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		JLabel nameLabel, passwordLabel; 
		JButton loginButton; 
		
		nameLabel = new JLabel("Username"); 
		passwordLabel = new JLabel("Password"); 
		nameLabel.setBounds(15, 7, 100, 25); 
		passwordLabel.setBounds(15, 42, 100, 25); 
		
		nameArea = new JTextArea(); 
		nameArea.setEditable(true);
		nameArea.setBorder(BorderFactory.createLineBorder(Color.black));
		nameArea.setBounds(125, 7, 150, 25); 
		
		passwordField = new JPasswordField(); 
		passwordField.setEditable(true);
		passwordField.setBounds(125, 42, 150, 25); 
		
		loginButton = new JButton("log in"); 
		loginButton.setBounds(175, 80, 100, 25); 
		loginButton.addActionListener(this);
		
		add(nameLabel); 
		add(passwordLabel); 
		add(nameArea); 
		add(passwordField); 
		add(loginButton); 
		setVisible(true); 
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		PasswordHashing ph = new PasswordHashing(); 
		username = nameArea.getText(); 
		password = ph.convertPassword(passwordField.getPassword().toString()); 
		System.out.println(username + " " + password);
	}
	
}

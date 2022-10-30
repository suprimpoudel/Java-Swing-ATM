

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AccountLogin {
	static void accountLogin(JDBCConnection jdbc, JFrame frame) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel enter_email, enter_password;
		JPasswordField passwordInput;
		JTextField emailInput;
		JButton cancel, login;
		
		enter_email = new JLabel("Enter your email address");
		enter_email.setBounds(40, 50, 300, 30);
		
		emailInput = new JTextField("");
		emailInput.setBounds(40, 100, 200, 30);
		
		enter_password = new JLabel("Enter password");
		enter_password.setBounds(40, 150, 300, 30);
		
		passwordInput = new JPasswordField("");
		passwordInput.setBounds(40, 200, 200, 30);
		
		cancel = new JButton("Cancel");
		cancel.setForeground(Color.white);
		cancel.setBackground(Color.red);
		cancel.setBounds(40, 250, 100, 30);
		
		login = new JButton("Login");
		login.setForeground(Color.white);
		login.setBackground(Color.green);
		login.setBounds(360, 250, 100, 30);
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				jdbc.closeConnection();
				DefaultPage.startActivity();
			}});
		
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String email = emailInput.getText().toString().toLowerCase().trim();
				String pass = new String(passwordInput.getPassword());
				int emailValue = jdbc.getDataInt("SELECT COUNT(*) FROM user WHERE email='"+email+"'");
				if(emailValue == 1) {
					String passwordStored = jdbc.getData("SELECT password FROM user WHERE email='"+email+"'");
					if(pass.equals(passwordStored)) {
						frame.getContentPane().removeAll();
			        	frame.repaint();
			        	panel.removeAll();
			        	AccountDetails.accountDetailsPane(jdbc, frame, email);
					} else {
						showError("Either email or password is wrong");
					}
				} else {
					showError("Either email or password is wrong");
				}
			}});
		
		panel.add(enter_email);
		panel.add(emailInput);
		panel.add(enter_password);
		panel.add(passwordInput);
		panel.add(cancel);
		panel.add(login);		
		
		frame.add(panel);
		
		frame.setSize(500, 350);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	static void showError(String message) {
		JOptionPane.showMessageDialog(null,
			    message,
			    "Error",
			    JOptionPane.WARNING_MESSAGE);	
	}
}

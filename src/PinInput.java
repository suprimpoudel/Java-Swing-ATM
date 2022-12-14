
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import java.util.Base64;
import java.util.Base64.Decoder;

public class PinInput {
	
	static boolean doesPinMatch(String pass, JDBCConnection jdbc, String atmNumber) {
		boolean status;
		String userPass = jdbc.getData("SELECT pin FROM atm_users WHERE atm_number = '"+atmNumber+"'");
		Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(userPass);
        String decodedUserPass = new String(bytes);
        if(pass.equals(decodedUserPass)) {
        	status = true;
        } else {
        	status = false;
        }
		return status;
	}
	
	static boolean isBlocked(String atmNumber, JDBCConnection jdbc) {
		boolean blocked;
		int status = jdbc.getDataInt("SELECT invalid FROM invalid_tries WHERE atm_number = '"+atmNumber+"'");
		if(status == 3) {
			blocked = true;
		} else {
			blocked = false;
		}
		return blocked;
	}
	
	static void pinInputPanel(JDBCConnection jdbc, String atmNumber, JFrame frame) {
		JPanel panel = new JPanel();
		//MainFrame frame = new MainFrame();
		JButton verifyBtn, forgotButton;
		JLabel enter_atm_pin;
		JPasswordField passwordPinInput;
		Font  normal_font  = new Font(Font.SERIF, Font.BOLD,  15);
		
		panel.setLayout(null);
		
		enter_atm_pin = new JLabel("Please enter your PIN:",  SwingConstants.CENTER);
		enter_atm_pin.setFont(normal_font);
		enter_atm_pin.setBounds(150, 130, 300, 30);
		
		passwordPinInput = new JPasswordField("");
		passwordPinInput.setBounds(150, 180, 300, 30);
		
		verifyBtn = new JButton("Verify");
		verifyBtn.setForeground(Color.white);
		verifyBtn.setBackground(Color.green);
		verifyBtn.setBounds(250, 230, 100, 30);
		
		forgotButton = new JButton("Forgot PIN?");
		forgotButton.setForeground(Color.white);
		forgotButton.setBackground(Color.red);
		forgotButton.setBounds(250, 300, 100, 30);
		
		panel.add(enter_atm_pin);
		panel.add(passwordPinInput);
		panel.add(verifyBtn);
		panel.add(forgotButton);
		
		
		//Pin only numbers with 4 digit setting
		passwordPinInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {		
				if(passwordPinInput.getPassword().length >= 4 && !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }
                char ch = evt.getKeyChar();

                if(!Character.isDigit(ch)) {
                	evt.consume();
                }
            }
		});
		
		forgotButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	ForgotPin.forgotPinPanel(atmNumber, jdbc, frame);
			}});
		
		//Verify button click handler
		verifyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pass = new String(passwordPinInput.getPassword());
		        boolean matchingPin = doesPinMatch(pass, jdbc, atmNumber);
		        
		        if(passwordPinInput.getPassword().length == 4) {
		        	if(!isBlocked(atmNumber, jdbc)) {
			        	if(matchingPin) {
				        	jdbc.executeQuery("UPDATE invalid_tries SET invalid = 0 WHERE atm_number = '"+atmNumber+"'");
				        	frame.getContentPane().removeAll();
				        	frame.repaint();
				        	panel.removeAll();
				        	ATMActions.atmActionPanel(jdbc, atmNumber, frame);
				        } else {
				        	JOptionPane.showMessageDialog(frame,
								    "Invalid Pin",
								    "Error",
								    JOptionPane.WARNING_MESSAGE);
				        	passwordPinInput.setText("");
				        	jdbc.executeQuery("UPDATE invalid_tries SET invalid = invalid + 1 WHERE atm_number = '"+atmNumber+"'");
				        }
			        } else {
			        	JOptionPane.showMessageDialog(frame,
							    "Your ATM has been blocked\nYou can unblock your ATM by clicking \"Unblock Card\" button.",
							    "Error",
							    JOptionPane.WARNING_MESSAGE);
			        	passwordPinInput.setText("");
			        }
		        }
			}});
		
		frame.add(panel);
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

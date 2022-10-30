

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ForgotPin {
	static void forgotPinPanel(String atmNumber, JDBCConnection jdbc, JFrame frame) {
		JLabel guide_text, enter_email, title;
		JButton sendOtp, cancel;
		JPanel panel = new JPanel();
		JTextField emailEnterInput;
		Font  title_font  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);
		
		panel.setLayout(null);
		
		title = new JLabel("Forgot Pin", SwingConstants.CENTER);
		title.setFont(title_font);
		title.setForeground(Color.red);
		title.setBounds(150, 50, 300, 50);
		
		guide_text = new JLabel("ATM Number: " + atmNumber);
		guide_text.setBounds(40, 130, 300, 50);
		
		enter_email = new JLabel("Please enter email address associated to this account: ");
		enter_email.setForeground(Color.BLUE);
		enter_email.setBounds(40, 180, 500, 50);
		
		emailEnterInput = new JTextField("");
		emailEnterInput.setBounds(40, 230, 400, 30);
		
		sendOtp = new JButton("Send OTP");
		sendOtp.setForeground(Color.white);
		sendOtp.setBackground(Color.green);
		sendOtp.setBounds(460, 230, 100, 30);
		
		sendOtp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailEnterInput.getText().toString().trim();
				if(email.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "Email not valid",
						    "Error",
						    JOptionPane.WARNING_MESSAGE);
				} else {
					String getEmail = jdbc.getData("SELECT email FROM user WHERE user_id=(SELECT user_id FROM atm_users WHERE atm_number ='"+atmNumber+"')");
					if(getEmail.equals(email) ) {
						Random rand = new Random();
						String defCode = "";
						for (int i = 0; i < 6; i++) {
						        int n = rand.nextInt(10) + 0;
						        defCode += Integer.toString(n);
						}
						String name = getName(atmNumber, jdbc);
						boolean status = SendMail.mailSend(getEmail, name, defCode);
						if(status) {
							JOptionPane.showMessageDialog(frame,
								    "OTP code sent to email",
								    "Email Sent",
								    JOptionPane.INFORMATION_MESSAGE);
							frame.getContentPane().removeAll();
							frame.repaint();
							long mailSentTime = System.currentTimeMillis();
							EnterOtp.enterOtpScreenPane(atmNumber, jdbc, frame, defCode, mailSentTime);
						} else {
							JOptionPane.showMessageDialog(frame,
								    "Error sending email",
								    "Error",
								    JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(frame,
							    "Email doesn't match with the one\nassociated to this acccount",
							    "Error",
							    JOptionPane.WARNING_MESSAGE);
					}
				}
			}});
		
		cancel = new JButton("Cancel");
		cancel.setForeground(Color.white);
		cancel.setBackground(Color.red);
		cancel.setBounds(40, 290, 100, 30);
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.repaint();
				PinInput.pinInputPanel(jdbc, atmNumber, frame);
			}});
		
		panel.add(title);
		panel.add(guide_text);
		panel.add(enter_email);
		panel.add(emailEnterInput);
		panel.add(sendOtp);
		panel.add(cancel);
		
		frame.add(panel);
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	static String getName(String atmNumber, JDBCConnection jdbc) {
		String name = null;
		String firstName = jdbc.getData("SELECT first_name FROM user WHERE user_id = (SELECT user_id FROM atm_users WHERE atm_number = '"+atmNumber+"')");
		String lastName = jdbc.getData("SELECT last_name FROM user WHERE user_id = (SELECT user_id FROM atm_users WHERE atm_number = '"+atmNumber+"')");
		name = firstName + ' ' + lastName;
		return name;
	}
}

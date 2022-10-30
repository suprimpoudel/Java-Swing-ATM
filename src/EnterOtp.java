

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EnterOtp {
	static void enterOtpScreenPane(String atmNumber, JDBCConnection jdbc, JFrame frame, String otpCode, long mailSentTime) {
		JPanel panel = new JPanel();
		JButton verify, cancel;
		JLabel enter_otp;
		JTextField otpNumberInput;
		Font normal_font = new Font(Font.MONOSPACED, Font.BOLD, 15);
		
		panel.setLayout(null);
		
		enter_otp = new JLabel("Enter the 6 digit otp we just sent to your email:", SwingConstants.CENTER);
		enter_otp.setForeground(Color.red);
		enter_otp.setFont(normal_font);
		enter_otp.setBounds(50, 50, 500, 60);
		
		otpNumberInput = new JTextField("");
		otpNumberInput.setBounds(250, 150, 100, 50);
		
		verify = new JButton("Verify");
		verify.setForeground(Color.white);
		verify.setBackground(Color.green);
		verify.setBounds(250, 230, 100, 30);
		
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
		
		panel.add(enter_otp);
		panel.add(otpNumberInput);
		panel.add(verify);
		panel.add(cancel);
		
		verify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String code = otpNumberInput.getText().toString();
				if(code.equals(otpCode)) {
					frame.getContentPane().removeAll();
					frame.repaint();
					long otpEnteredTime = System.currentTimeMillis();
					long minConvert = otpEnteredTime - mailSentTime;
					int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(minConvert);
					if(minutes > 2) {
						JOptionPane.showMessageDialog(frame,
							    "OTP expired",
							    "Error",
							    JOptionPane.WARNING_MESSAGE);
					} else {
						PinChange.pinChangePanel(jdbc, atmNumber, frame, "reset");
					}
				} else {
					JOptionPane.showMessageDialog(frame,
						    "OTP code doesn't match",
						    "Error",
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		otpNumberInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(otpNumberInput.getText().length()>=6&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }
                char ch = evt.getKeyChar();

                if(!Character.isDigit(ch)) {
                	evt.consume();
                }
            }
		});
		
		
		
		frame.add(panel);
		
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

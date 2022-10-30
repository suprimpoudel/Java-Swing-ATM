
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class WelcomeScreenATM {

	static void atmWelcomeScreenPane(JDBCConnection jdbc, JFrame frame) {
		JPanel panel = new JPanel();
		JButton nextBtn, exit;
		JLabel welcome_text, enter_atm_no;
		JTextField atmNumberInput;
		Font  title_font  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);
		Font  normal_font  = new Font(Font.SERIF, Font.BOLD,  15);
		
		panel.setLayout(null);
		
		welcome_text = new JLabel("ATM Service", SwingConstants.CENTER);
		welcome_text.setFont(title_font);
		welcome_text.setBounds(150, 50, 300, 30);
		
		enter_atm_no = new JLabel("Please enter your ATM Card Number:",  SwingConstants.CENTER);
		enter_atm_no.setFont(normal_font);
		enter_atm_no.setBounds(150, 130, 300, 30);
		
		atmNumberInput = new JTextField("");
		atmNumberInput.setBounds(150, 180, 300, 30);
		
		nextBtn = new JButton("Next");
		nextBtn.setForeground(Color.white);
		nextBtn.setBackground(Color.green);
		nextBtn.setBounds(250, 230, 100, 30);
		
		
		panel.add(welcome_text);
		panel.add(enter_atm_no);
		panel.add(atmNumberInput);
		panel.add(nextBtn);
		
		//Make only input numbers in ATM Number Input
		atmNumberInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(atmNumberInput.getText().length()>=6&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }
                char ch = evt.getKeyChar();

                if(!Character.isDigit(ch)) {
                	evt.consume();
                }
            }
		});
		
		//Next Button click handler
		nextBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(atmNumberInput.getText().length() != 6) {
					atmNumberInput.setText("");
					JOptionPane.showMessageDialog(frame,
						    "Invalid ATM Card number",
						    "Error",
						    JOptionPane.WARNING_MESSAGE);
					atmNumberInput.setText("");
					atmNumberInput.requestFocus();
				} else {
					int count = 0;
					try {
						count = jdbc.ifExists("SELECT COUNT(*) FROM atm_users WHERE atm_number = '"+atmNumberInput.getText()+"'");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					if (count == 1) {
						frame.getContentPane().removeAll();
						frame.repaint();
						PinInput.pinInputPanel(jdbc, atmNumberInput.getText().toString(), frame);				
					} else {
						atmNumberInput.setText("");
						JOptionPane.showMessageDialog(frame,
							    "Card Number doesn't exists",
							    "Error",
							    JOptionPane.WARNING_MESSAGE);
						atmNumberInput.requestFocus();
					}
				}
			}});
		
		exit = new JButton("Exit");
		exit.setForeground(Color.white);
		exit.setBackground(Color.red);
		exit.setBounds(40, 300, 100, 30);
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jdbc.closeConnection();
				frame.dispose();
				DefaultPage.startActivity();
			}});
		
		panel.add(exit);
		
		frame.add(panel);
		
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

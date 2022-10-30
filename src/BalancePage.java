
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BalancePage {
	static void balancePagePanel(JDBCConnection jdbc, String atmNumber, JFrame frame) {
		JPanel panel = new JPanel();
		JButton back;
		JLabel welcome_user, balance;
		Font  normal_font  = new Font(Font.SERIF, Font.BOLD,  20);
		Font balance_font = new Font(Font.MONOSPACED, Font.BOLD, 25);

		panel.setLayout(null);
		
		welcome_user = new JLabel("Greetings, Your balance remaining is", SwingConstants.CENTER);
		welcome_user.setFont(normal_font);
		welcome_user.setBounds(100, 150, 400, 35);
		String balanceOfUser = jdbc.getData("SELECT balance FROM user WHERE user_id = (SELECT user_id FROM atm_users WHERE atm_number = '"+atmNumber+"')");
		balance = new JLabel(""+balanceOfUser+".00", SwingConstants.CENTER);
		balance.setFont(balance_font);
		balance.setForeground(Color.red);
		balance.setBounds(100, 200, 400, 60);
		
		back = new JButton("Back");
		back.setForeground(Color.white);
		back.setBackground(Color.green);
		back.setBounds(40, 300, 100, 30);
		
		//back button on click handler
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	ATMActions.atmActionPanel(jdbc, atmNumber, frame);
			}
			
		});
		
		panel.add(welcome_user);
		panel.add(balance);
		panel.add(back);
		
		frame.add(panel);
		
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

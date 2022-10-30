
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DefaultPage{
	
	static void startActivity() {
		MainFrame frame = new MainFrame();
		JDBCConnection jdbc = new JDBCConnection();
		Font  welcome_font  = new Font(Font.SERIF, Font.ITALIC,  30);
		Font  normal_font  = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);
		JPanel panel = new JPanel();
		panel.setLayout(null);
				
		JLabel label = new JLabel("Welcome to Aadim Bank", SwingConstants.CENTER);
		label.setBounds(150, 50, 500, 80);
		label.setFont(welcome_font);
		panel.add(label);
		
		JLabel select_service = new JLabel("Please select a service: ");
		select_service.setBounds(20, 170, 500, 30);
		select_service.setFont(normal_font);
		panel.add(select_service);
		
		
		
		JButton deposit, atm_service, account_opening, account_login;
		
		deposit = new JButton("Deposit Money");
		deposit.setBounds(20, 230, 180, 100);
		panel.add(deposit);
		
		atm_service = new JButton("ATM Service");
		atm_service.setBounds(210, 230, 180, 100);
		panel.add(atm_service);
		
		account_opening = new JButton("Bank Account Opening");
		account_opening.setBounds(400, 230, 180, 100);
		panel.add(account_opening);
		
		account_login = new JButton("Account Login");
		account_login.setBounds(590, 230, 180, 100);
		panel.add(account_login);
		
		deposit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	DepositMoney.depositMoneyPanel(jdbc, frame);	
			}});
		
		atm_service.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	WelcomeScreenATM.atmWelcomeScreenPane(jdbc, frame);			
			}});
		
		account_opening.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	OpenBankAccount.openBankAccountPanel(jdbc, frame);		
			}});
		
		account_login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	AccountLogin.accountLogin(jdbc, frame);
			}});
		
		frame.add(panel);
		
		frame.setResizable(false);
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		startActivity();
	}

}

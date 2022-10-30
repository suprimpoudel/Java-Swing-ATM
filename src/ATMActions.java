

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ATMActions {
	static void atmActionPanel(JDBCConnection jdbc, String atmNumber, JFrame frame) {
		//Definitions
		JPanel panel = new JPanel();
		JButton exit, fastCashBtn, withdrawBtn, miniStatementBtn, balanceEnquiryBtn, pinChange;
		Font  normal_font  = new Font(Font.SERIF, Font.BOLD,  20);

		panel.setLayout(null);
		
		JLabel welcome_user = new JLabel("Select action:", SwingConstants.CENTER);
		welcome_user.setFont(normal_font);
		welcome_user.setBounds(150, 10, 300, 35);
		
		fastCashBtn = new JButton("Fast Cash");
		fastCashBtn.setBounds(40, 80, 200, 30);
		
		withdrawBtn = new JButton("Withdraw");
		withdrawBtn.setBounds(360, 80, 200, 30);
		
		miniStatementBtn = new JButton("Mini Statement");
		miniStatementBtn.setBounds(40, 160, 200, 30);
		
		balanceEnquiryBtn = new JButton("Balance Enquire");
		balanceEnquiryBtn.setBounds(360, 160, 200, 30);
		
		pinChange = new JButton("Pin Change");
		pinChange.setBounds(40, 240, 200, 30);
		
		exit = new JButton("Exit");
		exit.setForeground(Color.white);
		exit.setBackground(Color.red);
		exit.setBounds(360, 240, 200, 30);
		
		balanceEnquiryBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	BalancePage.balancePagePanel(jdbc, atmNumber, frame);
			}});
		
		miniStatementBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Statements.statementPanel(atmNumber, jdbc);
			}});
		
		fastCashBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				//new FastCash(atmNumber, jdbc);
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	FastCash.fashCashPanel(jdbc, atmNumber, frame);
			}});
		
		pinChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	PinChange.pinChangePanel(jdbc, atmNumber, frame, "logged");
			}});
		
		withdrawBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	WithdrawAmount.withdrawAmountPanel(jdbc, atmNumber, frame);
			}});
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
	        	frame.repaint();
	        	panel.removeAll();
	        	WelcomeScreenATM.atmWelcomeScreenPane(jdbc, frame);
			}
			
		});
		
		panel.add(welcome_user);
		panel.add(fastCashBtn);
		panel.add(withdrawBtn);
		panel.add(miniStatementBtn);
		panel.add(balanceEnquiryBtn);
		panel.add(pinChange);
		panel.add(exit);
		
		frame.add(panel);

		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

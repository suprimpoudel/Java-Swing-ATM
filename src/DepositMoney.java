

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class DepositMoney {
	static void depositMoneyPanel(JDBCConnection jdbc, JFrame frame) {
		JPanel panel = new JPanel();
		Font deposit_font = new Font(Font.MONOSPACED, Font.BOLD, 20);
		Font note_font = new Font(Font.DIALOG_INPUT, Font.ITALIC, 10);

		panel.setLayout(null);
		
		frame.setTitle("Deposit Money");
		
		JLabel note, bank_name, deposit_title, recipient_account_number, recipient_name, amount_to_deposit, sender_name, purpose_of_deposit;
		JTextField acc_number, receiver_name_input, amount_to_deposit_input, sender_name_input, purpose_of_deposit_input;
		JButton deposit, cancel;
		
		bank_name = new JLabel("Aadim Bank", SwingConstants.CENTER);
		bank_name.setForeground(Color.red);
		bank_name.setFont(deposit_font);
		bank_name.setBounds(150, 30, 300, 30);
		
		deposit_title = new JLabel("Money Deposit Form");
		deposit_title.setBounds(40, 50, 300, 60);
		
		recipient_name = new JLabel("Recipent Name: ");
		recipient_name.setBounds(40, 120, 200, 30);
		
		receiver_name_input = new JTextField("");
		receiver_name_input.setBounds(260, 120, 300, 30);
		
		recipient_account_number = new JLabel("Recipent Account Number: ");
		recipient_account_number.setBounds(40, 160, 200, 30);
		
		acc_number = new JTextField("");
		acc_number.setBounds(260, 160, 300, 30);
		
		amount_to_deposit = new JLabel("Amount: ");
		amount_to_deposit.setBounds(40, 200, 200, 30);
		
		amount_to_deposit_input = new JTextField("");
		amount_to_deposit_input.setBounds(260, 200, 300, 30);
		
		sender_name = new JLabel("Depositor Name: ");
		sender_name.setBounds(40, 300, 200, 30);
		
		sender_name_input = new JTextField("");
		sender_name_input.setBounds(260, 300, 300, 30);
		
		purpose_of_deposit = new JLabel("Purpose of Deposit: ");
		purpose_of_deposit.setBounds(40, 350, 200, 30);
		
		purpose_of_deposit_input = new JTextField("");
		purpose_of_deposit_input.setBounds(260, 350, 300, 30);
		
		note = new JLabel("Every fields must be entered!!");
		note.setFont(note_font);
		note.setForeground(Color.red);
		note.setBounds(40, 400, 300, 30);
		
		deposit = new JButton("Deposit");
		deposit.setForeground(Color.white);
		deposit.setBackground(Color.green);
		deposit.setBounds(460, 450, 100, 30);
		
		cancel = new JButton("Cancel");
		cancel.setForeground(Color.white);
		cancel.setBackground(Color.red);
		cancel.setBounds(40, 450, 100, 30);
		
		receiver_name_input.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(receiver_name_input.getText().length()>=60&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }
            }
		});
		
		acc_number.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(acc_number.getText().length()>10&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }
                char ch = evt.getKeyChar();

                if(!Character.isLetterOrDigit(ch)) {
                	evt.consume();
                }
            }
		});
		
		amount_to_deposit_input.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(amount_to_deposit_input.getText().length()>=8&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }
                char ch = evt.getKeyChar();

                if(!Character.isDigit(ch)) {
                	evt.consume();
                }
            }
		});
		
		sender_name_input.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(sender_name_input.getText().length()>=60&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }        
            }
		});
		
		purpose_of_deposit_input.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if(purpose_of_deposit_input.getText().length()>=60&&!(evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
		            evt.consume();
		         }
            }
		});
		
		deposit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String receive_name = receiver_name_input.getText().toString().trim();
				String account_num = acc_number.getText().toString().trim();
				String amount = amount_to_deposit_input.getText().toString().trim();
				String send_name = sender_name_input.getText().toString().trim();
				String remarks = purpose_of_deposit_input.getText().toString().trim();
				if(receive_name.isEmpty() || account_num.isEmpty() || amount.isEmpty() || send_name.isEmpty() || remarks.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "Please fill out all details",
						    "Error",
						    JOptionPane.WARNING_MESSAGE);
				} else {
					boolean isValid = ifExists(account_num, jdbc);
					if(!isValid) {
						JOptionPane.showMessageDialog(frame,
							    "Account Number doesn't exists",
							    "Error",
							    JOptionPane.WARNING_MESSAGE);
					} else {
						String getName = getName(account_num, jdbc);
						getName = getName.toLowerCase();
						if(getName.equals(receive_name.toLowerCase())) {
							Random rand = new Random();
							String defCode;
							boolean isUnique = false;
							do {
								defCode = "AB";
								for (int i = 0; i < 8; i++)
							    {
							        int n = rand.nextInt(10) + 0;
							        defCode += Integer.toString(n);
							    }
								isUnique = ifExistsCode(defCode, jdbc);
							} while(isUnique == false);
							int depositAmount = Integer.parseInt(amount);
							if(depositAmount > 0) {
								 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
								 LocalDateTime now = LocalDateTime.now();
								 String date = dtf.format(now).toString();
								int u_id = jdbc.getDataInt("SELECT user_id FROM user WHERE account_number='"+account_num+"'");
								jdbc.executeQuery("UPDATE user SET balance = balance + '"+depositAmount+"' WHERE user_id = '"+u_id+"'");
								jdbc.executeQuery("INSERT INTO transactions(transaction_id, user_id, user_name, debit_credit, date_performed ,amount, remarks) VALUES('"+defCode+"', '"+u_id+"', '"+send_name+"', 'credit', '"+date+"' , '"+amount+"', '"+remarks+"')");
								JOptionPane.showMessageDialog(frame,
									    "Transaction Successful",
									    "Success",
									    JOptionPane.INFORMATION_MESSAGE);
								frame.dispose();
								jdbc.closeConnection();
								DefaultPage.startActivity();
							} else {
								JOptionPane.showMessageDialog(frame,
									    "Enter valid amount",
									    "Error",
									    JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(frame,
								    "Receiver name doesn't match to the owner of the bank account",
								    "Error",
								    JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}});
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jdbc.closeConnection();
				frame.dispose();
				DefaultPage.startActivity();
			}});
		
		panel.add(bank_name);
		panel.add(deposit_title);
		panel.add(recipient_name);
		panel.add(receiver_name_input);
		panel.add(recipient_account_number);
		panel.add(acc_number);
		panel.add(amount_to_deposit);
		panel.add(amount_to_deposit_input);
		panel.add(sender_name);
		panel.add(sender_name_input);
		panel.add(purpose_of_deposit);
		panel.add(purpose_of_deposit_input);
		panel.add(note);
		panel.add(deposit);
		panel.add(cancel);

		frame.add(panel);
		
		frame.setSize(600, 550);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	static boolean ifExists(String account_number, JDBCConnection jdbc) {
		boolean status;
		int count = jdbc.getDataInt("SELECT COUNT(*) FROM user WHERE account_number = '"+account_number+"'");
		if(count == 0) {
			status = false;
		} else {
			status = true;
		}
		return status;
	}
	
	static boolean ifExistsCode(String code, JDBCConnection jdbc) {
		boolean status;
		int count = jdbc.getDataInt("SELECT COUNT(*) FROM transactions WHERE transaction_id = '"+code+"'");
		if(count == 0) {
			status = true;
		} else {
			status = false;
		}
		return status;
	}
	
	static String getName(String account_number, JDBCConnection jdbc) {
		String name = null;
		String firstName = jdbc.getData("SELECT first_name FROM user WHERE account_number = '"+account_number+"'");
		String lastName = jdbc.getData("SELECT last_name FROM user WHERE account_number = '"+account_number+"'");
		name = firstName + ' ' + lastName;
		return name;
	}
}

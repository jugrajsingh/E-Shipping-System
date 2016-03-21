import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import tools.DBConnect;

public class Mainlogin {
	public static String uniqueid = null;
	public static String usrname = null;
	public JFrame frame;
	private JTextField enteredusername;
	private JPasswordField enteredpassword;
	public Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainlogin window = new Mainlogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainlogin() {
		uniqueid = null;
		usrname = null;
		connection = DBConnect.dbconnect();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login Form");
		frame.setBounds(100, 100, 501, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		enteredusername = new JTextField();
		enteredusername.setName("Username Field");
		enteredusername.setToolTipText("Username");
		enteredusername.setColumns(10);

		JLabel label = new JLabel("Enter The Credentials Below");
		label.setIcon(new ImageIcon(this.getClass().getResource("/Login-Label.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblpassword = new JLabel("Password");
		lblpassword.setFont(new Font("Tahoma", Font.PLAIN, 18));

		enteredpassword = new JPasswordField();
		enteredpassword.setName("Passord Field");
		enteredpassword.setToolTipText("Password");
		enteredpassword.setEchoChar('*');

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isfieldsempty(enteredusername, enteredpassword)) {
				} else {
					uniqueid = null;
					usrname = null;
					PreparedStatement logintest;
					ResultSet resultoflogin;
					int count = 0;
					try {
						String loginquery = "SELECT * FROM accounts where username = ? and password = ?";
						logintest = connection.prepareStatement(loginquery);
						logintest.setString(1, enteredusername.getText());
						logintest.setString(2, enteredpassword.getText());
						resultoflogin = logintest.executeQuery();
						while (resultoflogin.next()) {
							uniqueid = resultoflogin.getString("usertype");
							usrname = resultoflogin.getString("username");
							count = count + 1;
						}
						if (count == 1) {
							JOptionPane.showMessageDialog(null,"Username and Password Accepted","Login Successful",0,new ImageIcon(this.getClass().getResource("/Login.png")));
							frame.dispose();
							new Mainbody();
						} else if (count == 0) {
							JOptionPane.showMessageDialog(null,"Authentication Failed !!! TRY AGAIN ---","Login Unsuccessful",0,new ImageIcon(this.getClass().getResource("/LoginFailed.png")));
						} else {
							JOptionPane.showMessageDialog(null,"Something Went Wrong");
						}
						resultoflogin.close();
						logintest.close();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});
		btnLogin.setToolTipText("Submit");
		btnLogin.setIcon(new ImageIcon(this.getClass().getResource("/Login.png")));
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton btnRegister = new JButton("Register");
		btnRegister.setIcon(new ImageIcon(this.getClass().getResource("/Register.png")));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				frame.setVisible(false);
				NewUser ob = new NewUser();
				ob.setVisible(true);
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(59)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
					.addGap(60))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(66)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblusername, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpassword, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(enteredusername, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
						.addComponent(enteredpassword, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
					.addGap(120))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(59)
					.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(43)
					.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 136, Short.MAX_VALUE)
					.addGap(60))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblusername, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(39)
							.addComponent(lblpassword, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(8)
							.addComponent(enteredusername, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(enteredpassword, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE)
						.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE))
					.addGap(28))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	public static boolean isfieldsempty(Object... field) {
		for (Object x : field) {
			if (((JTextComponent) x).getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,((JTextComponent) x).getName() + " is Empty");
				return true;
			}
		}
		return false;
	}

	public static boolean isfieldsequal(Object field1, Object field2) {
		if (((JTextComponent) field1).getText().equals(((JTextComponent) field2).getText()))
			return true;
		return false;
	}
}

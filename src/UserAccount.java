import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tools.CalendarProgram;
import tools.DBConnect;

public class UserAccount extends JFrame {

	private JPanel contentPane;
	private JPanel calpanel;
	private CalendarProgram cal;
	private JTextField firstname;
	private JTextField lastname;
	private JTextField username;
	private JPasswordField passwordField;
	private JTextField phone;
	private JTextField dob;
	private JTextField email;
	private JButton loaddate;
	private JPasswordField passwordField_1;
	public JTabbedPane tabbedPane;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JTextArea addressArea;
	/**
	 * Create the frame.
	 */
	Connection connection = DBConnect.dbconnect();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public UserAccount() {
		setTitle("User Account Details");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 76, 684, 345);
		contentPane.add(tabbedPane);

		JPanel generalpanel = new JPanel();
		tabbedPane.addTab("General", null, generalpanel, null);
		generalpanel.setLayout(null);

		JLabel lblName = new JLabel("First Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(59, 33, 64, 17);
		generalpanel.add(lblName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setBounds(59, 60, 64, 17);
		generalpanel.add(lblLastName);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(62, 88, 61, 17);
		generalpanel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(65, 116, 58, 17);
		generalpanel.add(lblPassword);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhone.setBounds(65, 144, 58, 17);
		generalpanel.add(lblPhone);

		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDateOfBirth.setBounds(46, 172, 77, 17);
		generalpanel.add(lblDateOfBirth);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(92, 200, 31, 17);
		generalpanel.add(lblEmail);

		JLabel lblDefaultAddress = new JLabel("Default Address");
		lblDefaultAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDefaultAddress.setBounds(28, 228, 95, 17);
		generalpanel.add(lblDefaultAddress);

		firstname = new JTextField();
		firstname.setName("First Name Field");
		firstname.setEditable(false);
		firstname.setBounds(133, 33, 190, 20);
		generalpanel.add(firstname);
		firstname.setColumns(10);

		lastname = new JTextField();
		lastname.setName("Last Name Field");
		lastname.setEditable(false);
		lastname.setColumns(10);
		lastname.setBounds(133, 60, 190, 20);
		generalpanel.add(lastname);

		username = new JTextField();
		username.setName("Username Field");
		username.setEditable(false);
		username.setColumns(10);
		username.setBounds(133, 88, 190, 20);
		generalpanel.add(username);

		passwordField = new JPasswordField();
		passwordField.setName("Password Field");
		passwordField.setEchoChar('*');
		passwordField.setEditable(false);
		passwordField.setBounds(133, 116, 190, 20);
		generalpanel.add(passwordField);

		phone = new JTextField();
		phone.setName("Phone no. Field");
		phone.setEditable(false);
		phone.setColumns(10);
		phone.setBounds(133, 144, 190, 20);
		generalpanel.add(phone);

		dob = new JTextField();
		dob.setName("Date of Birth Field");
		dob.setEditable(false);
		dob.setColumns(10);
		dob.setBounds(133, 172, 190, 20);
		generalpanel.add(dob);

		email = new JTextField();
		email.setName("Email Field");
		email.setEditable(false);
		email.setColumns(10);
		email.setBounds(133, 200, 190, 20);
		generalpanel.add(email);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(133, 226, 190, 80);
		generalpanel.add(scrollPane_1);

		addressArea = new JTextArea();
		addressArea.setName("Address Field");
		addressArea.setEditable(false);
		addressArea.setOpaque(false);
		addressArea.setLineWrap(true);
		addressArea.setWrapStyleWord(true);
		scrollPane_1.setViewportView(addressArea);

		JToggleButton tglbtnUpdate = new JToggleButton("Update");
		tglbtnUpdate.setIcon(new ImageIcon(this.getClass().getResource("/RequestAssignment.png")));
		tglbtnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pass = new String();
				boolean flag = false;
				String passfromtable = new String();
				if (tglbtnUpdate.isSelected()) {
					JPasswordField pwd = new JPasswordField();
					JOptionPane.showConfirmDialog(null, pwd,"Confirm Password",JOptionPane.OK_CANCEL_OPTION,1);
					pass = pwd.getText();
					String passquery = "SELECT * FROM accounts where username = ?";
					try {
						PreparedStatement passtest = connection.prepareStatement(passquery);
						passtest.setString(1, Mainlogin.usrname);
						ResultSet rst = passtest.executeQuery();
						if(rst.next()){
							passfromtable = rst.getString("password");
						}
						passtest.close();
						rst.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						flag = pass.equalsIgnoreCase(passfromtable);
					} catch (Exception e) {
					}
					if (flag) {
						firstname.setEditable(true);
						lastname.setEditable(true);
						username.setEditable(false);
						phone.setEditable(true);
						dob.setEditable(true);
						email.setEditable(true);
						addressArea.setEditable(true);
						addressArea.setOpaque(true);
						rdbtnMale.setEnabled(true);
						rdbtnFemale.setEnabled(true);
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date d;
						try {
							generalpanel.add(calpanel);
							d = df.parse(dob.getText());
							cal.setDate(d);
							cal.setChosenOtherButtonColor(new Color(160, 160, 160));
							cal.setChosenMonthButtonColor(new Color(240, 240, 240));
							calpanel.add(cal);
							generalpanel.add(loaddate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						tglbtnUpdate.setText("Save Change");
						generalpanel.repaint();
					} else {
						JOptionPane.showMessageDialog(null,"Wrong Password Please Check");
						tglbtnUpdate.setSelected(false);
					}

				} 
				else {
					String qString="UPDATE accounts SET username=?, firstname=?, lastname=?, phone=?, DOB=?, email=?, usertype=?, defaultaddress=?, gender=? WHERE username=?";
					try {
						if(Mainlogin.isfieldsempty(username,firstname,lastname,phone,dob,email,addressArea)){
							int choice = JOptionPane.showOptionDialog(null, "Want to Exit Without Saving", "User Data", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
							switch(choice){
							case JOptionPane.YES_OPTION:
								firstname.setEditable(false);
								lastname.setEditable(false);
								username.setEditable(false);
								phone.setEditable(false);
								dob.setEditable(false);
								email.setEditable(false);
								addressArea.setEditable(false);
								addressArea.setOpaque(false);
								rdbtnMale.setEnabled(false);
								rdbtnFemale.setEnabled(false);
								generalpanel.remove(calpanel);
								generalpanel.remove(loaddate);
								tglbtnUpdate.setText("Update");
								generalpanel.repaint();
								break;
							case JOptionPane.NO_OPTION: 
								tglbtnUpdate.setSelected(true);
								break;
							default:
								tglbtnUpdate.setSelected(true);
							}
						}
						else{
							int choice = JOptionPane.showOptionDialog(null, "Want to Save", "User Data", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
							switch(choice){
							case JOptionPane.YES_OPTION:
								PreparedStatement pst = connection.prepareStatement(qString);
								pst.setString(1, username.getText());
								pst.setString(2, firstname.getText());
								pst.setString(3, lastname.getText());
								pst.setString(4, phone.getText());
								pst.setString(5, dob.getText());
								pst.setString(6, email.getText());
								pst.setString(7, Mainlogin.uniqueid);
								pst.setString(8, addressArea.getText());
								pst.setString(9, buttonGroup.getSelection().getActionCommand().toString());
								pst.setString(10, Mainlogin.usrname);
								pst.execute();
								firstname.setEditable(false);
								lastname.setEditable(false);
								username.setEditable(false);
								phone.setEditable(false);
								dob.setEditable(false);
								email.setEditable(false);
								addressArea.setEditable(false);
								addressArea.setOpaque(false);
								rdbtnMale.setEnabled(false);
								rdbtnFemale.setEnabled(false);
								generalpanel.remove(calpanel);
								generalpanel.remove(loaddate);
								tglbtnUpdate.setText("Update");
								generalpanel.repaint();
								break;
							default:
								firstname.setEditable(false);
								lastname.setEditable(false);
								username.setEditable(false);
								phone.setEditable(false);
								dob.setEditable(false);
								email.setEditable(false);
								addressArea.setEditable(false);
								addressArea.setOpaque(false);
								rdbtnMale.setEnabled(false);
								rdbtnFemale.setEnabled(false);
								generalpanel.remove(calpanel);
								generalpanel.remove(loaddate);
								tglbtnUpdate.setText("Update");
								generalpanel.repaint();
								break;
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					loaduserdata();
				}
			}
		});
		tglbtnUpdate.setBounds(519, 283, 150, 23);
		generalpanel.add(tglbtnUpdate);
		
		cal = new CalendarProgram();
		
		loaddate = new JButton("<<");
		loaddate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		loaddate.setBounds(329, 171, 51, 23);
		loaddate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dob.setText(cal.getDate());
			}
		});
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setEditable(true);
		passwordField_1.setBounds(333, 116, 190, 20);

		JToggleButton btnChangePassword = new JToggleButton("Change Password");
		btnChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pass = new String();
				boolean flag = false;
				String passfromtable = new String();
				if (btnChangePassword.isSelected()) {
					JPasswordField pwd = new JPasswordField();
					JOptionPane.showConfirmDialog(null, pwd,"Confirm Password",JOptionPane.OK_CANCEL_OPTION,1);
					pass = pwd.getText();
					String passquery = "SELECT * FROM accounts where username = ?";
					try {
						PreparedStatement passtest = connection.prepareStatement(passquery);
						passtest.setString(1, Mainlogin.usrname);
						ResultSet rst = passtest.executeQuery();
						if(rst.next()){
							passfromtable = rst.getString("password");
						}
						passtest.close();
						rst.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						flag = pass.equalsIgnoreCase(passfromtable);
					} catch (Exception e) {
					}
					if (flag) {
						generalpanel.add(passwordField_1);
						passwordField_1.setText("");
						passwordField.setEditable(true);
						passwordField.setText("");
						generalpanel.repaint();
						btnChangePassword.setText("Save Change");
					} else {
						JOptionPane.showMessageDialog(null,"Wrong Password Please Check");
						btnChangePassword.setSelected(false);
					}
				}
				else {
					try{
						if(Mainlogin.isfieldsempty(passwordField,passwordField_1)){
							int choice = JOptionPane.showOptionDialog(null, "Want to Exit Without Saving", "Password", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
							switch(choice){
							case JOptionPane.YES_OPTION: 
								btnChangePassword.setText("Change Password");
								generalpanel.remove(passwordField_1);
								passwordField.setEditable(false);
								generalpanel.repaint();
								String passquery = "SELECT * FROM accounts where username = ?";
								PreparedStatement passtest = connection.prepareStatement(passquery);
								passtest.setString(1, Mainlogin.usrname);
								ResultSet rst = passtest.executeQuery();
								if(rst.next()){
									passwordField.setText(rst.getString("password"));
								}
								passtest.close();
								rst.close();
								break;
							case JOptionPane.NO_OPTION: 
								btnChangePassword.setSelected(true);
								break;
							default:
								btnChangePassword.setSelected(true);
									
							}
						}
						else if(Mainlogin.isfieldsequal(passwordField, passwordField_1)){
							String query = "UPDATE accounts SET password=? WHERE username=?";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, passwordField.getText());
							pst.setString(2, Mainlogin.usrname);
							pst.execute();
							pst.close();
							btnChangePassword.setText("Change Password");
							generalpanel.remove(passwordField_1);
							passwordField.setEditable(false);
							generalpanel.repaint();
							String passquery = "SELECT * FROM accounts where username = ?";
							PreparedStatement passtest = connection.prepareStatement(passquery);
							passtest.setString(1, Mainlogin.usrname);
							ResultSet rst = passtest.executeQuery();
							if(rst.next()){
								passwordField.setText(rst.getString("password"));
							}
							passtest.close();
							rst.close();
						}
						else{
							JOptionPane.showMessageDialog(null,"Password doesn`t match please check");
							int choice = JOptionPane.showOptionDialog(null, "Want to Exit Without Saving", "Password", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
							switch(choice){
							case JOptionPane.YES_OPTION:
								btnChangePassword.setText("Change Password");
								generalpanel.remove(passwordField_1);
								passwordField.setEditable(false);
								generalpanel.repaint();
								String passquery = "SELECT * FROM accounts where username = ?";
								PreparedStatement passtest = connection.prepareStatement(passquery);
								passtest.setString(1, Mainlogin.usrname);
								ResultSet rst = passtest.executeQuery();
								if(rst.next()){
									passwordField.setText(rst.getString("password"));
								}
								passtest.close();
								rst.close();
								break;
							case JOptionPane.NO_OPTION: 
								btnChangePassword.setSelected(true);
								break;
							default:
								btnChangePassword.setSelected(true);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});
		btnChangePassword.setBounds(548, 115, 121, 23);
		generalpanel.add(btnChangePassword);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnMale.setActionCommand("male");
		rdbtnMale.setEnabled(false);
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setBounds(370, 283, 47, 23);
		generalpanel.add(rdbtnMale);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGender.setBounds(329, 287, 35, 14);
		generalpanel.add(lblGender);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnFemale.setActionCommand("female");
		rdbtnFemale.setEnabled(false);
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setBounds(419, 283, 64, 23);
		generalpanel.add(rdbtnFemale);
		
		JButton btnOrderHistory = new JButton("Order History");
		btnOrderHistory.setIcon(new ImageIcon(this.getClass().getResource("/History.png")));
		btnOrderHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabbedPane.indexOfTab("Order History")>-1){
					tabbedPane.remove(tabbedPane.indexOfTab("Order History"));
				}
				tabbedPane.addTab("Order History", null, new OrderHistoryPanel(), null);
				tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Order History"));
				OrderHistoryPanel.btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						tabbedPane.remove(tabbedPane.indexOfTab("Order History"));
					}
				});
			}
		});
		btnOrderHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOrderHistory.setBounds(519, 11, 150, 23);
		generalpanel.add(btnOrderHistory);
		
		calpanel = new JPanel();
		calpanel.setBounds(390, 144, 165, 122);
		calpanel.setLayout(new BorderLayout(0, 0));


		JLabel lblUserName = new JLabel();
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblUserName.setText(Mainlogin.usrname.toUpperCase());
		lblUserName.setBounds(174, 11, 336, 54);
		contentPane.add(lblUserName);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setIcon(new ImageIcon(this.getClass().getResource("/Logout.png")));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				JOptionPane.showMessageDialog(null, "You Are Logged Out");
				Mainlogin m = new Mainlogin();
				m.frame.setVisible(true);
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnLogout.setBounds(520, 11, 154, 23);
		contentPane.add(btnLogout);

		JButton btnBackToMain = new JButton("Back to Main");
		btnBackToMain.setIcon(new ImageIcon(this.getClass().getResource("/Back.png")));
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Mainbody();
			}
		});
		btnBackToMain.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBackToMain.setBounds(520, 45, 154, 25);
		contentPane.add(btnBackToMain);
		
		JLabel label = new JLabel("",new ImageIcon(this.getClass().getResource("/UserAccount.png")),JLabel.CENTER);
		label.setBounds(10, 0, 154, 70);
		contentPane.add(label);
		loaduserdata();
	}
	
	void loaduserdata(){
		try {
			String query = "SELECT * FROM accounts where username = ?";
			PreparedStatement passtest = connection.prepareStatement(query);
			passtest.setString(1, Mainlogin.usrname);
			ResultSet rst = passtest.executeQuery();
			if(rst.next()){
				firstname.setText(rst.getString("firstname"));
				lastname.setText(rst.getString("lastname"));
				username.setText(rst.getString("username"));
				passwordField.setText(rst.getString("password"));
				phone.setText(rst.getString("phone"));
				dob.setText(rst.getString("DOB"));
				email.setText(rst.getString("email"));
				addressArea.setText(rst.getString("defaultaddress"));
				if(rst.getString("gender").equalsIgnoreCase("male")){
					buttonGroup.setSelected(rdbtnMale.getModel(), true);
				}
				else if(rst.getString("gender").equalsIgnoreCase("female")){
					buttonGroup.setSelected(rdbtnFemale.getModel(), true);
				}
			}
			passtest.close();
			rst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
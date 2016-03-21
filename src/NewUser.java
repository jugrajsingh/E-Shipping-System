import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tools.CalendarProgram;
import tools.DBConnect;

public class NewUser extends JFrame {
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField repasswordField;
	private JTextField firstnameField;
	private JTextField lastnameField;
	private JTextField date_of_birthField;
	private JTextField phoneField;
	private JTextField emailField;
	private JComboBox usertype;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JLabel lblOk;
	private JLabel lblPasswordNotMatch;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public NewUser() {
		setTitle("Registration Form for New User");
		setResizable(false);
		Connection connection = DBConnect.dbconnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWeclcomeToEcourier = new JLabel("Welcome to E-courier Service");
		lblWeclcomeToEcourier.setFont(new Font("Lucida Bright", Font.PLAIN, 20));
		lblWeclcomeToEcourier.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeclcomeToEcourier.setBounds(193, 11, 298, 24);
		contentPane.add(lblWeclcomeToEcourier);

		JPanel panel = new JPanel();
		panel.setBounds(0, 38, 684, 373);
		contentPane.add(panel);
		panel.setLayout(null);
		
				date_of_birthField = new JTextField();
				date_of_birthField.setName("Date of Birth");
				date_of_birthField.setToolTipText("Select From Calendar");
				date_of_birthField.setEditable(false);
				date_of_birthField.setBounds(180, 186, 170, 20);
				panel.add(date_of_birthField);
				date_of_birthField.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(108, 31, 61, 17);
		panel.add(lblUsername);

		usernameField = new JTextField("");
		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(usernameField.getText().isEmpty()){
				}
				else{
					try {
						String query="SELECT * FROM accounts where username = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, usernameField.getText());
						ResultSet rst = pst.executeQuery();
						if(rst.next()){
							lblOk.setText("* Username Already Taken try another.");
							lblOk.setForeground(Color.RED);
						}
						else{
							lblOk.setText("* Username Available");
							lblOk.setForeground(Color.GREEN);
						}
						pst.close();
						rst.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		usernameField.setName("Username Field");
		usernameField.setBounds(180, 31, 170, 20);
		panel.add(usernameField);
		usernameField.setColumns(10);

		JLabel lblMustBe = new JLabel("You must be fill all fields having *");
		lblMustBe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMustBe.setBounds(517, 11, 157, 14);
		panel.add(lblMustBe);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(111, 61, 58, 17);
		panel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(passwordField.getText().isEmpty()|repasswordField.getText().isEmpty()){
				}
				else{
					if (!Mainlogin.isfieldsequal(passwordField,repasswordField)) {
						lblPasswordNotMatch.setText("*Password didn`t match");
						lblPasswordNotMatch.setForeground(Color.RED);
					}
					else{
						lblPasswordNotMatch.setText("*Password matched");
						lblPasswordNotMatch.setForeground(Color.GREEN);
					}
				}
			}
		});
		passwordField.setName("Password Field");
		passwordField.setBounds(180, 62, 170, 20);
		panel.add(passwordField);

		repasswordField = new JPasswordField();
		repasswordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(passwordField.getText().isEmpty()|repasswordField.getText().isEmpty()){
				}
				else{
					if (!Mainlogin.isfieldsequal(passwordField,repasswordField)) {
						lblPasswordNotMatch.setText("*Password didn`t match");
						lblPasswordNotMatch.setForeground(Color.RED);
					}
					else{
						lblPasswordNotMatch.setText("*Password matched");
						lblPasswordNotMatch.setForeground(Color.GREEN);
					}
				}
			}
		});
		repasswordField.setName("Repassword Field");
		repasswordField.setBounds(180, 93, 170, 20);
		panel.add(repasswordField);

		JLabel lblReenterPassword = new JLabel("Re-Enter Password");
		lblReenterPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReenterPassword.setBounds(53, 93, 116, 17);
		panel.add(lblReenterPassword);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFirstName.setBounds(105, 124, 64, 17);
		panel.add(lblFirstName);

		firstnameField = new JTextField();
		firstnameField.setName("First Name");
		firstnameField.setBounds(180, 124, 170, 20);
		panel.add(firstnameField);
		firstnameField.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setBounds(105, 155, 64, 17);
		panel.add(lblLastName);

		lastnameField = new JTextField();
		lastnameField.setName("Last Name");
		lastnameField.setColumns(10);
		lastnameField.setBounds(180, 155, 170, 20);
		panel.add(lastnameField);
		CalendarProgram calendarpanel = new CalendarProgram();
		calendarpanel.setChosenOtherButtonColor(new Color(160, 160, 160));
		calendarpanel.setChosenMonthButtonColor(new Color(240, 240, 240));
		calendarpanel.setBounds(472, 61, 202, 148);
		panel.add(calendarpanel);

		JLabel lblDateofbirth = new JLabel("Date-of-Birth");
		lblDateofbirth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDateofbirth.setBounds(90, 186, 79, 17);
		panel.add(lblDateofbirth);

		JButton load_DOB = new JButton("<<");
		load_DOB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				date_of_birthField.setText(calendarpanel.getDate());
			}
		});
		load_DOB.setBounds(360, 186, 49, 20);
		panel.add(load_DOB);

		phoneField = new JTextField();
		phoneField.setName("Phone");
		phoneField.setBounds(180, 217, 170, 20);
		panel.add(phoneField);
		phoneField.setColumns(10);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhone.setBounds(130, 217, 39, 17);
		panel.add(lblPhone);

		emailField = new JTextField();
		emailField.setName("Email");
		emailField.setBounds(180, 248, 170, 20);
		panel.add(emailField);
		emailField.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(138, 248, 31, 17);
		panel.add(lblEmail);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(180, 279, 170, 83);
		panel.add(scrollPane);

		JTextArea addressField = new JTextArea();
		addressField.setInheritsPopupMenu(true);
		addressField.setName("Address Field");
		scrollPane.setViewportView(addressField);
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);

		JLabel lblDefaultAddress = new JLabel("Default \r\nAddress");
		lblDefaultAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDefaultAddress.setBounds(74, 279, 95, 17);
		panel.add(lblDefaultAddress);

		JButton btnreset = new JButton("Reset");
		btnreset.setIcon(new ImageIcon(this.getClass().getResource("/Reset.png")));
		btnreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				NewUser n = new NewUser();
				n.setVisible(true);
			}
		});
		btnreset.setBounds(532, 247, 103, 23);
		panel.add(btnreset);

		JButton btnsave = new JButton("Save");
		btnsave.setIcon(new ImageIcon(this.getClass().getResource("/Save.png")));
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainlogin.isfieldsempty(usernameField, passwordField,repasswordField, firstnameField, date_of_birthField,phoneField)) {
				} 
				else if (!Mainlogin.isfieldsequal(passwordField,repasswordField)) {
					JOptionPane.showMessageDialog(null, "Password didn`t match");
				} 
				else {
					String insertuserquery = "INSERT INTO accounts (username,password,firstname,lastname,phone,DOB,email,usertype,defaultaddress,gender) VALUES (?,?,?,?,?,?,?,?,?,?)";
					try {
						PreparedStatement insertuser = connection.prepareStatement(insertuserquery);
						insertuser.setString(1, usernameField.getText());
						insertuser.setString(2, passwordField.getText());
						insertuser.setString(3, firstnameField.getText());
						insertuser.setString(4, lastnameField.getText());
						insertuser.setString(5, phoneField.getText());
						insertuser.setString(6, date_of_birthField.getText());
						insertuser.setString(7, emailField.getText());
						insertuser.setString(8, (String) usertype.getSelectedItem());
						insertuser.setString(9, addressField.getText());
						insertuser.setString(10,buttonGroup.getSelection().getActionCommand().toString());
						insertuser.execute();
						JOptionPane.showMessageDialog(null,"Registration is Successful");
						setVisible(false);
						dispose();
						Mainlogin m = new Mainlogin();
						m.frame.setVisible(true);
						insertuser.closeOnCompletion();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnsave.setBounds(532, 281, 103, 23);
		panel.add(btnsave);

		JButton btncancel = new JButton("Cancel");
		btncancel.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btncancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				Mainlogin m = new Mainlogin();
				m.frame.setVisible(true);
			}
		});
		btncancel.setBounds(532, 315, 103, 23);
		panel.add(btncancel);
		
		usertype = new JComboBox();
		usertype.setModel(new DefaultComboBoxModel(new String[] {"seller", "picker"}));
		usertype.setBounds(419, 248, 103, 20);
		panel.add(usertype);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setSelected(true);
		rdbtnMale.setActionCommand("male");
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnMale.setBounds(413, 216, 47, 23);
		panel.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setActionCommand("female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnFemale.setBounds(462, 216, 59, 23);
		panel.add(rdbtnFemale);
		
		JLabel lblUserType = new JLabel("User Type");
		lblUserType.setLabelFor(usertype);
		lblUserType.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblUserType.setBounds(363, 251, 49, 14);
		panel.add(lblUserType);
		
		lblOk = new JLabel("");
		lblOk.setBounds(360, 34, 261, 14);
		panel.add(lblOk);
		
		JLabel label_4 = new JLabel("*");
		label_4.setBounds(170, 186, 22, 14);
		panel.add(label_4);
		
		JLabel label_3 = new JLabel("*");
		label_3.setBounds(170, 152, 22, 14);
		panel.add(label_3);
		
		JLabel label = new JLabel("*");
		label.setBounds(170, 59, 22, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setBounds(170, 89, 22, 14);
		panel.add(label_1);
		
		JLabel label_5 = new JLabel("*");
		label_5.setBounds(170, 31, 22, 14);
		panel.add(label_5);
		
		JLabel label_2 = new JLabel("*");
		label_2.setBounds(170, 124, 22, 14);
		panel.add(label_2);
		
		lblPasswordNotMatch = new JLabel("");
		lblPasswordNotMatch.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPasswordNotMatch.setBounds(355, 96, 116, 14);
		panel.add(lblPasswordNotMatch);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(this.getClass().getResource("New_User_Icon.png")));
		lblNewLabel.setBounds(113, -13, 95, 81);
		contentPane.add(lblNewLabel);
	}
}

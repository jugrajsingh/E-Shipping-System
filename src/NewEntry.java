import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Formatter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import tools.DBConnect;

public class NewEntry extends JPanel {
	private JButton btnSave;
	private JTextField packagetitle;
	private JTextField weight;
	private JTextField amounttopay;
	Connection conn;

	/**
	 * Create the panel.
	 */
	public NewEntry() {
		conn = DBConnect.dbconnect();

		JLabel lblPackageTitle = new JLabel("Package Title");
		lblPackageTitle.setFont(new Font("Tahoma", Font.PLAIN, 11));

		packagetitle = new JTextField();
		packagetitle.setName("Package Name");
		lblPackageTitle.setLabelFor(packagetitle);
		packagetitle.setColumns(10);

		JLabel lblSendersAddress = new JLabel("Sender`s Address");
		lblSendersAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JLabel lblReciversAddress = new JLabel("Receiver`s Address");
		lblReciversAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 11));

		weight = new JTextField();
		weight.setName("Weight");
		weight.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				double amt = 0.0;
				try {
					if (Double.parseDouble(weight.getText()) < 1){
						btnSave.setEnabled(true);
						amt = 15;
					}
					else if (Double.parseDouble(weight.getText()) >= 1.0){
						amt = Double.parseDouble(weight.getText()) * 2;
						btnSave.setEnabled(true);
					}
					amounttopay.setText(Double.toString(amt));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Entered Value is NOT a Weight","Re-enter Weight", JOptionPane.ERROR_MESSAGE);
					btnSave.setEnabled(false);
				}

			}
		});
		lblWeight.setLabelFor(weight);
		weight.setColumns(10);

		JLabel lblG = new JLabel("Kg");
		lblG.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblG.setLabelFor(weight);

		JLabel lblDescription = new JLabel("Description :-");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JScrollPane scrollPane_1 = new JScrollPane();

		JTextArea sendersaddress = new JTextArea();
		sendersaddress.setName("Sender`s Address");
		lblSendersAddress.setLabelFor(sendersaddress);
		sendersaddress.setWrapStyleWord(true);
		scrollPane_1.setViewportView(sendersaddress);
		sendersaddress.setLineWrap(true);

		JScrollPane scrollPane_2 = new JScrollPane();

		JTextArea receiversaddress = new JTextArea();
		receiversaddress.setName("Receiver`s Address");
		lblReciversAddress.setLabelFor(receiversaddress);
		receiversaddress.setWrapStyleWord(true);
		scrollPane_2.setViewportView(receiversaddress);
		receiversaddress.setLineWrap(true);

		JScrollPane scrollPane_3 = new JScrollPane();

		JTextArea description = new JTextArea();
		description.setName("Description");
		lblDescription.setLabelFor(description);
		description.setWrapStyleWord(true);
		scrollPane_3.setViewportView(description);
		description.setLineWrap(true);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnReset.setIcon(new ImageIcon(this.getClass().getResource("/Reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
				NewEntry newentry = new NewEntry();
				Mainbody.tabbedPane.addTab("New Entry", null, newentry, null);
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane
						.indexOfComponent(newentry));
			}
		});

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setIcon(new ImageIcon(this.getClass().getResource("/Save.png")));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainlogin.isfieldsempty(packagetitle, sendersaddress,
						receiversaddress, weight)) {
				} else {
					try {
						Calendar cal = Calendar.getInstance();
						Formatter fmt = new Formatter();
						fmt.format("%tF %tT", cal,cal);
						String insertquery = "INSERT INTO package_records(packagename,sender,receiver,entry_time,weight,Description,amount_to_pay,status,userid) VALUES (?,?,?,?,?,?,?,?,?)";
						PreparedStatement insertrecord = conn.prepareStatement(insertquery);
						insertrecord.setString(1, packagetitle.getText());
						insertrecord.setString(2, sendersaddress.getText());
						insertrecord.setString(3, receiversaddress.getText());
						insertrecord.setString(4, fmt.toString());
						insertrecord.setString(5, weight.getText());
						insertrecord.setString(6, description.getText());
						insertrecord.setString(7, amounttopay.getText());
						insertrecord.setString(8, "pending");
						insertrecord.setString(9, Mainlogin.usrname);
						int option = JOptionPane.showConfirmDialog(null,"PLEASE CHECK DATA BEFORE SAVE\n\n'OK' to save\n'CANCEL' to go back","Confirmation To Save",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						if (option == 0){
							insertrecord.execute();
							Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
							Mainbody.newentryexist = false;
							
						}
						insertrecord.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		JButton cancelnewentry = new JButton("Cancel");
		cancelnewentry.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelnewentry.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		cancelnewentry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
				Mainbody.newentryexist = false;
			}
		});

		JLabel lblAmountToPay = new JLabel("Amount to Pay");
		lblAmountToPay.setFont(new Font("Tahoma", Font.PLAIN, 11));

		amounttopay = new JTextField();
		amounttopay.setName("Amount to Pay");
		amounttopay.setEditable(false);
		amounttopay.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPackageTitle, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(packagetitle, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
							.addGap(193)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(42)
									.addComponent(weight, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblWeight, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
							.addGap(7)
							.addComponent(lblG, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAmountToPay, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(amounttopay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
							.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(cancelnewentry, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(3))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSendersAddress, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblReciversAddress, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_2))
							.addGap(52)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_3)
								.addComponent(lblDescription, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
							.addGap(26)))
					.addGap(5))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblPackageTitle, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addComponent(packagetitle, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(weight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblWeight))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblG)))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSendersAddress, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblReciversAddress, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDescription)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(29)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnReset)
								.addComponent(btnSave)
								.addComponent(cancelnewentry)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAmountToPay, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(amounttopay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(14))
		);
		setLayout(groupLayout);
	}

	public JPanel returnpanel() {
		return this;
	}
}

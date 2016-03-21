import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import tools.DBConnect;


public class UpdateOrderPanel extends JPanel {

	public static JComboBox comboBox;
	private Connection connection;
	private JTextField packname;
	private JTextArea senderaddrs;
	private JTextArea receiveraddrs;
	private JTextArea descriptionarea;
	private JComboBox status;
	private JButton btnConfirmDelivery;
	private JTextField timeofentry;
	private JTextField amountpaid;
	/**
	 * Create the panel.
	 */
	public UpdateOrderPanel() {
		connection=DBConnect.dbconnect();
		
		comboBox = new JComboBox();
		comboBox.removeAllItems();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query = "select * from package_records where userid = ? and packageid = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, Mainlogin.usrname);
					pst.setString(2, (String) comboBox.getSelectedItem());
					ResultSet rwst = pst.executeQuery();
					if(rwst.next()){
						packname.setText(rwst.getString("packagename"));
						senderaddrs.setText(rwst.getString("sender"));
						receiveraddrs.setText(rwst.getString("receiver"));
						descriptionarea.setText(rwst.getString("Description"));
						if(rwst.getString("status").equals("pending")){
							status.setModel(new DefaultComboBoxModel(new String[] {"pending", "cancelled"}));
							status.setSelectedIndex(0);
						}
						else{
							status.removeAllItems();
							status.addItem(rwst.getString("status"));
							status.setSelectedIndex(0);
						}
						timeofentry.setText(rwst.getString("entry_time"));
						amountpaid.setText(rwst.getString("amount_to_pay"));
						enablityonstatus((String) comboBox.getSelectedItem());
					}
					else{
					}
					pst.close();
					rwst.close();

				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblPackageName = new JLabel("Package Name");
		lblPackageName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		packname = new JTextField();
		packname.setName("Package Name");
		packname.setColumns(10);
		
		JLabel lblSendersAddress = new JLabel("Sender`s Address");
		lblSendersAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblReceiversAddress = new JLabel("Receiver`s Address");
		lblReceiversAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		status = new JComboBox();
		status.setModel(new DefaultComboBoxModel(new String[] {"pending", "cancelled"}));
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblSelectOrderidFrom = new JLabel("Select Orderid from Here");
		
		JLabel lblTimeOfEntry = new JLabel("Time of Entry");
		lblTimeOfEntry.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		timeofentry = new JTextField();
		timeofentry.setEditable(false);
		timeofentry.setColumns(10);
		
		amountpaid = new JTextField();
		amountpaid.setEditable(false);
		amountpaid.setColumns(10);
		
		JLabel lblAmountPaid = new JLabel("Amount Paid");
		lblAmountPaid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnClose = new JButton("Close");
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon(this.getClass().getResource("/Save.png")));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "UPDATE package_records SET packagename = ? , sender = ? , receiver = ? , Description = ? , status = ? WHERE  packageid = ?";
				try {
					if(Mainlogin.isfieldsempty(packname,senderaddrs,receiveraddrs)){
					}
					else{
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, packname.getText());
						pst.setString(2, senderaddrs.getText());
						pst.setString(3, receiveraddrs.getText());
						pst.setString(4, descriptionarea.getText());
						pst.setString(5, (String) status.getSelectedItem());
						pst.setString(6, (String) comboBox.getSelectedItem());
						pst.execute();
						pst.close();
						Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnReset = new JButton("Reset");
		btnReset.setIcon(new ImageIcon(this.getClass().getResource("/Reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query = "select * from package_records where userid = ? and packageid = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, Mainlogin.usrname);
					pst.setString(2, (String) comboBox.getSelectedItem());
					ResultSet rst = pst.executeQuery();
					if(rst.next()){
						packname.setText(rst.getString("packagename"));
						senderaddrs.setText(rst.getString("sender"));
						receiveraddrs.setText(rst.getString("receiver"));
						descriptionarea.setText(rst.getString("Description"));
						if(rst.getString("status").equals("pending")){
							status.removeAllItems();
							status.setModel(new DefaultComboBoxModel(new String[] {"pending", "cancelled"}));
							status.setSelectedIndex(0);
						}
						else{
							status.removeAllItems();
							status.addItem(rst.getString("status"));
							status.setSelectedIndex(0);
						}
						timeofentry.setText(rst.getString("entry_time"));
						amountpaid.setText(rst.getString("amount_to_pay"));
						enablityonstatus((String) comboBox.getSelectedItem());
					}
					else{
						JOptionPane.showMessageDialog(null, "not available");
					}
					pst.close();
					rst.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		senderaddrs = new JTextArea();
		senderaddrs.setName("Sender`s Address");
		scrollPane.setViewportView(senderaddrs);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		receiveraddrs = new JTextArea();
		receiveraddrs.setName("Receiver`s Address");
		scrollPane_1.setViewportView(receiveraddrs);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		descriptionarea = new JTextArea();
		scrollPane_2.setViewportView(descriptionarea);
		
		btnConfirmDelivery = new JButton("Confirm Delivery");
		btnConfirmDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int choice = JOptionPane.showConfirmDialog(null, "You REALLY Received the Selected Package?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				switch(choice){
				case JOptionPane.YES_OPTION:
					try {
						String query ="DELETE FROM confrimations WHERE packageid = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, comboBox.getSelectedItem().toString());
						pst.execute();
						pst.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						String query ="UPDATE package_records SET status = ? WHERE packageid = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, "delivered");
						pst.setString(2, comboBox.getSelectedItem().toString());
						pst.execute();
						pst.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					comboBox.removeAllItems();
					SellerDashboard.fillcombobox("packageid",comboBox);
					break;
				default:
				}
			}
		});
		btnConfirmDelivery.setEnabled(false);
		btnConfirmDelivery.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addComponent(lblPackageName)
					.addGap(10)
					.addComponent(packname, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
					.addGap(125)
					.addComponent(lblSelectOrderidFrom)
					.addGap(10)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addGap(29))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(28)
							.addComponent(lblSendersAddress)
							.addGap(10)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(19)
									.addComponent(lblReceiversAddress))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(69)
									.addComponent(lblDescription)))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTimeOfEntry)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(5)
									.addComponent(lblAmountPaid)))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblStatus)
									.addGap(10)
									.addComponent(status, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
								.addComponent(timeofentry, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(amountpaid, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
							.addGap(79))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnConfirmDelivery)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
								.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(29))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPackageName)
						.addComponent(packname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblSelectOrderidFrom))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSendersAddress)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
							.addGap(5))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addComponent(lblTimeOfEntry)
							.addGap(14)
							.addComponent(lblAmountPaid))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStatus)
								.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addComponent(timeofentry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(amountpaid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblReceiversAddress)
							.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
							.addComponent(btnReset)
							.addGap(11)
							.addComponent(btnSave)
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnClose)
								.addComponent(btnConfirmDelivery))
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
								.addComponent(lblDescription))
							.addGap(11))))
		);
		setLayout(groupLayout);
		
	}
	
	void enablityonstatus(String field){
		String query = "SELECT * FROM package_records where packageid = ?";
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, field);
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				if((rst.getString("status").equals("cancelled"))||(rst.getString("status").equals("delivered"))
						||(rst.getString("status").equals("picked"))||(rst.getString("status").equals("assigned"))){
					packname.setEditable(false);
					senderaddrs.setEditable(false);
					receiveraddrs.setEditable(false);
					descriptionarea.setEditable(false);
					senderaddrs.setOpaque(false);
					receiveraddrs.setOpaque(false);
					status.enable(false);
					descriptionarea.setOpaque(false);
					JOptionPane.showMessageDialog(null, "Your Can`t Update this Record\nBecause it is Already " + rst.getString("status"));
				}
				else{
					packname.setEditable(true);
					senderaddrs.setEditable(true);
					receiveraddrs.setEditable(true);
					descriptionarea.setEditable(true);
					senderaddrs.setOpaque(true);
					status.enable(true);
					receiveraddrs.setOpaque(true);
					descriptionarea.setOpaque(true);
				}
				if(rst.getString("status").equals("picked")){
					btnConfirmDelivery.setEnabled(true);
				}
				else{
					btnConfirmDelivery.setEnabled(false);
				}
			}
			
			pst.close();
			rst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

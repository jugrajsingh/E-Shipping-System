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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import tools.DBConnect;


public class OrderHistoryPanel extends JPanel {
	private Connection connection;
	private JTable table;
	public static JButton btnClose;
	/**
	 * Create the panel.
	 */
	public OrderHistoryPanel() {
		connection = DBConnect.dbconnect();
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String loadtable;
				PreparedStatement load;
				try {
					if((Mainlogin.uniqueid.equalsIgnoreCase("root"))|(Mainlogin.uniqueid.equalsIgnoreCase("admin"))){
						if(comboBox.getSelectedIndex()==0){
							loadtable = "select packageid,packagename,weight,entry_time from package_records order by entry_time desc";
							load = connection.prepareStatement(loadtable);
						}
						else if(comboBox.getSelectedIndex()==1){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "pending");
						}
						else if(comboBox.getSelectedIndex()==2){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "assigned");
						}
						else if(comboBox.getSelectedIndex()==3){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "picked");
						}
						else if(comboBox.getSelectedIndex()==4){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "delivered");
						}
						else{
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "cancelled");
						}
					}
					else{
						if(comboBox.getSelectedIndex()==0){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
						}
						else if(comboBox.getSelectedIndex()==1){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "pending");
						}
						else if(comboBox.getSelectedIndex()==2){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "assigned");
						}
						else if(comboBox.getSelectedIndex()==3){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "picked");
						}
						else if(comboBox.getSelectedIndex()==4){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "delivered");
						}
						else{
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "cancelled");
						}
					}
					ResultSet tablecontent=load.executeQuery();
					table.setModel(Rs2TableModel.resultSetToTableModel(tablecontent,false));
					load.closeOnCompletion();
					tablecontent.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"All", "Pending", "Assigned", "Picked", "Delivered", "Cancelled"}));

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Reset.png")));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String loadtable;
				PreparedStatement load;
				try {
					if((Mainlogin.uniqueid.equalsIgnoreCase("root"))|(Mainlogin.uniqueid.equalsIgnoreCase("admin"))){
						if(comboBox.getSelectedIndex()==0){
							loadtable = "select packageid,packagename,weight,entry_time from package_records order by entry_time desc";
							load = connection.prepareStatement(loadtable);
						}
						else if(comboBox.getSelectedIndex()==1){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "pending");
						}
						else if(comboBox.getSelectedIndex()==2){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "assigned");
						}
						else if(comboBox.getSelectedIndex()==3){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "picked");
						}
						else if(comboBox.getSelectedIndex()==4){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "delivered");
						}
						else{
							loadtable = "select packageid,packagename,weight,entry_time from package_records where status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, "cancelled");
						}
					}
					else{
						if(comboBox.getSelectedIndex()==0){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
						}
						else if(comboBox.getSelectedIndex()==1){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "pending");
						}
						else if(comboBox.getSelectedIndex()==2){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "assigned");
						}
						else if(comboBox.getSelectedIndex()==3){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "picked");
						}
						else if(comboBox.getSelectedIndex()==4){
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "delivered");
						}
						else{
							loadtable = "select packageid,packagename,weight,entry_time from package_records where userid = ? and status = ? order by entry_time desc";
							load = connection.prepareStatement(loadtable);
							load.setString(1, Mainlogin.usrname);
							load.setString(2, "cancelled");
						}
					}
					ResultSet tablecontent=load.executeQuery();
					table.setModel(Rs2TableModel.resultSetToTableModel(tablecontent,false));
					load.closeOnCompletion();
					tablecontent.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		GroupLayout gl_orderhistorypanel = new GroupLayout(this);
		gl_orderhistorypanel.setHorizontalGroup(
			gl_orderhistorypanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_orderhistorypanel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_orderhistorypanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_orderhistorypanel.createSequentialGroup()
							.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRefresh))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE))
					.addGap(10))
		);
		gl_orderhistorypanel.setVerticalGroup(
			gl_orderhistorypanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_orderhistorypanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(25)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
					.addGap(27)
					.addGroup(gl_orderhistorypanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRefresh)
						.addComponent(btnClose))
					.addContainerGap())
		);
		setLayout(gl_orderhistorypanel);
	}

}

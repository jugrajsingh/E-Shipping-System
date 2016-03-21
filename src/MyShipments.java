import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import tools.DBConnect;


public class MyShipments extends JPanel {
	private JTable table;
	private JPanel panel;
	private JComboBox comboBox;
	private JButton btnConfirmDelivery;
	private Connection connection;

	/**
	 * Create the panel.
	 */
	public MyShipments() {
		connection = DBConnect.dbconnect();
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnClose = new JButton("Close");
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
			}
		});
		
		JButton btnGetDetails = new JButton("Get Details");
		btnGetDetails.setIcon(new ImageIcon(this.getClass().getResource("/GetDetails.png")));
		btnGetDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()==0){
					if(table.getSelectedRow()>-1){
						if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
							Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
						Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),1)));
						Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
					}
				}
				else{
					if(table.getSelectedRow()>-1){
						if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
							Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
						Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),0)));
						Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
					}
				}
			}
		});
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setIcon(new ImageIcon(this.getClass().getResource("/Delete.png")));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()==0){
					if(table.getSelectedRow()>-1){
						try {
							int choice = JOptionPane.showConfirmDialog(null, "You REALLY Want to Delete Selected Record?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
							switch(choice){
							case JOptionPane.YES_OPTION:
								String query = "DELETE FROM bidding WHERE bidID = ?";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.setInt(1, (int) table.getValueAt(table.getSelectedRow(),0));
								pst.execute();
								pst.close();
								loadtable();
								break;
							default:
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else if(comboBox.getSelectedIndex()==1){
					if(table.getSelectedRow()>-1){
						try {
							int choice = JOptionPane.showConfirmDialog(null, "You REALLY Want to Delete Selected Record?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
							switch(choice){
							case JOptionPane.YES_OPTION:
								String query = "UPDATE package_records SET status = ? , pickerid = ? WHERE packageid = ?";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.setString(1, "pending");
								pst.setString(2, null);
								pst.setInt(3, (int) table.getValueAt(table.getSelectedRow(),0));
								pst.execute();
								pst.close();
								loadtable();
								break;
							default:
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadtable();
				if(comboBox.getSelectedIndex()==1){
					panel.add(btnConfirmDelivery);
					getParent().repaint();
				}
				else{
					panel.remove(btnConfirmDelivery);
				}
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Biddings", "Assigned Shipments", "Picked Shipments"}));
		
		panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
						.addComponent(comboBox, 0, 177, Short.MAX_VALUE)
						.addComponent(btnRemove, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
						.addComponent(btnGetDetails, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
						.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
							.addGap(0))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRemove, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnGetDetails, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
							.addGap(56)))
					.addGap(9))
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnConfirmDelivery = new JButton("Confirm Delivery");
		btnConfirmDelivery.setIcon(new ImageIcon(this.getClass().getResource("/Login.png")));
		btnConfirmDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					if(comboBox.getSelectedIndex()==1){
						int choice = JOptionPane.showConfirmDialog(null, "You REALLY Picked the Selected Package?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						switch(choice){
						case JOptionPane.YES_OPTION:
							try {
								String query = "INSERT INTO confrimations (packageid, pickerid) VALUES (?, ?)";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.setInt(1, (int) table.getValueAt(table.getSelectedRow(),0));
								pst.setString(2, Mainlogin.usrname);
								pst.execute();
								pst.close();
								String query1 = "UPDATE package_records SET status = ? WHERE packageid = ?";
								PreparedStatement pst1 = connection.prepareStatement(query1);
								pst1.setString(1, "picked");
								pst1.setInt(2, (int) table.getValueAt(table.getSelectedRow(),0));
								pst1.execute();
								pst1.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							loadtable();
							break;
						default:
						}
					}
				}
			}
		});
		panel.add(btnConfirmDelivery);
		panel.remove(btnConfirmDelivery);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()>=2){
					if(comboBox.getSelectedIndex()==0){
						if(table.getSelectedRow()>-1){
							if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
								Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
							Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),1)));
							Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
						}
					}
					else{
						if(table.getSelectedRow()>-1){
							if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
								Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
							Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),0)));
							Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
						}
					}
				}
			}
		});
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
		
		loadtable();
	}
	
	void loadtable(){
		try {
			if(comboBox.getSelectedIndex()==0){
				String query ="select bidID,packageid,packagename,weight,sender,receiver,biddingoffer from bidding,package_records where package_records.packageid = bidding.bidon and bidder = ?";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, Mainlogin.usrname);
				ResultSet rst = pst.executeQuery();
				table.setModel(Rs2TableModel.resultSetToTableModel(rst, false));
				table.getColumnModel().getColumn(0).setPreferredWidth(25);
				table.getColumnModel().getColumn(3).setPreferredWidth(32);
				table.getColumnModel().getColumn(0).setHeaderValue("ID");
				table.getColumnModel().getColumn(1).setHeaderValue("PackageID");
				table.getColumnModel().getColumn(2).setHeaderValue("Title");
				table.getColumnModel().getColumn(4).setHeaderValue("Origin");
				table.getColumnModel().getColumn(5).setHeaderValue("Destination");
				table.getColumnModel().getColumn(6).setHeaderValue("Your Offer $");
				pst.close();
				rst.close();
			}
			else if(comboBox.getSelectedIndex()==1){
				String query = "Select packageid,packagename,sender,receiver,weight,amount_to_pay from package_records where pickerid = ? and status = ?";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, Mainlogin.usrname);
				pst.setString(2, "assigned");
				ResultSet rst = pst.executeQuery();
				table.setModel(Rs2TableModel.resultSetToTableModel(rst, false));
				table.getColumnModel().getColumn(0).setPreferredWidth(25);
				table.getColumnModel().getColumn(4).setPreferredWidth(32);
				table.getColumnModel().getColumn(0).setHeaderValue("ID");
				table.getColumnModel().getColumn(1).setHeaderValue("PackageName");
				table.getColumnModel().getColumn(2).setHeaderValue("Origin");
				table.getColumnModel().getColumn(3).setHeaderValue("Destination");
				table.getColumnModel().getColumn(4).setHeaderValue("Weight");
				table.getColumnModel().getColumn(5).setHeaderValue("Price Offered $");
				pst.close();
				rst.close();
			}
			else{
				try {
					String query = "Select packageid,packagename,sender,receiver,weight,amount_to_pay from package_records where packageid IN(SELECT packageid FROM confrimations where pickerid = ?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, Mainlogin.usrname);
					ResultSet rst = pst.executeQuery();
					table.setModel(Rs2TableModel.resultSetToTableModel(rst, false));
					table.getColumnModel().getColumn(0).setPreferredWidth(25);
					table.getColumnModel().getColumn(4).setPreferredWidth(32);
					table.getColumnModel().getColumn(0).setHeaderValue("ID");
					table.getColumnModel().getColumn(1).setHeaderValue("PackageName");
					table.getColumnModel().getColumn(2).setHeaderValue("Origin");
					table.getColumnModel().getColumn(3).setHeaderValue("Destination");
					table.getColumnModel().getColumn(4).setHeaderValue("Weight");
					table.getColumnModel().getColumn(5).setHeaderValue("Price Offered $");
					pst.close();
					rst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

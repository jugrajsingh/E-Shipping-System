import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import tools.DBConnect;


public class SellerConfirmation extends JPanel {
	private JTable table;
	private JComboBox comboBox;
	private Connection connection;
	private JButton btnClose;
	private JButton btnRemoveSelectedBid;
	private JButton btnAssignedSelected;

	/**
	 * Create the panel.
	 */
	public SellerConfirmation() {
		connection = DBConnect.dbconnect();
		
		JScrollPane scrollPane = new JScrollPane();
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadtable(Integer.parseInt((String) comboBox.getSelectedItem()));
			}
		});
		
		btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
			}
		});
		
		btnRemoveSelectedBid = new JButton("Remove Selected Bid");
		btnRemoveSelectedBid.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemoveSelectedBid.setIcon(new ImageIcon(this.getClass().getResource("/Delete.png")));
		btnRemoveSelectedBid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
							if (Mainbody.tabbedPane.indexOfTab("Assignment Requests")>-1)
								Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Assignment Requests"));
							Mainbody.tabbedPane.addTab("Assignment Requests", new SellerConfirmation());
							Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Assignment Requests"));
							break;
						default:
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		btnAssignedSelected = new JButton("Assigned Selected");
		btnAssignedSelected.setIcon(new ImageIcon(this.getClass().getResource("/RequestAssignment.png")));
		btnAssignedSelected.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAssignedSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					try {
						int choice = JOptionPane.showConfirmDialog(null, "You REALLY Want to Assigned Your Package to Selected Picker?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						switch(choice){
						case JOptionPane.YES_OPTION:
							String query = "UPDATE package_records SET status = ?, pickerid = ?,amount_to_pay = ? WHERE packageid = ?";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, "assigned");
							pst.setString(2, (String) table.getValueAt(table.getSelectedRow(),1));
							pst.setDouble(3, Double.parseDouble(table.getValueAt(table.getSelectedRow(),6).toString()));
							pst.setInt(4, Integer.parseInt((String) comboBox.getSelectedItem()));
							pst.execute();
							pst.close();
							String query1 = "DELETE FROM bidding WHERE Bidon = ?";
							PreparedStatement pst1 = connection.prepareStatement(query1);
							pst1.setInt(1, Integer.parseInt((String) comboBox.getSelectedItem()));
							pst1.execute();
							pst1.close();
							if (Mainbody.tabbedPane.indexOfTab("Assignment Requests")>-1)
								Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Assignment Requests"));
							Mainbody.tabbedPane.addTab("Assignment Requests", new SellerConfirmation());
							Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Assignment Requests"));
							break;
						default:
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBox, 0, 89, Short.MAX_VALUE)
						.addComponent(btnRemoveSelectedBid, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnAssignedSelected, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRemoveSelectedBid)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAssignedSelected)
							.addPreferredGap(ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
							.addComponent(btnClose)))
					.addContainerGap())
		);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
		
		fillcombobox();
	}
	void fillcombobox(){
		try {
			String query = "select distinct packageid from package_records,bidding where packageid = bidon and userid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, Mainlogin.usrname);
			ResultSet rst = pst.executeQuery();
			while(rst.next()){
				comboBox.addItem(rst.getString("packageid"));
			}
			pst.close();
			rst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void loadtable(int pcode){
		try {
			String query = "select bidID,bidder,firstname,lastname,phone,email,biddingoffer from accounts,bidding where bidder = username and Bidon = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, pcode);
			ResultSet rst = pst.executeQuery();
			table.setModel(Rs2TableModel.resultSetToTableModel(rst, false));
			pst.close();
			rst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

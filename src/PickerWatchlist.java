import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import tools.DBConnect;


public class PickerWatchlist extends JPanel {
	private JTable table;
	private Connection connection;
	private JButton btnRequestAssignment;

	/**
	 * Create the panel.
	 */
	public PickerWatchlist() {
		connection = DBConnect.dbconnect();
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Reset.png")));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadtable();
				btnRequestAssignment.setEnabled(true);
			}
		});
		
		JButton btnGetDetails = new JButton("Get Details");
		btnGetDetails.setIcon(new ImageIcon(this.getClass().getResource("/GetDetails.png")));
		btnGetDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
						Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
					Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),1)));
					Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
				}
			}
		});
		
		JButton btnRemoveFromWatchlist = new JButton("Remove From Watchlist");
		btnRemoveFromWatchlist.setIcon(new ImageIcon(this.getClass().getResource("/Delete.png")));
		btnRemoveFromWatchlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					try {
						int choice = JOptionPane.showConfirmDialog(null, "You REALLY Want to Delete Selected Record?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						switch(choice){
						case JOptionPane.YES_OPTION:
							String query = "DELETE FROM watchlist WHERE idwatchlist = ?";
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
		});
		
		btnRequestAssignment = new JButton("Request Assignment");
		btnRequestAssignment.setIcon(new ImageIcon(this.getClass().getResource("/RequestAssignment.png")));
		btnRequestAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					JTextField jtf = new JTextField();
					int choice = JOptionPane.showConfirmDialog(null, jtf,"Enter Your Offer",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
					if(choice==JOptionPane.OK_OPTION){
						if(!jtf.getText().isEmpty()){
							try {
								String query = "INSERT INTO bidding(bidder, Bidon, biddingoffer) VALUES(?,?,?)";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.setString(1, Mainlogin.usrname);
								pst.setInt(2, (int) table.getValueAt(table.getSelectedRow(),1));
								pst.setString(3, jtf.getText());
								pst.execute();
								pst.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							try{
								String query = "select * from watchlist where user = ? and packageid = ?";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.setString(1, Mainlogin.usrname);
								pst.setInt(2, (int) table.getValueAt(table.getSelectedRow(),1));
								ResultSet rst = pst.executeQuery();
								if(rst.next()){
									btnRequestAssignment.setEnabled(false);
								}
								pst.close();
								rst.close();
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				if(table.getSelectedRow()>-1){
					if(evt.getClickCount()>=2){
						if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
							Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
						Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),1)));
						Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
					}
					if(evt.getClickCount()==1){
						try{
							String query = "select * from watchlist where user = ? and packageid = ?";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Mainlogin.usrname);
							pst.setInt(2, (int) table.getValueAt(table.getSelectedRow(),1));
							ResultSet rst = pst.executeQuery();
							if(rst.next()){
								btnRequestAssignment.setEnabled(false);
							}
							pst.close();
							rst.close();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		});
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedIndex());
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
						.addComponent(btnRemoveFromWatchlist, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnRequestAssignment, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
						.addComponent(btnGetDetails, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
						.addComponent(btnRefresh, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnRefresh)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnGetDetails)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRequestAssignment)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRemoveFromWatchlist)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnClose)))
					.addContainerGap())
		);
		setLayout(groupLayout);
		loadtable();
	}
	
	void loadtable(){
		try {
			String query = "select w.idwatchlist,w.packageid,p.packagename,p.amount_to_pay from watchlist as w,package_records as p where w.packageid = p.packageid and w.user = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, Mainlogin.usrname);
			ResultSet rst = pst.executeQuery();
			table.setModel(Rs2TableModel.resultSetToTableModel(rst, false));
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(0).setHeaderValue("ID");
			pst.close();
			rst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

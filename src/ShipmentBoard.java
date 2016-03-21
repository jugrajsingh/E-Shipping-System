import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import tools.DBConnect;


public class ShipmentBoard extends JPanel {
	private JTable table;
	private Connection connection;
	private JButton btnAddToWatch;
	/**
	 * Create the panel.
	 */
	public ShipmentBoard() {
		connection = DBConnect.dbconnect();
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				if(evt.getClickCount()>=2){
					if(table.getSelectedRow()>-1){
						if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
							Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
						Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),0)));
						Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
					}
				}
				if(evt.getClickCount()==1){
					if(table.getSelectedRow()>-1){
						try{
							String query = "select * from watchlist where user = ? and packageid = ?";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Mainlogin.usrname);
							pst.setInt(2, (int) table.getValueAt(table.getSelectedRow(),0));
							ResultSet rst = pst.executeQuery();
							if(rst.next()){
								btnAddToWatch.setText("Added to Watchlist");
								btnAddToWatch.setEnabled(false);
							}
							else{
								btnAddToWatch.setText("Add to Watchlist");
								btnAddToWatch.setEnabled(true);
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
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/Reset.png")));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				load_shipments();
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnRefresh);
		
		btnAddToWatch = new JButton("Add to Watchlist");
		btnAddToWatch.setIcon(new ImageIcon(this.getClass().getResource("/AddtoWatchlist.png")));
		btnAddToWatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					try {
						String query = "INSERT INTO watchlist (user, packageid) VALUES(?, ?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, Mainlogin.usrname);
						pst.setInt(2, (int) table.getValueAt(table.getSelectedRow(),0));
						pst.execute();
						pst.close();
						btnAddToWatch.setText("Added to Watchlist");
						btnAddToWatch.setEnabled(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnAddToWatch.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnAddToWatch);
		
		JButton btnDetails = new JButton("Details");
		btnDetails.setIcon(new ImageIcon(this.getClass().getResource("/GetDetails.png")));
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					if(Mainbody.tabbedPane.indexOfTab("Shipment Details")>-1)
						Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
					Mainbody.tabbedPane.addTab("Shipment Details", new ShipmentDetails((int) table.getValueAt(table.getSelectedRow(),0)));
					Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Shipment Details"));
					
				}
			}
		});
		btnDetails.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(btnDetails);
		
		JButton btnClose = new JButton("Close");
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedIndex());
			}
		});
		panel_1.add(btnClose);
		load_shipments();
	}
	void load_shipments(){
		try{
			String query = "select packageid,packagename,weight,amount_to_pay from package_records where status = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, "pending");
			ResultSet rst = pst.executeQuery();
			table.setModel(Rs2TableModel.resultSetToTableModel(rst,false));
			pst.close();
			rst.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

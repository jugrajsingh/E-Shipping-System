import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import tools.DBConnect;


public class PickerDashboard extends JPanel {
	private Connection connection;
	private JList notification;
	/**
	 * Create the panel.
	 */
	public PickerDashboard() {
		connection = DBConnect.dbconnect();
		
		JButton btnIndox = new JButton("Indox");
		btnIndox.setIcon(new ImageIcon(this.getClass().getResource("/Inbox.png")));
		btnIndox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("Inbox")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Inbox"));
				Mainbody.tabbedPane.addTab("Inbox", new Indox());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Inbox"));
			}
		});
		btnIndox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnwatchlist = new JButton("Watch List");
		btnwatchlist.setIcon(new ImageIcon(this.getClass().getResource("/GetDetails.png")));
		btnwatchlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("Watchlist")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Watchlist"));
				Mainbody.tabbedPane.addTab("Watchlist", new PickerWatchlist());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Watchlist"));
			}
		});
		btnwatchlist.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnFindShipments = new JButton("Find Shipments");
		btnFindShipments.setIcon(new ImageIcon(this.getClass().getResource("/Find.png")));
		btnFindShipments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("Find Shipment")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Find Shipment"));
				Mainbody.tabbedPane.addTab("Find Shipment", new ShipmentBoard());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Find Shipment"));
			}
		});
		btnFindShipments.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JScrollPane scrollPane = new JScrollPane();
		
		notification = new JList();
		notification.setFont(new Font("Tahoma", Font.PLAIN, 14));
		notification.setDoubleBuffered(true);
		notification.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
			}
		});
		notification.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(notification);
		
		JButton btnMyShipments = new JButton("My Shipments");
		btnMyShipments.setIcon(new ImageIcon(this.getClass().getResource("/MyShipments.png")));
		btnMyShipments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("My Shipments")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("My Shipments"));
				Mainbody.tabbedPane.addTab("My Shipments", new MyShipments());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("My Shipments"));
			}
		});
		btnMyShipments.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNotifications = new JLabel("Notifications");
		lblNotifications.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotifications.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnIndox, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
						.addComponent(btnMyShipments, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnwatchlist, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
						.addComponent(btnFindShipments, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNotifications, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
					.addGap(60))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addComponent(btnIndox, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
					.addGap(36)
					.addComponent(btnMyShipments, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
					.addGap(87))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(53)
					.addComponent(btnwatchlist, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
					.addGap(36)
					.addComponent(btnFindShipments, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(87))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNotifications)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(43))
		);
		setLayout(groupLayout);
		
		initalizenotification();
	}
	void initalizenotification(){
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		try {
			String query = "select * from messages where messagefor = ? and messagefrom <> ? order by time desc";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, Mainlogin.usrname);
			pst.setString(2, Mainlogin.usrname);
			ResultSet rst = pst.executeQuery();
			while(rst.next()){
				dlm.addElement(rst.getString("title"));
			}
			notification.setModel(dlm);
			pst.close();
			rst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import tools.DBConnect;


public class SellerDashboard extends JPanel {
	private Connection connection;
	private static Connection connection1;
	/**
	 * Create the panel.
	 */
	public SellerDashboard() {
		connection1 = DBConnect.dbconnect();
		connection = DBConnect.dbconnect();
		
		JButton btnNewEntry = new JButton("New Entry");
		btnNewEntry.setIcon(new ImageIcon(this.getClass().getResource("/NewEntry.png")));
		btnNewEntry.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("New Entry")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("New Entry"));
				Mainbody.tabbedPane.addTab("New Entry", new NewEntry());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("New Entry"));
			}
		});
		
		JButton btnOrderHistory = new JButton("Order History");
		btnOrderHistory.setIcon(new ImageIcon(this.getClass().getResource("/History.png")));
		btnOrderHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOrderHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Mainbody.tabbedPane.indexOfTab("Orders")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Orders"));
				Mainbody.tabbedPane.addTab("Orders",new OrderHistoryPanel());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Orders"));
				OrderHistoryPanel.btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Orders"));
					}
				});
			}
		});
		
		JButton btnUpdateOrder = new JButton("Update Order");
		btnUpdateOrder.setIcon(new ImageIcon(this.getClass().getResource("/UpdateOrder.png")));
		btnUpdateOrder.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("Update Order")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Update Order"));
				Mainbody.tabbedPane.addTab("Update Order", new UpdateOrderPanel());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Update Order"));
				fillcombobox("packageid",UpdateOrderPanel.comboBox);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnInbox = new JButton("Inbox");
		btnInbox.setIcon(new ImageIcon(this.getClass().getResource("/Inbox.png")));
		btnInbox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnInbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("Inbox")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Inbox"));
				Mainbody.tabbedPane.addTab("Inbox", new Indox());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Inbox"));
			}
		});
		
		JButton btnAssignmentRequests = new JButton("Assignment Requests");
		btnAssignmentRequests.setIcon(new ImageIcon(this.getClass().getResource("/RequestAssignment.png")));
		btnAssignmentRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Mainbody.tabbedPane.indexOfTab("Assignment Requests")>-1)
					Mainbody.tabbedPane.remove(Mainbody.tabbedPane.indexOfTab("Assignment Requests"));
				Mainbody.tabbedPane.addTab("Assignment Requests", new SellerConfirmation());
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Assignment Requests"));
			}
		});
		btnAssignmentRequests.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNotification = new JLabel("Notification");
		lblNotification.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotification.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnUpdateOrder, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnInbox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewEntry, GroupLayout.PREFERRED_SIZE, 99, Short.MAX_VALUE)
						.addComponent(btnAssignmentRequests, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnOrderHistory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(99)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
						.addComponent(lblNotification, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
					.addGap(73))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addComponent(lblNotification)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
					.addGap(51))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(btnNewEntry, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnInbox, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnUpdateOrder, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnOrderHistory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnAssignmentRequests, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(75))
		);
		setLayout(groupLayout);

	}
	public static void fillcombobox(String field,JComboBox combobox){
		String query = "SELECT * FROM package_records where userid = ?";
		try{
			PreparedStatement pst = connection1.prepareStatement(query);
			pst.setString(1, Mainlogin.usrname);
			ResultSet rst = pst.executeQuery();
			while(rst.next()){
				combobox.addItem(rst.getString(field));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

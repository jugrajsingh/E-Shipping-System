import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import tools.DBConnect;


public class ShipmentDetails extends JPanel {
	private JTextField Youroffer;
	private JTextField datelisted;
	private JTextField shipmentid;
	private JTextField weight;
	private Connection connection;
	private JLabel title;
	private JTextArea originarea;
	private JTextArea destinationarea;
	private JTextArea descriptionArea;
	private JTextArea comments;
	private JLabel lblCid;
	private JLabel lblCname;
	private JLabel labelstatus;
	private JButton btnAddToWatchlist;
	private JButton btnRequestAssignment;
	private JTextField AmountToPay;
	/**
	 * Create the panel.
	 */
	public ShipmentDetails(int x) {
		connection = DBConnect.dbconnect();
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		
		JLabel lblCustomerId = new JLabel("Customer ID :-");
		lblCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		lblCid = new JLabel("");
		lblCid.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblCustomerName = new JLabel("Customer Name :-");
		lblCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		lblCname = new JLabel("");
		
		JLabel lblOrigin = new JLabel("Origin :-");
		lblOrigin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblDestination = new JLabel("Destination:-");
		lblDestination.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JScrollPane scrollPane = new JScrollPane();
		
		originarea = new JTextArea();
		originarea.setWrapStyleWord(true);
		originarea.setLineWrap(true);
		originarea.setEditable(false);
		scrollPane.setViewportView(originarea);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		destinationarea = new JTextArea();
		scrollPane_2.setViewportView(destinationarea);
		destinationarea.setWrapStyleWord(true);
		destinationarea.setLineWrap(true);
		destinationarea.setEditable(false);
		
		JLabel lblWeight = new JLabel("Weight :-");
		lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		weight = new JTextField();
		weight.setEditable(false);
		weight.setColumns(10);
		
		JLabel lblStatus = new JLabel("Status:-");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		labelstatus = new JLabel("");
		labelstatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblAmountToPay = new JLabel("Amount to Pay:");
		lblAmountToPay.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		AmountToPay = new JTextField();
		AmountToPay.setEditable(false);
		AmountToPay.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblCustomerId)
							.addGap(10)
							.addComponent(lblCid, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(labelstatus, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
							.addGap(10))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblCustomerName)
							.addGap(10)
							.addComponent(lblCname, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
							.addGap(11))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblWeight, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(weight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblAmountToPay)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(AmountToPay, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
							.addGap(27))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblDestination, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(scrollPane_2, 0, 0, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(20)
									.addComponent(lblOrigin)
									.addGap(10)
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
							.addGap(70)))
					.addGap(0))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCustomerId)
						.addComponent(labelstatus, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStatus)
						.addComponent(lblCid, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCustomerName)
						.addComponent(lblCname, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOrigin)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(16)
							.addComponent(lblDestination)
							.addGap(11))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(11)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblWeight))
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(weight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblAmountToPay)
							.addComponent(AmountToPay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(4))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnBackToshipmentboard = new JButton("Back to Shipment board");
		btnBackToshipmentboard.setIcon(new ImageIcon(this.getClass().getResource("/Back.png")));
		btnBackToshipmentboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
				if(Mainbody.tabbedPane.indexOfTab("Find Shipment")<0){
					Mainbody.tabbedPane.addTab("Find Shipment", new ShipmentBoard());
				}
				Mainbody.tabbedPane.setSelectedIndex(Mainbody.tabbedPane.indexOfTab("Find Shipment"));
			}
		});
		panel_1.add(btnBackToshipmentboard, BorderLayout.EAST);
		
		JLabel lblShipmentTitle = new JLabel("Shipment Title :-");
		lblShipmentTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_1.add(lblShipmentTitle, BorderLayout.WEST);
		
		title = new JLabel("");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_1.add(title, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnComment = new JButton("Comment");
		btnComment.setIcon(new ImageIcon(this.getClass().getResource("/Comment.png")));
		btnComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField jtf = new JTextField();
				int choice = JOptionPane.showConfirmDialog(null, jtf,"Enter Your Comment",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
				if(choice==JOptionPane.OK_OPTION){
					if(!jtf.getText().isEmpty()){
						try{
							String query = "INSERT INTO comments(commenter, commentedon, comment) VALUES(?,?,?)";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Mainlogin.usrname);
							pst.setString(2, shipmentid.getText());
							pst.setString(3, jtf.getText());
							pst.execute();
							pst.close();
							loadcomments(Integer.parseInt(shipmentid.getText()));
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		});
		panel_2.add(btnComment);
		
		btnRequestAssignment = new JButton("Request Assignment");
		btnRequestAssignment.setIcon(new ImageIcon(this.getClass().getResource("/RequestAssignment.png")));
		btnRequestAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField jtf = new JTextField();
				int choice = JOptionPane.showConfirmDialog(null, jtf,"Enter Your Offer",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
				if(choice==JOptionPane.OK_OPTION){
					if(!jtf.getText().isEmpty()){
						try {
							String query = "INSERT INTO bidding(bidder, Bidon, biddingoffer) VALUES(?,?,?)";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Mainlogin.usrname);
							pst.setString(2, shipmentid.getText());
							pst.setString(3, jtf.getText());
							pst.execute();
							pst.close();
							loadpackagedata(Integer.parseInt(shipmentid.getText()));
						//	btnRequestAssignment.setEnabled(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		panel_2.add(btnRequestAssignment);
		
		btnAddToWatchlist = new JButton(" Add to Watchlist ");
		btnAddToWatchlist.setIcon(new ImageIcon(this.getClass().getResource("/AddtoWatchlist.png")));
		btnAddToWatchlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(labelstatus.getText().equals("pending")){
					try {
						String query = "INSERT INTO watchlist (user, packageid) VALUES(?, ?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, Mainlogin.usrname);
						pst.setString(2, shipmentid.getText());
						pst.execute();
						pst.close();
						btnAddToWatchlist.setText("Added to Watchlist");
						btnAddToWatchlist.setEnabled(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		panel_2.add(btnAddToWatchlist);
		
		JButton btnClose = new JButton("Close");
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
			}
		});
		panel_2.add(btnClose);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBasicDetails = new JLabel("Basic Details");
		lblBasicDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_3.add(lblBasicDetails, BorderLayout.NORTH);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblShipmentId = new JLabel("Shipment ID:-");
		panel_6.add(lblShipmentId, BorderLayout.NORTH);
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		shipmentid = new JTextField();
		shipmentid.setEditable(false);
		shipmentid.setColumns(15);
		panel_8.add(shipmentid, BorderLayout.NORTH);
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JLabel lblComments = new JLabel("Comments");
		panel_9.add(lblComments, BorderLayout.NORTH);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_9.add(scrollPane_3, BorderLayout.CENTER);
		
		comments = new JTextArea();
		comments.setLineWrap(true);
		comments.setWrapStyleWord(true);
		comments.setEditable(false);
		scrollPane_3.setViewportView(comments);
				
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7, BorderLayout.SOUTH);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		datelisted = new JTextField();
		datelisted.setFont(new Font("Tahoma", Font.PLAIN, 11));
		datelisted.setEditable(false);
		panel_7.add(datelisted);
		datelisted.setColumns(10);
		
		JLabel lblDateListed = new JLabel("Date Listed");
		panel_7.add(lblDateListed, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblescription = new JLabel("Description :-                  ");
		lblescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblescription.setIgnoreRepaint(true);
		panel_4.add(lblescription, BorderLayout.NORTH);
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblYourOffer = new JLabel("Your offer :-");
		panel_5.add(lblYourOffer);
		
		Youroffer = new JTextField();
		Youroffer.setEditable(false);
		panel_5.add(Youroffer, BorderLayout.SOUTH);
		Youroffer.setColumns(10);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		panel_4.add(scrollPane_4);
		
		descriptionArea = new JTextArea();
		descriptionArea.setEditable(false);
		scrollPane_4.setViewportView(descriptionArea);
		
		loadpackagedata(x);
		loadcomments(x);
	}
	public void loadpackagedata(int pcode){
		try {
			String query = "select * from package_records where packageid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, pcode);
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				title.setText(rst.getString("packagename"));
				shipmentid.setText(rst.getString("packageid"));
				originarea.setText(rst.getString("sender"));
				destinationarea.setText(rst.getString("receiver"));
				weight.setText(rst.getString("weight"));
				descriptionArea.setText(rst.getString("Description"));
				datelisted.setText(rst.getString("entry_time"));
				lblCid.setText(rst.getString("userid"));
				labelstatus.setText(rst.getString("status"));
				AmountToPay.setText(rst.getString("amount_to_pay")+ " $");
			}
			pst.close();
			rst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			String query = "SELECT firstname,lastname FROM accounts where username = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, lblCid.getText());
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				lblCname.setText(rst.getString("firstname")+" "+ rst.getString("lastname"));
			}
			pst.close();
			rst.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		try{
			String query = "select * from watchlist where user = ? and packageid = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, Mainlogin.usrname);
			pst.setString(2, shipmentid.getText());
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				btnAddToWatchlist.setText("Added to Watchlist");
				btnAddToWatchlist.setEnabled(false);
			}
			pst.close();
			rst.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			String query = "SELECT * FROM bidding where bidder = ? and Bidon = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, Mainlogin.usrname);
			pst.setInt(2, pcode);
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				Youroffer.setText(rst.getString("biddingoffer")+ " $");
				btnRequestAssignment.setEnabled(false);
			}else{
				btnRequestAssignment.setEnabled(true);
			}
			pst.close();
			rst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(labelstatus.getText().equals("pending")){
		}
		else{
			btnRequestAssignment.setEnabled(false);
			btnAddToWatchlist.setEnabled(false);
		}
	}
	public void loadcomments(int cid){
		try {
			String query = "SELECT * FROM comments where commentedon = ? order by timeofcomment desc";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, cid);
			ResultSet rst = pst.executeQuery();
			comments.setText("");
			while(rst.next()){
				comments.append(rst.getString("commenter")+" : \n      "+rst.getString("comment")+"\n");
			}
			pst.close();
			rst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import tools.DBConnect;


public class Indox extends JPanel {
	private JLabel lblUser;
	private JTextField fromField;
	private JTextField vsubjectField;
	private JTextField toField;
	private JTextField subjectField;
	private JComboBox comboBox;
	private Connection connection;
	private JTable table;
	private JTextArea vmessageArea;
	private JTextArea messageArea;
	private CardLayout clayout;
	private JButton btnSave;
	private JButton btnSend;
	private JButton btnDeleteMessage;
	private static int card =1,lastmessageloaded;

	/**
	 * Create the panel.
	 */
	public Indox() {
		connection = DBConnect.dbconnect();
		
		JScrollPane scrollPane = new JScrollPane();
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String loadlist;
				PreparedStatement pst;
				try{
					if(comboBox.getSelectedIndex()==0){
						loadlist = "select id,title from messages where messagefor = ? and messagefrom <> ? order by time desc";
						pst = connection.prepareStatement(loadlist);
						pst.setString(1, Mainlogin.usrname);
						pst.setString(2, Mainlogin.usrname);
					}
					else if(comboBox.getSelectedIndex()==1){
						loadlist = "select id,title from messages where messagefrom = ? and status = ? order by time desc";
						pst = connection.prepareStatement(loadlist);
						pst.setString(1, Mainlogin.usrname);
						pst.setString(2, "sent");
					}
					else if(comboBox.getSelectedIndex()==2){
						loadlist = "select id,title from messages where messagefor = ? and status = ? order by time desc";
						pst = connection.prepareStatement(loadlist);
						pst.setString(1, Mainlogin.usrname);
						pst.setString(2, "saved");
					}
					else{
						loadlist = "SELECT id,title FROM messages where messagefrom = ? or messagefor = ?";
						pst = connection.prepareStatement(loadlist);
						pst.setString(1, Mainlogin.usrname);
						pst.setString(2, Mainlogin.usrname);
					}
					ResultSet rst = pst.executeQuery();
					table.setModel(Rs2TableModel.resultSetToTableModel(rst,false));
					table.getColumnModel().getColumn(0).setPreferredWidth(25);
					table.getColumnModel().getColumn(0).setHeaderValue("ID");
					table.getColumnModel().getColumn(1).setHeaderValue("Message");
					pst.close();
					rst.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Indox", "Sent", "Saved", "All Messages"}));
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("PopupMenu.border"));
		
		JButton btnNewMessage = new JButton("New Message");
		btnNewMessage.setIcon(new ImageIcon(this.getClass().getResource("/NewMessage.png")));
		btnNewMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clayout.show(panel, "newpanel");
				card=2;
				btnSave.setText("Save");
				btnSave.setEnabled(true);
				btnSend.setEnabled(true);
			}
		});
		btnNewMessage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnClose = new JButton("Close");
		btnClose.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mainbody.tabbedPane.remove(Mainbody.tabbedPane.getSelectedComponent());
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnSend = new JButton("Send");
		btnSend.setIcon(new ImageIcon(this.getClass().getResource("/Send.png")));
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(Mainlogin.isfieldsempty(toField,subjectField)){
					}
					else if(checkuser(toField.getText())){
						String query = "INSERT INTO messages (messagefrom, messagefor, title, body, status) VALUES(?,?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, Mainlogin.usrname);
						pst.setString(2, toField.getText());
						pst.setString(3, subjectField.getText());
						pst.setString(4, messageArea.getText());
						pst.setString(5, "sent");
						pst.execute();
						pst.close();
						btnSend.setEnabled(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnDeleteMessage = new JButton("Delete Message");
		btnDeleteMessage.setIcon(new ImageIcon(this.getClass().getResource("/Delete.png")));
		btnDeleteMessage.setEnabled(false);
		btnDeleteMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>-1){
					try {
						int choice = JOptionPane.showConfirmDialog(null, "You REALLY Want to Delete Selected Message?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						switch(choice){
						case JOptionPane.YES_OPTION:
							String query = "DELETE FROM messages WHERE id = ?";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setInt(1, (int) table.getValueAt(table.getSelectedRow(),0));
							pst.execute();
							pst.close();
							btnDeleteMessage.setEnabled(false);
							break;
						default:
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnDeleteMessage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon(this.getClass().getResource("/Save.png")));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(card==1){
					if(Mainlogin.isfieldsempty(fromField,vsubjectField)){
					}
					else{
						try {
							String query = "INSERT INTO messages (messagefrom, messagefor, title, body, status) VALUES(?,?,?,?,?)";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, fromField.getText());
							pst.setString(2, Mainlogin.usrname);
							pst.setString(3, vsubjectField.getText());
							pst.setString(4, vmessageArea.getText());
							pst.setString(5, "saved");
							pst.execute();
							pst.close();
							btnSave.setText("Saved");
							btnSave.setEnabled(false);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				else{
					if(Mainlogin.isfieldsempty(subjectField,toField)){
					}
					else{
						try {
							String query = "INSERT INTO messages (messagefrom, messagefor, title, body, status) VALUES(?,?,?,?,?)";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Mainlogin.usrname);
							pst.setString(2, Mainlogin.usrname);
							pst.setString(3, subjectField.getText());
							pst.setString(4, messageArea.getText());
							pst.setString(5, "saved");
							pst.execute();
							pst.close();
							btnSave.setText("Saved");
							btnSave.setEnabled(false);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		lblUser = new JLabel("");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(panel, GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(btnNewMessage)
									.addGap(18)
									.addComponent(lblUser, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(btnDeleteMessage)))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnSave)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnSend)
							.addGap(10)
							.addComponent(btnClose)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewMessage)
						.addComponent(btnDeleteMessage)
						.addComponent(lblUser, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSave)
						.addComponent(btnSend, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				if(evt.getClickCount()>=2){
					if(table.getSelectedRow()>-1){
						loadmessage((int) table.getValueAt(table.getSelectedRow(),0));
						clayout.show(panel, "viewpanel");
						card=1;
						btnSend.setEnabled(false);
					}
				}
			}
		});
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		clayout = new CardLayout(0, 0);
		panel.setLayout(clayout);
		
		JPanel ViewMessages = new JPanel();
		ViewMessages.setName("viewpanel");
		panel.add(ViewMessages,"viewpanel");
		
		JLabel lblFrom = new JLabel("From:- ");
		
		fromField = new JTextField();
		fromField.setName("From");
		fromField.setEditable(false);
		fromField.setColumns(10);
		
		JLabel lblSubject = new JLabel("Subject:- ");
		
		vsubjectField = new JTextField();
		vsubjectField.setName("Subject");
		vsubjectField.setEditable(false);
		vsubjectField.setColumns(10);
		
		JLabel lblvMessage = new JLabel("Message:- ");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		vmessageArea = new JTextArea();
		vmessageArea.setName("Message Body");
		vmessageArea.setWrapStyleWord(true);
		vmessageArea.setLineWrap(true);
		vmessageArea.setOpaque(false);
		vmessageArea.setEditable(false);
		scrollPane_1.setViewportView(vmessageArea);
		
		JButton btnForward = new JButton("Forward");
		btnForward.setIcon(new ImageIcon(this.getClass().getResource("/Forward.png")));
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toField.setText("");
				subjectField.setText(vsubjectField.getText());
				messageArea.setText(vmessageArea.getText());
				clayout.show(panel, "newpanel");
				card=2;
				btnSave.setText("Save");
				btnSave.setEnabled(true);
				btnSend.setEnabled(true);
			}
		});
		btnForward.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GroupLayout gl_ViewMessages = new GroupLayout(ViewMessages);
		gl_ViewMessages.setHorizontalGroup(
			gl_ViewMessages.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewMessages.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_ViewMessages.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ViewMessages.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblvMessage))
						.addGroup(gl_ViewMessages.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblFrom)
							.addComponent(lblSubject)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_ViewMessages.createParallelGroup(Alignment.TRAILING)
						.addComponent(vsubjectField, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addGroup(gl_ViewMessages.createSequentialGroup()
							.addComponent(fromField, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
							.addGap(166)))
					.addGap(37)
					.addComponent(btnForward)
					.addContainerGap())
		);
		gl_ViewMessages.setVerticalGroup(
			gl_ViewMessages.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ViewMessages.createSequentialGroup()
					.addGroup(gl_ViewMessages.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ViewMessages.createSequentialGroup()
							.addGap(14)
							.addGroup(gl_ViewMessages.createParallelGroup(Alignment.BASELINE)
								.addComponent(fromField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFrom))
							.addGroup(gl_ViewMessages.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_ViewMessages.createSequentialGroup()
									.addGap(17)
									.addComponent(lblSubject))
								.addGroup(gl_ViewMessages.createSequentialGroup()
									.addGap(14)
									.addComponent(vsubjectField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(11)
							.addGroup(gl_ViewMessages.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
								.addComponent(lblvMessage)))
						.addGroup(Alignment.TRAILING, gl_ViewMessages.createSequentialGroup()
							.addContainerGap(163, Short.MAX_VALUE)
							.addComponent(btnForward)))
					.addContainerGap())
		);
		ViewMessages.setLayout(gl_ViewMessages);
		
		JPanel NewMessage = new JPanel();
		NewMessage.setName("newpanel");
		panel.add(NewMessage,"newpanel");
		
		JLabel lblTo = new JLabel("To:- ");
		
		toField = new JTextField();
		toField.setName("To Field");
		toField.setColumns(10);
		
		JLabel lblSubject_1 = new JLabel("Subject:- ");
		
		subjectField = new JTextField();
		subjectField.setName("Subject");
		subjectField.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message:-");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		messageArea = new JTextArea();
		messageArea.setName("Message Body");
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		scrollPane_2.setViewportView(messageArea);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/Close.png")));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadmessage(lastmessageloaded);
				clayout.show(panel, "viewpanel");
				card=1;
				btnSend.setEnabled(false);
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnReset = new JButton("Reset");
		btnReset.setIcon(new ImageIcon(this.getClass().getResource("/Reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toField.setText("");
				messageArea.setText("");
				subjectField.setText("");
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GroupLayout gl_NewMessage = new GroupLayout(NewMessage);
		gl_NewMessage.setHorizontalGroup(
			gl_NewMessage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_NewMessage.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_NewMessage.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTo)
						.addComponent(lblSubject_1)
						.addComponent(lblMessage))
					.addGap(10)
					.addGroup(gl_NewMessage.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_NewMessage.createSequentialGroup()
							.addComponent(toField, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
							.addGap(219))
						.addGroup(Alignment.TRAILING, gl_NewMessage.createSequentialGroup()
							.addGroup(gl_NewMessage.createParallelGroup(Alignment.TRAILING)
								.addComponent(subjectField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_NewMessage.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_NewMessage.setVerticalGroup(
			gl_NewMessage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_NewMessage.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_NewMessage.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTo)
						.addComponent(toField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_NewMessage.createParallelGroup(Alignment.BASELINE)
						.addComponent(subjectField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSubject_1))
					.addGap(18)
					.addGroup(gl_NewMessage.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_NewMessage.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
							.addComponent(lblMessage))
						.addGroup(gl_NewMessage.createSequentialGroup()
							.addComponent(btnReset)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCancel)))
					.addContainerGap())
		);
		NewMessage.setLayout(gl_NewMessage);
		setLayout(groupLayout);
		
		lblUser.setText("Indox for :-  " + Mainlogin.usrname.toUpperCase());
	}
	
	void loadmessage(int mid){
		try {
			String query = "select * from messages where id = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, mid);
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				fromField.setText(rst.getString("messagefrom"));
				vsubjectField.setText(rst.getString("title"));
				vmessageArea.setText(rst.getString("body"));
				if(rst.getString("status").equals("saved")){
					btnSave.setText("Saved");
					btnSave.setEnabled(false);
				}
				else{
					btnSave.setText("Save");
					btnSave.setEnabled(true);
				}
				btnDeleteMessage.setEnabled(true);
			}
			lastmessageloaded = mid;
			pst.close();
			rst.close();
			lblUser.setText("Indox for :-  " + Mainlogin.usrname.toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	boolean checkuser(String user){
		try {
			String query = "select * from accounts where username = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, user);
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "User NOT Found Please Check");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

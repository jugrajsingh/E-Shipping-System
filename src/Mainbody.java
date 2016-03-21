import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import tools.DBConnect;

public class Mainbody extends JFrame {

	private JPanel contentPane;
	public static boolean newentryexist = false;
	public static JTabbedPane tabbedPane;
	public Mainbody() {
		setTitle("E-Shipping Service");
		DBConnect.dbconnect();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 460);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Main");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.setIcon(new ImageIcon(this.getClass().getResource("/Logout.png")));
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				JOptionPane.showMessageDialog(null, "You Are Logged Out","Logout",0,new ImageIcon(this.getClass().getResource("/LogoutBig.png")));
				Mainlogin m = new Mainlogin();
				m.frame.setVisible(true);
			}
		});
		mnNewMenu.add(mntmLogout);
		
				JSeparator separator = new JSeparator();
				mnNewMenu.add(separator);
		
		JSeparator separator_3 = new JSeparator();
		mnNewMenu.add(separator_3);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(this.getClass().getResource("/Exit.png")));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmExit);
		
		JMenu mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);

		JMenuItem mntmGeneral = new JMenuItem("General");
		mntmGeneral.setIcon(new ImageIcon(this.getClass().getResource("/Setting.png")));
		mntmGeneral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				UserAccount useraccount = new UserAccount();
				useraccount.setVisible(true);
			}
		});
		mnAccount.add(mntmGeneral);
		
		JMenuItem menuItem = new JMenuItem("Logout");
		menuItem.setIcon(new ImageIcon(this.getClass().getResource("/Logout.png")));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				JOptionPane.showMessageDialog(null, "You Are Logged Out","Logout",0,new ImageIcon(this.getClass().getResource("/LogoutBig.png")));
				Mainlogin m = new Mainlogin();
				m.frame.setVisible(true);
			}
		});
		
		JSeparator separator_2 = new JSeparator();
		mnAccount.add(separator_2);

		JSeparator separator_1 = new JSeparator();
		mnAccount.add(separator_1);
		mnAccount.add(menuItem);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{684, 0};
		gbl_contentPane.rowHeights = new int[]{60, 340, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel title = new JLabel("Welcome to The E-shipping Service");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.fill = GridBagConstraints.BOTH;
		gbc_title.insets = new Insets(0, 0, 5, 0);
		gbc_title.gridx = 0;
		gbc_title.gridy = 0;
		contentPane.add(title, gbc_title);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		contentPane.add(tabbedPane, gbc_tabbedPane);

		if(Mainlogin.uniqueid.equals("seller")){
			tabbedPane.add("main",new SellerDashboard());
		}
		else{
			tabbedPane.addTab("main", new PickerDashboard());
		}
	}
}


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class POS extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/pos?useSSL=false";
					  
	//Database credentials
	static final String USER = "root";
	static final String PASS = "42597";
	Connection conn = null;
	Statement stmt = null;
	
	//Create all necessary jpanels
	JPanel jframe;
    JPanel tabs;
    JPanel buttons;
    JPanel settingsTab;
    JPanel curTransTab;
    JPanel cloTransTab;
    JPanel dailyTab;

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new POS();
            }
        });
	}

	public POS(){
		//Create window using jframe
		JFrame frame = new JFrame("Nail Tech POS System");
	
		frame.setVisible(true);
		frame.setSize(1000, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		CardLayout cl = new CardLayout();
		
		//initiate panels
		jframe = new JPanel(new BorderLayout());
	    tabs = new JPanel(cl);
	    buttons = new JPanel();
	    settingsTab = new JPanel();
	    curTransTab = new JPanel();
	    cloTransTab = new JPanel();
	    dailyTab = new JPanel();
        
        //Create buttons to switch between cards in cardlayout
        JButton currentTransactionsBtn = new JButton("Current Transaction");
        JButton closedTransactionsBtn = new JButton("Closed Transactions");
        JButton dailyReportsBtn = new JButton("Daily Reports");
        JButton settingsBtn = new JButton("Settings");
        
        //Create buttons for settings tab
        JButton addServiceBtn = new JButton("Add Service");
        JButton removeServiceBtn = new JButton("Remove Service");
        
        settingsBtn.setPreferredSize(new Dimension(150, 80));
        currentTransactionsBtn.setPreferredSize(new Dimension(150, 80));
        closedTransactionsBtn.setPreferredSize(new Dimension(150, 80));
        dailyReportsBtn.setPreferredSize(new Dimension(150, 80));
        
        //populate settings tabs
        settingsTab.setLayout(new BoxLayout(settingsTab, BoxLayout.Y_AXIS));
        
        settingsTab.add(addServiceBtn);
        addServiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addServiceBtn.setMaximumSize(new Dimension(200, 100));
        
        settingsTab.add(removeServiceBtn);
        removeServiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeServiceBtn.setMaximumSize(new Dimension(200, 100));
        
        //Add jpanels to the tabs jpanel, which is using cardLayout
        tabs.add(settingsTab, "Settings");
        tabs.add(curTransTab, "Current Transactions");
        tabs.add(cloTransTab, "Closed Transactions");
        tabs.add(dailyTab, "Daily Reports");
        
        //Add existing buttons to the buttons jpanel
        buttons.add(currentTransactionsBtn);
        buttons.add(closedTransactionsBtn);
        buttons.add(dailyReportsBtn);
        buttons.add(settingsBtn);
        buttons.setBackground(Color.GRAY);
        
        //Set up buttons to switch jpanels based on which button was clicked
        settingsBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(tabs, "Settings");
				
			}
        });
        
        currentTransactionsBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(tabs, "Current Transactions");
				
			}
        });
        
        closedTransactionsBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(tabs, "Closed Transactions");
				
			}
        });
        
        dailyReportsBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(tabs, "Daily Reports");
				
			}
        });
        
        //Choose tab to display on startup
        cl.show(tabs, "Current Transactions");
        
        //Add the tabs and buttons jpanel to the main jframe panel
        jframe.add(tabs, BorderLayout.CENTER);
        jframe.add(buttons, BorderLayout.PAGE_START);
        
        //Add the main panel (jframe) to JFrame
        frame.add(jframe);
        //connectDB();
	}
	
	public static void connectDB() {
		try {
			System.out.println("Connecting.");
			DriverManager.getConnection(DB_URL, USER, PASS );
			System.out.println("Connected.");
			
		} catch (SQLException e) { 
			System.out.println(e.getMessage());
			System.out.println("Error connecting.");
		}
	}
	
	public void addService(){
		
		
	}
	
}


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class POS extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/pos?useSSL=false";
					  
	//Database credentials
	static final String USER = "root";
	static final String PASS = "42597";
	static Connection conn = null;
	static Statement stmt = null;
	
	//Create all necessary jpanels
	JPanel jframe;
    JPanel tabs;
    JPanel buttons;
    JPanel curTransTab;
    JPanel cloTransTab;
    JPanel dailyTab;
    
  //Create buttons to switch between cards in cardlayout
    JButton currentTransactionsBtn;
    JButton closedTransactionsBtn;
    JButton dailyReportsBtn;
    JButton fullSet, fillIn, manicure, pedicure, manPed, nailRepair, 
    polishCh;

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new POS();
                try {
					retrievePrice();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	    curTransTab = new JPanel();
	    cloTransTab = new JPanel();
	    dailyTab = new JPanel();
        
        //Create buttons to switch between cards in cardlayout
        currentTransactionsBtn = new JButton("Current Transaction");
        closedTransactionsBtn = new JButton("Closed Transactions");
        dailyReportsBtn = new JButton("Daily Reports");
        
        //Create buttons for actual services
        fullSet = new JButton("Full Set");
        fillIn = new JButton("Fill-In");
        manicure = new JButton("Manicure");
        pedicure = new JButton("Pedicure");
        manPed = new JButton("Mani/Pedi");
        nailRepair = new JButton("Nail Repair");
        polishCh = new JButton("Polish Change");
        
        
        currentTransactionsBtn.setPreferredSize(new Dimension(150, 80));
        closedTransactionsBtn.setPreferredSize(new Dimension(150, 80));
        dailyReportsBtn.setPreferredSize(new Dimension(150, 80));
        
        //Add service buttons to current transaction tab
        curTransTab.setLayout(new GridLayout(0,2));
        
        curTransTab.add(fullSet);
        curTransTab.add(fillIn);
        curTransTab.add(manicure);
        curTransTab.add(pedicure);
        curTransTab.add(manPed);
        curTransTab.add(nailRepair);
        curTransTab.add(polishCh);
        
        //Add jpanels to the tabs jpanel, which is using cardLayout
        tabs.add(curTransTab, "Current Transactions");
        tabs.add(cloTransTab, "Closed Transactions");
        tabs.add(dailyTab, "Daily Reports");
        
        //Add existing buttons to the buttons jpanel
        buttons.add(currentTransactionsBtn);
        buttons.add(closedTransactionsBtn);
        buttons.add(dailyReportsBtn);
        buttons.setBackground(Color.GRAY);
        
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
	}
	
	public static void retrievePrice() throws SQLException{
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("select * from Services");
	        while (rs.next()) {
	            String service = rs.getString("Service");
	            double price = rs.getDouble("Price");
	            System.out.println(service + "\t\t$" + price);
	        }
	    } catch (SQLException e ) {
	        e.getMessage();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }

	}
}
	
	


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

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
    JPanel endBtns;
    JPanel total;
    
    //Create jlabel to display the total price
    JLabel totalLabel;
    double totalPrice = 0.0;
    
    //Labels to keep track of user and customer
    JLabel user, customer, currentTime;
    
    //Create buttons to switch between cards in cardlayout
    JButton currentTransactionsBtn;
    JButton closedTransactionsBtn;
    JButton dailyReportsBtn;
    JButton fullSet, fillIn, manicure, pedicure, manPed, nailRepair, 
    polishCh, voidSelection, voidAll;
    
    //Create a table
    private String[] colNames = { "Service", "Price"};
    private DefaultTableModel model = new DefaultTableModel(colNames, 0);
    private JTable table = new JTable(model);

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
	    curTransTab = new JPanel();
	    cloTransTab = new JPanel();
	    dailyTab = new JPanel();
	    endBtns = new JPanel();
	    total = new JPanel();
        
        //Create buttons to switch between cards in cardlayout
        currentTransactionsBtn = new JButton("Current Transaction");
        closedTransactionsBtn = new JButton("Closed Transactions");
        dailyReportsBtn = new JButton("Daily Reports");
        
        //Create buttons for actual services
        fullSet = new JButton("Full Set");
        fullSet.setFont(new Font("Arial", Font.PLAIN, 25));
        fillIn = new JButton("Fill-In");
        fillIn.setFont(new Font("Arial", Font.PLAIN, 25));
        manicure = new JButton("Manicure");
        manicure.setFont(new Font("Arial", Font.PLAIN, 25));
        pedicure = new JButton("Pedicure");
        pedicure.setFont(new Font("Arial", Font.PLAIN, 25));
        manPed = new JButton("Mani/Pedi");
        manPed.setFont(new Font("Arial", Font.PLAIN, 25));
        nailRepair = new JButton("Nail Repair");
        nailRepair.setFont(new Font("Arial", Font.PLAIN, 25));
        polishCh = new JButton("Polish Change");
        polishCh.setFont(new Font("Arial", Font.PLAIN, 25));
        voidSelection = new JButton("Void Selection");
        voidSelection.setFont(new Font("Arial", Font.PLAIN, 25));
        voidAll = new JButton("Void Transaction");
        voidAll.setFont(new Font("Arial", Font.PLAIN, 25));

        currentTransactionsBtn.setPreferredSize(new Dimension(150, 80));
        closedTransactionsBtn.setPreferredSize(new Dimension(150, 80));
        dailyReportsBtn.setPreferredSize(new Dimension(150, 80));
        
        //Add service buttons to current transaction tab
        curTransTab.setLayout(new GridLayout(0,2));
     
        curTransTab.add(new JScrollPane(table));
        curTransTab.add(total);
        curTransTab.add(fullSet);
        curTransTab.add(fillIn);
        curTransTab.add(manicure);
        curTransTab.add(pedicure);
        curTransTab.add(manPed);
        curTransTab.add(nailRepair);
        curTransTab.add(polishCh);
        curTransTab.add(endBtns, BorderLayout.PAGE_END);
        
        //Add jpanels to the tabs jpanel, which is using cardLayout
        tabs.add(curTransTab, "Current Transactions");
        tabs.add(cloTransTab, "Closed Transactions");
        tabs.add(dailyTab, "Daily Reports");
        
        //Initialize labels that are responsible for the display of the total
        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        
        //Labels that handle USER and CUSTOMER
        user = new JLabel("User: ");
        user.setFont(new Font("Arial", Font.BOLD, 22));
        user.setForeground(Color.WHITE);
        customer = new JLabel("Customer: ");
        customer.setFont(new Font("Arial", Font.BOLD, 22));
        customer.setForeground(Color.CYAN);
        
        //Create running clock
        currentTime = new JLabel(timestamp());
        int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	currentTime.setText(timestamp());
            }
        };
        new Timer(delay, taskPerformer).start();
        //currentTime = new JLabel(timestamp());
        currentTime.setFont(new Font("Arial", Font.BOLD, 22));
        currentTime.setForeground(Color.WHITE);
        
        
        
        //Add existing buttons to the buttons jpanels
        buttons.add(currentTransactionsBtn);
        buttons.add(closedTransactionsBtn);
        buttons.add(dailyReportsBtn);
        buttons.setBackground(Color.DARK_GRAY);
        
        endBtns.add(voidSelection);
        endBtns.add(voidAll);
        endBtns.add(customer, BorderLayout.PAGE_END);
        endBtns.add(user, BorderLayout.PAGE_END);
        endBtns.add(currentTime, BorderLayout.PAGE_END);
        endBtns.setBackground(Color.DARK_GRAY);
        
        //Add total label to the total panel
        total.add(totalLabel);
        
        //Change table text size
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        
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
        
        //***************************Provide service buttons with an action***************************
        
        manicure.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Regular", "No Chip" };
				
				double price;
				String typeString;

			    int type = JOptionPane.showOptionDialog(null, "Regular or No Chip Manicure?", "Manicure",
			        JOptionPane.DEFAULT_OPTION, 0, null, buttons, buttons[0]);

			    if( type == 1 ){
			    	typeString = "No Chip Manicure";
			    	try {
						price = retrievePrice("No Chip Mani");
						model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			    else{
			    	typeString = "Reg. Manicure ";
			    	try {
			    		price = retrievePrice("Manicure");
			    		model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
			    		totalPrice += price;
			    		totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			}
        });
        	
        manPed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				double price;
				String typeString = "Mani/Pedi";
				
				try {
					price = retrievePrice("Mani/Pedi");
					model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
					totalPrice += price;
					totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        });
        
        polishCh.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Nails", "Toes" };
				double price;
				String typeString;

			    int type = JOptionPane.showOptionDialog(null, "", "Polish Change",
			        JOptionPane.DEFAULT_OPTION, 0, null, buttons, buttons[0]);

			    if( type == 1 ){
			    	typeString = "Toes Polish Change";
			    	try {
						price = retrievePrice("Toes Polish Change");
						model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			    else{
			    	typeString = "Nails Polish Change";
			    	try {
			    		price = retrievePrice("Nails Polish Change");
			    		model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			}
        });
        
        pedicure.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Regular", "No Chip" };
				String typeString;
				double price;

			    int type = JOptionPane.showOptionDialog(null, "Regular or No Chip Pedicure?", "Pedicure",
			        JOptionPane.DEFAULT_OPTION, 0, null, buttons, buttons[0]);

			    if( type == 1 ){
			    	typeString = "No Chip Pedicure";
			    	try {
						price = retrievePrice("No Chip Pedi");
						model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			    else{
			    	typeString = "Reg. Pedicure";
			    	try {
			    		price = retrievePrice("Pedicure");
			    		model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			}
        });
        
        nailRepair.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String typeString;
				double price;
				try {
					typeString = "Nail Repair";
					price = retrievePrice("Nail Repair");
					model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
					totalPrice += price;
					totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        });
        
        fullSet.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Pink & White", "Gel", "Acrylic" };
				String typeString;
				double price;

			    int type = JOptionPane.showOptionDialog(null, "What kind of Full-Set?", "Full-Set",
			        JOptionPane.DEFAULT_OPTION, 0, null, buttons, buttons[1]);

			    if( type == 0 ){
			    	typeString = "Pink & White Full";
			    	try {
						price = retrievePrice("Pink & White Full");
						model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			    else if(type == 1){
			    	typeString = "Gel Full-Set";
			    	try {
			    		price = retrievePrice("Gel Full-Set");
			    		model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
			    		totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			    else{
			    	typeString = "Acrylic Full-Set";
			    	try {
			    		price = retrievePrice("Acrylic Full-Set");
			    		model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
			    		totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			}
        });
        
        fillIn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Pink & White", "Gel", "Acrylic" };
				String typeString;
				double price;

			    int type = JOptionPane.showOptionDialog(null, "What kind of Fill-In?", "Fill-In",
			        JOptionPane.DEFAULT_OPTION, 0, null, buttons, buttons[1]);

			    if( type == 0 ){
			    	typeString = "Pink & White Fill-In";
			    	try {
						price = retrievePrice("Pink & White Fill-In");
						model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			    else if(type == 1){
			    	try {
			    		typeString = "Gel Fill-In";
			    		price = retrievePrice("Gel Fill-In");
			    		model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			    else{
			    	typeString = "Acrylic Fill-In";
			    	try {
			    		price = retrievePrice("Acrylic Fill-In");
			    		model.addRow(new Object[]{typeString, String.format("$%10.2f", price)});
						totalPrice += price;
						totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}
			    }
			}
        });
        
        voidSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				double priceToSubtract = 0;
				
				if (table.getSelectedRow() != -1) {
					priceToSubtract = Double.parseDouble(((String) table.getValueAt(table.getSelectedRow(), 1)).substring(1));
		            // remove selected row from the model
		            model.removeRow(table.getSelectedRow());
		        }
				
				totalPrice -= priceToSubtract;
				totalLabel.setText(String.format("Total: $" + "%-10.2f", totalPrice));
			}
        });
        
        voidAll.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(null, "Void Transaction?", "Confirmation",
				        JOptionPane.DEFAULT_OPTION, 0, null, new String[] {"Yes", "No"}, "No");
				if(result == 0) {
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
				} 
				totalPrice = 0;
				totalLabel.setText(String.format("Total: $" + "%-10.2f", 0.00));
			}
        });
      //********************************************************************************************
        
        
        //Choose tab to display on startup
        cl.show(tabs, "Current Transactions");
        
        //Add the tabs and buttons jpanel to the main jframe panel
        jframe.add(tabs, BorderLayout.CENTER);
        jframe.add(buttons, BorderLayout.PAGE_START);
        
        //Add the main panel (jframe) to JFrame
        frame.add(jframe);
	}
	
	public static double retrievePrice(String whichService) throws SQLException{
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("select * from Services where Service = '" + whichService + "'");
	        while (rs.next()) {
	            double price = rs.getDouble("Price");
	            return price;
	        }
	    } catch (SQLException e ) {
	        e.getMessage();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
		return 0;
	}
	
	/**
	 * A method that retrieves current time and date to use as a timestamp
	 * 
	 * @return current time and date as a string
	 */
	public static String timestamp() {
		
		DateFormat df = new SimpleDateFormat("mm/dd/yy h:mm:ss a");
		Calendar calobj = Calendar.getInstance();
		return df.format(calobj.getTime());
	}
	
	
}
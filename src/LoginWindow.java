import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LoginWindow extends JFrame{

	private static final long serialVersionUID = 1L;

	public LoginWindow(){
		JFrame frame = new JFrame("Nail Tech POS System");
		
		frame.setVisible(true);
		frame.setSize(1000, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginWindow();
            }
        });
	}

}

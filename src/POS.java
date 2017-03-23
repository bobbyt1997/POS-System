
import javax.swing.JFrame;

public class POS extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		new POS();
	}

	public POS(){
		//create the window using JFrame
        super("Nail Tech POS System");
        setVisible(true);
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MainClass {
	
	static PrintStream out;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {		// Uruchomienie ramki Swing
			@Override
			public void run() {
				new Application();
				try {
					out = new PrintStream(new FileOutputStream("Raport2.txt"));
					System.setOut(out);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
}

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Application extends JFrame {
	
	private JButton buttonStart, buttonStart2;
	
	public Application(){
		
		//- Ustawienia ramki
		
		setTitle("Styczeñ 2015 - Stronnicowanie - JAKUB KURANDA 210301");
		setSize(300, 100);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		//- Przyciski
		
		//-----------------------------------------------------
		buttonStart = new JButton("Losowy ci¹g");						// Przycisk startuj¹cy
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int x = 20;
				int n = 3;
				
				int ciagOdwolan[]=new int[x];
				int ciagOdwolan2[]=new int[x];
				int ciagOdwolan3[]= new int[x];
				
				// Generacja ci¹gów
				
				Random rand = new Random();
				
				for(int i=0;i<ciagOdwolan.length;i++){
					ciagOdwolan[i]= rand.nextInt(20);
				}
				for(int i=0;i<ciagOdwolan2.length;i++){
					ciagOdwolan2[i]=ciagOdwolan[i];
				}
				for(int i=0;i<ciagOdwolan3.length;i++){
					ciagOdwolan3[i]=ciagOdwolan[i];
				}

				
				// Wywo³ania!
				
				Fifo fifo = new Fifo(n,x);
				fifo.symulacja(ciagOdwolan);
				Opt aprok = new Opt(n,x);
				aprok.symulacja(ciagOdwolan2);
				Mfu lru = new Mfu(n,x);
				lru.symulacja(ciagOdwolan3);
				
			}
		});
		
		add(buttonStart);	// <--	Dodaj przycisk do okienka
		//-----------------------------------------------------
		
		//-----------------------------------------------------
				buttonStart2 = new JButton("Zdefiniowany ci¹g");						// Przycisk startuj¹cy
				buttonStart2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						int x = 20;
						int n = 3;
						
						int ciagOdwolan[]=new int[x];
						int ciagOdwolan2[]=new int[x];
						int ciagOdwolan3[]= new int[x];
						
						
						
						File file = new File("list2.txt");
						
						try {
							
							Scanner scanner = new Scanner(file);

							int i=0;
							
							for(String b : scanner.nextLine().split(" ")){	// Oddzielaj wartoœci spacj¹
								
								ciagOdwolan[i++] = new Integer(b);
								
							};
								
							
							scanner.close();
							
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						
						// ----------------------
						
						for(int i=0;i<ciagOdwolan2.length;i++){
							ciagOdwolan2[i]=ciagOdwolan[i];
						}
						for(int i=0;i<ciagOdwolan3.length;i++){
							ciagOdwolan3[i]=ciagOdwolan[i];
						}
						
						// Wywo³ania!
						
						Fifo fifo = new Fifo(n,x);
						fifo.symulacja(ciagOdwolan);
						Opt aprok = new Opt(n,x);
						aprok.symulacja(ciagOdwolan2);
						Mfu lru = new Mfu(n,x);
						lru.symulacja(ciagOdwolan3);
						
					}
				});
				
				add(buttonStart2);	// <--	Dodaj przycisk do okienka
				//-----------------------------------------------------
		
		//-----------------------------------
		setVisible(true);	// Wyœwietl ramkê
		//-----------------------------------
		
	}

}

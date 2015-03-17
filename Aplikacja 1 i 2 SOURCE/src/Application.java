import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Application extends JFrame {
	
	private Timer timer;								// Po prostu Timer z klasy Swing
	private int speed = 1000;							// Co ile uruchamiany jest ActionListener ( w milisekundach )
	
	private JButton buttonStart, buttonReset;
	private JCheckBoxMenuItem buttonOpenQueue;
	
	private Simulation simulation;
	public DrawSimulation drawing;
	
	public Application(){
		
		//- Ustawienia ramki
		
		setTitle("Styczeñ 2015 - Symulacja alokowania czasu procesów CPU - JAKUB KURANDA 210301");
		setSize(600, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		//- G³ówny timer symulacji
		
		simulation = new Simulation(this);
		
		timer = new Timer(speed,simulation);
		
		//- Górna belka ------------------------------------
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);	// Ustaw belkê menu
		
		JMenu menuOptions = new JMenu("Opcje");
		JMenu menuAlgorythm = new JMenu("Algorytm");
		
		menuBar.add(menuOptions);
		menuBar.add(menuAlgorythm);
		
		// Opcje:
		
		buttonOpenQueue = new JCheckBoxMenuItem("Otwarta pula", true);
		
		buttonOpenQueue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(!buttonOpenQueue.isSelected()){
					
					System.out.println("Pula zamkniêta");
					simulation.setOpenQueue(false);
					simulation.LoadProcesses();
					
				} else {
					
					System.out.println("Pula otwarta");
					simulation.setOpenQueue(true);
					simulation.getListOfProcesses().clear();
					
				}
				
			}
		});
		
		menuOptions.add(buttonOpenQueue);
		
		// Lista algorytmów:
		
		ButtonGroup algorythmsGroup = new ButtonGroup(); // Grupa
		
		JRadioButtonMenuItem buttonFCFS = new JRadioButtonMenuItem("FCFS",true);
		JRadioButtonMenuItem buttonSJF = new JRadioButtonMenuItem("SJF niewyw³aszczaj¹cy");
		JRadioButtonMenuItem buttonSRTF = new JRadioButtonMenuItem("SRTF wyw³aszczaj¹cy");
		JRadioButtonMenuItem buttonRR = new JRadioButtonMenuItem("Round-Robin");
		JRadioButtonMenuItem buttonPrio = new JRadioButtonMenuItem("Priorytetytowy z postarzaniem");
		
		buttonFCFS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.setAlghoritm(0);
				
			}
		});
		
		buttonSJF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.setAlghoritm(1);
				
			}
		});
		
		buttonSRTF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.setAlghoritm(2);
				
			}
		});
		
		buttonRR.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.setAlghoritm(3);
				
			}
		});
		buttonPrio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.setAlghoritm(4);
				
			}
		});
		
		algorythmsGroup.add(buttonFCFS);
		algorythmsGroup.add(buttonSJF);
		algorythmsGroup.add(buttonSRTF);
		algorythmsGroup.add(buttonRR);
		algorythmsGroup.add(buttonPrio);
		
		
		menuAlgorythm.add(buttonFCFS);
		menuAlgorythm.add(buttonSJF);
		menuAlgorythm.add(buttonSRTF);
		menuAlgorythm.add(buttonRR);
		menuAlgorythm.add(buttonPrio);
		
		//-------------------------------------------------
		
		// Lista prêdkoœci symulacji:
		
		ButtonGroup speedsGroup = new ButtonGroup(); // Grupa
		
		JRadioButtonMenuItem buttonSpeed1 = new JRadioButtonMenuItem("1 s",true);
		JRadioButtonMenuItem buttonSpeed2 = new JRadioButtonMenuItem("0,5 s");
		JRadioButtonMenuItem buttonSpeed3 = new JRadioButtonMenuItem("0,25 s");
		
		speedsGroup.add(buttonSpeed1);
		speedsGroup.add(buttonSpeed2);
		speedsGroup.add(buttonSpeed3);
		
		buttonSpeed1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setSpeed(1000);
				timer.setDelay(getSpeed());
			}
		});
		
		buttonSpeed2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setSpeed(500);
				timer.setDelay(getSpeed());
			}
		});
		
		buttonSpeed3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setSpeed(250);
				timer.setDelay(getSpeed());
			}
		});
		
		
		JMenu submenuSpeeds = new JMenu("Prêdkoœæ symulacji");
		
		submenuSpeeds.add(buttonSpeed1);
		submenuSpeeds.add(buttonSpeed2);
		submenuSpeeds.add(buttonSpeed3);
		
		menuOptions.add(submenuSpeeds);
		
		
		//- Przyciski
		
		//-----------------------------------------------------
		buttonStart = new JButton("Start");						// Przycisk startuj¹cy/zatrzymuj¹cy symulacjê
		buttonStart.addActionListener(new ActionListener() {
			
			public boolean on = false;	// Przycisk w³¹czony?
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Prze³¹cznik przycisku
				if(!on){
					on = true;
					timer.start();
					System.out.println("Timer zosta³ w³¹czony.");
					buttonStart.setText("Stop");
				} else {
					on = false;
					timer.stop();
					System.out.println("Timer zosta³ wy³¹czony.");
					buttonStart.setText("Start");
				}
				
			}
		});
		
		add(buttonStart);	// <--	Dodaj przycisk do okienka
		//-----------------------------------------------------
		
		buttonReset = new JButton("Reset");						// Przycisk resetuj¹cy symulacjê
		buttonReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				timer.stop();	// Resetuj timer
				simulation.reset();	// Resetuj dane symulacji
				
				System.out.println("Zresetowano dane symulacji!");
				
			}
		});
		
		add(buttonReset);	// <--	Dodaj przycisk do okienka
		//-----------------------------------------------------
		
				JButton buttonSave = new JButton("Zapisz");						// Przycisk resetuj¹cy symulacjê
				buttonSave.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						System.out.println("------------------------------");
						System.out.println("------------------------------");
						System.out.println("Œredni czas oczekiwania:"+simulation.averageWaitTime());
						System.out.println("Iloœæ rotacji RR : "+simulation.getRotations());
						System.out.println("Suma oczekiwañ podczas symulacji : "+simulation.allProcessesWaitTime);
						
					}
				});
				
				add(buttonSave);	// <--	Dodaj przycisk do okienka
		
		//---- WIZUALIZACJA
		
		// TODO
		
		drawing = new DrawSimulation(simulation);	// WIZUALIZACJA
        getContentPane().add(drawing);
        
		//-----------------------------------
		setVisible(true);	// Wyœwietl ramkê
		//-----------------------------------
		
	}
	
	public Timer getTimer(){	// Zwraca timer ramki
		
		return this.timer;
		
	}
	
	public void setSpeed(int d){	// Ustawia prêdkoœæ symulacji
		
		this.speed = d;
		
	}
	
	public int getSpeed(){	// zwraca prêdkoœæ symulacji
		
		return this.speed;
		
	}

}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Simulation implements ActionListener {
	
	public Application app;
	
	private int processorTime = 0;						// Aktualny czas procesora
	private boolean openQueue = true;					// Otwarta pula proces�w?
	private int maxProcessesInQueue = 15;				// Ile maksymalnie proces�w w kolejce?
	private int maxProcessesInSimulation = 20;			// Ile maksymalnie proces�w podczas wykonania symulacji?
	private int countProcesses = 0;						// Licznik, ile by�o proces�w
	private Process actualProcess = null;						// Aktualnie obs�ugiwany proces
	private Process lastProcess = null;						// Ostatnio obs�ugiwany proces
	private int propability = 50;						// Prawdopodobie�stwo stworzenia nowego procesu w cyklu
	private int alghoritm = 0;							// 0-FCFS; 1-SJF; 2-Round-Robin;
	public int allProcessesWaitTime = 0;					// Suma czas�w oczekiwania wszystkich prces�w
	private int timeSlice = 1;								// Kwant czasu
	private int timeSliceIndex=0;							// Kt�ra cz�c kwantu jest aktualnie obs�ugiwana?
	private int rotations=0;							// Ile razy zmieniono proces ( prze��cznik kontekstu )
	private int maxWaitPrio = 3;						// Ile proces mo�e czeka�, zanim zostanie postarzony

	private ArrayList<Process> list;					// Lista proces�w aktualna ( �ywa )
	private ArrayList<Process> listClosed;				// Lista proces�w wczytana z pliku
	
	public Simulation(Application app){								// Konstruktor
		
		this.app = app;
		
		list = new ArrayList<Process>();
		listClosed = new ArrayList<Process>();
		
		LoadProcesses();								// Za�aduj procesy z pliku
		
	}
	
	// ---------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent arg0) {		// G��wna p�tla
		
		app.drawing.repaint();
		
		//--------------
		// Generowanie proces�w
		
		if(countProcesses < maxProcessesInSimulation){
			
			// Wygeneruj proces, je�eli pula jest otwarta, trafiono w prawdopodobie�stwo i kolejka nie jest pe�na
			if(isOpenQueue() && new Random().nextInt(100) < propability  && getListOfProcesses().size() < maxProcessesInQueue){
				GenerateProcess();
			}
		}
		
		// Dodaj proces do aktualnej listy, je�li pula jest zamkni�ta oraz czas procesora == czasowi dodania procesu
		if(!isOpenQueue()){
			
			for(Process e : listClosed){
				
				if(e.getTimeAdded() == GetProcessorTime()){
					
					AddProcess(e);	// Dodaj do prawdziwej listy proces z listy zamkni�tej
					
				}
			}
		}
		
		//--------------------------------------------
		// Wybieranie odpowiedniego procesu do obs�ugi
		
		if(getListOfProcesses().size() > 0){
			
			switch(getAlghoritm()){
			case 0:	// Algorytm FCSF
				
				if(actualProcess != null) break;	// Je�li jest ustawiony aktualny proces, zako�cz case
				
				actualProcess = getListOfProcesses().get(0);	// Zawsze pierwszy na li�cie
				System.out.println("%Actual= "+actualProcess.toString() + "<--");
				
			break;
			case 1:	// Algorytm SJF Niewyw�aszczaj�cy
				
				if(actualProcess != null) break;	// Je�li jest ustawiony aktualny proces, zako�cz case
					
				int i = getListOfProcesses().get(0).getTimeLeft();	// Ustaw najmniejsz� warto�� pozosta�ego czasu tymczasowo
				actualProcess = getListOfProcesses().get(0);
				
				for(Process p : getListOfProcesses()){
					
					if(p.getTimeLeft() < i){	// Co p�tl� ustawiaj najkr�tszy proces jako ten, a p�niej si� go nadpisuje
						
						i = p.getTimeLeft();
						actualProcess = p;
						
					}
				}
				
				System.out.println("%Actual= "+actualProcess.toString() + "<--");
				
			break;
			case 2:	// Algorytm SRTF wyw�aszczaj�cy
					
				int e = getListOfProcesses().get(0).getTimeLeft();	// Ustaw najmniejsz� warto�� pozosta�ego czasu tymczasowo
				actualProcess = getListOfProcesses().get(0);
				
				for(Process p : getListOfProcesses()){
					
					if(p.getTimeLeft() < e){	// Co p�tl� ustawiaj najkr�tszy proces jako ten, a p�niej si� go nadpisuje
						
						e = p.getTimeLeft();
						actualProcess = p;
						
					}
				}
				
				if(!actualProcess.equals(lastProcess)) System.out.println("%Actual= "+actualProcess.toString() + "<--");
				
				lastProcess = actualProcess;
				
			break;
			
			case 3:	// Algorytm Round-Robin
			
				if(lastProcess == null) actualProcess = getListOfProcesses().get(0);	// Je�li nie by�o ostatniego procesu
				
				if(timeSliceIndex < timeSlice){
					
					timeSliceIndex++;
					
				} else {
					
					timeSliceIndex = 0;	// zresetuj licznik kwantu
					rotations++;	// Dodaj do licznika prze�acznika kontekstu
					
					int actualIndex = getListOfProcesses().indexOf(actualProcess);
					
					if(actualIndex +1 <= getListOfProcesses().size()-1){
						
						actualProcess = getListOfProcesses().get(actualIndex +1);
						lastProcess = actualProcess;
						System.out.println("%Actual= "+actualProcess.toString() + "<--");
						
					} else {
						
						actualProcess = getListOfProcesses().get(0);
						lastProcess = actualProcess;
						System.out.println("%Actual= "+actualProcess.toString() + "<--");
						
					}
					
				}
				
			break;
			case 4:	// Priorytetytowy z postarzaniem
				
				for(Process p : getListOfProcesses()){
					
					if(p != actualProcess){
						
						p.setWaitedUntilElder(p.getWaitedUntilElder() +1);
						
						if(p.getWaitedUntilElder() == maxWaitPrio){
							
							p.setPriority(p.getPriority() +1);
							p.setWaitedUntilElder(0);
							System.out.println("++");
							
						}	
					}
				}
				
				if(actualProcess != null) break;	// Je�li jest ustawiony aktualny proces, zako�cz case
				
				int c = getListOfProcesses().get(0).getPriority();	// Ustaw najmniejsz� warto�� priorytetu tymczasowo
				actualProcess = getListOfProcesses().get(0);
				
				for(Process p : getListOfProcesses()){
					
					if(p.getPriority() > c){	// Co p�tl� ustawiaj najwa�niejszy proces jako ten, a p�niej si� go nadpisuje
						
						i = p.getPriority();
						actualProcess = p;
						
					}
				}
				
				System.out.println("%Actual= "+actualProcess.toString() + "<--");
			
			break;
			}
			
		}
		
		// Zmniejsz pozosta�y czas aktualnego procesu i usu� go, je�li zosta� wykonany do ko�ca
		
		if(actualProcess != null){
		
			processesWaitQueue(actualProcess);	// Ca�a lista czeka cykl, ale ten proces nie
			
			actualProcess.decreaseTimeLeft();
			System.out.println("Doing process --------------- "+ actualProcess.toString());
			
			if(actualProcess.getTimeLeft() == 0)
				
				if(getListOfProcesses().remove(actualProcess)){
					
					allProcessesWaitTime += actualProcess.getTimeWaited();	// Dodaj do czasu
					
					System.out.println("Removed process --.");
					timeSliceIndex = 0;	// zresetuj kwant czasu
					actualProcess = null;
					
				}
		
		}
		
		//----
		SetProcessorTime(GetProcessorTime()+1);		// Zwi�ksz licznik o jeden
		//Toolkit.getDefaultToolkit().beep();
		
	}
	// ----------------------------------------------------------------------------
	
	public int GetProcessorTime(){		// Zwr�� aktualny czas procesora
		
		return processorTime;
		
	}
	
	public void SetProcessorTime(int time){		// Ustaw aktualny czas procesora
		
		this.processorTime = time;
		
	}
	
	public void AddProcess(Process proc){		// Dodaj istniej�cy proces do listy
		
		list.add(proc);
		countProcesses++;
		System.out.println(proc.toString());
		
	}
	
	public double averageWaitTime(){
		
		return (double)this.allProcessesWaitTime / (double)maxProcessesInSimulation;
		
	}
	
	public void removeProcess(Process process){
		
		getListOfProcesses().remove(process);
		
	}
	
	public void GenerateProcess(){		// Wygeneruj proces
		
		int time = new Random().nextInt(6)+1;
		int timeAdded = GetProcessorTime();
		int priority = new Random().nextInt(8)+1;
		
		AddProcess(new Process(timeAdded,time,priority));	// Dodaj do listy
		
	}
	
	public void LoadProcesses(){	// Za�aduj procesy z pliku
		
		File file = new File("list1.txt");
		
		try {
			
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){	// Dla ka�dej linii w pliku
				
				Integer[] processData = new Integer[3];	// Tymczasowa tablica dla warto�ci procesu
				
				int i=0;
				
				for(String e : scanner.nextLine().split(" ")){	// Oddzielaj warto�ci spacj�
					
					processData[i++] = new Integer(e);
					
				};
				
				
				listClosed.add(new Process(processData[0],processData[1],processData[2]));	// Dodaj do listy zamkni�tej
				
			}
			
			scanner.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}
	
	// Zapisz, �e proces czeka�
	public void processWait(Process proc){
		
		proc.setTimeWaited(proc.getTimeWaited()+1);
		
	}
	
	// Zapisuje w ka�dym procesie, �e przeczeka� jeden cykl, opr�cz jednego procesu, kt�ry jest obs�ugiwany
	public void processesWaitQueue(Process iAmNotWaiting){
		
		for(Process e : getListOfProcesses()){
			
			if(e != iAmNotWaiting) processWait(e);
			
		}
		
	}
	
	public void reset(){
		
		SetProcessorTime(0);
		getListOfProcesses().clear();
		actualProcess = null;
		lastProcess = null;
		allProcessesWaitTime = 0;
		countProcesses = 0;
		timeSliceIndex = 0;
		rotations = 0;
		
	}
	
	public boolean isOpenQueue() {
		return openQueue;
	}

	public void setOpenQueue(boolean openQueue) {
		this.openQueue = openQueue;
	}
	
	public ArrayList<Process> getListOfProcesses(){
		
		return this.list;
		
	}
	
	public int getPropability() {
		return propability;
	}

	public void setPropability(int propability) {
		this.propability = propability;
	}
	
	public int getAlghoritm() {
		return alghoritm;
	}

	public void setAlghoritm(int alghoritm) {
		this.alghoritm = alghoritm;
	}
	
	public int getRotations(){
		return rotations;
	}

	
}

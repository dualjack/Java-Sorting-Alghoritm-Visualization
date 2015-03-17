public class Process {
	
	private int time;			// Czas trwania fazy
	private int timeLeft;		// Pozosta³y czas fazy
	private int priority;		// Priorytet
	private int timeAdded;		// Kiedy dodano
	private int timeWaited=0;	// Ile proces czeka³ na wykonanie?
	private int waitedUntilElder=0;	// Ile procesor przeczeka³, zanim zosta³ postarzony

	public Process(int timeAdded, int time, int priority){	// Konstruktor
		
		setTime(time);
		setTimeLeft(time);
		setPriority(priority);
		setTimeAdded(timeAdded);
		
	}
	
	public String toString(){	// Reprezentacja tekstowa procesu
		
		String string = new String("P# tAdded: " + getTimeAdded() +
				", time: " + getTime() + ", tLeft: " + getTimeLeft() + ", priority: " + getPriority() + ", waited: " + getTimeWaited());
		
		return string;
		
	}

	public void setTime(int time) {	// Ustawia liczbê cykli
		
		this.time = time;
		
	}
	
	public void setTimeAdded(int timeAdded){
		
		this.timeAdded = timeAdded;
		
	}

	public void setPriority(int priority) {	// Ustawia priorytet

		this.priority = priority;
		
	}
	
	public void setTimeLeft(int timeLeft){
		
		this.timeLeft = timeLeft;
		
	}
	
	public void decreaseTimeLeft(){
		
		this.timeLeft--;
		
	}
	
	public int getTime(){
		
		return this.time;
		
	}
	
	public int getTimeLeft(){
		
		return this.timeLeft;
		
	}
	
	public int getPriority(){
		
		return this.priority;
		
	}
	
	public int getTimeAdded(){
		
		return this.timeAdded;
		
	}
	
	public int getTimeWaited() {
		return timeWaited;
	}

	public void setTimeWaited(int timeWaited) {
		this.timeWaited = timeWaited;
	}
	
	public int getWaitedUntilElder() {
		return waitedUntilElder;
	}

	public void setWaitedUntilElder(int waitedUntilElder) {
		this.waitedUntilElder = waitedUntilElder;
	}

}

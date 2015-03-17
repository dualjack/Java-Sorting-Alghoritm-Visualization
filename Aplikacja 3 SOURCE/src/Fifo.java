public class Fifo {

	int [] pamiecFizyczna;
	int [] pamiecWirtualna;
	int [] ciagOdwolan;
	int iloscWyrzuconych = 0;
	int licznik = 0;
	int wielkoscPamieciFizycznej;
	int licznikCiaguOdwolan=0;
	int [] stos;
	
	
	public Fifo (int n,int x){
		pamiecFizyczna = new int [n];
		wielkoscPamieciFizycznej = n;
		ciagOdwolan = new int[x];
		pamiecWirtualna = new int [x];
		stos = new int[wielkoscPamieciFizycznej];
	
	}
	
	public void symulacja (int tab[]){
		System.out.println();
//	System.out.println("FIFO");
		//wypelnienie ciagu odwolan
		for(int i=0;i<tab.length;i++){
		ciagOdwolan[i]=tab[i];
		}
		for (int i = 0;i<pamiecFizyczna.length;i++){
			pamiecFizyczna[i]=ciagOdwolan[i];
			stos = pamiecFizyczna; 
			iloscWyrzuconych++;
			licznikCiaguOdwolan++;
		}
		for (int i = pamiecFizyczna.length;i<ciagOdwolan.length;i++){ /////////
			if (czyWystepuje(ciagOdwolan[i])){
			}
			else {
				zamianaWStosie();
				iloscWyrzuconych += 1;
			}
		}
		
		
		System.out.println("Ilosc zmian stron w FIFO "+iloscWyrzuconych );
	}
	
	public boolean czyWystepuje(int j){
		for (int i =0;i<pamiecFizyczna.length;i++){
			if (pamiecFizyczna[i]==j){
				return true;
			}
		}
		return false;
	}
	
	
	public void zamianaWStosie(){
		int [] temp = new int [wielkoscPamieciFizycznej];
		for(int i = 0;i<wielkoscPamieciFizycznej-1;i++){
			temp [i] = stos[i+1];
		}
		temp[wielkoscPamieciFizycznej-1] = ciagOdwolan[licznikCiaguOdwolan];
		stos = temp;
		licznikCiaguOdwolan++;
	}
}
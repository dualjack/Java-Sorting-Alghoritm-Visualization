public class Opt {

	int [] pamiecFizyczna;
	int [] pamiecWirtualna;
	int [] ciagOdwolan;
	int iloscWyrzuconych = 0;
	int licznik = 0;
	int wielkoscPamieciFizycznej;
	int licznikCiaguOdwolan=0;
	int [] stos;
	int [] stosZBitem0;
	int [] drugaSzansa;
	
	public Opt (int n,int x){//n ramki, x strony{

		pamiecFizyczna = new int [n];
		wielkoscPamieciFizycznej = n;

		ciagOdwolan = new int[x];
		pamiecWirtualna = new int [x];

		drugaSzansa = new int[wielkoscPamieciFizycznej];
		stos = new int[wielkoscPamieciFizycznej];

	}
	
	public void symulacja (int tab[]){
		for (int i=0;i<tab.length;i++){
			ciagOdwolan[i]=tab[i];
		}
		System.out.println();
		for (int i = 0;i<drugaSzansa.length;i++){
			drugaSzansa[i] = 1;
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
				iloscWyrzuconych++;
			}
			
		}
		
			iloscWyrzuconych -= 2;
			
		System.out.println("Ilosc zmian w Optymalnym "+iloscWyrzuconych );
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
		while (sprawdzPierwszyElement()){
			zamienPierwszyZOstatnim();
		}
		usunPierwszyIZamien();
	}
	
	public void zamienPierwszyZOstatnim(){
		int temp [] = new int [pamiecFizyczna.length];
		for (int i = 0;i<stos.length-1;i++){
			temp [i] = stos[i+1];
		}
		temp[wielkoscPamieciFizycznej-1] = stos[0];
		stos = temp;
		
		int tempDruga [] = new int [pamiecFizyczna.length];
		for (int i = 0;i<stos.length-1;i++){
			tempDruga [i] = drugaSzansa[i+1];
		}
		tempDruga[wielkoscPamieciFizycznej-1] = 0;
		drugaSzansa = tempDruga;
		
		
	}
	
	public boolean sprawdzPierwszyElement(){
		if (drugaSzansa[0] == 1){
			return true;
		}
		return false;
	}
	
	public void usunPierwszyIZamien(){
		int temp [] = new int [pamiecFizyczna.length];
		for (int i = 0;i<stos.length-1;i++){
			temp [i] = stos[i+1];
		}
		temp[wielkoscPamieciFizycznej-1] = ciagOdwolan[licznikCiaguOdwolan];
		stos = temp;
		drugaSzansa[wielkoscPamieciFizycznej-1] = 1;
		licznikCiaguOdwolan++;
	}

}
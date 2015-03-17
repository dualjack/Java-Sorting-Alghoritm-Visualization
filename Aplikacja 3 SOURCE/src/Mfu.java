public class Mfu {

		int [] pamiecFizyczna;
		int [] pamiecWirtualna;
		int [] ciagOdwolan;
		int iloscWyrzuconych = 0;
		int licznik = 0;
		int wielkoscPamieciFizycznej;
		int licznikCiaguOdwolan=0;
		int [] stos;
		int [] czasOczekiwania;
		public Mfu (int n, int x){
			pamiecFizyczna = new int [n];
			wielkoscPamieciFizycznej = n;
			ciagOdwolan = new int[x];
			pamiecWirtualna = new int [x];
			stos = new int[wielkoscPamieciFizycznej];
			czasOczekiwania = new int [wielkoscPamieciFizycznej];
		}
		
		public void symulacja (int tab[]){
			System.out.println();
			for(int i=0;i<tab.length;i++){
				ciagOdwolan[i]=tab[i];
			}
			//wypelnienie ciagu odwolan

			for (int i = 0;i<pamiecFizyczna.length;i++){
				pamiecFizyczna[i]=ciagOdwolan[i];
				stos = pamiecFizyczna; 
				iloscWyrzuconych++;
				licznikCiaguOdwolan++;
			}
				for (int i = pamiecFizyczna.length;i<ciagOdwolan.length;i++){ /////////
				if (czyWystepuje(ciagOdwolan[i])){
					wyzerowanieGdyWystepuje(ciagOdwolan[i]);
				}
				else {
					zwiekszenieCzasuOczekiwania(ciagOdwolan[i]);
					zamianaWStosie();
					iloscWyrzuconych++;
				}
			}
			
				iloscWyrzuconych-=1;
			System.out.println("Ilosc wyrzuconych stron w MFU "+iloscWyrzuconych );
		}
		
		public boolean czyWystepuje(int j){
			for (int i =0;i<pamiecFizyczna.length;i++){
				if (pamiecFizyczna[i]==j){
					return true;
				}
			}
			return false;
		}
		
		public void zwiekszenieCzasuOczekiwania(int k){
			for (int i = 0;i <czasOczekiwania.length;i++){
				czasOczekiwania[i]++;
			}
			int indeksDoWyzerowania = 0;
			for (int i = 0;i<stos.length;i++){
				if(stos[i] ==k){
					indeksDoWyzerowania = i;
				}
			}
			czasOczekiwania[indeksDoWyzerowania] = 0;
		}
		
		public void zamianaWStosie(){
			int [] temp = new int [wielkoscPamieciFizycznej];
			int najwiekszy = 0;
			int indeksNajwiekszy = 0;
			for (int i = 0;i<czasOczekiwania.length;i++){
				if (czasOczekiwania[i]>najwiekszy){
					indeksNajwiekszy =i;
				}
			}
			for(int i = 0;i<wielkoscPamieciFizycznej-1;i++){
				if (i != indeksNajwiekszy){
				temp [i] = stos[i];
				}
			}
			temp[indeksNajwiekszy] = ciagOdwolan[licznikCiaguOdwolan];
			stos = temp;
			licznikCiaguOdwolan++;
		}
		
		public void wyzerowanieGdyWystepuje(int k){
			int indeksDoWyzerowania = 0;
			for (int i = 0;i<stos.length;i++){
				if(stos[i] ==k){
					indeksDoWyzerowania = i;
				}
			}
			czasOczekiwania[indeksDoWyzerowania] = 0;
		}
	}
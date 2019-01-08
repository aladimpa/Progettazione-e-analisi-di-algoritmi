import java.util.HashMap;
import java.util.Map;

public class CercaOperando {
	
	private Long operando[];
	
	public CercaOperando() {
		operando = new Long[2];
	}
	public Long[] getOperando() {
		return operando;
	}
	
	public Long getOperandoDatoIndice(int i) {
		return operando[i];
	}

	public void setOperandoDatoIndice(Long operando, int i) {
		this.operando[i] = operando;
	}

	public void setOperando(Long[] operando) {
		this.operando = operando;
	}
	public void Cerca(String linea, int i, HashMap<String, Long> variabili) {
		System.out.println(linea.charAt(i));
		if (Character.isDigit(linea.charAt(i))) {
			int inizio_valore = i;
			System.out.println("inizio valore:" + linea.charAt(i));
			do {
				i = i+1;
				if (i == linea.length()) {
					//significa che c'è un errore perchè l'espressione deve finire con una parentesi chiusa
					System.out.println("Errore nella sintassi dell'espressione: boh");
					System.exit(0);
				}
			} while (Character.isDigit(linea.charAt(i)));
			int fine_valore = i;
			System.out.println("Stampa ultimo numero " + linea.charAt(i));
			if (Character.isAlphabetic(linea.charAt(i))) {
				System.out.println("Errore nella sintassi del'espressione: dopo il numero c'è subito una lettera");
				System.exit(0);
			}
			//operando[0] = Long.parseLong(linea.substring(inizio_valore, fine_valore));
			setOperandoDatoIndice(Long.parseLong(linea.substring(inizio_valore, fine_valore)), 0);
			//System.out.println("valore: " + operando + " ok");
			
		} else {
			int inizio_nome = i;

			System.out.println("inizio nome:" + linea.charAt(i));
			do {
				i = i+1;
				if (i == linea.length()) {
					//significa che c'è un errore perchè termina a caso dopo il nome della variabile
					System.out.println("Errore nella sintassi dell'espressione: nome variabile completo o parziale e poi basta");
					System.exit(0);
				}
				System.out.println( linea.charAt(i));
			} while (linea.charAt(i) != ' ' && linea.charAt(i) != ')');
			
			//i attuale indica il primo spazio dopo il nome della variabile
			int fine_nome = i;
			System.out.println("fine_nome " + fine_nome + " " + linea.charAt(fine_nome));
			
			//estraggo il nome della variabie dalla stringa
			String nome_variabile = linea.substring(inizio_nome, fine_nome);
			System.out.println(nome_variabile);
			if (nome_variabile.contentEquals("ADD") || nome_variabile.contentEquals("SUB") ||  nome_variabile.contentEquals("MUL") ||  nome_variabile.contentEquals("DIV")) {
				System.out.println("Errore nella sintassi dell'espressione: manca parentesi aperta prima dell'operatore");
				System.exit(0);
			}
			boolean trovato = false;
			for (Map.Entry<String, Long> entry : variabili.entrySet()) {
				System.out.println("nome:" + entry.getKey());
				if (nome_variabile.equals(entry.getKey())) {
					trovato = true;
					//operando[0] = entry.getValue();
					setOperandoDatoIndice(entry.getValue(), 0);
					break;
				}
			}
			if (!trovato) {
				System.out.print("Errore: operazione su variabile non dichiarata");
				System.exit(0);
			}
		}
		//operando[1] = (long) i;
		setOperandoDatoIndice((long)i, 1);
		//return operando;
	}
}

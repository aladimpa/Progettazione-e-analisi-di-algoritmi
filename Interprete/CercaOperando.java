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
		if (Character.isDigit(linea.charAt(i))) {
			int inizio_valore = i;
			do {
				i = i+1;
				if (i == linea.length()) {
					//significa che c'è un errore perchè l'espressione deve finire con una parentesi chiusa
					System.out.println("Errore nella sintassi dell'espressione");
					System.exit(0);
				}
			} while (Character.isDigit(linea.charAt(i)));
			int fine_valore = i;
			if (Character.isAlphabetic(linea.charAt(i))) {
				System.out.println("Errore nella sintassi del'espressione: dopo il numero c'è subito una lettera");
				System.exit(0);
			}
			operando[0] = Long.parseLong(linea.substring(inizio_valore, fine_valore));
			
		} else {
			int inizio_nome = i;
			do {
				i = i+1;
				if (i == linea.length()) {
					//significa che c'è un errore perchè termina a caso dopo il nome della variabile
					System.out.println("Errore nella sintassi dell'espressione: nome variabile completo o parziale e poi basta");
					System.exit(0);
				}
			} while (linea.charAt(i) != ' ' && linea.charAt(i) != ')');
			
			//i attuale indica il primo spazio dopo il nome della variabile
			int fine_nome = i;
			
			//estraggo il nome della variabie dalla stringa
			String nome_variabile = linea.substring(inizio_nome, fine_nome);
			if (nome_variabile.contentEquals("ADD") || nome_variabile.contentEquals("SUB") ||  nome_variabile.contentEquals("MUL") ||  nome_variabile.contentEquals("DIV")) {
				System.out.println("Errore nella sintassi dell'espressione: manca parentesi aperta prima dell'operatore");
				System.exit(0);
			}
			boolean trovato = false;
			for (Map.Entry<String, Long> entry : variabili.entrySet()) {
				if (nome_variabile.equals(entry.getKey())) {
					trovato = true;
					operando[0] = entry.getValue();
					break;
				}
			}
			if (!trovato) {
				System.out.print("Errore: operazione su variabile non dichiarata");
				System.exit(0);
			}
		}
		operando[1] = (long) i;
		//return operando;
	}
}

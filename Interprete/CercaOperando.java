import java.util.HashMap;
import java.util.Map;

public class CercaOperando {
	
	/**
	 * Operando contiene:
	 * - il valore dell'operando all'indice 0
	 * - il valore dell'iteratore i
	 */
	private Long operando[];
	
	/**
	 * Costruttore
	 */
	public CercaOperando() {
		operando = new Long[2];
	}
	
	/**
	 * Getter di Operando
	 * @return operando
	 */
	public Long[] getOperando() {
		return operando;
	}
	
	/**
	 * Cerca il valore numerico dell'operando
	 * @param linea e' la riga del file che si sta analizzando
	 * @param i indica l'i-esimo carattere contenuto nella riga in analisi
	 * @param variabili contiene il nome e il corrispettivo valore delle variabili gia' dichiarate
	 */
	public void Cerca(String linea, int i, HashMap<String, Long> variabili) {
		if (Character.isDigit(linea.charAt(i))) {
			int inizio_valore = i;
			do {
				i = i+1;
				if (i == linea.length()) {
					System.out.println("Errore nella sintassi dell'espressione: manca parentesi chiusa");
					System.exit(0);
				}
			} while (Character.isDigit(linea.charAt(i)));
			int fine_valore = i;
			if (Character.isAlphabetic(linea.charAt(i))) {
				System.out.println("Errore nella sintassi del'espressione: al valore della variabile c'è attaccata una lettera");
				System.exit(0);
			}
			operando[0] = Long.parseLong(linea.substring(inizio_valore, fine_valore));
		} else {
			int inizio_nome = i;
			do {
				i = i+1;
				if (i == linea.length()) {
					System.out.println("Errore nella sintassi dell'espressione: riga incompleta");
					System.exit(0);
				}
			} while (linea.charAt(i) != ' ' && linea.charAt(i) != ')');
			int fine_nome = i;
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
	}
}

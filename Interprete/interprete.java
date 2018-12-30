import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Interprete {
	
	public enum Chiave {
		GET, SET, ADD, SUB, MUL, DIV
	};
	
	private static Long[] Opera(String linea, int i, Integer parentesi_aperte, Integer parentesi_chiuse, HashMap<String, Long> variabili ) {
		Long primo_operando = null;
		Long secondo_operando = null;
		Long risultato[] = new Long[2];
		boolean operazione_interna[] = new boolean[2];
		operazione_interna[0]=false;
		operazione_interna[1]=false;
		boolean entro = false;
		System.out.println("stampa" + linea.charAt(i));
		do {
			i=i+1;
		}while (linea.charAt(i) == ' ');
		if (i+3 < linea.length() && linea.charAt(i) == 'A' && linea.charAt(i+1) == 'D' && linea.charAt(i+2) == 'D' && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			System.out.println("Entro");
			i = i+2;
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				entro = true;
				//parentesi_aperte = parentesi_aperte + 1;
				//chiamo ancora
				//primo_operando = Opera(linea, i, parentesi_aperte, parentesi_chiuse, variabili)[0];
				risultato = Opera(linea, i, 1, 0, variabili);
				primo_operando = risultato[0];
			} else {
				if (Character.isDigit(linea.charAt(i))) {
					int inizio_valore = i;
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
					primo_operando = Long.parseLong(linea.substring(inizio_valore, fine_valore));
					System.out.println("valore: " + primo_operando + " ok");
					
				} else {
					int inizio_nome = i;
					do {
						i = i+1;
						if (i == linea.length()) {
							//significa che c'è un errore perchè termina a caso dopo il nome della variabile
							System.out.println("Errore nella sintassi dell'espressione: nome variabile completo o parziale e poi basta");
							System.exit(0);
						}
					} while (linea.charAt(i) != ' ');
					
					//i attuale indica il primo spazio dopo il nome della variabile
					int fine_nome = i;
					System.out.println("fine_nome " + fine_nome + " " + linea.charAt(fine_nome));
					
					//estraggo il nome della variabie dalla stringa
					String nome_variabile = linea.substring(inizio_nome, fine_nome);
					System.out.println(nome_variabile);
					if (nome_variabile.contentEquals("ADD")) {
						System.out.println("Errore nella sintassi dell'espressione: manca parentesi aperta prima di ADD");
						System.exit(0);
					}
					boolean trovato = false;
					for (Map.Entry<String, Long> entry : variabili.entrySet()) {
						if (entry.getKey() == nome_variabile) {
							trovato = true;
							primo_operando = entry.getValue();
							break;
						}
					}
					if (!trovato) {
						System.out.print("Errore: operazione su variabile non dichiarata");
						System.exit(0);
					}
				}
				operazione_interna[0]=true;
			}
			if (entro) {
				i = Math.toIntExact(risultato[1]);
			}
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			System.out.println(linea.charAt(i));
			if(linea.charAt(i) == '(') {
				System.out.println("Entro2");
				//parentesi_aperte = parentesi_aperte + 1;
				//chiamo ancora
				//secondo_operando = Opera(linea, i, parentesi_aperte, parentesi_chiuse, variabili)[0];
				risultato = Opera(linea, i, 1, 0, variabili);
				secondo_operando = risultato[0];
				
			} else {
				if (Character.isDigit(linea.charAt(i))) {
					int inizio_valore = i;
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
					secondo_operando = Long.parseLong(linea.substring(inizio_valore, fine_valore));
					System.out.println("valore: " + secondo_operando + " ok");
				} else {
					int inizio_nome = i;
					do {
						i = i+1;
						if (i == linea.length()) {
							//significa che c'è un errore perchè termina a caso dopo il nome della variabile
							System.out.println("Errore nella sintassi dell'espressione: nome variabile completo o parziale e poi basta");
							System.exit(0);
						}
					} while (linea.charAt(i) != ' ');
					
					//i attuale indica il primo spazio dopo il nome della variabile
					int fine_nome = i;
					System.out.println("fine_nome " + fine_nome + " " + linea.charAt(fine_nome));
					
					//estraggo il nome della variabie dalla stringa
					String nome_variabile = linea.substring(inizio_nome, fine_nome);
					boolean trovato = false;
					for (Map.Entry<String, Long> entry : variabili.entrySet()) {
						if (entry.getKey() == nome_variabile) {
							trovato = true;
							secondo_operando = entry.getValue();
							break;
						}
					}
					if (!trovato) {
						System.out.print("Errore: operazione su variabile non dichiarata");
						System.exit(0);
					}
					
				}
				operazione_interna[1]=true;
				
			}
			
			
			
		}
		if (linea.charAt(i) == 'S' && linea.charAt(i+1) == 'U' && linea.charAt(i+2) == 'B') {
			
		}
		if (linea.charAt(i) == 'M' && linea.charAt(i+1) == 'U' && linea.charAt(i+2) == 'L') {
			
		}
		if (linea.charAt(i) == 'D' && linea.charAt(i+1) == 'I' && linea.charAt(i+2) == 'V') {
			
		}
		if (operazione_interna[0]==true && operazione_interna[1]==true) {
			System.out.println("i in funzione: " + i);
			risultato[1]=(long) i;
		} else {
			if (!entro) {
				i = Math.toIntExact(risultato[1]);
			}
		}
		risultato[0] = primo_operando + secondo_operando;
		System.out.println("eccomi" + linea.charAt(i));
		System.out.println("eccomi" + linea.charAt(i-1));
		if(linea.charAt(i) == ')') {
			parentesi_chiuse = parentesi_chiuse + 1;
			i=i+1;
		} else {
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == ')') {
				parentesi_chiuse = parentesi_chiuse + 1;
				i = i+1;
			} else {
				System.out.println("Errore nella sintassi dell'espressione: manca la parentesi chiusa");
				System.exit(0);
			}
		}
		System.out.println("par ap: "+ parentesi_aperte);
		System.out.println("par ch: "+ parentesi_chiuse);
		if (parentesi_aperte != parentesi_chiuse) {
			System.out.println("Errore nella sintassi dell'espressione: manca la parentesi chiusa");
			System.exit(0);
		}
		risultato[1] = (long) i;
		return risultato;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Numero degli argomenti inseriti errato");
			System.exit(0);
		} else {
			// hsahmap contiene: nome_variabile, valore_variabile
			HashMap<String, Long> variabili = new HashMap<String, Long>();
			String nomeFile = args[0];
			FileReader lettore = new FileReader(nomeFile);
			Scanner in = new Scanner(lettore);
			do {
				String linea = in.nextLine();
				int i=0;
				int parentesi_chiuse = 0;
				int parentesi_aperte = 0;
				int comandi = 0;
				do {
					String nome_variabile;
					if (linea.charAt(i) == '(') {
						parentesi_aperte = parentesi_aperte + 1;
						i=i+1;
						continue;
					}
					if (linea.charAt(i) == ')') {
						parentesi_chiuse = parentesi_chiuse + 1;
						i=i+1;
						continue;
					}
					if (linea.charAt(i) == ' ') {
						i=i+1;
						continue;
					}
					if (i+2>=linea.length())
						continue;
					if (i+3 < linea.length() && linea.charAt(i) == 'S' && linea.charAt(i+1) == 'E' && linea.charAt(i+2) == 'T' && linea.charAt(i+3) == ' ') {
						comandi = comandi + 1;
						if (comandi != parentesi_aperte) {
							System.out.println("Errore nella sintassi dell'espressione: manca parentesi prima di SET");
							System.exit(0);
						}
						i = i+3;
						do {
							i=i+1;
							if (i == linea.length()) {
								//significa che c'è un errore perchè termina a caso dopo un set e alcuni possibili spazi
								System.out.println("Errore nella sintassi dell'espressione: dopo SET non c'è niente");
								System.exit(0);
							}
						}while (linea.charAt(i) == ' ');
						int inizio_nome = i;
						System.out.println("inizio_nome " + inizio_nome + " " + linea.charAt(inizio_nome));
						do {
							i = i+1;
							if (i == linea.length()) {
								//significa che c'è un errore perchè termina a caso dopo il nome della variabile
								System.out.println("Errore nella sintassi dell'espressione: nome variabile completo o parziale e poi basta");
								System.exit(0);
							}
						} while (linea.charAt(i) != ' ');
						
						//i attuale indica il primo spazio dopo il nome della variabile
						int fine_nome = i;
						System.out.println("fine_nome " + fine_nome + " " + linea.charAt(fine_nome));
						
						//estraggo il nome della variabie dalla stringa
						nome_variabile = linea.substring(inizio_nome, fine_nome);
						System.out.println("Nome variabile: " + nome_variabile);
						do {
							i=i+1;
							if (i == linea.length()) {
								//significa che c'è un errore perchè termina a caso dopo il nome della variabile e alcuni spazi
								System.out.println("Errore nella sintassi dell'espressione: nome variabile più spazi o no e basta");
								System.exit(0);
							}
						}while (linea.charAt(i) == ' ');
						if (linea.charAt(i) == '(') {
							//chiamare la ricorsiva che calcola le operazioni per ottenere il valore che lo andrò a inserire in *
						} else {
							if (Character.isDigit(linea.charAt(i))) {
								int inizio_valore = i;
								do {
									i = i+1;
									if (i == linea.length()) {
										//significa che c'è un errore perchè l'espressione deve finire con una parentesi chiusa
										System.out.println("Errore nella sintassi dell'espressione: non c'è la parentesi chiusa che termina il SET");
										System.exit(0);
									}
								} while (Character.isDigit(linea.charAt(i)));
								int fine_valore = i;
								System.out.println("Stampa ultimo numero " + linea.charAt(i));
								if (Character.isAlphabetic(linea.charAt(i))) {
									System.out.println("Errore nella sintassi del'espressione: dopo il numero c'è subito una lettera");
									System.exit(0);
								}
								// startIndex : starting index is inclusive
								// endIndex : ending index is exclusive
								Long valore_variabile = Long.parseLong(linea.substring(inizio_valore, fine_valore));
								System.out.println("valore: " + valore_variabile + " ok");
								
								// quello che viene da qui deve spostarsi ad *
								variabili.put(nome_variabile, valore_variabile);
								
								do {
									if (linea.charAt(i) == ' ') {
										i=i+1;
									}
								}while (linea.charAt(i) == ' ');
								if (linea.charAt(i) != ')') {
									System.out.println("Errore nella sintassi dell'espressione");
									System.exit(0);
								}
								continue;
								
								// fino a qui
								
								
							}else {
								//errore
								System.out.print("Stringa mal formata");
								System.exit(0);
							}
						}
						// *
					}
					if (i+3< linea.length() && linea.charAt(i) == 'G' && linea.charAt(i+1) == 'E' && linea.charAt(i+2) == 'T' && (linea.charAt(i+3) == '(' || linea.charAt(i+3) == ' ')) {
						comandi = comandi + 1;
						if (comandi != parentesi_aperte) {
							System.out.println("Errore nella sintassi dell'espressione");
							System.exit(0);
						}
						i = i+2;
						do {
							i=i+1;
						}while (linea.charAt(i) == ' ');
						if (linea.charAt(i) == '(') {
							//parentesi_aperte = parentesi_aperte + 1;
							//chiamare la ricorsiva che calcola le operazioni
							System.out.println(linea.charAt(i+1));
							//System.out.println("Risultato: " + Opera(linea, i, parentesi_aperte, parentesi_chiuse, variabili));
							Long ritorno_di_opera[] = Opera(linea, i, 1, 0, variabili);
							Long risultato = ritorno_di_opera[0];
							i = Math.toIntExact(ritorno_di_opera[1]);
							System.out.println("Risultato "+ risultato);
							System.out.println("i "+i);
							if (i == linea.length()) {
								System.out.println("Errore nella sintassi dell'espressione: termina senza l'ultima parentesi chiusa");
								System.exit(0);
							}
							System.out.println(linea.charAt(i));
						} else {
							System.out.println("Errore nella sintassi dell'espressione: manca parentesi aperta prima di identificatore operazione");
							System.exit(0);
						}
					}
					
				} while (i<linea.length());
				
				if (parentesi_chiuse != parentesi_aperte) {
					System.out.println("Errore nella sintassi dell'espressione: parentesi " + parentesi_aperte + " " + parentesi_chiuse);
					System.exit(0);
				}
				
			} while(in.hasNextLine());
			in.close();
			System.out.print(Arrays.asList(variabili));
		}
	}
}
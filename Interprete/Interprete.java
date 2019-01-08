import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Interprete {
	
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
				if (linea.isEmpty()) {
					continue;
				}
				int i=0;
				int parentesi_chiuse = 0;
				int parentesi_aperte = 0;
				int comandi = 0;
				Long valore_variabile = null;
				String nome_variabile = null;
				Long risultato = null;
				do {
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
					if (i+3>=linea.length()) {
						System.out.println("Errore nella sintassi dell'espressione: finisce troppo presto");
						System.exit(0);
					}
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
						
						//estraggo il nome della variabie dalla stringa
						nome_variabile = linea.substring(inizio_nome, fine_nome);
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
							Opera operazione = new Opera();
							Long ritorno_di_opera[] = operazione.Operazione(linea, i, 1, 0, variabili);
							valore_variabile = ritorno_di_opera[0];
							i = Math.toIntExact(ritorno_di_opera[1]);
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
								if (Character.isAlphabetic(linea.charAt(i))) {
									System.out.println("Errore nella sintassi del'espressione: dopo il numero c'è subito una lettera");
									System.exit(0);
								}
								// startIndex : starting index is inclusive
								// endIndex : ending index is exclusive
								valore_variabile = Long.parseLong(linea.substring(inizio_valore, fine_valore));
							}else {
								//errore
								System.out.print("Stringa mal formata");
								System.exit(0);
							}
						}
						// *
						boolean trovato = false;
						for (Map.Entry<String, Long> entry : variabili.entrySet()) {
							if (nome_variabile.equals(entry.getKey())) {
								trovato = true;
								entry.setValue(valore_variabile);
								break;
							}
						}
						if (!trovato) {
							variabili.put(nome_variabile, valore_variabile);
						}
						do {
							if (i >= linea.length()) {
								System.out.println("Errore nella sintassi dell'espressione: manca parentesi finale");
								System.exit(0);
							}
							if (linea.charAt(i) == ' ') {
								i=i+1;
							}
						}while (linea.charAt(i) == ' ');
						if (linea.charAt(i) != ')') {
							System.out.println("Errore nella sintassi dell'espressione");
							System.exit(0);
						}
						System.out.println(nome_variabile + " = " + valore_variabile);
						continue;
					} else {
						if (linea.charAt(i+3) != ' ' && linea.charAt(i+3) != '(') {
							System.out.println("Errore nella sintassi dell'espressione: nome identificatore errato");
							System.exit(0);
						}
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
							//System.out.println("Risultato: " + Opera(linea, i, parentesi_aperte, parentesi_chiuse, variabili));
							Opera operazione = new Opera();
							Long ritorno_di_opera[] = operazione.Operazione(linea, i, 1, 0, variabili);
							risultato = ritorno_di_opera[0];
							i = Math.toIntExact(ritorno_di_opera[1]);
							if (i == linea.length()) {
								System.out.println("Errore nella sintassi dell'espressione: termina senza l'ultima parentesi chiusa");
								System.exit(0);
							}
						} else {
							System.out.println("Errore nella sintassi dell'espressione: manca parentesi aperta prima di identificatore operazione");
							System.exit(0);
						}
						System.out.println(risultato);
					} else {
						if (linea.charAt(i+3) != ' ' && linea.charAt(i+3) != '(') {
							System.out.println("Errore nella sintassi dell'espressione: nome variabile errato");
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
			//System.out.print(Arrays.asList(variabili));
		}
	}
}


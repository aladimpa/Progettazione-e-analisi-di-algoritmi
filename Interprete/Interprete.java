import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Interprete {

	/**
	 * Lettura del file: 
	 * - dichiarazione di variabile se individuato identificatore SET 
	 * - calcolo dell'operazione se individuato identificatore GET 
	 * Chiama la classe Opera se necessario
	 * 
	 * @param args 					 file da riga di comando
	 * @throws FileNotFoundException se non riesce ad aprire il file di input
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Numero degli argomenti inseriti errato");
			System.exit(0);
		} else {
			try {
				// hsahmap variabili contiene: nome_variabile, valore_variabile
				HashMap<String, Long> variabili = new HashMap<String, Long>();
				String nomeFile = args[0];
				FileReader lettore = new FileReader(nomeFile);
				Scanner in = new Scanner(lettore);
				do {
					String linea = in.nextLine();
					if (linea.isEmpty()) {
						continue;
					}
					int i = 0;
					int parentesi_chiuse = 0;
					int parentesi_aperte = 0;
					int comandi = 0;
					Long valore_variabile = null;
					String nome_variabile = null;
					Long risultato = null;
					do {
						if (linea.charAt(i) == '(') {
							parentesi_aperte = parentesi_aperte + 1;
							i = i + 1;
							continue;
						}
						if (linea.charAt(i) == ')') {
							parentesi_chiuse = parentesi_chiuse + 1;
							i = i + 1;
							continue;
						}
						if (linea.charAt(i) == ' ') {
							i = i + 1;
							continue;
						}
						if (i + 3 >= linea.length()) {
							System.out.println("Errore nella sintassi dell'espressione: incompleta");
							System.exit(0);
						}
						if (i + 3 < linea.length() && linea.charAt(i) == 'S' && linea.charAt(i + 1) == 'E'
								&& linea.charAt(i + 2) == 'T' && linea.charAt(i + 3) == ' ') {
							comandi = comandi + 1;
							if (comandi != parentesi_aperte) {
								System.out.println("Errore nella sintassi dell'espressione: manca parentesi prima di SET");
								System.exit(0);
							}
							i = i + 3;
							do {
								i = i + 1;
								if (i == linea.length()) {
									System.out.println("Errore nella sintassi dell'espressione: dopo SET léspressione e' incompleta");
									System.exit(0);
								}
							} while (linea.charAt(i) == ' ');
							int inizio_nome = i;
							do {
								i = i + 1;
								if (i == linea.length()) {
									System.out.println("Errore nella sintassi dell'espressione: dichiarazione variabile incompleta");
									System.exit(0);
								}
							} while (linea.charAt(i) != ' ');
							int fine_nome = i;
							nome_variabile = linea.substring(inizio_nome, fine_nome);
							do {
								i = i + 1;
								if (i == linea.length()) {
									System.out.println("Errore nella sintassi dell'espressione: dichiarazione variabile incompleta");
									System.exit(0);
								}
							} while (linea.charAt(i) == ' ');
							if (linea.charAt(i) == '(') {
								Opera operazione = new Opera();
								Long ritorno_di_opera[] = operazione.Operazione(linea, i, 1, 0, variabili);
								valore_variabile = ritorno_di_opera[0];
								i = Math.toIntExact(ritorno_di_opera[1]);
							} else {
								if (Character.isDigit(linea.charAt(i))) {
									int inizio_valore = i;
									do {
										i = i + 1;
										if (i == linea.length()) {
											System.out.println("Errore nella sintassi dell'espressione: "
													+ "non c'è la parentesi chiusa che termina il SET");
											System.exit(0);
										}
									} while (Character.isDigit(linea.charAt(i)));
									int fine_valore = i;
									if (Character.isAlphabetic(linea.charAt(i))) {
										System.out.println("Errore nella sintassi del'espressione: al valore c'è attaccata una lettera");
										System.exit(0);
									}
									// startIndex : l'indice iniziale e' incluso
									// endIndex : l'indice finale e' escluso
									valore_variabile = Long.parseLong(linea.substring(inizio_valore, fine_valore));
								} else {
									System.out.print("Stringa mal formata");
									System.exit(0);
								}
							}
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
									System.out.println("Errore nella sintassi dell'espressione: manca parentesi chiusa");
									System.exit(0);
								}
								if (linea.charAt(i) == ' ') {
									i = i + 1;
								}
							} while (linea.charAt(i) == ' ');
							if (linea.charAt(i) != ')') {
								System.out.println("Errore nella sintassi dell'espressione");
								System.exit(0);
							}
							System.out.println(nome_variabile + " = " + valore_variabile);
							continue;
						} else {
							if (linea.charAt(i + 3) != ' ' && linea.charAt(i + 3) != '(') {
								System.out.println("Errore nella sintassi dell'espressione: nome identificatore errato");
								System.exit(0);
							}
						}
						if (i + 3 < linea.length() 
								&& linea.charAt(i) == 'G' && linea.charAt(i + 1) == 'E' && linea.charAt(i + 2) == 'T'
								&& (linea.charAt(i + 3) == '(' || linea.charAt(i + 3) == ' ')) {
							comandi = comandi + 1;
							if (comandi != parentesi_aperte) {
								System.out.println("Errore nella sintassi dell'espressione");
								System.exit(0);
							}
							i = i + 2;
							do {
								i = i + 1;
							} while (linea.charAt(i) == ' ');
							if (linea.charAt(i) == '(') {
								Opera operazione = new Opera();
								Long ritorno_di_opera[] = operazione.Operazione(linea, i, 1, 0, variabili);
								risultato = ritorno_di_opera[0];
								i = Math.toIntExact(ritorno_di_opera[1]);
								if (i == linea.length()) {
									System.out.println("Errore nella sintassi dell'espressione: termina senza l'ultima parentesi chiusa");
									System.exit(0);
								}
							} else {
								System.out.println("Errore nella sintassi dell'espressione: "
										+ "manca parentesi aperta prima di identificatore operazione");
								System.exit(0);
							}
							System.out.println(risultato);
						} else {
							if (linea.charAt(i + 3) != ' ' && linea.charAt(i + 3) != '(') {
								System.out.println("Errore nella sintassi dell'espressione: nome variabile errato");
								System.exit(0);
							}
						}

					} while (i < linea.length());
					if (parentesi_chiuse != parentesi_aperte) {
						System.out.println("Errore nella sintassi dell'espressione: parentesi " 
											+ parentesi_aperte + " " + parentesi_chiuse);
						System.exit(0);
					}

				} while (in.hasNextLine());
				in.close();
			} catch (FileNotFoundException ex) {
				System.out.println(ex);
			}
		}
	}
}

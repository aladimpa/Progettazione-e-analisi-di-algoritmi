import java.util.ArrayList;
import java.util.HashMap;
import org.unige.aims.GridWorld.Coordinate;

public class EliminazioneU {

	private Coordinate coordinata_da_esaminare;
	private Coordinate coordinata_di_arrivo;

	/**
	 * Costruttore
	 */
	public EliminazioneU() {
	}

	/**
	 * Elimina i cappi a forma di U che si sono verificati durante la ricerca del
	 * percorso
	 * 
	 * @param percorso               lista di coordinate nella quale si memorizza il
	 *                               percorso
	 * @param adiacenti_per_percorso contiene tutte le coordinate adiacenti senza
	 *                               ostacoli relative ad ogni coordinata del
	 *                               percorso
	 */
	public void eliminaU(ArrayList<Coordinate> percorso,
			HashMap<Coordinate, Iterable<Coordinate>> adiacenti_per_percorso) {
		for (int i = 0; i < percorso.size(); i++) {
			/*
			 * Sono stati scelti al massimo 4 confronti per rendendere il tempo di
			 * esecuzione della funzione lineare e non quadratico; 3 confronti di
			 * conseguenza ad esperimenti fatti
			 */
			for (int j = 4; j < 9; j = j + 2) {
				if ((i + j) >= percorso.size()) {
					continue;
				}
				// Se il cappio si sviluppa in direzione sud ad esempio: dato
				// [(0,0)(0,1)(1,1)(2,1)(2,0)] restituisce [(0,0)(1,0)(2,0)]
				if (((percorso.get(i + j).row - percorso.get(i).row) == 2)
						&& ((percorso.get(i + j).col - percorso.get(i).col) == 0)) {
					coordinata_da_esaminare = percorso.get(i);
					coordinata_di_arrivo = percorso.get(i + j);
					for (Coordinate adiacente_riga : adiacenti_per_percorso.getOrDefault(coordinata_da_esaminare,
							new ArrayList<Coordinate>())) {
						if (adiacente_riga.row - coordinata_da_esaminare.row == 1
								&& coordinata_di_arrivo.row - adiacente_riga.row == 1) {
							modificaPercorso(percorso, i, j, adiacente_riga);
						}
					}
				}
				if ((i + j) >= percorso.size()) {
					continue;
				}
				// Se il cappio si sviluppa in direzione nord ad esempio: dato
				// [(2,0)(2,1)(1,1)(0,1)(0,0)] restituisce [(2,0)(1,0)(0,0)]
				if (((percorso.get(i + j).row - percorso.get(i).row) == -2)
						&& ((percorso.get(i + j).col - percorso.get(i).col) == 0)) {
					coordinata_da_esaminare = percorso.get(i);
					coordinata_di_arrivo = percorso.get(i + j);
					for (Coordinate adiacente_riga : adiacenti_per_percorso.getOrDefault(coordinata_da_esaminare,
							new ArrayList<Coordinate>())) {
						if (adiacente_riga.row - coordinata_da_esaminare.row == -1
								&& coordinata_di_arrivo.row - adiacente_riga.row == -1) {
							modificaPercorso(percorso, i, j, adiacente_riga);
						}
					}
				}
				if ((i + j) >= percorso.size()) {
					continue;
				}
				// Se il cappio si sviluppa in direzione est ad esempio: dato
				// [(0,0)(1,0)(1,1)(1,2)(0,2)] restituise [(0,0)(0,1)(0,2)]
				if (((percorso.get(i + j).col - percorso.get(i).col) == 2)
						&& ((percorso.get(i + j).row - percorso.get(i).row) == 0)) {
					coordinata_da_esaminare = percorso.get(i);
					coordinata_di_arrivo = percorso.get(i + j);
					for (Coordinate adiacente_colonna : adiacenti_per_percorso.getOrDefault(coordinata_da_esaminare,
							new ArrayList<Coordinate>())) {
						if (adiacente_colonna.col - coordinata_da_esaminare.col == 1
								&& coordinata_di_arrivo.col - adiacente_colonna.col == 1) {
							modificaPercorso(percorso, i, j, adiacente_colonna);
						}
					}
				}
				if ((i + j) >= percorso.size()) {
					continue;
				}
				// Se il cappio si sviluppa in direzione ovest ad esempio: dato
				// [(0,2)(1,2)(1,1)(1,0)(0,0)] restituisce [(0,2)(0,1)(0,0)]
				if (((percorso.get(i + j).col - percorso.get(i).col) == -2)
						&& ((percorso.get(i + j).row - percorso.get(i).row) == 0)) {
					coordinata_da_esaminare = percorso.get(i);
					coordinata_di_arrivo = percorso.get(i + j);

					for (Coordinate adiacente_colonna : adiacenti_per_percorso.getOrDefault(coordinata_da_esaminare,
							new ArrayList<Coordinate>())) {
						if (adiacente_colonna.col - coordinata_da_esaminare.col == -1
								&& coordinata_di_arrivo.col - adiacente_colonna.col == -1) {
							modificaPercorso(percorso, i, j, adiacente_colonna);
						}
					}
				}
			}
		}
	}

	/**
	 * Elimina le coordinate del percorso superflue e imposta la coordinata mancante
	 * 
	 * @param percorso           lista di coordinate nella quale si memorizza il
	 *                           percorso
	 * @param i+1				 indice di riferimento per l'inizio della
	 *                           cancellazione e per l'impostazione della coordinata
	 *                           mancante
	 * @param j                  indice dell'ultimo elemento facente parte del
	 *                           cappio
	 * @param elemento_adiacente contiene la coordinata mancante
	 */
	private void modificaPercorso(ArrayList<Coordinate> percorso, int i, int j, Coordinate elemento_adiacente) {
		/*
		 * L'indice k indica quante volte devo cancellare la coordinata (i+1)-esima del
		 * percorso Si cancella sempre allo stesso indice perchè ad ogni cancellazione
		 * l'elemento successivo diventa quello corrente
		 */
		for (int k = i; k < (i + j) - 2; k++) {
			percorso.remove(i + 1);
		}
		percorso.set(i + 1, elemento_adiacente);
	}
}

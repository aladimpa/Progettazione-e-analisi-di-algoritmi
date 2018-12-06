import java.util.ArrayList;
import org.unige.aims.GridWorld.Coordinate;

public class EliminazioneCappi {

	/**
	 * Costruttore
	 */
	public EliminazioneCappi() {
	}

	/**
	 * Elimina i cappi che si sono verificati durante la ricerca del percorso
	 * 
	 * @param percorso        lista di coordinate nella quale si memorizza il
	 *                        percorso
	 */
	public void eliminaCappi(ArrayList<Coordinate> percorso) {
		for (int i = 0; i < percorso.size(); i++) {
			/*
			 * Sono stati scelti al massimo 4 confronti per rendendere il tempo di
			 * esecuzione della funzione lineare e non quadratico; 4 confronti di
			 * conseguenza ad esperimenti fatti
			 */
			for (int j = 3; j < 10; j = j + 2) {
				if ((i + j) >= percorso.size())
					continue;
				// Cappi che si verificano in direzione est oppure ovest, ad esempio:
				// dato [(0,0)(1,0)(1,1)(0,1)] restituisce [(0,0)(0,1)]
				if (Math.abs(percorso.get(i + j).col - percorso.get(i).col) == 1
						&& percorso.get(i + j).row - percorso.get(i).row == 0) {
					eliminaCoordinateSuperflue(percorso, i, j);
				}
				if ((i + j) >= percorso.size())
					continue;
				// Cappi che si verificano in direzione nord oppure sud, ad esempio:
				// dato [(0,0)(0,1)(1,1)(1,0)] restituisce [(0,0)(1,0)]
				if (Math.abs(percorso.get(i + j).row - percorso.get(i).row) == 1
						&& percorso.get(i + j).col - percorso.get(i).col == 0) {
					eliminaCoordinateSuperflue(percorso, i, j);
				}
			}
		}
	}

	/**
	 * Elimina le coordinate del percorso individuate dalla funzione EliminaCappi
	 * 
	 * @param percorso        lista di coordinate nella quale si memorizza il
	 *                        percorso
	 * @param i+1			  indice di riferimento per l'inizio della
	 *                        cancellazione
	 * @param j-1			  è il numero di coordinate da cancellare
	 */
	private void eliminaCoordinateSuperflue(ArrayList<Coordinate> percorso, int i, int j) {
		/*
		 * L'indice k indica quante volte devo cancellare la coordinata (i+1)-esima del
		 * percorso Si cancella sempre allo stesso indice perchè ad ogni cancellazione
		 * l'elemento successivo diventa quello corrente
		 */
		for (int k = 0; k < j - 1; k++) {
			percorso.remove(i + 1);
		}
	}
}

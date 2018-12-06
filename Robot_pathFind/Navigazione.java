import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.unige.aims.GridWorld;
import org.unige.aims.GridWorld.Coordinate;
import org.unige.aims.GridWorld.Direction;

public class Navigazione {
	private Iterable<Coordinate> adiacenti;
	private ArrayList<Coordinate> celle_disponibili;
	private Direction spostamento;

	/**
	 * Costruttore
	 */
	public Navigazione() {
		celle_disponibili = new ArrayList<Coordinate>();
	}

	/**
	 * Trova sempre un percorso viste specifiche
	 * 
	 * @param gw                     GridWorld che si sta analizzando
	 * @param percorso               lista di coordinate nella quale si memorizza il
	 *                               percorso
	 * @param celle_visitate         lista di coordinate nella quale si memorizzano
	 *                               le celle già visitata
	 * @param adiacenti_per_percorso servirà a EliminazioneU per eliminare i cappi a
	 *                               forma di U se il precorso non è ottimale
	 * @return per indicare se il traguardo è stato raggiunto
	 */
	public boolean navigazioneRobot(GridWorld gw, ArrayList<Coordinate> percorso, HashSet<Coordinate> celle_visitate,
			HashMap<Coordinate, Iterable<Coordinate>> adiacenti_per_percorso) {
		do {
			celle_visitate.add(gw.getCurrentCell());
			adiacenti = gw.getAdjacentFreeCells();
			celle_disponibili.clear();
			for (Coordinate cella : adiacenti) {
				if (!celle_visitate.contains(cella)) {
					celle_disponibili.add(cella);
				}
			}
			if (!celle_disponibili.isEmpty()) {
				// se ho almeno uno spostamento disponibile
				spostamento = sceltaDirezione(celle_disponibili, gw.getCurrentCell());
				gw.moveToAdjacentCell(spostamento);
				percorso.add(gw.getCurrentCell());
				adiacenti_per_percorso.put(gw.getCurrentCell(), gw.getAdjacentFreeCells());
			} else {
				// se non c'erano spostamenti disponibili
				if (!vaiIndietro(gw, percorso, adiacenti_per_percorso)) {
					return false;
				}
			}
		} while (!gw.targetReached());
		return true;
	}

	/**
	 * Sceglie la nuova direzione che il robot deve prendere
	 * 
	 * @param celle_disponibili sono le coordinate senza ostacoli in cui il robot
	 *                          può andare
	 * @param posizione         è la posizione corrente del robot
	 * @return la direzione scelta
	 */
	private Direction sceltaDirezione(ArrayList<Coordinate> celle_disponibili, Coordinate posizione) {
		ArrayList<Direction> direzioni_disponibili = new ArrayList<Direction>();
		// Conversione delle coordinate in direzioni attraverso la funzione
		// definizioneDirezione
		for (Coordinate cella : celle_disponibili) {
			direzioni_disponibili.add(definizioneDirezione(posizione, cella));
		}
		// Si sceglie di privilegiare la direzione sud oppure est, se disponibili
		// entrambe, a seconda della posizione corrente
		if (direzioni_disponibili.contains(Direction.SOUTH) && direzioni_disponibili.contains(Direction.EAST)) {
			if (posizione.row > posizione.col) {
				return Direction.EAST;
			} else {
				return Direction.SOUTH;
			}
		}
		// Altrimenti viene utilizzata la seguente priorità; sud, est, nord, ovest
		if (direzioni_disponibili.contains(Direction.SOUTH)) {
			return Direction.SOUTH;
		}
		if (direzioni_disponibili.contains(Direction.EAST)) {
			return Direction.EAST;
		}
		if (direzioni_disponibili.contains(Direction.NORTH)) {
			return Direction.NORTH;
		}
		if (direzioni_disponibili.contains(Direction.WEST)) {
			return Direction.WEST;
		}

		/*
		 * Necessario perchè Eclipse non riconosce che sono state esaurite tutte le
		 * possibilità di direzioni disponibili
		 */
		return null;
	}

	/**
	 * Date due coordinate restituisce la direzione che le congiunge
	 * 
	 * @param posizione    è la coordinata dalla quale calcolare la direzione
	 * @param destinazione è la coordinata verso la quale si calcola la direzione
	 * @return la direzione individuata
	 */
	private Direction definizioneDirezione(Coordinate posizione, Coordinate destinazione) {
		if (posizione.col - destinazione.col == -1) {
			return Direction.EAST;
		}
		if (posizione.col - destinazione.col == 1) {
			return Direction.WEST;
		}
		if (posizione.row - destinazione.row == -1) {
			return Direction.SOUTH;
		}
		if (posizione.row - destinazione.row == 1) {
			return Direction.NORTH;
		}

		/*
		 * Necessario perchè Eclipse non riconosce che sono state esaurite tutte le
		 * possibilità di direzioni disponibili
		 */
		return null;
	}

	/**
	 * Serve per far eseguire un movimento in direzione opposto all'ultimo
	 * spostamento effettuato
	 * 
	 * @param gw                     GridWorld che si sta analizzando
	 * @param percorso               lista di coordinate nella quale si memorizza il
	 *                               percorso
	 * @param adiacenti_per_percorso servirà a EliminazioneU per eliminare i cappi a
	 *                               forma di U se il precorso non è ottimale
	 * @return se è stato possibile tornare indietro
	 */
	private boolean vaiIndietro(GridWorld gw, ArrayList<Coordinate> percorso,
			HashMap<Coordinate, Iterable<Coordinate>> adiacenti_per_percorso) {
		if (gw.getCurrentCell().row == 0 && gw.getCurrentCell().col == 0) {
			return false;
		}
		Direction spostamento_opposto = definizioneDirezione(gw.getCurrentCell(), percorso.get(percorso.size() - 2));
		adiacenti_per_percorso.remove(gw.getCurrentCell());
		gw.moveToAdjacentCell(spostamento_opposto);
		percorso.remove(percorso.size() - 1);
		return true;
	}

	/**
	 * Stampa il percorso individuato
	 * 
	 * @param percorso il percorso da stampare
	 */
	public void stampaPercorso(ArrayList<Coordinate> percorso) {
		// Viene utilizzato uno StringBuffer per diminuire il tempo di stampa a console
		StringBuffer stampa = new StringBuffer();
		stampa.append("Percorso:");
		for (Coordinate p : percorso) {
			stampa.append(" " + p);
		}
		System.out.println(stampa);
	}

}

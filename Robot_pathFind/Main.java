import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.unige.aims.GridWorld;
import org.unige.aims.GridWorld.Coordinate;

public class Main {
	/**
	 * Inizializza un GridWorld; chiama la classe NAvigazioneRobot per trovare un
	 * percorso (non sempre minimo); se opportuno elimina passi superflui.
	 * 
	 * @param args parametri da riga di comando
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Argomenti errati!");
		} else {
			if (Integer.parseInt(args[0]) > 0
					&& (Double.parseDouble(args[1]) >= 0 && Double.parseDouble(args[1]) <= 1)) {
				/*
				 * Creazione GridWorld: Dimensione: args[0]*args[0] Posizione iniziale del
				 * robot: (0,0) Posizione traguardo: (args[0]-1,args[0]-1)
				 */
				GridWorld gw = new GridWorld(Integer.parseInt(args[0]), Double.parseDouble(args[1]),
						Long.parseLong(args[2]));
				int percorso_minimo = gw.getMinimumDistanceToTarget();
				// adiacenti_per_percorso: servirà ad eliminare i cappi a forma di U se il
				// precorso non è ottimale
				HashMap<Coordinate, Iterable<Coordinate>> adiacenti_per_percorso = new HashMap<Coordinate, Iterable<Coordinate>>();
				ArrayList<Coordinate> percorso = new ArrayList<Coordinate>();
				HashSet<Coordinate> celle_visitate = new HashSet<Coordinate>();
				percorso.add(gw.getCurrentCell());
				adiacenti_per_percorso.put(gw.getCurrentCell(), gw.getAdjacentFreeCells());
				Navigazione cercaPercorso = new Navigazione();
				boolean traguardo = cercaPercorso.navigazioneRobot(gw, percorso, celle_visitate,
						adiacenti_per_percorso);
				if (!traguardo) {
					System.out.print("Nessun percorso!");
				} else {
					if ((percorso.size() - 1) != percorso_minimo) {
						EliminazioneCappi eliminaCappi = new EliminazioneCappi();
						eliminaCappi.eliminaCappi(percorso);
					}
					if ((percorso.size() - 1) != percorso_minimo) {
						EliminazioneU eliminaU = new EliminazioneU();
						eliminaU.eliminaU(percorso, adiacenti_per_percorso);
					}
					cercaPercorso.stampaPercorso(percorso);
				}
			} else {
				System.err.println("Dati degli argomenti errati");
			}
		}
	}
}

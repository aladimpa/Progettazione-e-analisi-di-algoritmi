import java.util.HashMap;

public class Opera {
	
	
	public Opera() {}
	
	public Long[] Operazione(String linea, int i, int parentesi_aperte, int parentesi_chiuse, HashMap<String, Long> variabili ) {
		Long primo_operando = null;
		Long secondo_operando = null;
		Long risultato[] = new Long[2];
		boolean operazione_interna[] = new boolean[2];
		operazione_interna[0]=false;
		operazione_interna[1]=false;
		boolean entro = false;
		boolean operazione = false;
		Long operando[] = new Long[2];
		do {
			i=i+1;
		}while (linea.charAt(i) == ' ');
		if (i+3 < linea.length() && linea.charAt(i) == 'A' && linea.charAt(i+1) == 'D' && linea.charAt(i+2) == 'D' && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			operazione = true;
			i = i+2;
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				entro = true;
				risultato = Operazione(linea, i, 1, 0, variabili);
				primo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				//operando = ricerca.Cerca(linea, i, variabili);
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				primo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				operazione_interna[0]=true;
			}
			if (entro) {
				i = Math.toIntExact(risultato[1]);
			}
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				risultato = Operazione(linea, i, 1, 0, variabili);
				secondo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				secondo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				operazione_interna[1]=true;
			}
			risultato[0] = primo_operando + secondo_operando;
		}
		if (i+3 < linea.length() && linea.charAt(i) == 'S' && linea.charAt(i+1) == 'U' && linea.charAt(i+2) == 'B'  && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			operazione = true;
			i = i+2;
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				entro = true;
				risultato = Operazione(linea, i, 1, 0, variabili);
				primo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				primo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				operazione_interna[0]=true;
			}
			if (entro) {
				i = Math.toIntExact(risultato[1]);
			}
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				risultato = Operazione(linea, i, 1, 0, variabili);
				secondo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				secondo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				operazione_interna[1]=true;
			}
			risultato[0] = primo_operando - secondo_operando;
		}
		if (i+3 < linea.length() && linea.charAt(i) == 'M' && linea.charAt(i+1) == 'U' && linea.charAt(i+2) == 'L'  && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			operazione = true;
			i = i+2;
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				entro = true;
				risultato = Operazione(linea, i, 1, 0, variabili);
				primo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				primo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				operazione_interna[0]=true;
			}
			if (entro) {
				i = Math.toIntExact(risultato[1]);
			}
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				risultato = Operazione(linea, i, 1, 0, variabili);
				secondo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				secondo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				operazione_interna[1]=true;
			}
			risultato[0] = primo_operando * secondo_operando;
		}
		if (i+3 < linea.length() && linea.charAt(i) == 'D' && linea.charAt(i+1) == 'I' && linea.charAt(i+2) == 'V'  && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			operazione = true;
			i = i+2;
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				entro = true;
				risultato = Operazione(linea, i, 1, 0, variabili);
				primo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				primo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				operazione_interna[0]=true;
			}
			if (entro) {
				i = Math.toIntExact(risultato[1]);
			}
			do {
				i=i+1;
			}while (linea.charAt(i) == ' ');
			if(linea.charAt(i) == '(') {
				risultato = Operazione(linea, i, 1, 0, variabili);
				secondo_operando = risultato[0];
			} else {
				CercaOperando ricerca = new CercaOperando();
				ricerca.Cerca(linea, i, variabili);
				operando = ricerca.getOperando();
				secondo_operando = operando[0];
				i = Math.toIntExact(operando[1]);
				if (secondo_operando == 0) {
					System.out.println("Errore nel valore del secondo operando: divisore uguale a zero");
					System.exit(0);
				}
				operazione_interna[1]=true;
			}
			risultato[0] = primo_operando / secondo_operando;
		}
		if (!operazione) {
			System.out.println("errore nella sintassi dell'operazione: esempio ADD (espressione espressione)");
			System.exit(0);
		}
		if (operazione_interna[0]==true && operazione_interna[1]==true) {
			risultato[1]=(long) i;
		} else {
			if (!entro) {
				i = Math.toIntExact(risultato[1]);
			}
		}
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
		if (parentesi_aperte != parentesi_chiuse) {
			System.out.println("Errore nella sintassi dell'espressione: manca la parentesi chiusa");
			System.exit(0);
		}
		risultato[1] = (long) i;
		return risultato;
	}
}

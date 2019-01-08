import java.util.HashMap;

public class Opera {
	
	private Long primo_operando;
	private Long secondo_operando;
	private Long risultato[];
	private boolean operazione_interna_primo;
	private boolean operazione_interna_secondo;
	private boolean entro;
	private boolean operazione;
	private Long operando[];
	
	public Opera() {
		primo_operando = null;
		secondo_operando = null;
		risultato = new Long[2];
		operazione_interna_primo=false;
		operazione_interna_secondo=false;
		entro = false;
		operazione = false;
		operando = new Long[2];
	}
	
	public Long getPrimo_operando() {
		return primo_operando;
	}



	public void setPrimo_operando(Long primo_operando) {
		this.primo_operando = primo_operando;
	}



	public Long getSecondo_operando() {
		return secondo_operando;
	}



	public void setSecondo_operando(Long secondo_operando) {
		this.secondo_operando = secondo_operando;
	}



	public Long[] getRisultato() {
		return risultato;
	}



	public void setRisultato(Long[] risultato) {
		this.risultato = risultato;
	}



	

	public boolean isOperazione_interna_primo() {
		return operazione_interna_primo;
	}

	public void setOperazione_interna_primo(boolean operazione_interna_primo) {
		this.operazione_interna_primo = operazione_interna_primo;
	}

	public boolean isOperazione_interna_secondo() {
		return operazione_interna_secondo;
	}

	public void setOperazione_interna_secondo(boolean operazione_interna_secondo) {
		this.operazione_interna_secondo = operazione_interna_secondo;
	}

	public boolean isEntro() {
		return entro;
	}



	public void setEntro(boolean entro) {
		this.entro = entro;
	}



	public boolean isOperazione() {
		return operazione;
	}



	public void setOperazione(boolean operazione) {
		this.operazione = operazione;
	}



	public Long[] getOperando() {
		return operando;
	}



	public void setOperando(Long[] operando) {
		this.operando = operando;
	}

	private int EseguiOperazione(String linea, int i, HashMap<String, Long> variabili ) {
		operazione = true;
		System.out.println("Entro");
		i = i+2;
		do {
			i=i+1;
		}while (linea.charAt(i) == ' ');
		if(linea.charAt(i) == '(') {
			entro = true;
			//se ci fosse ADD ( numero numero) come faccio?
			//parentesi_aperte = parentesi_aperte + 1;
			//chiamo ancora
			//primo_operando = Opera(linea, i, parentesi_aperte, parentesi_chiuse, variabili)[0];
			risultato = Operazione(linea, i, 1, 0, variabili);
		} else {
			CercaOperando ricerca = new CercaOperando();
			//operando = ricerca.Cerca(linea, i, variabili);
			ricerca.Cerca(linea, i, variabili);
			operando = ricerca.getOperando();
			primo_operando = operando[0];
			i = Math.toIntExact(operando[1]);
			operazione_interna_primo=true;
			
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
			risultato = Operazione(linea, i, 1, 0, variabili);
			secondo_operando = risultato[0];
			
		} else {
			CercaOperando ricerca = new CercaOperando();
			//operando = ricerca.Cerca(linea, i, variabili);
			ricerca.Cerca(linea, i, variabili);
			operando = ricerca.getOperando();
			secondo_operando = operando[0];
			i = Math.toIntExact(operando[1]);
			
			operazione_interna_secondo=true;
			
		}
		return i;
	}

	public Long[] Operazione(String linea, int i, int parentesi_aperte, int parentesi_chiuse, HashMap<String, Long> variabili ) {
		System.out.println("stampa" + linea.charAt(i));
		do {
			i=i+1;
		}while (linea.charAt(i) == ' ');
		if (i+3 < linea.length() && linea.charAt(i) == 'A' && linea.charAt(i+1) == 'D' && linea.charAt(i+2) == 'D' && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			i = EseguiOperazione(linea, i, variabili );
			
			risultato[0] = primo_operando + secondo_operando;
		}
		if (i+3 < linea.length() && linea.charAt(i) == 'S' && linea.charAt(i+1) == 'U' && linea.charAt(i+2) == 'B'  && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			i = EseguiOperazione(linea, i, variabili );
			risultato[0] = primo_operando - secondo_operando;
		}
		if (i+3 < linea.length() && linea.charAt(i) == 'M' && linea.charAt(i+1) == 'U' && linea.charAt(i+2) == 'L'  && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			i = EseguiOperazione(linea, i, variabili );
			risultato[0] = primo_operando * secondo_operando;
		}
		if (i+3 < linea.length() && linea.charAt(i) == 'D' && linea.charAt(i+1) == 'I' && linea.charAt(i+2) == 'V'  && (linea.charAt(i+3) == ' ' || linea.charAt(i+3) == '(')) {
			i = EseguiOperazione(linea, i, variabili );
			if (secondo_operando == 0) {
					System.out.println("Errore nel valore del secondo operando: divisore uguale a zero");
					System.exit(0);
				}
			risultato[0] = primo_operando / secondo_operando;
		}
		if (!operazione) {
			System.out.println("errore nella sintassi dell'operazione: esempio ADD (espressione espressione)");
			System.exit(0);
		}
		if (operazione_interna_primo==true && operazione_interna_secondo==true) {
			System.out.println("i in funzione: " + i);
			risultato[1]=(long) i;
		} else {
			if (!entro) {
				i = Math.toIntExact(risultato[1]);
			}
		}
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
}

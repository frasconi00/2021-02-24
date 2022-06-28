package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulatore {
	
	//parametri della simulazione
	private Match m;
	private int N;
	
	//output della simulazione
	private int golCasa;
	private int golTrasferta;
	
	//modello del mondo
	int nGiocatoriCasa;
	int nGiocatoriTrasferta;
	private int nEspulsioniCasa;
	private int nEspulsioniTrasferta;
	private int idSquadraMigliore;
	int err;
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	public Simulatore(Match m, int N) {
		this.m = m;
		this.N = N;
	}
	
	public void init(int idSquadraMigliore) {
		this.golCasa = 0;
		this.golTrasferta = 0;
		this.nGiocatoriCasa = 11;
		this.nGiocatoriTrasferta = 11;
		this.nEspulsioniCasa = 0;
		this.nEspulsioniTrasferta = 0;
		this.idSquadraMigliore = idSquadraMigliore;
		err=0;
		
		this.queue = new PriorityQueue<Event>();
		
		for(int count=1; count<=N ; count++) {
			int rand = ((int)(Math.random()*100+1));
			if(rand<=50) {
				this.queue.add(new Event(EventType.GOAL, count));
			}else if(rand>=51 && rand <=80) {
				this.queue.add(new Event(EventType.ESPULSIONE, count));
			} else {
				this.queue.add(new Event(EventType.INFORTUNIO, count));
			}
		}
		
	}
	
	public void run() {
		
		while((!this.queue.isEmpty()) && (this.err==0)) {
			
			if(this.nGiocatoriCasa<=0) {
				this.err=-1;
			}
			
			if(this.nGiocatoriTrasferta<=0) {
				this.err=1;
			}
			
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		
		switch (e.getType()) {
		case GOAL:
			
			if(this.nGiocatoriCasa>this.nGiocatoriTrasferta) {
				
				this.golCasa++;
				
			} else if (this.nGiocatoriCasa<this.nGiocatoriTrasferta) {
				
				this.golTrasferta++;
				
			} else { //this.nGiocatoriCasa==this.nGiocatoriTrasferta
				
				if(this.idSquadraMigliore==this.m.teamHomeID) {
					this.golCasa++;
					
				} else {
					this.golTrasferta++;
				}
				
			}
			
			break;
		
		case ESPULSIONE:
			
			int random2 = ((int)(Math.random()*100+1));
			if(random2<=60) {
				//espulsione nella squadra migliore
				if(this.idSquadraMigliore==this.m.teamHomeID) {
					this.nGiocatoriCasa--;
					this.nEspulsioniCasa++;
				} else {
					this.nGiocatoriTrasferta--;
					this.nEspulsioniTrasferta++;
				}
				
			} else {
				//espulsione nella squadra peggiore
				if(this.idSquadraMigliore==this.m.teamHomeID) {
					this.nGiocatoriTrasferta--;
					this.nEspulsioniTrasferta++;
				} else {
					this.nGiocatoriCasa--;
					this.nEspulsioniCasa++;
				}
			}
			
			break;
			
		case INFORTUNIO:
			
			//aggiungiamo 2 o 3 eventi
			int tot = 1;
			tot+=((int)(Math.random()*2+1));
			
			int count = e.getCount();
			
			for(int i=count+1;i<=count+tot;i++) {
				int rand = ((int)(Math.random()*100+1));
				if(rand<=50) {
					this.queue.add(new Event(EventType.GOAL, i));
				}else if(rand>=51 && rand <=80) {
					this.queue.add(new Event(EventType.ESPULSIONE, i));
				} else {
					this.queue.add(new Event(EventType.INFORTUNIO, i));
				}
			}
			
			break;
		}
		
	}

	public int getGolCasa() {
		return golCasa;
	}

	public int getGolTrasferta() {
		return golTrasferta;
	}

	public int getnEspulsioniCasa() {
		return nEspulsioniCasa;
	}

	public int getnEspulsioniTrasferta() {
		return nEspulsioniTrasferta;
	}
	
}

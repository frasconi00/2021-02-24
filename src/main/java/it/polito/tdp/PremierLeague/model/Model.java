package it.polito.tdp.PremierLeague.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Map<Integer,Player> idMap;
	
	private Graph<Player, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}
	
	public List<Match> listAllMatches() {
		
		List<Match> result = this.dao.listAllMatches();
		
		Collections.sort(result, new Comparator<Match>() {

			@Override
			public int compare(Match o1, Match o2) {
				return o1.getMatchID()-o2.getMatchID();
			}
		});
		
		return result;
	}
	
	public void creaGrafo(int matchID) {
		//creo il grafo
		this.grafo = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(matchID));
		this.idMap = new HashMap<Integer, Player>();
		for(Player p : this.grafo.vertexSet())
			this.idMap.put(p.getPlayerID(), p);
		
		//aggiungo gli archi
		
		for(Adiacenza a : this.dao.getAdiacenze(matchID)) {
			
			if(a.getE1()>a.getE2()) {
				Graphs.addEdgeWithVertices(this.grafo, this.idMap.get(a.getId1()), this.idMap.get(a.getId2()), a.getE1()-a.getE2());
			} else {
				Graphs.addEdgeWithVertices(this.grafo, this.idMap.get(a.getId2()), this.idMap.get(a.getId1()), a.getE2()-a.getE1());
			}
			
		}
		
		
//		System.out.println("#vertici: "+this.grafo.vertexSet().size());
//		System.out.println("#archi: "+this.grafo.edgeSet().size());
		
//		int count=0;
//		for(DefaultWeightedEdge e : this.grafo.edgeSet()) 
//			System.out.println("Arco "+(++count)+", peso: "+this.grafo.getEdgeWeight(e));
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else
			return true;
	}
	
	public GiocatoreMigliore trovaGiocatoreMigliore() {
		
		int id = -1;
		double deltaMax = Double.MIN_VALUE;
		
		for(Player p : this.grafo.vertexSet()) {
			
			double sommaUscenti=0;
			double sommaEntranti=0;
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p)) {
				sommaUscenti+=this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(p)) {
				sommaEntranti+=this.grafo.getEdgeWeight(e);
			}
			
			double delta = sommaUscenti - sommaEntranti;
			if(delta>deltaMax) {
				id=p.getPlayerID();
				deltaMax = delta;
			}
			
		}
		
		GiocatoreMigliore g = new GiocatoreMigliore(this.idMap.get(id), deltaMax);
		return g;
	}
	
	public String simula(Match m, int N) {
		
		Simulatore sim = new Simulatore(m, N);
		
		int idSquadraMigliore = this.trovaGiocatoreMigliore().getPlayer().getPlayerID();
		sim.init(idSquadraMigliore);
		sim.run();
		
		if(sim.err==-1) {
			return "\nTutti i giocatori della squadra di casa sono stati espulsi!";
		} 
		if(sim.err==1) {
			return "\nTutti i giocatori della squadra in trasferta sono stati espulsi!";
		}
		
		String s = "\n\nRisultato partita: "+sim.getGolCasa()+" - "+sim.getGolTrasferta();
		s+="\nNumero di espulsioni casa: "+sim.getnEspulsioniCasa();
		s+="\nNumero di espulsioni trasferta: "+sim.getnEspulsioniTrasferta();
		
		return s;
		
	}
	
	
}

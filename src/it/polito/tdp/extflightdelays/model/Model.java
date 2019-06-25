package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private ExtFlightDelaysDAO dao;
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private List<Airport> airports;
	private List<Airport> percorsoFinale;
	private int oreVolo;

	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

	}

	public void creaGrafo(int voli) {

		// vertici
		this.airports = new ArrayList<>(dao.getAirportMinimum(voli));
		Graphs.addAllVertices(this.grafo, airports);

		// archi
		for (Airport a : grafo.vertexSet()) {
			List<Airport> destination = dao.loadDestination(a.getId());
			for (Airport d : destination) {
				if (!d.equals(a)) {
					if (this.grafo.containsVertex(d) && (!this.grafo.containsEdge(a, d) || !this.grafo.containsEdge(d,a))) {
						DefaultWeightedEdge e = this.grafo.addEdge(a, d);
						double peso = dao.getPeso(a.getId(), d.getId());
						this.grafo.setEdgeWeight(e, peso);
					}
				}

			}
		}
	}

	public Graph<Airport, DefaultWeightedEdge> getGrafo() {
		return this.grafo;
	}
	
	public List<Airport> getAirport(){
		Collections.sort(airports);
		return airports;
	}

	public List<AeroportiCollegati> getVicini(Airport partenza) {
		List<AeroportiCollegati> result = new ArrayList<AeroportiCollegati>();
		List<Airport> vicini = Graphs.neighborListOf(this.grafo, partenza);
		for(Airport a : vicini) {
			DefaultWeightedEdge e = grafo.getEdge(a, partenza);
			double peso = grafo.getEdgeWeight(e);
			result.add(new AeroportiCollegati(a, partenza, peso));
		}
		Collections.sort(result);
		return result;
	}

	public List<Airport> cercaPercorso(int ore, Airport partenza) {
		percorsoFinale = new ArrayList<Airport>();
		List<Airport> parziale = new ArrayList<Airport>();
		this.oreVolo = ore;
		parziale.add(partenza);
		
		cerca(parziale, partenza);
		
		return percorsoFinale;
	}

	private void cerca(List<Airport> parziale, Airport partenza) {
		// TODO Auto-generated method stub
		
	}

}

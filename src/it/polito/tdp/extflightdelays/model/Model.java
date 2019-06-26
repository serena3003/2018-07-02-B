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
	private double oreVoloTot; // risultato della ricorsione
	private int oreVoloMax; // inserite dall'utente

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
					if (this.grafo.containsVertex(d)
							&& (!this.grafo.containsEdge(a, d) || !this.grafo.containsEdge(d, a))) {
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

	public List<Airport> getAirport() {
		Collections.sort(airports);
		return airports;
	}

	public List<AeroportiCollegati> getVicini(Airport partenza) {
		List<AeroportiCollegati> result = new ArrayList<AeroportiCollegati>();
		List<Airport> vicini = Graphs.neighborListOf(this.grafo, partenza);
		for (Airport a : vicini) {
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
		int oreVoloMax = ore;
		double oreVolo = 0.0;
		oreVoloTot = 0.0;
		parziale.add(partenza);

		cerca(parziale, partenza);

		return percorsoFinale;
	}

	private void cerca(List<Airport> parziale, Airport partenza) {
		if (parziale.size() > 2) {
			double oreVolo = calcolaOre(parziale, partenza);
			System.out.println(oreVolo+"\n");
			if ((oreVolo < oreVoloMax) && oreVolo > oreVoloTot) {
				oreVoloTot = oreVolo;
				percorsoFinale = new ArrayList<Airport>(parziale);
				System.out.println("nuovo percorso:\n" + parziale.toString() + "\n");
			}
		} 

		for (Airport a : Graphs.neighborListOf(this.grafo, partenza)) {
			//Airport a = grafo.getEdgeTarget(e);
			if (!parziale.contains(a)) {
				parziale.add(a);
				cerca(parziale, partenza);
				parziale.remove(a);
			}
		}

	}

	private double calcolaOre(List<Airport> parziale, Airport partenza) {
		double somma = 0;
		for (Airport a : parziale) {
			if(!a.equals(partenza)) {
			DefaultWeightedEdge e = this.grafo.getEdge(partenza, a);
			if(e!=null)
				somma = somma + (this.grafo.getEdgeWeight(e) * 2);
			else {
				DefaultWeightedEdge e1 = this.grafo.getEdge(a, partenza);
				somma = somma + (this.grafo.getEdgeWeight(e1) * 2);
			}
			}
		}
		return somma;
	}

}

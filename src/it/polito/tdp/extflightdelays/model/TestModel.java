package it.polito.tdp.extflightdelays.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.creaGrafo(15);
		
		Graph<Airport, DefaultWeightedEdge> grafo = model.getGrafo();
		
		System.out.println("Grafo creato di " + grafo.vertexSet().size()+" vertici e " + grafo.edgeSet().size()+" archi");
		Airport partenza = new Airport(0,	"ABE",	"Lehigh Valley International Airport",	"Allentown",	"PA",	"USA",	40.65236,	-75.4404,	-4.0);
		List<Airport> list = model.cercaPercorso(10, partenza);
		for(Airport a : list) {
			System.out.println(a.toString());
		}
		/*for(DefaultWeightedEdge e : grafo.edgeSet()) {
			System.out.println(e.toString() +"-"+ grafo.getEdgeWeight(e));
		}
		
		/*Airport partenza = new Airport(0,	"ABE",	"Lehigh Valley International Airport",	"Allentown",	"PA",	"USA",	40.65236,	-75.4404,	-4.0);
		
		List<AeroportiCollegati> vicini = model.getVicini(partenza);
		for(AeroportiCollegati ac : vicini) {
    		System.out.println(ac.getPeso() + " - ");
    		if(ac.getDestinazione().equals(partenza)) {
    			System.out.println(ac.getDestinazione().getAirportName());
    			System.out.println("---");
    		} else {
    			System.out.println(ac.getPartenza().getAirportName());
    			System.out.println("!!!!");
    		}
    	}*/
	}

}

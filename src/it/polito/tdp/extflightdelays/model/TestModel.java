package it.polito.tdp.extflightdelays.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.creaGrafo(15);
		
		System.out.println("Grafo creato di " + model.getGrafo().vertexSet().size()+" vertici e " + model.getGrafo().edgeSet().size()+" archi");
		/*for(Airport a : model.getGrafo().vertexSet()) {
			System.out.println(a.toString());
		}*/
		
		Airport partenza = new Airport(0,	"ABE",	"Lehigh Valley International Airport",	"Allentown",	"PA",	"USA",	40.65236,	-75.4404,	-4.0);
		
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
    	}
	}

}

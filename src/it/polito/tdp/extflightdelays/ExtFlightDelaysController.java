/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.AeroportiCollegati;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="voliMinimo"
	private TextField voliMinimo; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizza"
	private Button btnAnalizza; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxAeroportoPartenza"
	private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

	@FXML // fx:id="btnAeroportiConnessi"
	private Button btnAeroportiConnessi; // Value injected by FXMLLoader

	@FXML // fx:id="numeroOreTxtInput"
	private TextField numeroOreTxtInput; // Value injected by FXMLLoader

	@FXML // fx:id="btnOttimizza"
	private Button btnOttimizza; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaAeroporti(ActionEvent event) {
		try {
			int voli = Integer.parseInt(voliMinimo.getText());
			txtResult.clear();
			if (voli < 0) {
				txtResult.appendText("Numero voli non corretto; reinserirlo!");
			} else {
				model.creaGrafo(voli);
				cmbBoxAeroportoPartenza.getItems().addAll(model.getAirport());
				txtResult.clear();
				txtResult.appendText("Grafo creato! Selezionare un aeroporto dal menu a tendina.");
			}
		} catch (NumberFormatException e) {
			txtResult.clear();
			txtResult.appendText("Numero voli non corretto; reinserirlo!");
		}

	}

	@FXML
	void doCalcolaAeroportiConnessi(ActionEvent event) {
		try {
			txtResult.clear();
			Airport partenza = cmbBoxAeroportoPartenza.getValue();
			List<AeroportiCollegati> vicini = model.getVicini(partenza);
			txtResult.appendText("I vicini di " + partenza.getAirportName() + " sono:\n");
			for (AeroportiCollegati ac : vicini) {
				txtResult.appendText(ac.getPeso() + " - ");
				txtResult.appendText(ac.getDestinazione().getAirportName() + "\n");
			}
		} catch (NullPointerException e) {
			txtResult.clear();
			txtResult.appendText("Grafo non ancora creato, prima ANALIZZA AEROPORTI!");
		}
	}

	@FXML
	void doCercaItinerario(ActionEvent event) {

		try {
			txtResult.clear();
			int ore = Integer.parseInt(numeroOreTxtInput.getText());
			Airport partenza = cmbBoxAeroportoPartenza.getValue();
			model.cercaPercorso(ore, partenza);
		} catch (NullPointerException e) {
			txtResult.clear();
			txtResult.appendText("Grafo non ancora creato, prima ANALIZZA AEROPORTI!");
		}catch (NumberFormatException e) {
			txtResult.clear();
			txtResult.appendText("Numero ore di volo non corretto; reinserirlo!");
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert voliMinimo != null : "fx:id=\"voliMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert numeroOreTxtInput != null : "fx:id=\"numeroOreTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert btnOttimizza != null : "fx:id=\"btnOttimizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;

	}
}

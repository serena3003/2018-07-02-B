package it.polito.tdp.extflightdelays.model;

public class AeroportiCollegati implements Comparable<AeroportiCollegati> {
	
	private Airport destinazione;
	private Airport partenza; 
	private double peso;
	
	public AeroportiCollegati(Airport destinazione, Airport partenza, double peso) {
		super();
		this.destinazione = destinazione;
		this.partenza = partenza;
		this.peso = peso;
	}

	public Airport getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(Airport destinazione) {
		this.destinazione = destinazione;
	}

	public Airport getPartenza() {
		return partenza;
	}

	public void setPartenza(Airport partenza) {
		this.partenza = partenza;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinazione == null) ? 0 : destinazione.hashCode());
		result = prime * result + ((partenza == null) ? 0 : partenza.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AeroportiCollegati other = (AeroportiCollegati) obj;
		if (destinazione == null) {
			if (other.destinazione != null)
				return false;
		} else if (!destinazione.equals(other.destinazione))
			return false;
		if (partenza == null) {
			if (other.partenza != null)
				return false;
		} else if (!partenza.equals(other.partenza))
			return false;
		return true;
	}

	@Override
	public int compareTo(AeroportiCollegati o) {
		if(peso<o.getPeso()) {
			return -1;
		}else if(peso>o.getPeso()) {
			return +1;
		}
		return 0;
	}
	
	

}

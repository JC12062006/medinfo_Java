package controller;

import java.time.LocalDateTime;

public class Rdv {
	
	private int idRdv;
    private LocalDateTime dateCreation;
    private String motif;
    private String statut;
    private String origine;
    private int fkIdPatient;
    private int fkIdCreneau;
    
    public Rdv(int idRdv, int fkIdPatient, int fkIdCreneau, LocalDateTime datecreation, String motif, String statut, String origine){
    	
    	this.dateCreation = datecreation.now();
    	this.idRdv = idRdv;
    	this.fkIdPatient = fkIdPatient;
    	this.fkIdCreneau = fkIdCreneau;
    	this.motif = motif;
    	this.statut = statut;
    	this.origine = "Desktop";
    }

	public int getIdRdv() {
		return idRdv;
	}

	public void setIdRdv(int idRdv) {
		this.idRdv = idRdv;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public int getFkIdPatient() {
		return fkIdPatient;
	}

	public void setFkIdPatient(int fkIdPatient) {
		this.fkIdPatient = fkIdPatient;
	}

	public int getFkIdCreneau() {
		return fkIdCreneau;
	}

	public void setFkIdCreneau(int fkIdCreneau) {
		this.fkIdCreneau = fkIdCreneau;
	}
    
    
}

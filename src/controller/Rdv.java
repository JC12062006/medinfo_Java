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
    
    private String nomCompletPatient;
    private String dateCreneauFormatee;
    
    public Rdv(int idRdv, int fkIdPatient, int fkIdCreneau, LocalDateTime datecreation, String motif, String statut, String origine, String nomCompletPatient, String dateCreneauFormatee){
    	
    	// J'ai corrigé datecreation.now() qui écrasait la date récupérée en BDD
    	this.dateCreation = (datecreation != null) ? datecreation : LocalDateTime.now(); 
    	this.idRdv = idRdv;
    	this.fkIdPatient = fkIdPatient;
    	this.fkIdCreneau = fkIdCreneau;
    	this.motif = motif;
    	this.statut = statut;
    	this.origine = origine; // Corrigé : pour utiliser l'origine de la BDD plutôt que "Desktop" en dur
    	this.nomCompletPatient = nomCompletPatient;
    	this.dateCreneauFormatee = dateCreneauFormatee;
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
	
	public String getNomCompletPatient() {
		return nomCompletPatient;
	}

	public void setNomCompletPatient(String nomCompletPatient) {
		this.nomCompletPatient = nomCompletPatient;
	}

	public String getDateCreneauFormatee() {
		return dateCreneauFormatee;
	}

	public void setDateCreneauFormatee(String dateCreneauFormatee) {
		this.dateCreneauFormatee = dateCreneauFormatee;
	}
    
    
}

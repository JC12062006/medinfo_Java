package controller;

public class Specialite {

	private int idSpecialite;
	private String libelle;
	
	
	public Specialite(int idSpecialite, String libelle) {
		
		this.idSpecialite = idSpecialite;
		this.libelle = libelle;
	}

	// ______GETTERS___________
	public int getIdSpecialite() {
		return idSpecialite;
	}


	public void setIdSpecialite(int idSpecialite) {
		this.idSpecialite = idSpecialite;
	}
	
	

	//_____________SETTERS_____________
	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
}

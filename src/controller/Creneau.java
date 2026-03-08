package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Creneau {

    private int id_creneau;
    private LocalDate date_jour;
    private LocalTime heure_debut;
    private LocalTime heure_fin;
    private String statut;
    private boolean disponibilite;
    private String nom_medecin;
    private String prenom_medecin;
    private String salle;


    public Creneau(int id_creneau, LocalDate date_jour, LocalTime heure_debut, LocalTime heure_fin,
                   String statut, boolean disponibilite, String nom_medecin, String prenom_medecin, String salle) {
        this.id_creneau = id_creneau;
        this.date_jour = date_jour;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.statut = statut;
        this.disponibilite = disponibilite;
        this.nom_medecin = nom_medecin;
        this.prenom_medecin = prenom_medecin;
        this.salle = salle;
    }


	public int getId_creneau() {
		return id_creneau;
	}


	public void setId_creneau(int id_creneau) {
		this.id_creneau = id_creneau;
	}


	public LocalDate getDate_jour() {
		return date_jour;
	}


	public void setDate_jour(LocalDate date_jour) {
		this.date_jour = date_jour;
	}


	public LocalTime getHeure_debut() {
		return heure_debut;
	}


	public void setHeure_debut(LocalTime heure_debut) {
		this.heure_debut = heure_debut;
	}


	public LocalTime getHeure_fin() {
		return heure_fin;
	}


	public void setHeure_fin(LocalTime heure_fin) {
		this.heure_fin = heure_fin;
	}


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public boolean isDisponibilite() {
		return disponibilite;
	}


	public void setDisponibilite(boolean disponibilite) {
		this.disponibilite = disponibilite;
	}


	public String getNom_medecin() {
		return nom_medecin;
	}


	public void setNom_medecin(String nom_medecin) {
		this.nom_medecin = nom_medecin;
	}


	public String getPrenom_medecin() {
		return prenom_medecin;
	}


	public void setPrenom_medecin(String prenom_medecin) {
		this.prenom_medecin = prenom_medecin;
	}


	public String getSalle() {
		return salle;
	}


	public void setSalle(String salle) {
		this.salle = salle;
	}

}
	



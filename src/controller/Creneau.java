package controller;

import java.time.LocalDateTime;

public class Creneau {

    private int id_creneau;
    private LocalDateTime date_heure_debut;
    private LocalDateTime date_heure_fin;
    private String statut;
    private boolean disponibilite;
    private int fk_id_medecin;
    private int fk_id_salle;

    public Creneau(int id_creneau, LocalDateTime date_heure_debut, LocalDateTime date_heure_fin,
                   String statut, boolean disponibilite, int fk_id_medecin, int fk_id_salle) {
        this.id_creneau = id_creneau;
        this.date_heure_debut = date_heure_debut;
        this.date_heure_fin = date_heure_fin;
        this.statut = statut;
        this.disponibilite = disponibilite;
        this.fk_id_medecin = fk_id_medecin;
        this.fk_id_salle = fk_id_salle;
    }

	public int getId_creneau() {
		return id_creneau;
	}

	public void setId_creneau(int id_creneau) {
		this.id_creneau = id_creneau;
	}

	public LocalDateTime getDate_heure_debut() {
		return date_heure_debut;
	}

	public void setDate_heure_debut(LocalDateTime date_heure_debut) {
		this.date_heure_debut = date_heure_debut;
	}

	public LocalDateTime getDate_heure_fin() {
		return date_heure_fin;
	}

	public void setDate_heure_fin(LocalDateTime date_heure_fin) {
		this.date_heure_fin = date_heure_fin;
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

	public int getFk_id_medecin() {
		return fk_id_medecin;
	}

	public void setFk_id_medecin(int fk_id_medecin) {
		this.fk_id_medecin = fk_id_medecin;
	}

	public int getFk_id_salle() {
		return fk_id_salle;
	}

	public void setFk_id_salle(int fk_id_salle) {
		this.fk_id_salle = fk_id_salle;
	}
}



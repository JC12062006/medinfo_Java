package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.Creneau;

public class ModelCreneau {
	

	private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");

	public static ArrayList<Creneau> selectAllCreneaux(String filtre) {
    
	    ArrayList<Creneau> lesCreneaux = new ArrayList<Creneau>();
	    String requete;
	
	    if (filtre == null || filtre.equals("")) {
	        requete =
	            "SELECT c.id_creneau, " +
	            "DATE(c.date_heure_debut) AS date_jour, " +
	            "TIME(c.date_heure_debut) AS heure_debut, " +
	            "TIME(c.date_heure_fin) AS heure_fin, " +
	            "c.statut, c.disponibilite, " +
	            "u.nom AS nom_medecin, u.prenom AS prenom_medecin, " +
	            "s.libelle AS salle " +
	            "FROM creneau c " +
	            "JOIN medecin m ON c.fk_id_medecin = m.id_medecin " +
	            "JOIN utilisateur u ON m.fk_id_utilisateur = u.id_utilisateur " +
	            "JOIN salle s ON c.fk_id_salle = s.id_salle " +
	            "ORDER BY c.date_heure_debut";
	    } else {
	        requete =
	            "SELECT c.id_creneau, " +
	            "DATE(c.date_heure_debut) AS date_jour, " +
	            "TIME(c.date_heure_debut) AS heure_debut, " +
	            "TIME(c.date_heure_fin) AS heure_fin, " +
	            "c.statut, c.disponibilite, " +
	            "u.nom AS nom_medecin, u.prenom AS prenom_medecin, " +
	            "s.libelle AS salle " +
	            "FROM creneau c " +
	            "JOIN medecin m ON c.fk_id_medecin = m.id_medecin " +
	            "JOIN utilisateur u ON m.fk_id_utilisateur = u.id_utilisateur " +
	            "JOIN salle s ON c.fk_id_salle = s.id_salle " +
	            "WHERE " +
	            "u.nom LIKE '%" + filtre + "%' OR " +
	            "u.prenom LIKE '%" + filtre + "%' OR " +
	            "s.libelle LIKE '%" + filtre + "%' OR " +
	            "c.statut LIKE '%" + filtre + "%' OR " +
	            "DATE(c.date_heure_debut) LIKE '%" + filtre + "%' OR " +
	            "(c.disponibilite = 1 AND 'true' LIKE '%" + filtre + "%') OR " +
	            "(c.disponibilite = 0 AND 'false' LIKE '%" + filtre + "%') " +
	            "ORDER BY c.date_heure_debut";
	    }
	
	
	
	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet desResultats = unStat.executeQuery(requete);
	
	        while (desResultats.next()) {
	
	            Creneau unCreneau = new Creneau(
	                desResultats.getInt("id_creneau"),
	                desResultats.getDate("date_jour").toLocalDate(),
	                desResultats.getTime("heure_debut").toLocalTime(),
	                desResultats.getTime("heure_fin").toLocalTime(),
	                desResultats.getString("statut"),
	                desResultats.getBoolean("disponibilite"),
	                desResultats.getString("nom_medecin"),
	                desResultats.getString("prenom_medecin"),
	                desResultats.getString("salle")
	            );
	
	            lesCreneaux.add(unCreneau);
	        }
	
	        unStat.close();
	        uneBdd.seDeconnecter();
	
	    } catch (SQLException exp) {
	        System.out.println("Erreur d'exécution : " + requete);
	    }
	
	    return lesCreneaux;

	}

	public static void genererPlanning(int idMedecin, int idSalle, String dateDebut, String dateFin, int dureeMinutes) {
	    String requete = "{call generer_planning_medecin(?, ?, ?, ?, ?)}";
	    
	    try {
	        uneBdd.seConnecter();
	        java.sql.CallableStatement unStat = uneBdd.getMaConnexion().prepareCall(requete);
	        
	        // On remplace les ? par les paramètres
	        unStat.setInt(1, idMedecin);
	        unStat.setInt(2, idSalle);
	        // MySQL accepte nativement les chaînes de caractères au format "YYYY-MM-DD HH:MM:SS" pour les DATETIME
	        unStat.setString(3, dateDebut); 
	        unStat.setString(4, dateFin);
	        unStat.setInt(5, dureeMinutes);
	        
	        // Exécution de la procédure
	        unStat.execute();
	        
	        unStat.close();
	        uneBdd.seDeconnecter();
	        
	    } catch (SQLException exp) {
	        System.out.println("Erreur lors de la génération du planning : " + exp.getMessage());
	    }
	}
}


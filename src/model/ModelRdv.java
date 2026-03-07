package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.Rdv;

public class ModelRdv {
	
	private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");

	public static void executerRequete(String requete) {
	
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			unStat.execute(requete);
			unStat.close();
			uneBdd.seDeconnecter();
		}
		catch (SQLException exp) {
			System.out.println("Erreur d'execution de la requete:" + requete);
			}
	}
    
	public static ArrayList<Rdv> selectAllRdv(String filtre) {
	    ArrayList<Rdv> lesRdv = new ArrayList<Rdv>();
	    String requete;
	    		
	    if(filtre.equals("")){
            // Jointure pour tout récupérer
	    	requete = "SELECT r.*, u.nom, u.prenom, c.date_heure_debut " +
                      "FROM rendez_vous r " +
                      "INNER JOIN patient p ON r.fk_id_patient = p.id_patient " +
                      "INNER JOIN utilisateur u ON p.fk_id_utilisateur = u.id_utilisateur " +
                      "INNER JOIN creneau c ON r.fk_id_creneau = c.id_creneau " +
                      "ORDER BY c.date_heure_debut ASC";
	    } else {
            // Jointure avec la barre de recherche
	    	requete = "SELECT r.*, u.nom, u.prenom, c.date_heure_debut " +
                      "FROM rendez_vous r " +
                      "INNER JOIN patient p ON r.fk_id_patient = p.id_patient " +
                      "INNER JOIN utilisateur u ON p.fk_id_utilisateur = u.id_utilisateur " +
                      "INNER JOIN creneau c ON r.fk_id_creneau = c.id_creneau " +
                      "WHERE u.nom LIKE '%" + filtre + "%' OR u.prenom LIKE '%" + filtre + "%' " +
                      "ORDER BY c.date_heure_debut ASC";
	    }
	
        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet desResultats = unStat.executeQuery(requete);

            // Formateur de date pour le créneau
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");
	
            while (desResultats.next()) {
                // 1. Concaténer nom et prénom
                String nomComplet = desResultats.getString("nom") + " " + desResultats.getString("prenom");
                
                // 2. Récupérer et formater la date du créneau
                LocalDateTime dateDebut = desResultats.getObject("date_heure_debut", LocalDateTime.class);
                String dateFormatee = (dateDebut != null) ? dateDebut.format(formatter) : "Date inconnue";

                Rdv unRdv = new Rdv(
                    desResultats.getInt("id_rdv"), 
                    desResultats.getInt("fk_id_patient"), 
                    desResultats.getInt("fk_id_creneau"), 
                    desResultats.getObject("date_creation", LocalDateTime.class), 
                    desResultats.getString("motif"), 
                    desResultats.getString("statut"), 
                    desResultats.getString("origine"),
                    nomComplet,        // <-- Nouvel argument
                    dateFormatee       // <-- Nouvel argument
                );
                
                lesRdv.add(unRdv);
            }
            unStat.close();
            uneBdd.seDeconnecter();
	
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution : " + requete);
        }
	
        return lesRdv;
    }	
	
	public static void updateRdv(Rdv unRdv) {
	    // On construit la chaîne de caractères avec les valeurs de l'objet unRdv
	    String requete = "UPDATE rendez_vous SET " 
	            + "motif = '" + unRdv.getMotif() + "', "
	            + "statut = '" + unRdv.getStatut() + "', "
	            + "origine = '" + unRdv.getOrigine() + "', "
	            + "fk_id_patient = " + unRdv.getFkIdPatient() + ", "
	            + "fk_id_creneau = " + unRdv.getFkIdCreneau() + " "
	            + "WHERE id_rdv = " + unRdv.getIdRdv() + ";";
	            
	    // On exécute la requête avec ta méthode existante
	    executerRequete(requete);
	}
	
	public static void deleteRdv(int idRdv) {
		String requete = "delete from rendez_vous where id_rdv =" + idRdv +";";
		executerRequete (requete);
	}
	
	
}





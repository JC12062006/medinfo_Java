package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.Rdv;

public class ModelRdv {
	
private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");
    
	public static ArrayList<Rdv> selectAllRdv(String filtre) {
	    	
	    	ArrayList<Rdv> lesPatients = new ArrayList<Rdv>();
	    	
	    	String requete;
	    		
	    	if(filtre == ""){
	    		requete = "SELECT * FROM rendez_vous";
	    	}else {
	    		requete = "SELECT p.*, u.* "
								+ "FROM patient p "
						        + "INNER JOIN utilisateur u ON p.fk_id_utilisateur = u.id_utilisateur "
						        + "ORDER BY u.nom, u.prenom "
						        + "WHERE u.nom LIKE '%"+filtre+"%' OR u.prenom LIKE '%"+filtre+"%'";
	    	}
	
	        try {
	            uneBdd.seConnecter();
	            Statement unStat = uneBdd.getMaConnexion().createStatement();
	            ResultSet desResultats = unStat.executeQuery(requete);
	
	            while (desResultats.next()) {
					Rdv unPatient = new Rdv(desResultats.getInt("id_rdv"), desResultats.getInt("fk_id_patient"), desResultats.getInt("fk_id_creneau"), 
											desResultats.getObject("date_creation", LocalDateTime.class), desResultats.getString("motif"), desResultats.getString("statut"), desResultats.getString("origine"));
					
					//ajout de la promotion dans l'ArrayList
					lesPatients.add(unPatient);
				}
				unStat.close();
				uneBdd.seDeconnecter();
	
	        } catch (SQLException exp) {
	            System.out.println("Erreur d'exécution : " + requete);
	        }
	
	        return lesPatients;
	    }
}

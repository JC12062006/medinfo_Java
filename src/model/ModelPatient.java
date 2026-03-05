package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Patient;

public class ModelPatient extends ModelUtilisateur {
	
    private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");
    
	public static ArrayList<Patient> selectAllPatientsFiltre(String filtre) {
	    	
	    	ArrayList<Patient> lesPatients = new ArrayList<Patient>();
	    	
	    	String requete;
	    		
	    	if(filtre == ""){
	    		requete = "SELECT p.*, u.* "
	                    + "FROM patient p "
	                    + "INNER JOIN utilisateur u ON p.fk_id_utilisateur = u.id_utilisateur "
	                    + "ORDER BY u.nom, u.prenom";
	    	}else {
	    		requete = "SELECT p.*, u.* "
								+ "FROM patient p "
						        + "INNER JOIN utilisateur u ON p.fk_id_utilisateur = u.id_utilisateur "
						        + "WHERE u.nom LIKE '%"+filtre+"%' OR u.prenom LIKE '%"+filtre+"%'";
	    	}
	
	        try {
	            uneBdd.seConnecter();
	            Statement unStat = uneBdd.getMaConnexion().createStatement();
	            ResultSet desResultats = unStat.executeQuery(requete);
	
	            while (desResultats.next()) {
					Patient unPatient = new Patient(desResultats.getString("nom"),
							desResultats.getString("prenom"), desResultats.getInt("id_patient"));
					
					//ajout de la promotion dans l'ArrayList
					lesPatients.add(unPatient);
				}
				unStat.close();
				uneBdd.seDeconnecter();
	
	        } catch (SQLException exp) {
	            //System.out.println("Erreur d'exécution : " + requete);
	            
	            System.out.println("Raison exacte : " + exp.getMessage());
	        }
	
	        return lesPatients;
	    }
}

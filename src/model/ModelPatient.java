package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Patient;

public class ModelPatient extends ModelUtilisateur {

	private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");
    //private static BDD uneBdd = new BDD("192.168.20.110", "clientProd", "password75", "medinfo");

   public static ArrayList<Patient> selectAllPatientsFiltre(String filtre) {
	    	
	    	ArrayList<Patient> lesPatients = new ArrayList<Patient>();
	    	
	    	String requete;
	    		
	    	if(filtre == null || filtre.isEmpty()){
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
							desResultats.getString("prenom"), desResultats.getInt("id_patient"), 
							desResultats.getString("email"), desResultats.getString("telephone"));
					
					//ajout de la promotion dans l'ArrayList
					lesPatients.add(unPatient);
				}
				unStat.close();
				uneBdd.seDeconnecter();
	
	        } catch (SQLException exp) {
	            //System.out.println("Erreur d'exécution : " + requete);
	            
	            System.out.println("Erreur : " + exp.getMessage());
	        }
	
	        return lesPatients;
	    }
  
    public static boolean insertPatient(Patient p) {
	    // 1ère requête : Insérer les données générales dans Utilisateur
	    String requeteUtilisateur = "INSERT INTO utilisateur " +
	            "(nom, prenom, email, hash_password, telephone, role, date_naissance) VALUES (" +
	            "'" + p.getNom() + "', " +
	            "'" + p.getPrenom() + "', " +
	            "'" + p.getEmail() + "', " +
	            "'" + p.getHashPassword() + "', " + 
	            "'" + p.getTelephone() + "', " +
	            "'" + p.getRole() + "', " +
	    		"'" + p.getDateNaissance() + "');";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        
	        // Exécution de l'insertion de l'utilisateur
	        unStat.executeUpdate(requeteUtilisateur, Statement.RETURN_GENERATED_KEYS);
	        
	        // Récupération de l'ID généré par MySQL
	        ResultSet rs = unStat.getGeneratedKeys();
	        int nouvelIdUtilisateur = 0;
	        
	        if (rs.next()) {
	            nouvelIdUtilisateur = rs.getInt(1); 
	            p.setFkIdUtilisateur(nouvelIdUtilisateur); 
	        }
	        
	        // Si l'utilisateur a bien été inséré
	        if(nouvelIdUtilisateur > 0) {
	            // 2ème requête : Insérer les données spécifiques dans Patient
	            String requetePatient = "INSERT INTO patient " +
	                    "(adresse, num_secu, sexe, fk_id_utilisateur) VALUES (" +
	                    "'" + p.getAdresse() + "', " +
	                    "'" + p.getNumSecu() + "', " +
	                    "'" + p.getSexe() + "', " +
	                    nouvelIdUtilisateur + ");";
	            
	            unStat.executeUpdate(requetePatient);
	            
	            unStat.close();
	            uneBdd.seDeconnecter();
	            return true; // ✅ Succès total
	        }

	        unStat.close();
	        uneBdd.seDeconnecter();
	        return false; // ❌ L'ID n'a pas été généré

	    } catch (SQLException exp) {
	        System.out.println("Erreur SQL : " + exp.getMessage());
	        return false; // ❌ Erreur SQL (ex: email ou n° sécu en doublon)
	    }
	}
}

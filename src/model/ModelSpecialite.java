package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Specialite;

public class ModelSpecialite {
	
	// Connexion à la base MedInfo
    private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");
    
    public static ArrayList<Specialite> selectAllSpecialite() {
    	
    	ArrayList<Specialite> lesSpecialites = new ArrayList<Specialite>();

        String requete = "SELECT * FROM specialite";

        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet desResultats = unStat.executeQuery(requete);

            while (desResultats.next()) {
				Specialite uneSpecialite = new Specialite(desResultats.getInt("id_specialite"),
						desResultats.getString("libelle"));
				
				//ajout de la promotion dans l'ArrayList
				lesSpecialites.add(uneSpecialite);
			}
			unStat.close();
			uneBdd.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution : " + requete);
        }

        return lesSpecialites;
    }
}

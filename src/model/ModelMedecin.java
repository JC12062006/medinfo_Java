package model;

import java.sql.SQLException;
import java.sql.Statement;

import controller.Medecin;

public class ModelMedecin {

    private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");

    public static void insertMedecin(Medecin m) {

        String requete = "INSERT INTO medecin " +
                "(rpps, est_conventionne, formations, langues_parlees, experiences, description, fk_id_utilisateur, fk_id_specialite) VALUES (" +
                "'" + m.getRpps() + "', " +
                m.getEstConventionne() + ", " +
                "'" + m.getFormations() + "', " +
                "'" + m.getLanguesParlees() + "', " +
                "'" + m.getExperiences() + "', " +
                "'" + m.getDescription() + "', " +
                m.getFkIdUtilisateur() + ", " +
                m.getFkIdSpecialite() + ");";

        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();

            int lignes = unStat.executeUpdate(requete);

            System.out.println(">>> Lignes insérées dans medecin : " + lignes);

            unStat.close();
            uneBdd.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur SQL : " + exp.getMessage());
            System.out.println("Requête : " + requete);
        }
    }
}

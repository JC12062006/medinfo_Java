package model;

import java.sql.SQLException;
import java.sql.Statement;

import controller.Patient;

public class ModelPatient {

    private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");

    public static void insertPatient(Patient p) {

        String requete = "INSERT INTO patient " +
                "(adresse, num_secu, sexe, fk_id_utilisateur) VALUES (" +
                "'" + p.getAdresse() + "', " +
                "'" + p.getNumSecu() + "', " +
                "'" + p.getSexe() + "', " +
                p.getFkIdUtilisateur() + ");";

        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();

            int lignes = unStat.executeUpdate(requete);
            System.out.println(">>> Lignes insérées dans patient : " + lignes);

            unStat.close();
            uneBdd.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur SQL : " + exp.getMessage());
            System.out.println("Requête : " + requete);
        }
    }
}

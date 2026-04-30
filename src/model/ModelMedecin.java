package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Medecin;

public class ModelMedecin {

    private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");

    // -----------------------------------------
    // INSERTION
    // -----------------------------------------
    public static void insertMedecin(Medecin m) {

        String requete = "INSERT INTO medecin " +
                "(rpps, est_conventionne, formations, langues_parlees, experiences, description, fk_id_utilisateur, fk_id_specialite) VALUES (" +
                "'" + m.getRpps() + "', " +
                "'" + m.getEstConventionne() + "', " +
                "'" + m.getFormations() + "', " +
                "'" + m.getLanguesParlees() + "', " +
                "'" + m.getExperiences() + "', " +
                "'" + m.getDescription() + "', " +
                "'" + m.getFkIdUtilisateur() + "', " +
                "'" + m.getFkIdSpecialite() + "');";

        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            unStat.executeUpdate(requete);
            unStat.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution : " + requete);
        }
    }

    // -----------------------------------------
    // SELECT ALL
    // -----------------------------------------
    public static ArrayList<Medecin> selectAllMedecins() {
        ArrayList<Medecin> lesMedecins = new ArrayList<>();

        // Requête modifiée avec une jointure sur la table specialite
        String requete =
                "SELECT m.*, u.nom, u.prenom, u.email, u.telephone, s.libelle as specialite_nom " +
                "FROM medecin m " +
                "INNER JOIN utilisateur u ON m.fk_id_utilisateur = u.id_utilisateur " +
                "INNER JOIN specialite s ON m.fk_id_specialite = s.id_specialite;";

        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet rs = unStat.executeQuery(requete);

            while (rs.next()) {
                Medecin m = new Medecin(
                        rs.getString("rpps"),
                        rs.getInt("est_conventionne"),
                        rs.getString("formations"),
                        rs.getString("langues_parlees"),
                        rs.getString("experiences"),
                        rs.getString("description"),
                        rs.getInt("fk_id_utilisateur"),
                        rs.getInt("fk_id_specialite")
                );

                m.setNom(rs.getString("nom"));
                m.setPrenom(rs.getString("prenom"));
                m.setEmail(rs.getString("email"));
                m.setTelephone(rs.getString("telephone"));
                
                // ON RÉCUPÈRE LE NOM DE LA SPÉCIALITÉ ICI
                m.setNomSpecialite(rs.getString("specialite_nom"));

                lesMedecins.add(m);
            }
            unStat.close();
            uneBdd.seDeconnecter();
        } catch (SQLException exp) {
            System.out.println("Erreur SQL : " + exp.getMessage());
        }
        return lesMedecins;
    }
}

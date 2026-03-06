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
        requete = "SELECT * FROM creneau";
    } else {
        requete = "SELECT * FROM creneau "
                + "WHERE statut LIKE '%" + filtre + "%' "
                + "OR fk_id_medecin LIKE '%" + filtre + "%' "
                + "OR fk_id_salle LIKE '%" + filtre + "%' "
                + "ORDER BY date_heure_debut";
    }

    try {
        uneBdd.seConnecter();
        Statement unStat = uneBdd.getMaConnexion().createStatement();
        ResultSet desResultats = unStat.executeQuery(requete);

        while (desResultats.next()) {
            Creneau unCreneau = new Creneau(
                desResultats.getInt("id_creneau"),
                desResultats.getTimestamp("date_heure_debut").toLocalDateTime(),
                desResultats.getTimestamp("date_heure_fin").toLocalDateTime(),
                desResultats.getString("statut"),
                desResultats.getBoolean("disponibilite"),
                desResultats.getInt("fk_id_medecin"),
                desResultats.getInt("fk_id_salle")
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
}


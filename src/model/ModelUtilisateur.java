package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import controller.User;

public class ModelUtilisateur {

    private static BDD uneBdd = new BDD("localhost", "root", "", "medinfo");

    public static String sha1(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erreur SHA1 : " + e.getMessage());
            return null;
        }
    }

    public static User selectWhereUser(String email, String mdp) {
        User unUser = null;

        String hash = sha1(mdp);

        String requete = "SELECT * FROM utilisateur WHERE email = '" + email +
                "' AND hash_password = '" + hash + "';";

        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            ResultSet unRes = unStat.executeQuery(requete);

            if (unRes.next()) {
                unUser = new User(
                        unRes.getInt("id_utilisateur"),
                        unRes.getString("nom"),
                        unRes.getString("prenom"),
                        unRes.getString("email"),
                        unRes.getString("telephone"),
                        unRes.getString("hash_password"),
                        unRes.getString("date_naissance"),
                        unRes.getString("role")
                );
            }

            unStat.close();
            uneBdd.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution : " + requete);
        }

        return unUser;
    }

    /**
     * Insertion d'un utilisateur + récupération de son ID
     */
    public static int insertUtilisateur(User u) {
        int id = 0;

        String requete = "INSERT INTO utilisateur " +
                "(nom, prenom, email, hash_password, telephone, role, date_naissance) VALUES (" +
                "'" + u.getNom() + "', " +
                "'" + u.getPrenom() + "', " +
                "'" + u.getEmail() + "', " +
                "'" + u.getHashPassword() + "', " +
                "'" + u.getTelephone() + "', " +
                "'Medecin', " +
                "'" + u.getDateNaissance() + "');";

        try {
            uneBdd.seConnecter();
            Statement unStat = uneBdd.getMaConnexion().createStatement();
            unStat.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = unStat.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            unStat.close();
            uneBdd.seDeconnecter();

        } catch (SQLException exp) {
            System.out.println("Erreur d'exécution : " + requete);
        }

        return id;
    }
}

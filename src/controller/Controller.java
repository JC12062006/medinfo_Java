package controller;

import model.Model;

public class Controller {

    /**
     * Connexion utilisateur
     */
    public static User selectWhereUser(String email, String mdp) {
        return Model.selectWhereUser(email, mdp);
    }

    /**
     * Inscription d'un utilisateur secrétaire
     */
    public static void insertUtilisateur(User unUser) {
        Model.insertUtilisateur(unUser);
    }

    /**
     * Hashage SHA1 (relayé vers Model)
     */
    public static String sha1(String input) {
        return Model.sha1(input);
    }

    /**
     * Insertion d'un utilisateur médecin
     * Retourne l'ID auto-généré
     */
    public static int insertUtilisateurMedecin(User unUser) {
        return Model.insertUtilisateurMedecin(unUser);
    }

    /**
     * Insertion d'un médecin
     */
    public static void insertMedecin(Medecin unMedecin) {
        Model.insertMedecin(unMedecin);
    }
}

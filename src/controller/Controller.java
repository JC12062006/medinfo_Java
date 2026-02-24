package controller;

import model.Model;

public class Controller {

    /**
     * Méthode de connexion utilisateur
     */
    public static User selectWhereUser(String email, String mdp) {
        return Model.selectWhereUser(email, mdp);
    }

    /**
     * Méthode d'inscription d'un utilisateur (secrétaire)
     */
    public static void insertUtilisateur(User unUser) {
        Model.insertUtilisateur(unUser);
    }

    /**
     * Hashage SHA1 (utilisé pour l'inscription)
     */
    public static String sha1(String mdp) {
        return Model.sha1(mdp);
    }
}

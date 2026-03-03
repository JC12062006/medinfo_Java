package controller;

import model.ModelUtilisateur;

public class ControllerUtilisateur {

    /**
     * Méthode de connexion utilisateur
     */
    public static User selectWhereUser(String email, String mdp) {
        return ModelUtilisateur.selectWhereUser(email, mdp);
    }

    /**
     * Méthode d'inscription d'un utilisateur (secrétaire)
     
    public static void insertUtilisateur(User unUser) {
        ModelUtilisateur.insertUtilisateur(unUser);
    }
    
	*/
    
    
    /**
     * Hashage SHA1 (utilisé pour l'inscription)
     
    public static String sha1(String mdp) {
        return ModelUtilisateur.sha1(mdp);
    }
    
    */
}

package controller;

import model.ModelUtilisateur;

public class ControllerUtilisateur {

    public static User selectWhereUser(String email, String mdp) {
        return ModelUtilisateur.selectWhereUser(email, mdp);
    }

    public static int insertUtilisateur(User unUser) {
        return ModelUtilisateur.insertUtilisateur(unUser);
    }
}

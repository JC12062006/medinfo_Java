package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDD {

    // Informations de connexion à la base MedInfo
    private String serveur;
    private String user;
    private String mdp;
    private String bdd;

    private Connection maConnexion;

    public BDD(String serveur, String user, String mdp, String bdd) {
        this.serveur = serveur;
        this.user = user;
        this.mdp = mdp;
        this.bdd = bdd;
        this.maConnexion = null;
    }

    /**
     * Chargement du pilote JDBC MySQL
     */
    public void chargerPilote() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exp) {
            System.out.println("Erreur : Pilote JDBC MySQL introuvable.");
        }
    }

    /**
     * Connexion à la base de données
     */
    public void seConnecter() {
        this.chargerPilote();
        String url = "jdbc:mysql://" + this.serveur + "/" + this.bdd + "?useSSL=false&serverTimezone=UTC";

        try {
            this.maConnexion = DriverManager.getConnection(url, this.user, this.mdp);
        } catch (SQLException exp) {
            System.out.println("Erreur de connexion à : " + url);
        }
    }

    /**
     * Déconnexion propre
     */
    public void seDeconnecter() {
        try {
            if (this.maConnexion != null && !this.maConnexion.isClosed()) {
                this.maConnexion.close();
            }
        } catch (SQLException exp) {
            System.out.println("Erreur lors de la fermeture de la connexion.");
        }
    }

    /**
     * Getter de la connexion active
     */
    public Connection getMaConnexion() {
        return this.maConnexion;
    }
}

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
        // C'est toujours une bonne pratique de forcer le port 3306 dans l'URL par sécurité
        String url = "jdbc:mysql://" + this.serveur + ":3306/" + this.bdd + "?useSSL=false&serverTimezone=UTC";

        try {
            this.maConnexion = DriverManager.getConnection(url, this.user, this.mdp);
            System.out.println("Connexion réussie à la base distante !");
        } catch (SQLException exp) {
            System.out.println("Erreur de connexion à : " + url);
            // AJOUTEZ CECI POUR COMPRENDRE POURQUOI ÇA PLANTE :
            System.out.println("Cause de l'erreur : " + exp.getMessage()); 
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

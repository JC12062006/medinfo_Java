package view;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        
    	// changer l'aspect graphique de toute l'appli
    	FlatLightLaf.setup();
    	
    	// Lancement de la fenêtre de connexion
        new VueConnexion();
    }
}
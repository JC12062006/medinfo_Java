package view;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        
    	FlatLightLaf.setup();
    	
    	// Lancement de la fenêtre de connexion
        new VueConnexion();
    }
}

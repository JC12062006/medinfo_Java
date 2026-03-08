package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelFooter extends JPanel {

    public PanelFooter() {
        // La bonne position tout en bas de la fenêtre
        this.setBounds(0, 460, 900, 40); 
        this.setBackground(Color.decode("#3546B0")); 
        this.setLayout(null);

        // 1. Le texte du copyright (placé à gauche)
        JLabel lbTexte = new JLabel("© 2026 MedInfo - Tous droits réservés");
        lbTexte.setForeground(Color.LIGHT_GRAY);
        lbTexte.setFont(new Font("Arial", Font.ITALIC, 12));
        lbTexte.setBounds(20, 10, 300, 20); // Démarre à x=20 pour ne pas être collé au bord

        // 2. Le texte de contact (placé à droite)
        JLabel lbContact = new JLabel("Un problème avec l'application ? Contactez-nous : lebackyard@gmail.com");
        lbContact.setForeground(Color.LIGHT_GRAY);
        lbContact.setFont(new Font("Arial", Font.ITALIC, 11)); // Police légèrement plus petite
        lbContact.setHorizontalAlignment(SwingConstants.RIGHT); // On aligne le texte à droite
        lbContact.setBounds(400, 10, 480, 20); // Prend la partie droite du footer (jusqu'à x=880)

        // Ajout des deux labels au footer
        this.add(lbTexte);
        this.add(lbContact);
    }
}
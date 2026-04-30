package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.User;
import controller.ControllerStyle;

public class VueProfilSecretaire extends JFrame {

    public VueProfilSecretaire(User unUser) {
        // Configuration de base
        this.setTitle("Mon Profil - MedInfo");
        this.setSize(550, 750);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER ---
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(ControllerStyle.PRIMARY);
        header.setPreferredSize(new Dimension(0, 150));
        header.setBorder(new EmptyBorder(30, 0, 0, 0));

        JLabel lbIcon = new JLabel("👤", SwingConstants.CENTER);
        lbIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lbIcon.setForeground(Color.WHITE);

        JLabel lbTitre = new JLabel(unUser.getPrenom() + " " + unUser.getNom(), SwingConstants.CENTER);
        lbTitre.setForeground(Color.WHITE);
        lbTitre.setFont(new Font("SansSerif", Font.BOLD, 24));

        header.add(lbIcon);
        header.add(lbTitre);
        this.add(header, BorderLayout.NORTH);

        // --- 2. CONTENU ---
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setOpaque(false);
        mainContainer.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- CARTE INFOS PERSONNELLES ---
        JPanel infoCard = creerCard();
        infoCard.add(creerTitreSection("INFORMATIONS PERSONNELLES"));
        infoCard.add(Box.createRigidArea(new Dimension(0, 15)));
        infoCard.add(creerInfoLigne("Email", unUser.getEmail()));
        infoCard.add(creerInfoLigne("Téléphone", unUser.getTelephone()));
        infoCard.add(creerInfoLigne("Date de Naissance", unUser.getDateNaissance()));
        infoCard.add(creerInfoLigne("Rôle", unUser.getRole()));

        mainContainer.add(infoCard);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- CARTE SUPPORT LE BACKYARD ---
        JPanel supportCard = creerCard();
        supportCard.add(creerTitreSection("SUPPORT TECHNIQUE"));
        
        // CORRECTION ICI : Changement du texte et ajout de marge pour l'italique
        JLabel lbBackyard = new JLabel("Développé par Le Backyard "); 
        lbBackyard.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lbBackyard.setForeground(ControllerStyle.PRIMARY);
        // L'EmptyBorder à droite empêche la lettre 'd' de déborder du cadre du Label
        lbBackyard.setBorder(new EmptyBorder(0, 0, 0, 10)); 
        
        supportCard.add(lbBackyard);
        supportCard.add(Box.createRigidArea(new Dimension(0, 15)));

        JTextArea txtSupport = new JTextArea(
            "Besoin d'assistance ? Nos experts sont là :\n\n" +
            "📞 01 88 45 00 00 (Lundi - Vendredi)\n" +
            "📧 support@lebackyard.fr"
        );
        txtSupport.setEditable(false);
        txtSupport.setLineWrap(true);
        txtSupport.setWrapStyleWord(true);
        txtSupport.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtSupport.setForeground(ControllerStyle.TEXT_MAIN);
        txtSupport.setBackground(Color.WHITE);
        supportCard.add(txtSupport);

        mainContainer.add(supportCard);

        JScrollPane scroll = new JScrollPane(mainContainer);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        this.add(scroll, BorderLayout.CENTER);

        // --- 3. FOOTER ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(0, 0, 25, 0));

        JButton btFermer = new JButton("Retour au tableau de bord");
        ControllerStyle.applyPrimaryBtn(btFermer);
        btFermer.setPreferredSize(new Dimension(250, 45));
        btFermer.addActionListener(e -> this.dispose());
        
        footer.add(btFermer);
        this.add(footer, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    // --- HELPER METHODS ---

    private JPanel creerCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(ControllerStyle.BORDER_SOFT, 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        return card;
    }

    private JLabel creerTitreSection(String titre) {
        JLabel lbl = new JLabel(titre);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(ControllerStyle.TEXT_MUTED);
        return lbl;
    }

    private JPanel creerInfoLigne(String label, String valeur) {
        JPanel ligne = new JPanel(new BorderLayout());
        ligne.setOpaque(false);
        ligne.setMaximumSize(new Dimension(500, 35));
        ligne.setBorder(new MatteBorder(0, 0, 1, 0, ControllerStyle.BG_ALT));

        JLabel lbKey = new JLabel(label);
        lbKey.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbKey.setForeground(ControllerStyle.TEXT_MUTED);

        JLabel lbVal = new JLabel(valeur);
        lbVal.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbVal.setForeground(ControllerStyle.TEXT_MAIN);

        ligne.add(lbKey, BorderLayout.WEST);
        ligne.add(lbVal, BorderLayout.EAST);
        return ligne;
    }
}
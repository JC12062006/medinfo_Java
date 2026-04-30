package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import controller.ControllerMedecin;
import controller.ControllerStyle;
import controller.Medecin;
import controller.Tableau;
import controller.User;

public class VueListeMedecins extends JFrame implements ActionListener {

    private User unUser;
    private JButton btRetour = new JButton("⬅ Retour");
    private JTable tableMedecins;
    private Tableau unTableau;

    public VueListeMedecins(User unUser) {
        this.unUser = unUser;

        // Configuration de la fenêtre
        this.setTitle("MedInfo - Annuaire Corps Médical");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran pour la lisibilité
        this.setMinimumSize(new Dimension(900, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER (Navigation & Titre) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(new MatteBorder(0, 0, 1, 0, ControllerStyle.BORDER_SOFT));

        // Bouton retour à gauche
        JPanel pnlRetour = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        pnlRetour.setOpaque(false);
        ControllerStyle.applyPrimaryBtn(btRetour);
        btRetour.setBackground(ControllerStyle.PRIMARY_SOFT);
        btRetour.setForeground(ControllerStyle.PRIMARY);
        pnlRetour.add(btRetour);

        // Titre au centre
        JLabel titre = new JLabel("Annuaire des Médecins", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setForeground(ControllerStyle.TEXT_MAIN);

        header.add(pnlRetour, BorderLayout.WEST);
        header.add(titre, BorderLayout.CENTER);
        // Espace vide à droite pour le centrage optique du titre
        header.add(Box.createRigidArea(new Dimension(170, 0)), BorderLayout.EAST);

        this.add(header, BorderLayout.NORTH);

        // --- 2. CONTENU CENTRAL (Tableau des médecins) ---
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(30, 40, 40, 40));

        // Entêtes du tableau
        String entetes[] = {"Nom", "Prénom", "Email", "Téléphone", "Spécialité"};

        this.unTableau = new Tableau(obtenirDonnees(), entetes);
        this.tableMedecins = new JTable(this.unTableau);
        
        // Application du style global pour les tables
        ControllerStyle.applyTableStyle(tableMedecins);

        JScrollPane scroll = new JScrollPane(tableMedecins);
        scroll.setBorder(new LineBorder(ControllerStyle.BORDER_SOFT, 1));
        scroll.getViewport().setBackground(Color.WHITE);
        
        mainContent.add(scroll, BorderLayout.CENTER);

        // Statistiques en bas de page
        JLabel lbCount = new JLabel("Nombre de praticiens enregistrés : " + unTableau.getRowCount());
        lbCount.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lbCount.setForeground(ControllerStyle.TEXT_MUTED);
        lbCount.setBorder(new EmptyBorder(10, 0, 0, 0));
        mainContent.add(lbCount, BorderLayout.SOUTH);

        this.add(mainContent, BorderLayout.CENTER);

        // Listeners
        this.btRetour.addActionListener(this);

        this.setVisible(true);
    }

    public Object[][] obtenirDonnees() {
        ArrayList<Medecin> lesMedecins = ControllerMedecin.selectAllMedecins();
        Object matrice[][] = new Object[lesMedecins.size()][5];

        int i = 0;
        for (Medecin m : lesMedecins) {
            matrice[i][0] = m.getNom();
            matrice[i][1] = m.getPrenom();
            matrice[i][2] = m.getEmail();
            matrice[i][3] = m.getTelephone();
            
            // REMPLACÉ : m.getFkIdSpecialite() par m.getNomSpecialite()
            matrice[i][4] = m.getNomSpecialite(); 
            
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btRetour) {
            this.dispose();
            new VueGenerale(this.unUser);
        }
    }
}
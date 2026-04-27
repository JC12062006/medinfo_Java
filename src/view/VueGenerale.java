package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.User;

public class VueGenerale extends JFrame implements ActionListener {

    private User unUser;

    // Panels structurels
    private JPanel panelNord = new JPanel();
    private JPanel panelCentral = new JPanel();
    
    // Boutons organisés par groupes
    private JButton btAjouterMedecin = new JButton("Médecin");
    private JButton btAjouterPatient = new JButton("Patient");
    private JButton btListeMedecins = new JButton("Liste Médecins");
    private JButton btListePatients = new JButton("Liste Patients");
    private JButton btGestionRdv = new JButton("Gestion Rdv");
    private JButton btGestionCreneau = new JButton("Créneaux");
    private JButton btDeconnexion = new JButton("Deconnexion");

    private JLabel lbBienvenue = new JLabel();

    public VueGenerale(User unUser) {
        this.unUser = unUser;

        this.setTitle("MedInfo - Espace Secrétaire");
        this.setMinimumSize(new Dimension(900, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centre la fenêtre au démarrage
        
        // Utilisation du BorderLayout pour le responsive
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.decode("#4D61F4"));

        // -------------------------
        // 1. LE MENU (HAUT)
        // -------------------------
        panelNord.setLayout(new BorderLayout());
        panelNord.setBackground(Color.decode("#4D61F4"));
        panelNord.setBorder(new EmptyBorder(15, 15, 15, 15)); // Marges internes

        // Panel pour les boutons de gestion (Grid 2 lignes, 3 colonnes)
        JPanel gridBoutons = new JPanel(new GridLayout(2, 3, 10, 10));
        gridBoutons.setOpaque(false);

        // Ajout des boutons dans un ordre logique
        gridBoutons.add(btAjouterMedecin);
        gridBoutons.add(btAjouterPatient);
        gridBoutons.add(btGestionRdv);
        gridBoutons.add(btListeMedecins);
        gridBoutons.add(btListePatients);
        gridBoutons.add(btGestionCreneau);

        // Style pour le bouton déconnexion à part
        btDeconnexion.setBackground(Color.decode("#E74C3C"));
        btDeconnexion.setForeground(Color.WHITE);
        btDeconnexion.setFont(new Font("Arial", Font.BOLD, 12));

        panelNord.add(gridBoutons, BorderLayout.CENTER);
        panelNord.add(btDeconnexion, BorderLayout.EAST); // Déconnexion isolée à droite

        // -------------------------
        // 2. ZONE D'ACCUEIL (CENTRE)
        // -------------------------
        panelCentral.setLayout(new GridBagLayout()); // Permet un centrage parfait
        panelCentral.setOpaque(false);

        lbBienvenue.setText("Bienvenue, " + unUser.getPrenom() + " " + unUser.getNom());
        lbBienvenue.setFont(new Font("Arial", Font.BOLD, 32));
        lbBienvenue.setForeground(Color.WHITE);

        panelCentral.add(lbBienvenue); // Le GridBagLayout centrera le label par défaut

        // -------------------------
        // ASSEMBLAGE FINAL
        // -------------------------
        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);
        this.add(new PanelFooter(), BorderLayout.SOUTH);

        // Listeners
        this.ajouterEcouteurs();

        this.setVisible(true);
    }

    private void ajouterEcouteurs() {
        JButton[] boutons = {btDeconnexion, btAjouterMedecin, btAjouterPatient, 
                             btGestionRdv, btGestionCreneau, btListeMedecins, btListePatients};
        for (JButton btn : boutons) {
            btn.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // On gère la fermeture de la vue actuelle si nécessaire
        if (source == btDeconnexion || source == btGestionRdv || 
            source == btGestionCreneau || source == btListeMedecins || source == btListePatients) {
            this.dispose();
        }

        if (source == btAjouterMedecin) new VueAjouterMedecin();
        else if (source == btAjouterPatient) new VueAjouterPatient();
        else if (source == btDeconnexion) new VueConnexion();
        else if (source == btGestionRdv) new VueGestionRdv(unUser);
        else if (source == btGestionCreneau) new VueGestionCreneau(unUser);
        else if (source == btListeMedecins) new VueListeMedecins(unUser);
        else if (source == btListePatients) new VueListePatients(unUser);
    }
}
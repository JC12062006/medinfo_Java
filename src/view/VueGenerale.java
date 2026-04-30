package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import controller.User;
import controller.ControllerStyle;

public class VueGenerale extends JFrame implements ActionListener {

    private User unUser;

    // Boutons de la Sidebar
    private JButton btMonProfil = new JButton("Mon Profil"); // Ajouté
    private JButton btGestionRdv = new JButton("Gestion des RDV");
    private JButton btGestionCreneau = new JButton("Planning Créneaux");
    private JButton btListePatients = new JButton("Liste Patients");
    private JButton btListeMedecins = new JButton("Liste Médecins");
    private JButton btAjouterPatient = new JButton("➕  Nouveau Patient");
    private JButton btAjouterMedecin = new JButton("➕  Nouveau Médecin");
    private JButton btDeconnexion = new JButton("Déconnexion");

    public VueGenerale(User unUser) {
        this.unUser = unUser;

        this.setTitle("MedInfo - Tableau de bord");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // --- 1. SIDEBAR ---
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, ControllerStyle.BORDER_SOFT));
        sidebar.setLayout(new BorderLayout());

        // Header Sidebar
        JPanel sideHeader = new JPanel(new GridLayout(2, 1));
        sideHeader.setOpaque(false);
        sideHeader.setBorder(new EmptyBorder(30, 25, 30, 20));
        
        JLabel lbLogo = new JLabel("MedInfo");
        lbLogo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lbLogo.setForeground(ControllerStyle.PRIMARY);
        
        JLabel lbRole = new JLabel("Session : " + unUser.getRole());
        lbRole.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbRole.setForeground(ControllerStyle.TEXT_MUTED);
        
        sideHeader.add(lbLogo);
        sideHeader.add(lbRole);
        sidebar.add(sideHeader, BorderLayout.NORTH);

        // Menu de navigation
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setOpaque(false);
        sideMenu.setBorder(new EmptyBorder(0, 15, 0, 15));

        // Liste des boutons à afficher
        JButton[] tousLesBoutons = {
            btMonProfil, // Profil en premier pour l'accessibilité
            btListePatients, 
            btListeMedecins, 
            btAjouterPatient, 
            btAjouterMedecin, 
            btGestionRdv, 
            btGestionCreneau
        };

        for (JButton btn : tousLesBoutons) {
            configurerBouton(btn);
            sideMenu.add(btn);
            sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        sidebar.add(sideMenu, BorderLayout.CENTER);

        // Footer Sidebar
        JPanel sideFooter = new JPanel(new BorderLayout());
        sideFooter.setOpaque(false);
        sideFooter.setBorder(new EmptyBorder(20, 15, 30, 15));
        
        btDeconnexion.setFont(new Font("SansSerif", Font.BOLD, 13));
        btDeconnexion.setForeground(new Color(220, 53, 69));
        btDeconnexion.setBackground(new Color(255, 245, 245));
        btDeconnexion.setBorder(new MatteBorder(1, 1, 1, 1, new Color(220, 53, 69)));
        sideFooter.add(btDeconnexion, BorderLayout.SOUTH);
        
        sidebar.add(sideFooter, BorderLayout.SOUTH);

        // --- 2. MAIN PANEL ---
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(ControllerStyle.BG_ALT);

        JPanel welcomeBox = new JPanel();
        welcomeBox.setLayout(new BoxLayout(welcomeBox, BoxLayout.Y_AXIS));
        welcomeBox.setOpaque(false);

        JLabel lbHello = new JLabel("Bonjour, " + unUser.getPrenom() + " !");
        lbHello.setFont(new Font("SansSerif", Font.BOLD, 48));
        lbHello.setForeground(ControllerStyle.TEXT_MAIN);
        lbHello.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lbBackyard = new JLabel("Application gérée par Le Backyard");
        lbBackyard.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lbBackyard.setForeground(ControllerStyle.TEXT_MUTED);
        lbBackyard.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeBox.add(lbHello);
        welcomeBox.add(Box.createRigidArea(new Dimension(0, 10)));
        welcomeBox.add(lbBackyard);
        
        mainPanel.add(welcomeBox);

        this.add(sidebar, BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        this.ajouterEcouteurs();
        this.setVisible(true);
    }

    private void configurerBouton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBackground(Color.WHITE);
        btn.setForeground(ControllerStyle.TEXT_MAIN);
        btn.setBorder(new EmptyBorder(12, 15, 12, 15));
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(270, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void ajouterEcouteurs() {
        JButton[] boutons = {btDeconnexion, btAjouterMedecin, btAjouterPatient, 
                             btGestionRdv, btGestionCreneau, btListeMedecins, 
                             btListePatients, btMonProfil};
        for (JButton btn : boutons) {
            btn.addActionListener(this);
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (btn != btDeconnexion) {
                        btn.setBackground(ControllerStyle.PRIMARY_SOFT);
                        btn.setForeground(ControllerStyle.PRIMARY);
                    }
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (btn != btDeconnexion) {
                        btn.setBackground(Color.WHITE);
                        btn.setForeground(ControllerStyle.TEXT_MAIN);
                    }
                }
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btDeconnexion) {
            this.dispose();
            new VueConnexion();
        } else if (source == btMonProfil) {
            // Pas de dispose() ici pour pouvoir revenir au menu facilement
            new VueProfilSecretaire(unUser);
        } else {
            if (source == btListePatients) { this.dispose(); new VueListePatients(unUser); }
            else if (source == btListeMedecins) { this.dispose(); new VueListeMedecins(unUser); }
            else if (source == btAjouterPatient) new VueAjouterPatient();
            else if (source == btAjouterMedecin) new VueAjouterMedecin();
            else if (source == btGestionCreneau) { this.dispose(); new VueGestionCreneau(unUser); }
            else if (source == btGestionRdv) { this.dispose(); new VueGestionRdv(unUser); }
        }
    }
}
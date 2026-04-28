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
    private JButton btGestionRdv = new JButton("📅  Gestion des RDV");
    private JButton btGestionCreneau = new JButton("⏰  Planning Créneaux");
    private JButton btListePatients = new JButton("👥  Liste Patients");
    private JButton btListeMedecins = new JButton("👨‍⚕️  Liste Médecins");
    private JButton btAjouterPatient = new JButton("➕  Nouveau Patient");
    private JButton btAjouterMedecin = new JButton("➕  Nouveau Médecin");
    private JButton btDeconnexion = new JButton("🚪  Déconnexion");

    public VueGenerale(User unUser) {
        this.unUser = unUser;

        this.setTitle("MedInfo - Tableau de bord");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // --- 1. SIDEBAR (Navigation à gauche) ---
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, ControllerStyle.BORDER_SOFT));
        sidebar.setLayout(new BorderLayout());

        // Header de la Sidebar (Logo / Titre)
        JPanel sideHeader = new JPanel(new GridLayout(2, 1));
        sideHeader.setOpaque(false);
        sideHeader.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        JLabel lbLogo = new JLabel("MedInfo");
        lbLogo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lbLogo.setForeground(ControllerStyle.PRIMARY);
        
        JLabel lbRole = new JLabel("Espace Secrétariat");
        lbRole.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbRole.setForeground(ControllerStyle.TEXT_MUTED);
        
        sideHeader.add(lbLogo);
        sideHeader.add(lbRole);
        sidebar.add(sideHeader, BorderLayout.NORTH);

        // Menu de navigation
        JPanel sideMenu = new JPanel(new GridLayout(8, 1, 0, 10)); // 8 lignes pour espacer
        sideMenu.setOpaque(false);
        sideMenu.setBorder(new EmptyBorder(0, 15, 0, 15));

        // On applique le style à tous les boutons
        JButton[] menuButtons = {btGestionRdv, btGestionCreneau, btListePatients, 
                                 btListeMedecins, btAjouterPatient, btAjouterMedecin};
        
        for (JButton btn : menuButtons) {
            ControllerStyle.applyPrimaryBtn(btn);
            // On surcharge un peu pour que ce soit moins "massif" que les boutons de validation
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBackground(Color.WHITE);
            btn.setForeground(ControllerStyle.TEXT_MAIN);
            btn.setBorder(new EmptyBorder(10, 15, 10, 15));
            sideMenu.add(btn);
        }

        sidebar.add(sideMenu, BorderLayout.CENTER);

        // Pied de Sidebar (Déconnexion)
        JPanel sideFooter = new JPanel(new BorderLayout());
        sideFooter.setOpaque(false);
        sideFooter.setBorder(new EmptyBorder(20, 15, 30, 15));
        
        btDeconnexion.setBackground(ControllerStyle.PRIMARY_SOFT);
        btDeconnexion.setForeground(ControllerStyle.DANGER);
        btDeconnexion.setFont(new Font("SansSerif", Font.BOLD, 13));
        btDeconnexion.setBorder(new MatteBorder(1, 1, 1, 1, ControllerStyle.DANGER));
        sideFooter.add(btDeconnexion, BorderLayout.SOUTH);
        
        sidebar.add(sideFooter, BorderLayout.SOUTH);

        // --- 2. MAIN PANEL (Contenu à droite) ---
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(ControllerStyle.BG_ALT);

        // Container pour le message de bienvenue
        JPanel welcomeBox = new JPanel();
        welcomeBox.setLayout(new BoxLayout(welcomeBox, BoxLayout.Y_AXIS));
        welcomeBox.setOpaque(false);

        JLabel lbHello = new JLabel("Bonjour, " + unUser.getPrenom() + " !");
        lbHello.setFont(new Font("SansSerif", Font.BOLD, 48));
        lbHello.setForeground(ControllerStyle.TEXT_MAIN);
        lbHello.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbSub = new JLabel("Que souhaitez-vous faire aujourd'hui ?");
        lbSub.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lbSub.setForeground(ControllerStyle.TEXT_MUTED);
        lbSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbSub.setBorder(new EmptyBorder(10, 0, 0, 0));

        welcomeBox.add(lbHello);
        welcomeBox.add(lbSub);

        mainPanel.add(welcomeBox); // Centrage automatique via GridBagLayout

        // Assemblage final
        this.add(sidebar, BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);

        // Ecouteurs
        this.ajouterEcouteurs();

        this.setVisible(true);
    }

    private void ajouterEcouteurs() {
        JButton[] boutons = {btDeconnexion, btAjouterMedecin, btAjouterPatient, 
                             btGestionRdv, btGestionCreneau, btListeMedecins, btListePatients};
        for (JButton btn : boutons) {
            btn.addActionListener(this);
            // Petit effet de survol simple
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (btn != btDeconnexion) btn.setBackground(ControllerStyle.PRIMARY_SOFT);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (btn != btDeconnexion) btn.setBackground(Color.WHITE);
                }
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // On ferme la vue actuelle pour les changements de page majeurs
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
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.User;

public class VueGenerale extends JFrame implements ActionListener {

    private User unUser;

    private JPanel panelMenu = new JPanel();
    private JButton btDeconnexion = new JButton("Déconnexion");

    private JButton btAjouterMedecin = new JButton("Ajouter un médecin");
    private JButton btAjouterPatient = new JButton("Ajouter un patient");
    private JButton btGestionRdv = new JButton("Gestion Rdv");
    private JButton btGestionCreneau = new JButton("Gestion Créneaux");

    // Nouveaux boutons
    private JButton btListeMedecins = new JButton("Liste Médecins");
    private JButton btListePatients = new JButton("Liste Patients");

    private JPanel panelAccueil = new JPanel();
    private JLabel lbBienvenue = new JLabel();

    public VueGenerale(User unUser) {

        this.unUser = unUser;

        this.setTitle("MedInfo - Espace Secrétaire");
     // On définit la taille exacte de l'intérieur de la fenêtre
        this.getContentPane().setPreferredSize(new java.awt.Dimension(900, 500));
        // On demande à la fenêtre de s'adapter à cette taille intérieure
        this.pack();
        // On place la fenêtre à l'écran (remplace le x=300, y=100)
        this.setLocation(300, 100);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.decode("#4D61F4"));

        // -------------------------
        // MENU HAUT
        // -------------------------
        // Hauteur passée de 40 à 80 pour laisser la place à 2 lignes
        this.panelMenu.setBounds(50, 10, 800, 80); 
        this.panelMenu.setBackground(Color.decode("#4D61F4"));
        
        // Passage sur 2 lignes et 4 colonnes (les boutons respirent !)
        this.panelMenu.setLayout(new GridLayout(2, 4, 10, 10));

        this.panelMenu.add(this.btAjouterMedecin);
        this.panelMenu.add(this.btGestionRdv);
        this.panelMenu.add(this.btGestionCreneau);
        this.panelMenu.add(this.btAjouterPatient);
        this.panelMenu.add(this.btListeMedecins);
        this.panelMenu.add(this.btListePatients);
        this.panelMenu.add(this.btDeconnexion);

        this.add(this.panelMenu);

        // Listeners
        this.btDeconnexion.addActionListener(this);
        this.btAjouterMedecin.addActionListener(this);
        this.btAjouterPatient.addActionListener(this);
        this.btGestionRdv.addActionListener(this);
        this.btGestionCreneau.addActionListener(this);
        this.btListeMedecins.addActionListener(this);
        this.btListePatients.addActionListener(this);

        // -------------------------
        // PANEL ACCUEIL
        // -------------------------
        // Descendu un peu (Y passe de 80 à 110) pour compenser la taille du menu
        this.panelAccueil.setBounds(50, 110, 800, 320); 
        this.panelAccueil.setBackground(Color.decode("#4D61F4"));
        this.panelAccueil.setLayout(null);

        this.lbBienvenue.setText(
                "Bienvenue " + unUser.getPrenom() + " " + unUser.getNom()
        );
        
        // Embellissement du message
        this.lbBienvenue.setFont(new Font("Arial", Font.BOLD, 24)); // Police plus grande et en gras
        this.lbBienvenue.setForeground(Color.WHITE); // Texte en blanc
        this.lbBienvenue.setHorizontalAlignment(SwingConstants.CENTER); // Centrage parfait
        this.lbBienvenue.setBounds(0, 100, 800, 40); // Prend toute la largeur du panel pour centrer correctement

        this.panelAccueil.add(this.lbBienvenue);
        this.add(this.panelAccueil);
        
        //ajout du footer.
        this.add(new PanelFooter());

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.btAjouterMedecin) {
            new VueAjouterMedecin();
        }

        if (e.getSource() == this.btAjouterPatient) {
            new VueAjouterPatient();
        }

        if (e.getSource() == this.btDeconnexion) {
            this.dispose();
            new VueConnexion();
        }

        if (e.getSource() == this.btGestionRdv) {
            this.dispose();
            new VueGestionRdv(this.unUser);
        }

        if (e.getSource() == this.btGestionCreneau) {
            this.dispose();
            new VueGestionCreneau(this.unUser);
        }

        if (e.getSource() == this.btListeMedecins) {
        	this.dispose();
            new VueListeMedecins(this.unUser);
        }

        if (e.getSource() == this.btListePatients) {
        	this.dispose();
            new VueListePatients(this.unUser);
        }
    }
}

package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import controller.ControllerPatient;
import controller.ControllerStyle;
import controller.Patient;
import controller.Tableau;
import controller.User;

public class VueListePatients extends JFrame implements ActionListener {

    private User unUser;
    private JButton btRetour = new JButton("⬅ Retour");
    private JTable tablePatients;
    private Tableau unTableau;

    public VueListePatients(User unUser) {
        this.unUser = unUser;

        // Configuration de la fenêtre
        this.setTitle("MedInfo - Annuaire Patients");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran pour le confort
        this.setMinimumSize(new Dimension(900, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER (Navigation) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(new MatteBorder(0, 0, 1, 0, ControllerStyle.BORDER_SOFT));

        // Partie gauche : Bouton retour
        JPanel pnlRetour = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        pnlRetour.setOpaque(false);
        ControllerStyle.applyPrimaryBtn(btRetour);
        btRetour.setBackground(ControllerStyle.PRIMARY_SOFT);
        btRetour.setForeground(ControllerStyle.PRIMARY);
        pnlRetour.add(btRetour);

        // Partie centrale : Titre
        JLabel titre = new JLabel("Annuaire des Patients", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setForeground(ControllerStyle.TEXT_MAIN);

        header.add(pnlRetour, BorderLayout.WEST);
        header.add(titre, BorderLayout.CENTER);
        // Label vide à droite pour équilibrer le titre au centre
        header.add(Box.createRigidArea(new Dimension(170, 0)), BorderLayout.EAST);

        this.add(header, BorderLayout.NORTH);

        // --- 2. CONTENU CENTRAL (Tableau) ---
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(30, 40, 40, 40));

        // Initialisation du tableau
        String entetes[] = {"Nom", "Prénom", "Email", "Téléphone"};
        this.unTableau = new Tableau(obtenirDonnees(), entetes);
        this.tablePatients = new JTable(this.unTableau);
        
        // Application du style "CSS" Java
        ControllerStyle.applyTableStyle(tablePatients);

        JScrollPane scroll = new JScrollPane(tablePatients);
        scroll.setBorder(new LineBorder(ControllerStyle.BORDER_SOFT, 1));
        scroll.getViewport().setBackground(Color.WHITE);
        
        mainContent.add(scroll, BorderLayout.CENTER);

        // Petit compteur en bas
        JLabel lbCount = new JLabel("Total : " + unTableau.getRowCount() + " patients enregistrés");
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
        ArrayList<Patient> lesPatients = ControllerPatient.selectAllPatientsFiltre("");
        Object matrice[][] = new Object[lesPatients.size()][4];

        int i = 0;
        for (Patient p : lesPatients) {
            matrice[i][0] = p.getNom();
            matrice[i][1] = p.getPrenom();
            matrice[i][2] = p.getEmail();
            matrice[i][3] = p.getTelephone();
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
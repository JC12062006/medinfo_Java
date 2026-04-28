package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import controller.ControllerCreneau;
import controller.ControllerStyle;
import controller.Creneau;
import controller.Tableau;
import controller.User;

public class VueGestionCreneau extends JFrame implements ActionListener, KeyListener {

    private User unUser;
    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Rechercher");
    private JButton btActualiser = new JButton("Actualiser");
    private JButton btRetour = new JButton("⬅ Retour");
    private JButton btGenerer = new JButton("⚡ Générer Planning");

    private JTable tableCreneaux;
    private Tableau unTableau;
    private JLabel lbNbCreneaux = new JLabel();

    public VueGestionCreneau(User unUser) {
        this.unUser = unUser;

        this.setTitle("MedInfo - Gestion des Créneaux");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Plein écran pour voir tout le planning
        this.setMinimumSize(new Dimension(1000, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER (Titre & Retour) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(new MatteBorder(0, 0, 1, 0, ControllerStyle.BORDER_SOFT));

        JLabel titre = new JLabel("  Planning & Créneaux");
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setForeground(ControllerStyle.PRIMARY);
        
        JPanel pnlRetour = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        pnlRetour.setOpaque(false);
        pnlRetour.add(btRetour);
        ControllerStyle.applyPrimaryBtn(btRetour);
        btRetour.setBackground(ControllerStyle.PRIMARY_SOFT);
        btRetour.setForeground(ControllerStyle.PRIMARY);

        header.add(titre, BorderLayout.CENTER);
        header.add(pnlRetour, BorderLayout.WEST);
        this.add(header, BorderLayout.NORTH);

        // --- 2. BARRE D'OUTILS (Actions & Filtre) ---
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(20, 30, 10, 30));

        // Zone de recherche (Gauche)
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearch.setOpaque(false);
        txtFiltre.setPreferredSize(new Dimension(250, 40));
        ControllerStyle.applyTextField(txtFiltre);
        ControllerStyle.applyPrimaryBtn(btFiltrer);
        
        pnlSearch.add(new JLabel("Filtrer par médecin ou salle :"));
        pnlSearch.add(txtFiltre);
        pnlSearch.add(btFiltrer);

        // Zone Actions (Droite)
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlActions.setOpaque(false);
        ControllerStyle.applyPrimaryBtn(btActualiser);
        ControllerStyle.applySecondaryBtn(btGenerer); // Couleur verte pour l'action importante
        
        pnlActions.add(btActualiser);
        pnlActions.add(btGenerer);

        toolbar.add(pnlSearch, BorderLayout.WEST);
        toolbar.add(pnlActions, BorderLayout.EAST);
        
        // --- 3. CONTENU CENTRAL (Tableau) ---
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(10, 30, 20, 30));

        String entetes[] = {"ID", "Jour", "Début", "Fin", "Statut", "Dispo", "Médecin", "Salle"};
        this.unTableau = new Tableau(obtenirDonnees(""), entetes);
        this.tableCreneaux = new JTable(this.unTableau);
        ControllerStyle.applyTableStyle(tableCreneaux);

        JScrollPane scroll = new JScrollPane(tableCreneaux);
        scroll.setBorder(new LineBorder(ControllerStyle.BORDER_SOFT, 1));
        scroll.getViewport().setBackground(Color.WHITE);
        
        mainContent.add(scroll, BorderLayout.CENTER);

        // Footer (Stats)
        lbNbCreneaux.setText("Nombre de créneaux trouvés : " + unTableau.getRowCount());
        lbNbCreneaux.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lbNbCreneaux.setForeground(ControllerStyle.TEXT_MUTED);
        mainContent.add(lbNbCreneaux, BorderLayout.SOUTH);

        this.add(toolbar, BorderLayout.CENTER); // On utilise un panel intermédiaire pour layout toolbar+table
        
        // Ajustement pour empiler Toolbar et Table
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(toolbar, BorderLayout.NORTH);
        centerWrapper.add(mainContent, BorderLayout.CENTER);
        this.add(centerWrapper, BorderLayout.CENTER);

        // Listeners
        this.btFiltrer.addActionListener(this);
        this.btActualiser.addActionListener(this);
        this.btRetour.addActionListener(this);
        this.btGenerer.addActionListener(this);
        this.txtFiltre.addKeyListener(this);

        this.setVisible(true);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Creneau> lesCreneaux = ControllerCreneau.selectAllCreneaux(filtre);
        Object matrice[][] = new Object[lesCreneaux.size()][8];

        int i = 0;
        for (Creneau unC : lesCreneaux) {
            matrice[i][0] = unC.getId_creneau();
            matrice[i][1] = unC.getDate_jour();
            matrice[i][2] = unC.getHeure_debut();
            matrice[i][3] = unC.getHeure_fin();
            matrice[i][4] = unC.getStatut();
            matrice[i][5] = unC.isDisponibilite() ? "Oui" : "Non"; // Plus lisible que true/false
            matrice[i][6] = unC.getNom_medecin() + " " + unC.getPrenom_medecin();
            matrice[i][7] = unC.getSalle();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btFiltrer) {
            filtrer();
        } else if (e.getSource() == this.btActualiser) {
            this.txtFiltre.setText("");
            filtrer();
        } else if (e.getSource() == this.btRetour) {
            this.dispose();
            new VueGenerale(this.unUser);
        } else if (e.getSource() == this.btGenerer) {
            this.dispose();
            new VueGenerationCreneau(this.unUser);
        }
    }

    private void filtrer() {
        String filtre = this.txtFiltre.getText();
        this.unTableau.setDonnees(this.obtenirDonnees(filtre));
        this.lbNbCreneaux.setText("Nombre de créneaux trouvés : " + unTableau.getRowCount());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) filtrer();
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
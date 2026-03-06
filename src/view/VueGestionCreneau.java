package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.ControllerCreneau;
import controller.Creneau;
import controller.Tableau;
import controller.User;

public class VueGestionCreneau extends JFrame implements ActionListener, KeyListener {

    private User unUser;

    private JTextField txtFiltre = new JTextField();
    private JButton btFiltrer = new JButton("Filtrer");
    private JButton btActualiser = new JButton("Actualiser");
    private JButton btRetour = new JButton("Retour à l'accueil");

    private JTable tableCreneaux;
    private JScrollPane scroll;
    private Tableau unTableau;

    private JLabel lbNbCreneaux = new JLabel();

    public VueGestionCreneau(User unUser) {

        this.unUser = unUser;

        this.setTitle("Gestion des Créneaux");
        this.setBounds(200, 100, 1000, 500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.decode("#4D61F4"));

        // Titre
        JLabel titre = new JLabel("Gestion des Créneaux");
        titre.setBounds(350, 20, 400, 40);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(Color.white);
        this.add(titre);

        // Bouton retour
        this.btRetour.setBounds(50, 20, 150, 30);
        this.add(btRetour);

        // Zone filtre
        this.txtFiltre.setBounds(50, 100, 200, 30);
        this.add(txtFiltre);

        this.btFiltrer.setBounds(270, 100, 120, 30);
        this.add(btFiltrer);

        this.btActualiser.setBounds(410, 100, 120, 30);
        this.add(btActualiser);

        // Tableau
        String entetes[] = {"ID", "Début", "Fin", "Statut", "Dispo", "Médecin", "Salle"};

        this.unTableau = new Tableau(obtenirDonnees(""), entetes);
        this.tableCreneaux = new JTable(this.unTableau);

        this.scroll = new JScrollPane(this.tableCreneaux);
        this.scroll.setBounds(50, 160, 900, 260);
        this.add(this.scroll);

        // Compteur
        this.lbNbCreneaux.setBounds(50, 430, 400, 20);
        this.lbNbCreneaux.setForeground(Color.white);
        this.lbNbCreneaux.setText("Nombre de créneaux : " + unTableau.getRowCount());
        this.add(this.lbNbCreneaux);

        // Listeners
        this.btFiltrer.addActionListener(this);
        this.btActualiser.addActionListener(this);
        this.btRetour.addActionListener(this);
        this.txtFiltre.addKeyListener(this);

        this.setVisible(true);
    }

    public Object[][] obtenirDonnees(String filtre) {
        ArrayList<Creneau> lesCreneaux = ControllerCreneau.selectAllCreneaux(filtre);
        Object matrice[][] = new Object[lesCreneaux.size()][7];

        int i = 0;
        for (Creneau unC : lesCreneaux) {
            matrice[i][0] = unC.getId_creneau();
            matrice[i][1] = unC.getDate_heure_debut();
            matrice[i][2] = unC.getDate_heure_fin();
            matrice[i][3] = unC.getStatut();
            matrice[i][4] = unC.isDisponibilite();
            matrice[i][5] = unC.getFk_id_medecin();
            matrice[i][6] = unC.getFk_id_salle();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.btFiltrer) {
            String filtre = this.txtFiltre.getText();
            this.unTableau.setDonnees(this.obtenirDonnees(filtre));
            this.lbNbCreneaux.setText("Nombre de créneaux : " + unTableau.getRowCount());
        }
        else if (e.getSource() == this.btActualiser) {
            this.txtFiltre.setText("");
            this.unTableau.setDonnees(this.obtenirDonnees(""));
            this.lbNbCreneaux.setText("Nombre de créneaux : " + unTableau.getRowCount());
        }
        else if (e.getSource() == this.btRetour) {
            this.dispose();
            new VueGenerale(this.unUser);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String filtre = this.txtFiltre.getText();
            this.unTableau.setDonnees(this.obtenirDonnees(filtre));
            this.lbNbCreneaux.setText("Nombre de créneaux : " + unTableau.getRowCount());
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}

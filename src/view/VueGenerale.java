package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.User;

public class VueGenerale extends JFrame implements ActionListener {

    private User unUser;

    private JPanel panelMenu = new JPanel();
    private JButton btDeconnexion = new JButton("Déconnexion");

    private JPanel panelAccueil = new JPanel();
    private JLabel lbBienvenue = new JLabel();
    private JButton btAjouterMedecin = new JButton("Ajouter un médecin");

    public VueGenerale(User unUser) {

        this.unUser = unUser;

        this.setTitle("MedInfo - Espace Secrétaire");
        this.setBounds(300, 100, 900, 500);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.getContentPane().setBackground(Color.cyan);

        // -------------------------
        // MENU HAUT
        // -------------------------
        this.panelMenu.setBounds(200, 10, 500, 40);
        this.panelMenu.setBackground(Color.cyan);
        this.panelMenu.setLayout(new GridLayout(1, 2, 10, 10));
        this.panelMenu.add(this.btAjouterMedecin);

        this.panelMenu.add(this.btDeconnexion);
        this.add(this.panelMenu);

        this.btDeconnexion.addActionListener(this);
        this.btAjouterMedecin.addActionListener(this);

        // -------------------------
        // PANEL ACCUEIL
        // -------------------------
        this.panelAccueil.setBounds(50, 80, 800, 350);
        this.panelAccueil.setBackground(Color.cyan);
        this.panelAccueil.setLayout(null);

        this.lbBienvenue.setText(
                "Bienvenue " + unUser.getPrenom() + " " + unUser.getNom() + " (Secrétaire)"
        );
        this.lbBienvenue.setBounds(200, 50, 500, 30);

        this.panelAccueil.add(this.lbBienvenue);
        this.add(this.panelAccueil);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.btAjouterMedecin) {
            new VueAjouterMedecin();
        }

        if (e.getSource() == this.btDeconnexion) {
            this.dispose();
            new VueConnexion();
        }
    }
}

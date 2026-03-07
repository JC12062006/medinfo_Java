package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.ControllerMedecin;
import controller.Medecin;
import controller.Tableau;
import controller.User;

public class VueListeMedecins extends JFrame implements ActionListener {

    private User unUser;
    private JButton btRetour = new JButton("Retour");
    private JTable tableMedecins;
    private Tableau unTableau;

    public VueListeMedecins(User unUser) {

        this.unUser = unUser;

        this.setTitle("Liste des Médecins");
        this.setBounds(200, 100, 900, 500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.decode("#4D61F4"));

        JLabel titre = new JLabel("Liste des Médecins");
        titre.setBounds(300, 20, 400, 40);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(Color.white);
        this.add(titre);

        this.btRetour.setBounds(50, 20, 150, 30);
        this.add(btRetour);
        this.btRetour.addActionListener(this);

        String entetes[] = {"Nom", "Prénom", "Email", "Téléphone", "Spécialité"};

        this.unTableau = new Tableau(obtenirDonnees(), entetes);
        this.tableMedecins = new JTable(this.unTableau);

        JScrollPane scroll = new JScrollPane(this.tableMedecins);
        scroll.setBounds(50, 100, 800, 300);
        this.add(scroll);

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
            matrice[i][4] = m.getFkIdSpecialite();
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

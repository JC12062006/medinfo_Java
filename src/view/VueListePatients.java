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

import controller.ControllerPatient;
import controller.Patient;
import controller.Tableau;
import controller.User;

public class VueListePatients extends JFrame implements ActionListener {

    private User unUser;
    private JButton btRetour = new JButton("Retour");
    private JTable tablePatients;
    private Tableau unTableau;

    public VueListePatients(User unUser) {

        this.unUser = unUser;

        this.setTitle("Liste des Patients");
        this.setBounds(200, 100, 900, 500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.decode("#4D61F4"));

        JLabel titre = new JLabel("Liste des Patients");
        titre.setBounds(300, 20, 400, 40);
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(Color.white);
        this.add(titre);

        this.btRetour.setBounds(50, 20, 150, 30);
        this.add(btRetour);
        this.btRetour.addActionListener(this);

        String entetes[] = {"Nom", "Prénom", "Email", "Téléphone"};

        this.unTableau = new Tableau(obtenirDonnees(), entetes);
        this.tablePatients = new JTable(this.unTableau);

        JScrollPane scroll = new JScrollPane(this.tablePatients);
        scroll.setBounds(50, 100, 800, 300);
        this.add(scroll);

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

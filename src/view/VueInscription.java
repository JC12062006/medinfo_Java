package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import controller.Controller;
import controller.User;

public class VueInscription extends JFrame implements ActionListener {

    private JPanel panelForm = new JPanel();

    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtDateNaissance = new JTextField(); // format YYYY-MM-DD
    private JPasswordField txtMdp = new JPasswordField();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Créer le compte");
    private JButton btRetour = new JButton("Retour");

    public VueInscription() {

        this.setTitle("MedInfo - Inscription Secrétaire");
        this.setBounds(600, 150, 600, 420);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.getContentPane().setBackground(Color.cyan);

        // Formulaire
        this.panelForm.setBounds(100, 40, 400, 300);
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setLayout(new GridLayout(7, 2, 10, 10));

        this.panelForm.add(new JLabel("Nom :"));
        this.panelForm.add(this.txtNom);

        this.panelForm.add(new JLabel("Prénom :"));
        this.panelForm.add(this.txtPrenom);

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Téléphone :"));
        this.panelForm.add(this.txtTelephone);

        this.panelForm.add(new JLabel("Date de naissance (YYYY-MM-DD) :"));
        this.panelForm.add(this.txtDateNaissance);

        this.panelForm.add(new JLabel("Mot de passe :"));
        this.panelForm.add(this.txtMdp);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.add(this.panelForm);

        // Bouton retour
        this.btRetour.setBounds(240, 350, 120, 25);
        this.add(this.btRetour);

        // Listeners
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btRetour.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.btAnnuler) {
            viderChamps();
        }
        else if (e.getSource() == this.btRetour) {
            this.dispose();
            new VueConnexion();
        }
        else if (e.getSource() == this.btValider) {
            traitementInscription();
        }
    }

    public void viderChamps() {
        this.txtNom.setText("");
        this.txtPrenom.setText("");
        this.txtEmail.setText("");
        this.txtTelephone.setText("");
        this.txtDateNaissance.setText("");
        this.txtMdp.setText("");
    }

    public void traitementInscription() {

        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String email = this.txtEmail.getText();
        String telephone = this.txtTelephone.getText();
        String dateNaissance = this.txtDateNaissance.getText();
        String mdp = new String(this.txtMdp.getPassword());

        if (nom.equals("") || prenom.equals("") || email.equals("") ||
                telephone.equals("") || dateNaissance.equals("") || mdp.equals("")) {

            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
            return;
        }

        // Hashage du mot de passe
        String hash = Controller.sha1(mdp);

        // Création de l'objet User
        User unUser = new User(nom, prenom, email, telephone, hash, dateNaissance);

        // Insertion dans la BDD
        Controller.insertUtilisateur(unUser);

        JOptionPane.showMessageDialog(this, "Compte secrétaire créé avec succès");

        // Retour à la connexion
        this.dispose();
        new VueConnexion();
    }
}

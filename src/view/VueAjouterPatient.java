package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.*;
import model.ModelUtilisateur;

public class VueAjouterPatient extends JFrame implements ActionListener {

    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtDateNaissance = new JTextField();

    private JTextField txtAdresse = new JTextField();
    private JTextField txtNumSecu = new JTextField();

    private JComboBox<String> cbSexe = new JComboBox<>(new String[]{"homme", "femme"});

    private JButton btValider = new JButton("Valider");
    private JButton btAnnuler = new JButton("Annuler");

    public VueAjouterPatient() {

        this.setTitle("Ajouter un patient");
        this.setBounds(400, 150, 600, 500);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(10, 2, 5, 5));
        panelForm.setBackground(Color.decode("#4D61F4"));

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(txtNom);

        panelForm.add(new JLabel("Prénom :"));
        panelForm.add(txtPrenom);

        panelForm.add(new JLabel("Email :"));
        panelForm.add(txtEmail);

        panelForm.add(new JLabel("Téléphone :"));
        panelForm.add(txtTelephone);

        panelForm.add(new JLabel("Date naissance (yyyy-mm-dd) :"));
        panelForm.add(txtDateNaissance);

        panelForm.add(new JLabel("Adresse :"));
        panelForm.add(txtAdresse);

        panelForm.add(new JLabel("Numéro de sécurité sociale :"));
        panelForm.add(txtNumSecu);

        panelForm.add(new JLabel("Sexe :"));
        panelForm.add(cbSexe);

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(Color.decode("#4D61F4"));
        panelBoutons.add(btValider);
        panelBoutons.add(btAnnuler);

        this.add(panelForm, BorderLayout.CENTER);
        this.add(panelBoutons, BorderLayout.SOUTH);

        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btAnnuler) {
            this.dispose();
        }

        if (e.getSource() == btValider) {

            // 1) Création de l'utilisateur
            User u = new User(
                    txtNom.getText(),
                    txtPrenom.getText(),
                    txtEmail.getText(),
                    txtTelephone.getText(),
                    ModelUtilisateur.sha1("123"),
                    txtDateNaissance.getText()
            );

            int idUser = ControllerUtilisateur.insertUtilisateur(u);

            // 2) Création du patient
            Patient p = new Patient(
                    txtAdresse.getText(),
                    txtNumSecu.getText(),
                    cbSexe.getSelectedItem().toString(),
                    idUser
            );

            ControllerPatient.insertPatient(p);

            JOptionPane.showMessageDialog(this,
                    "Patient ajouté avec succès",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        }
    }
}

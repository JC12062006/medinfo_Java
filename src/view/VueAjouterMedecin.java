package view;

import controller.Controller;
import controller.Medecin;
import controller.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueAjouterMedecin extends JFrame implements ActionListener {

    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtDateNaissance = new JTextField();
    private JTextField txtRpps = new JTextField();
    private JTextField txtFormations = new JTextField();
    private JTextField txtLangues = new JTextField();
    private JTextField txtExperiences = new JTextField();
    private JTextArea txtDescription = new JTextArea();

    private JCheckBox cbConventionne = new JCheckBox("Conventionné");

    private JComboBox<String> cbSpecialite;

    private JButton btValider = new JButton("Valider");
    private JButton btAnnuler = new JButton("Annuler");

    public VueAjouterMedecin() {

        this.setTitle("Ajouter un médecin");
        this.setBounds(400, 150, 600, 650);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(13, 2, 5, 5));

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

        panelForm.add(new JLabel("RPPS :"));
        panelForm.add(txtRpps);

        panelForm.add(new JLabel("Formations :"));
        panelForm.add(txtFormations);

        panelForm.add(new JLabel("Langues parlées :"));
        panelForm.add(txtLangues);

        panelForm.add(new JLabel("Expériences :"));
        panelForm.add(txtExperiences);

        panelForm.add(new JLabel("Conventionné :"));
        panelForm.add(cbConventionne);

        panelForm.add(new JLabel("Spécialité :"));

        cbSpecialite = new JComboBox<>(new String[]{
                "1 - Cardiologie",
                "2 - Dermatologie",
                "3 - Gynécologie",
                "4 - Pédiatrie",
                "5 - Neurologie",
                "6 - Médecine générale"
        });

        panelForm.add(cbSpecialite);

        panelForm.add(new JLabel("Description :"));
        panelForm.add(new JScrollPane(txtDescription));

        JPanel panelBoutons = new JPanel();
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

            // ============================
            // 1. Génération du mot de passe
            // ============================
            String mdpAuto = "Med-" + (int)(Math.random() * 9000 + 1000);
            String hash = Controller.sha1(mdpAuto);

            // ============================
            // 2. Création de l'utilisateur
            // ============================
            User unUser = new User(
                    txtNom.getText(),
                    txtPrenom.getText(),
                    txtEmail.getText(),
                    txtTelephone.getText(),
                    hash,
                    txtDateNaissance.getText()
            );

            int idUtilisateur = Controller.insertUtilisateurMedecin(unUser);

            // ============================
            // 3. Création du médecin
            // ============================
            int idSpecialite = Integer.parseInt(
                    cbSpecialite.getSelectedItem().toString().split(" ")[0]
            );

            Medecin unMedecin = new Medecin(
                    txtRpps.getText(),
                    cbConventionne.isSelected() ? 1 : 0,
                    txtFormations.getText(),
                    txtLangues.getText(),
                    txtExperiences.getText(),
                    txtDescription.getText(),
                    idUtilisateur,
                    idSpecialite
            );

            Controller.insertMedecin(unMedecin);

            // ============================
            // 4. Message final
            // ============================
            JOptionPane.showMessageDialog(this,
                    "Médecin ajouté avec succès.\nMot de passe généré : " + mdpAuto,
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        }
    }
}

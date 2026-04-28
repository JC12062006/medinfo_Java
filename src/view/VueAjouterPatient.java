package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.ControllerPatient;
import controller.ControllerStyle;
import controller.Patient;
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

    private JButton btValider = new JButton("Enregistrer le patient");
    private JButton btAnnuler = new JButton("Annuler");

    public VueAjouterPatient() {
        this.setTitle("MedInfo - Nouveau Patient");
        this.setSize(600, 850); // Fenêtre haute pour accommoder tous les champs
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        header.setBackground(Color.WHITE);
        header.setBorder(new MatteBorder(0, 0, 1, 0, ControllerStyle.BORDER_SOFT));
        
        JLabel titreHead = new JLabel("Inscription d'un nouveau Patient");
        titreHead.setFont(new Font("SansSerif", Font.BOLD, 20));
        titreHead.setForeground(ControllerStyle.PRIMARY);
        header.add(titreHead);
        
        this.add(header, BorderLayout.NORTH);

        // --- 2. FORMULAIRE (Card) ---
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(ControllerStyle.BORDER_SOFT, 1),
            new EmptyBorder(30, 45, 30, 45)
        ));

        // Ajout des champs
        ajouterChamp("NOM", txtNom, card);
        ajouterChamp("PRÉNOM", txtPrenom, card);
        ajouterChamp("EMAIL", txtEmail, card);
        ajouterChamp("TÉLÉPHONE", txtTelephone, card);
        ajouterChamp("DATE DE NAISSANCE (YYYY-MM-DD)", txtDateNaissance, card);
        ajouterChamp("ADRESSE POSTALE", txtAdresse, card);
        ajouterChamp("N° SÉCURITÉ SOCIALE", txtNumSecu, card);

        // Cas particulier pour la ComboBox
        JLabel lbSexe = new JLabel("SEXE");
        ControllerStyle.applyFormLabel(lbSexe);
        lbSexe.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lbSexe);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        cbSexe.setMaximumSize(new Dimension(350, 40));
        cbSexe.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(cbSexe);

        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 3. BOUTONS ---
        ControllerStyle.applySecondaryBtn(btValider);
        btValider.setMaximumSize(new Dimension(350, 50));
        btValider.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(btValider);

        card.add(Box.createRigidArea(new Dimension(0, 10)));

        btAnnuler.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btAnnuler.setForeground(ControllerStyle.TEXT_MUTED);
        btAnnuler.setBorderPainted(false);
        btAnnuler.setContentAreaFilled(false);
        btAnnuler.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btAnnuler.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(btAnnuler);

        centerPanel.add(card);
        this.add(centerPanel, BorderLayout.CENTER);

        // Listeners
        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);

        this.setVisible(true);
    }

    private void ajouterChamp(String label, JTextField txt, JPanel container) {
        JLabel lbl = new JLabel(label);
        ControllerStyle.applyFormLabel(lbl);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lbl);
        
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        
        ControllerStyle.applyTextField(txt);
        txt.setMaximumSize(new Dimension(350, 40));
        txt.setPreferredSize(new Dimension(350, 40));
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txt);
        
        container.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            this.dispose();
        }

        if (e.getSource() == btValider) {
            if (txtNom.getText().isEmpty() || txtEmail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir au moins le nom et l'email.");
                return;
            }

            Patient p = new Patient(
                0,
                txtNom.getText(),
                txtPrenom.getText(),
                txtEmail.getText(),
                txtTelephone.getText(),
                ModelUtilisateur.sha1("123"), // Mot de passe par défaut
                txtDateNaissance.getText(),
                "patient",
                txtAdresse.getText(),
                txtNumSecu.getText(),
                cbSexe.getSelectedItem().toString()
            );

            ControllerPatient.insertPatient(p);

            JOptionPane.showMessageDialog(this, "Patient enregistré avec succès !");
            this.dispose();
        }
    }
}
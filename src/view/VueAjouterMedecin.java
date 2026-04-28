package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

import controller.*;
import model.ModelUtilisateur;

public class VueAjouterMedecin extends JFrame implements ActionListener {

    private JTextField txtPrenom = new JTextField();
    private JTextField txtNom = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelephone = new JTextField();
    private JTextField txtDateNaissance = new JTextField();
    private JTextField txtRpps = new JTextField();
    private JTextField txtFormations = new JTextField();
    private JTextField txtLangues = new JTextField();
    private JTextField txtExperiences = new JTextField();
    private JTextArea txtDescription = new JTextArea();

    private JComboBox<String> cbxSpecialites = new JComboBox<>();
    private JCheckBox cbConventionne = new JCheckBox("Praticien conventionné");

    private JButton btValider = new JButton("Enregistrer le médecin");
    private JButton btAnnuler = new JButton("Annuler");

    public VueAjouterMedecin() {
        this.setTitle("MedInfo - Nouveau Médecin");
        this.setSize(650, 900); // Fenêtre haute pour accommoder le formulaire complet
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        header.setBackground(Color.WHITE);
        header.setBorder(new MatteBorder(0, 0, 1, 0, ControllerStyle.BORDER_SOFT));
        
        JLabel titreHead = new JLabel("Ajouter un nouveau Praticien");
        titreHead.setFont(new Font("SansSerif", Font.BOLD, 20));
        titreHead.setForeground(ControllerStyle.PRIMARY);
        header.add(titreHead);
        
        this.add(header, BorderLayout.NORTH);

        // --- 2. CENTRE (Scrollable Card) ---
        // On utilise un JScrollPane au cas où l'écran de l'utilisateur est petit
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(ControllerStyle.BORDER_SOFT, 1),
            new EmptyBorder(30, 45, 30, 45)
        ));

        // Ajout des champs (Identité)
        ajouterChamp("NOM", txtNom, card);
        ajouterChamp("PRÉNOM", txtPrenom, card);
        ajouterChamp("EMAIL", txtEmail, card);
        ajouterChamp("TÉLÉPHONE", txtTelephone, card);
        ajouterChamp("DATE DE NAISSANCE (YYYY-MM-DD)", txtDateNaissance, card);
        
        // Ajout des champs (Professionnel)
        ajouterChamp("NUMÉRO RPPS", txtRpps, card);
        ajouterChamp("FORMATIONS", txtFormations, card);
        ajouterChamp("LANGUES PARLÉES", txtLangues, card);
        ajouterChamp("EXPÉRIENCES", txtExperiences, card);

        // Spécialité
        JLabel lbSpe = new JLabel("SPÉCIALITÉ");
        ControllerStyle.applyFormLabel(lbSpe);
        lbSpe.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lbSpe);
        cbxSpecialites.setMaximumSize(new Dimension(400, 40));
        cbxSpecialites.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(cbxSpecialites);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Description (TextArea)
        JLabel lbDesc = new JLabel("DESCRIPTION / BIO");
        ControllerStyle.applyFormLabel(lbDesc);
        lbDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lbDesc);
        txtDescription.setRows(3);
        txtDescription.setLineWrap(true);
        txtDescription.setBorder(new EmptyBorder(5, 5, 5, 5));
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setMaximumSize(new Dimension(400, 80));
        scrollDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(scrollDesc);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Checkbox conventionné
        cbConventionne.setOpaque(false);
        cbConventionne.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbConventionne.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(cbConventionne);
        
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 3. BOUTONS ---
        ControllerStyle.applySecondaryBtn(btValider);
        btValider.setMaximumSize(new Dimension(400, 50));
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

        centerWrapper.add(card);
        
        // JScrollPane pour permettre le défilement du formulaire si besoin
        JScrollPane mainScroll = new JScrollPane(centerWrapper);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        this.add(mainScroll, BorderLayout.CENTER);

        // Listeners & Initialisation
        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);
        remplirCBX();

        this.setVisible(true);
    }

    private void ajouterChamp(String label, JTextField txt, JPanel container) {
        JLabel lbl = new JLabel(label);
        ControllerStyle.applyFormLabel(lbl);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(lbl);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        ControllerStyle.applyTextField(txt);
        txt.setMaximumSize(new Dimension(400, 40));
        txt.setPreferredSize(new Dimension(400, 40));
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(txt);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    public void remplirCBX() {
        cbxSpecialites.removeAllItems();
        ArrayList<Specialite> lesSpecialites = ControllerSpecialite.selectAllSpecialite();
        for (Specialite uneSpecialite : lesSpecialites) {
            cbxSpecialites.addItem(uneSpecialite.getIdSpecialite() + " - " + uneSpecialite.getLibelle());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            this.dispose();
        }

        if (e.getSource() == btValider) {
            // Logique d'insertion identique à ton original
            User u = new User(
                txtNom.getText(),
                txtPrenom.getText(),
                txtEmail.getText(),
                txtTelephone.getText(),
                ModelUtilisateur.sha1("123"),
                txtDateNaissance.getText()
            );

            int idUser = ControllerUtilisateur.insertUtilisateur(u);

            String item = cbxSpecialites.getSelectedItem().toString();
            int idSpecialite = Integer.parseInt(item.split(" - ")[0]);

            Medecin m = new Medecin(
                txtRpps.getText(),
                cbConventionne.isSelected() ? 1 : 0,
                txtFormations.getText(),
                txtLangues.getText(),
                txtExperiences.getText(),
                txtDescription.getText(),
                idUser,
                idSpecialite
            );

            ControllerMedecin.insertMedecin(m);
            JOptionPane.showMessageDialog(this, "Médecin ajouté avec succès !");
            this.dispose();
        }
    }
}
package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.ControllerUtilisateur;
import controller.ControllerStyle;
import controller.User;

public class VueConnexion extends JFrame implements ActionListener, KeyListener {

    private JTextField txtEmail = new JTextField();
    private JPasswordField txtMdp = new JPasswordField();
    private JButton btAnnuler = new JButton("Vider");
    private JButton btValider = new JButton("Se connecter");

    public VueConnexion() {
        this.setTitle("MedInfo - Authentification");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null); // Centrer sur l'écran

        // Fond de la fenêtre
        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new GridBagLayout()); // Pour centrer la "Carte" de connexion

        // --- LA CARTE DE CONNEXION (Panel blanc) ---
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(400, 500));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(
            new LineBorder(ControllerStyle.BORDER_SOFT, 1),
            new EmptyBorder(40, 40, 40, 40)
        ));

        // 1. Logo
        try {
            ImageIcon icon = new ImageIcon("src/images/logo.png");
            // Redimensionnement du logo si nécessaire
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel lbLogo = new JLabel(new ImageIcon(img));
            lbLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(lbLogo);
        } catch (Exception e) {
            JLabel lbLogoText = new JLabel("MedInfo");
            lbLogoText.setFont(new Font("SansSerif", Font.BOLD, 32));
            lbLogoText.setForeground(ControllerStyle.PRIMARY);
            lbLogoText.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(lbLogoText);
        }

        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // 2. Titre
        JLabel lbTitre = new JLabel("Connexion Personnel");
        lbTitre.setFont(new Font("SansSerif", Font.BOLD, 18));
        lbTitre.setForeground(ControllerStyle.TEXT_MAIN);
        lbTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lbTitre);

        card.add(Box.createRigidArea(new Dimension(0, 30)));

     // --- 3. Champs de saisie ---

     // Label Email
     JLabel lbEmail = new JLabel("Adresse Email");
     ControllerStyle.applyFormLabel(lbEmail);
     lbEmail.setAlignmentX(Component.CENTER_ALIGNMENT); // <-- AJOUTE CECI
     card.add(lbEmail);

     card.add(Box.createRigidArea(new Dimension(0, 5)));

     // Champ Email
     ControllerStyle.applyTextField(txtEmail);
     txtEmail.setMaximumSize(new Dimension(320, 40)); // On fixe une largeur max pour que ce soit beau
     txtEmail.setAlignmentX(Component.CENTER_ALIGNMENT); // <-- AJOUTE CECI
     card.add(txtEmail);

     card.add(Box.createRigidArea(new Dimension(0, 20)));

     // Label Mot de passe
     JLabel lbMdp = new JLabel("Mot de passe");
     ControllerStyle.applyFormLabel(lbMdp);
     lbMdp.setAlignmentX(Component.CENTER_ALIGNMENT); // <-- AJOUTE CECI
     card.add(lbMdp);

     card.add(Box.createRigidArea(new Dimension(0, 5)));

     // Champ Mot de passe
     ControllerStyle.applyTextField(txtMdp);
     txtMdp.setMaximumSize(new Dimension(320, 40)); // On fixe la même largeur max
     txtMdp.setAlignmentX(Component.CENTER_ALIGNMENT); // <-- AJOUTE CECI
     card.add(txtMdp);

        // 4. Boutons
        ControllerStyle.applySecondaryBtn(btValider);
        btValider.setAlignmentX(Component.CENTER_ALIGNMENT);
        btValider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        card.add(btValider);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        btAnnuler.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btAnnuler.setForeground(ControllerStyle.TEXT_MUTED);
        btAnnuler.setBackground(Color.WHITE);
        btAnnuler.setBorder(null);
        btAnnuler.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btAnnuler.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(btAnnuler);

        // Ajout de la carte au centre
        this.add(card);

        // Listeners
        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);
        txtEmail.addKeyListener(this);
        txtMdp.addKeyListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAnnuler) {
            txtEmail.setText("");
            txtMdp.setText("");
        } else if (e.getSource() == btValider) {
            traitementConnexion();
        }
    }

    public void traitementConnexion() {
        String email = txtEmail.getText();
        String mdp = new String(txtMdp.getPassword());

        if (email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Champs vides", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User unUser = ControllerUtilisateur.selectWhereUser(email, mdp);

        if (unUser == null) {
            JOptionPane.showMessageDialog(this, "Identifiants incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            String role = unUser.getRole();
            if (role.equalsIgnoreCase("Medecin") || role.equalsIgnoreCase("Patient")) {
                JOptionPane.showMessageDialog(this, 
                    "Accès refusé : Cette application est réservée au personnel administratif.", 
                    "Erreur d'accès", JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
                new VueGenerale(unUser);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) traitementConnexion();
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controller;
import controller.User;

public class VueConnexion extends JFrame implements ActionListener, KeyListener {

    private JPanel panelForm = new JPanel();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Connexion");
    private JButton btInscription = new JButton("Créer un compte");

    private JTextField txtEmail = new JTextField();
    private JPasswordField txtMdp = new JPasswordField();

    public VueConnexion() {
        this.setTitle("MedInfo - Connexion");
        this.setBounds(600, 200, 600, 350);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.getContentPane().setBackground(Color.cyan);

        // Logo
        ImageIcon uneImage = new ImageIcon("src/images/logo.png");
        JLabel imageLogo = new JLabel(uneImage);
        imageLogo.setBounds(20, 40, 250, 250);
        this.add(imageLogo);

        // Formulaire
        this.panelForm.setBounds(300, 40, 260, 200);
        this.panelForm.setBackground(Color.cyan);
        this.panelForm.setLayout(new GridLayout(4, 2, 10, 10));

        this.panelForm.add(new JLabel("Email :"));
        this.panelForm.add(this.txtEmail);

        this.panelForm.add(new JLabel("Mot de passe :"));
        this.panelForm.add(this.txtMdp);

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);

        this.panelForm.add(new JLabel(""));
        this.panelForm.add(this.btInscription);

        this.add(this.panelForm);

        // Listeners
        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btInscription.addActionListener(this);

        this.txtEmail.addKeyListener(this);
        this.txtMdp.addKeyListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.btAnnuler) {
            this.txtEmail.setText("");
            this.txtMdp.setText("");
        }
        else if (e.getSource() == this.btValider) {
            this.traitementConnexion();
        }
        else if (e.getSource() == this.btInscription) {
            // Ouvrir la fenêtre d'inscription
            this.dispose();
            new VueInscription();
        }
    }

    public void traitementConnexion() {
        String email = this.txtEmail.getText();
        String mdp = new String(this.txtMdp.getPassword());

        if (email.equals("") || mdp.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
        } else {

            User unUser = Controller.selectWhereUser(email, mdp);

            if (unUser == null) {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Bienvenue " + unUser.getPrenom() + " " + unUser.getNom());

                this.dispose();
                new VueGenerale(unUser);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.traitementConnexion();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

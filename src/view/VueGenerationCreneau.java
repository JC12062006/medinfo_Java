package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.ControllerCreneau;
import controller.ControllerStyle;
import controller.User;

public class VueGenerationCreneau extends JFrame implements ActionListener {
    
    private User unUser;
    
    // Composants de saisie
    private JTextField txtIdMedecin = new JTextField();
    private JTextField txtIdSalle = new JTextField();
    private JTextField txtDateDebut = new JTextField();
    private JTextField txtDateFin = new JTextField();
    private JTextField txtDuree = new JTextField("30");
    
    // Boutons
    private JButton btGenerer = new JButton("Lancer la génération");
    private JButton btAnnuler = new JButton("Réinitialiser");
    private JButton btRetour = new JButton("⬅ Retour");

    public VueGenerationCreneau(User unUser) {
        this.unUser = unUser;
        
        // Configuration de la fenêtre
        this.setTitle("MedInfo - Générateur de Planning");
        this.setSize(600, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        this.getContentPane().setBackground(ControllerStyle.BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        header.setBackground(Color.WHITE);
        header.setBorder(new MatteBorder(0, 0, 1, 0, ControllerStyle.BORDER_SOFT));
        
        ControllerStyle.applyPrimaryBtn(btRetour);
        btRetour.setBackground(ControllerStyle.PRIMARY_SOFT);
        btRetour.setForeground(ControllerStyle.PRIMARY);
        header.add(btRetour);
        
        JLabel titreHead = new JLabel("Génération de créneaux");
        titreHead.setFont(new Font("SansSerif", Font.BOLD, 18));
        titreHead.setForeground(ControllerStyle.TEXT_MAIN);
        header.add(titreHead);
        
        this.add(header, BorderLayout.NORTH);

        // --- 2. CENTRE (La Carte Formulaire) ---
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(ControllerStyle.BORDER_SOFT, 1),
            new EmptyBorder(30, 45, 30, 45)
        ));

        // En-tête de la carte
        JLabel lbDesc = new JLabel("Paramètres du planning");
        lbDesc.setFont(new Font("SansSerif", Font.BOLD, 18));
        lbDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lbDesc);
        
        JLabel lbSub = new JLabel("Format requis : YYYY-MM-DD HH:MM");
        lbSub.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lbSub.setForeground(ControllerStyle.TEXT_MUTED);
        lbSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lbSub);
        
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // Ajout des champs via la méthode de centrage
        ajouterChamp("ID DU MÉDECIN", txtIdMedecin, card);
        ajouterChamp("ID DE LA SALLE", txtIdSalle, card);
        ajouterChamp("DATE & HEURE DE DÉBUT", txtDateDebut, card);
        ajouterChamp("DATE & HEURE DE FIN", txtDateFin, card);
        ajouterChamp("DURÉE PAR CRÉNEAU (MIN)", txtDuree, card);

        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Bouton de validation
        ControllerStyle.applySecondaryBtn(btGenerer);
        btGenerer.setMaximumSize(new Dimension(350, 50)); 
        btGenerer.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(btGenerer);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Bouton réinitialiser
        btAnnuler.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btAnnuler.setForeground(ControllerStyle.TEXT_MUTED);
        btAnnuler.setBorderPainted(false);
        btAnnuler.setContentAreaFilled(false);
        btAnnuler.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btAnnuler.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(btAnnuler);

        centerPanel.add(card);
        this.add(centerPanel, BorderLayout.CENTER);

        // --- LISTENERS ---
        btRetour.addActionListener(this);
        btAnnuler.addActionListener(this);
        btGenerer.addActionListener(this);
        
        this.setVisible(true);
    }

    /**
     * Crée un bloc Label + Champ parfaitement centré
     */
    private void ajouterChamp(String label, JTextField txt, JPanel container) {
        // Label
        JLabel lbl = new JLabel(label);
        ControllerStyle.applyFormLabel(lbl);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrage horizontal dans le BoxLayout
        lbl.setHorizontalAlignment(SwingConstants.CENTER); // Centrage du texte
        container.add(lbl);
        
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Champ
        ControllerStyle.applyTextField(txt);
        txt.setMaximumSize(new Dimension(350, 40)); // Largeur fixe pour l'harmonie
        txt.setPreferredSize(new Dimension(350, 40));
        txt.setAlignmentX(Component.CENTER_ALIGNMENT); 
        container.add(txt);
        
        // Espace
        Component spacer = Box.createRigidArea(new Dimension(0, 18));
        ((JComponent)spacer).setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(spacer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btRetour) {
            this.dispose();
            new VueGestionCreneau(this.unUser);
        } 
        else if (e.getSource() == btAnnuler) {
            txtIdMedecin.setText("");
            txtIdSalle.setText("");
            txtDateDebut.setText("");
            txtDateFin.setText("");
            txtDuree.setText("30");
        }
        else if (e.getSource() == btGenerer) {
            validerEtGenerer();
        }
    }

    private void validerEtGenerer() {
        String medecin = txtIdMedecin.getText();
        String salle = txtIdSalle.getText();
        String dDebut = txtDateDebut.getText();
        String dFin = txtDateFin.getText();
        String duree = txtDuree.getText();
        
        if (medecin.isEmpty() || salle.isEmpty() || dDebut.isEmpty() || dFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Veuillez remplir tous les champs avant de générer.");
            return;
        }
        
        try {
            int idM = Integer.parseInt(medecin);
            int idS = Integer.parseInt(salle);
            int dur = Integer.parseInt(duree);
            
            // Formatage pour MySQL
            String fullDebut = dDebut + ":00";
            String fullFin = dFin + ":00";
            
            ControllerCreneau.genererPlanning(idM, idS, fullDebut, fullFin, dur);
            
            JOptionPane.showMessageDialog(this, "✅ Succès : Le planning a été créé.");
            this.dispose();
            new VueGestionCreneau(this.unUser);
            
        } catch (NumberFormatException exp) {
            JOptionPane.showMessageDialog(this, "❌ Erreur : Seuls des nombres sont autorisés pour les ID et la durée.");
        }
    }
} 
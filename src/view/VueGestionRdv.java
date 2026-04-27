package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import controller.*;

public class VueGestionRdv extends JFrame implements ActionListener {

    private User unUser;
    
    // --- Palette de couleurs MedInfo ---
    private final Color PRIMARY = Color.decode("#4D61F4");
    private final Color PRIMARY_SOFT = Color.decode("#EEF1FF");
    private final Color SECONDARY = Color.decode("#D7FF7B");
    private final Color BG_ALT = Color.decode("#F6F8FC");
    private final Color TEXT_MAIN = Color.decode("#1F2933");
    private final Color DANGER = Color.decode("#E53E3E");
    private final Color BORDER = Color.decode("#E2E8F0");

    private JTextField txtMotif = new JTextField(), txtFiltre = new JTextField();
    private JComboBox<String> cbxPatients = new JComboBox<>(), cbxStatut;
    private JButton btAnnuler = new JButton("Vider"), btModifier = new JButton("Enregistrer"), 
                    btSupprimer = new JButton("Supprimer"), btRetour = new JButton("Menu"), 
                    btFiltrer = new JButton("Rechercher");

    private JTable tableRdv;
    private Tableau unTableau;
    private JLabel lbNbRdv = new JLabel();
    private ArrayList<Patient> tousLesPatients;

    public VueGestionRdv(User unUser) {
        this.unUser = unUser;
        this.setTitle("MedInfo - Gestion des Rendez-vous");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        this.getContentPane().setBackground(BG_ALT);
        this.setLayout(new BorderLayout());

        // --- 1. HEADER (Barre supérieure blanche) ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
        
        // Logo / Titre à gauche
        JLabel title = new JLabel("  Gestion des Rendez-vous");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(PRIMARY);
        header.add(title, BorderLayout.WEST);

        // Recherche à droite
        JPanel searchZone = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 18));
        searchZone.setOpaque(false);
        txtFiltre.setPreferredSize(new Dimension(250, 35));
        btFiltrer.setBackground(PRIMARY);
        btFiltrer.setForeground(Color.WHITE);
        searchZone.add(new JLabel("Rechercher :"));
        searchZone.add(txtFiltre);
        searchZone.add(btFiltrer);
        header.add(searchZone, BorderLayout.EAST);

        this.add(header, BorderLayout.NORTH);

        // --- 2. SIDEBAR (Formulaire à gauche) ---
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(380, 0));
        sidebar.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(0, 0, 20, 0);
        g.gridx = 0; g.weightx = 1;

        // Composants Sidebar
        ajouterChamp(sidebar, "PATIENT", cbxPatients, g, 0);
        cbxPatients.setEditable(true);
        
        ajouterChamp(sidebar, "STATUT", cbxStatut = new JComboBox<>(new String[]{"Sélectionner...", "À Confirmer", "Confirmé", "Annulé", "Honoré"}), g, 2);
        ajouterChamp(sidebar, "MOTIF", txtMotif, g, 4);

        // Boutons d'action
        btModifier.setBackground(SECONDARY);
        btModifier.setForeground(TEXT_MAIN);
        btModifier.setFont(new Font("SansSerif", Font.BOLD, 13));
        btModifier.setPreferredSize(new Dimension(0, 45));
        g.gridy = 6; sidebar.add(btModifier, g);

        btSupprimer.setBackground(PRIMARY_SOFT);
        btSupprimer.setForeground(DANGER);
        btSupprimer.setBorder(new LineBorder(DANGER, 1));
        g.gridy = 7; sidebar.add(btSupprimer, g);

        btAnnuler.setOpaque(false);
        btAnnuler.setContentAreaFilled(false);
        btAnnuler.setBorder(null);
        g.gridy = 8; sidebar.add(btAnnuler, g);

        this.add(sidebar, BorderLayout.WEST);

        // --- 3. MAIN CONTENT (Tableau) ---
        JPanel main = new JPanel(new BorderLayout(0, 20));
        main.setOpaque(false);
        main.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Tableau stylisé
        String entetes[] = {"ID", "Motif", "Origine", "Statut", "Patient", "Date & Heure"};
        this.unTableau = new Tableau(obtenirDonnees(""), entetes);
        this.tableRdv = new JTable(this.unTableau);
        this.tableRdv.setRowHeight(45);
        this.tableRdv.setGridColor(BORDER);
        this.tableRdv.setSelectionBackground(PRIMARY_SOFT);
        this.tableRdv.setSelectionForeground(PRIMARY);
        this.tableRdv.getTableHeader().setBackground(Color.WHITE);
        this.tableRdv.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scroll = new JScrollPane(tableRdv);
        scroll.setBorder(new LineBorder(BORDER, 1));
        scroll.getViewport().setBackground(Color.WHITE);
        main.add(scroll, BorderLayout.CENTER);

        // Footer & Retour
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        lbNbRdv.setText("Total : " + unTableau.getRowCount() + " RDV enregistrés");
        footer.add(lbNbRdv, BorderLayout.WEST);
        footer.add(btRetour, BorderLayout.EAST);
        main.add(footer, BorderLayout.SOUTH);

        this.add(main, BorderLayout.CENTER);

        // --- LOGIQUE ---
        initLogic();
        this.setVisible(true);
    }

    private void ajouterChamp(JPanel p, String label, JComponent field, GridBagConstraints g, int y) {
        JLabel lb = new JLabel(label);
        lb.setFont(new Font("SansSerif", Font.BOLD, 11));
        lb.setForeground(Color.GRAY);
        g.gridy = y; p.add(lb, g);
        field.setPreferredSize(new Dimension(0, 40));
        field.setBackground(BG_ALT);
        g.gridy = y + 1; p.add(field, g);
    }

    private void initLogic() {
        btRetour.addActionListener(this); btAnnuler.addActionListener(this);
        btModifier.addActionListener(this); btSupprimer.addActionListener(this);
        btFiltrer.addActionListener(this);
        btModifier.setEnabled(false); btSupprimer.setEnabled(false);

        tableRdv.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int l = tableRdv.getSelectedRow();
                txtMotif.setText(unTableau.getValueAt(l, 1).toString());
                cbxStatut.setSelectedItem(unTableau.getValueAt(l, 3).toString());
                cbxPatients.setSelectedItem(unTableau.getValueAt(l, 4).toString());
                btModifier.setEnabled(true); btSupprimer.setEnabled(true);
            }
        });

        this.tousLesPatients = ControllerPatient.selectAllPatientsFiltre("");
        JTextField ed = (JTextField) cbxPatients.getEditor().getEditorComponent();
        ed.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) return;
                rafraichirCbxPatients(ed.getText());
                if(cbxPatients.getItemCount() > 0) cbxPatients.showPopup();
            }
        });
    }

    // --- Méthodes métier ---

    public void rafraichirCbxPatients(String filtre) {
        cbxPatients.removeAllItems();
        String f = filtre.toLowerCase();
        for (Patient p : this.tousLesPatients) {
            String aff = p.getIdPatient() + " - " + p.getNom() + " " + p.getPrenom();
            if (filtre.isEmpty() || aff.toLowerCase().contains(f)) cbxPatients.addItem(aff);
        }
    }

    public Object [][] obtenirDonnees(String filtre){
        ArrayList<Rdv> lesRdv = ControllerRdv.selectAllRdv(filtre);
        Object matrice [][] = new Object[lesRdv.size()] [6];
        int i = 0;
        for (Rdv r : lesRdv) {
            matrice[i][0] = r.getIdRdv();
            matrice[i][1] = r.getMotif();
            matrice[i][2] = r.getOrigine();
            matrice[i][3] = r.getStatut();
            matrice[i][4] = r.getNomCompletPatient();
            matrice[i][5] = r.getDateCreneauFormatee(); 
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btRetour) { this.dispose(); new VueGenerale(unUser); }
        else if (e.getSource() == btAnnuler) viderChamps();
        else if (e.getSource() == btFiltrer) {
            unTableau.setDonnees(obtenirDonnees(txtFiltre.getText()));
            lbNbRdv.setText("Total : " + unTableau.getRowCount() + " RDV");
        }
        else if (e.getSource() == btModifier) updateRdv();
        else if (e.getSource() == btSupprimer) deleteRdv();
    }

    public void updateRdv() {
        int l = tableRdv.getSelectedRow();
        int id = Integer.parseInt(unTableau.getValueAt(l, 0).toString());
        String m = txtMotif.getText();
        String s = cbxStatut.getSelectedItem().toString();
        String p = cbxPatients.getSelectedItem().toString();
        if (m.isEmpty() || s.contains("Sélect") || !p.contains("-")) return;
        
        int idPatient = Integer.parseInt(p.split(" - ")[0]);
        Rdv r = new Rdv(id, idPatient, 1, java.time.LocalDateTime.now(), m, s, "Cabinet", "", "");        
        ControllerRdv.updateRdv(r);
        unTableau.setDonnees(obtenirDonnees(""));
        viderChamps();
    }

    public void deleteRdv() {
        int l = tableRdv.getSelectedRow();
        int id = Integer.parseInt(unTableau.getValueAt(l, 0).toString());
        if(JOptionPane.showConfirmDialog(this, "Supprimer ?", "Attention", 0) == 0) {
            ControllerRdv.deleteRdv(id);
            unTableau.setDonnees(obtenirDonnees(""));
            lbNbRdv.setText("Total : " + unTableau.getRowCount() + " RDV");
            viderChamps();
        }
    }

    public void viderChamps() {
        txtMotif.setText(""); cbxStatut.setSelectedIndex(0);
        cbxPatients.setSelectedItem(""); btModifier.setEnabled(false); btSupprimer.setEnabled(false);
    }
}
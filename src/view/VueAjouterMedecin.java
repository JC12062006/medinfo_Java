package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.ControllerSpecialite;
import controller.Specialite;

public class VueAjouterMedecin extends JFrame implements ActionListener {

	private JComboBox<String> cbxSpecialites = new JComboBox<String>();
	
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

    private JComboBox<String> cbSpecialite;

    private JButton btValider = new JButton("Valider");
    private JButton btAnnuler = new JButton("Annuler");

    public VueAjouterMedecin() {

        this.setTitle("Ajouter un médecin");
        this.setBounds(400, 150, 600, 600);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(12, 2, 5, 5));

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

        panelForm.add(new JLabel("Specialites : "));
		panelForm.add(this.cbxSpecialites);

        panelForm.add(cbxSpecialites);

        panelForm.add(new JLabel("Description :"));
        panelForm.add(new JScrollPane(txtDescription));

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btValider);
        panelBoutons.add(btAnnuler);

        this.add(panelForm, BorderLayout.CENTER);
        this.add(panelBoutons, BorderLayout.SOUTH);

        btValider.addActionListener(this);
        btAnnuler.addActionListener(this);
        
     // Remplir le ComboBox avec les specialites existantes
     	this.remplirCBX();

        this.setVisible(true);
    }
    
    public void remplirCBX() {
		cbxSpecialites.removeAllItems();
		ArrayList<Specialite> lesSpecialites = ControllerSpecialite.selectAllSpecialite();
		for (Specialite uneSpecialite : lesSpecialites) {
			// On ajoute "ID - Nom"
			cbxSpecialites.addItem(uneSpecialite.getIdSpecialite() + " - " + uneSpecialite.getLibelle());
		}
	}

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btAnnuler) {
            this.dispose();
        }

        if (e.getSource() == btValider) {

            JOptionPane.showMessageDialog(this,
                    "Médecin ajouté (à relier avec le contrôleur)",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        }
    }
}
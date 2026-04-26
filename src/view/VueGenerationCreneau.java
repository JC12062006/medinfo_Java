package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ControllerCreneau;
import controller.User;

public class VueGenerationCreneau extends JFrame implements ActionListener {
	
	private User unUser;
	
	private JPanel panelForm = new JPanel();
	
	private JTextField txtIdMedecin = new JTextField();
	private JTextField txtIdSalle = new JTextField();
	private JTextField txtDateDebut = new JTextField();
	private JTextField txtDateFin = new JTextField();
	private JTextField txtDuree = new JTextField("30"); // 30 min par défaut
	
	private JButton btGenerer = new JButton("Générer");
	private JButton btAnnuler = new JButton("Vider");
	private JButton btRetour = new JButton("Retour aux créneaux");

	public VueGenerationCreneau(User unUser) {
		this.unUser = unUser;
		
		this.setTitle("Générer un Planning");
		this.setBounds(300, 150, 600, 500); // Fenêtre plus petite, centrée
		this.setLayout(null);
		this.getContentPane().setBackground(Color.decode("#4D61F4"));
		
		// Titre
        JLabel titre = new JLabel("Création automatique de créneaux");
        titre.setBounds(100, 20, 400, 40);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setForeground(Color.white);
        this.add(titre);
        
        // Bouton retour
        this.btRetour.setBounds(20, 60, 180, 30);
        this.add(this.btRetour);
        this.btRetour.addActionListener(this);
        
        // --- Panel Formulaire ---
 		this.panelForm.setBounds(50, 100, 450, 300);
 		this.panelForm.setBackground(Color.decode("#4D61F4"));
 		this.panelForm.setLayout(new GridLayout(6, 2, 20, 20));
 		
 		// Labels colorés en blanc
 		JLabel lbMedecin = new JLabel("ID du Médecin : ");
 		lbMedecin.setForeground(Color.white);
 		this.panelForm.add(lbMedecin);
 		this.panelForm.add(this.txtIdMedecin);
 		
 		JLabel lbSalle = new JLabel("ID de la Salle : ");
 		lbSalle.setForeground(Color.white);
 		this.panelForm.add(lbSalle);
 		this.panelForm.add(this.txtIdSalle);
 		
 		JLabel lbDebut = new JLabel("Date Début (YYYY-MM-DD HH:MM) : ");
 		lbDebut.setForeground(Color.white);
 		this.panelForm.add(lbDebut);
 		this.panelForm.add(this.txtDateDebut);
 		
 		JLabel lbFin = new JLabel("Date Fin (YYYY-MM-DD HH:MM) : ");
 		lbFin.setForeground(Color.white);
 		this.panelForm.add(lbFin);
 		this.panelForm.add(this.txtDateFin);
 		
 		JLabel lbDuree = new JLabel("Durée (en minutes) : ");
 		lbDuree.setForeground(Color.white);
 		this.panelForm.add(lbDuree);
 		this.panelForm.add(this.txtDuree);
 		
 		this.panelForm.add(this.btAnnuler);
 		this.panelForm.add(this.btGenerer);
 		
 		this.add(this.panelForm);
 		
 		// Listeners
 		this.btAnnuler.addActionListener(this);
 		this.btGenerer.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btRetour) {
			this.dispose();
			new VueGestionCreneau(this.unUser);
		} 
		else if (e.getSource() == this.btAnnuler) {
			this.txtIdMedecin.setText("");
			this.txtIdSalle.setText("");
			this.txtDateDebut.setText("");
			this.txtDateFin.setText("");
			this.txtDuree.setText("30");
		}
		else if (e.getSource() == this.btGenerer) {
			// On récupère les textes
			String medecin = this.txtIdMedecin.getText();
			String salle = this.txtIdSalle.getText();
			String dateDebut = this.txtDateDebut.getText() + ":00"; // Ajout des secondes pour MySQL
			String dateFin = this.txtDateFin.getText() + ":00";
			String duree = this.txtDuree.getText();
			
			// Petites vérifications pour éviter les plantages
			if (medecin.equals("") || salle.equals("") || this.txtDateDebut.getText().equals("") || this.txtDateFin.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
				return;
			}
			
			try {
				int idM = Integer.parseInt(medecin);
				int idS = Integer.parseInt(salle);
				int dur = Integer.parseInt(duree);
				
				// Appel au contrôleur pour déclencher la procédure stockée !
				ControllerCreneau.genererPlanning(idM, idS, dateDebut, dateFin, dur);
				
				JOptionPane.showMessageDialog(this, "Le planning a été généré avec succès en base de données !");
				
				// On redirige vers la vue des créneaux pour voir le résultat
				this.dispose();
				new VueGestionCreneau(this.unUser);
				
			} catch (NumberFormatException exp) {
				JOptionPane.showMessageDialog(this, "Erreur : L'ID Médecin, l'ID Salle et la Durée doivent être des nombres.");
			}
		}
	}
}
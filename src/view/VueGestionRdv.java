package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.ControllerRdv;
import controller.ControllerPatient;
import controller.Patient;
import controller.Rdv;
import controller.Tableau;

public class VueGestionRdv extends JFrame implements ActionListener, KeyListener {
	
	
	
	private JPanel panelForm = new JPanel();

	private JTextField txtMotif = new JTextField();
	// J'ajoute les champs pour le filtre qui manquaient dans tes déclarations
	private JTextField txtFiltre = new JTextField(); 
	private JButton btFiltrer = new JButton("Filtrer");
	
	// Les comboboxs (j'ai retiré le "static" de cbxStatut)
	private JComboBox<String> cbxPatients = new JComboBox<String>();
	private JComboBox<String> cbxStatut;
	
	private JButton btAnnuler = new JButton("Annuler");
	private JButton btValider = new JButton("Valider");
	private JButton btModifier = new JButton("Modifier");
	private JButton btSupprimer = new JButton("Supprimer");
	
	private JTable tableRdv;
	private JScrollPane scrollRdv;
	private Tableau unTableau;
	
	private JLabel lbNbRdv = new JLabel();
	
	public VueGestionRdv() {
		
		this.setTitle("Gestion des Rendez-vous");
	    this.setBounds(200, 100, 1000, 500); // Taille et position de la fenêtre
	    this.setLayout(null);
	    this.getContentPane().setBackground(Color.decode("#4D61F4"));
		
		// Remplir la Combobox avec les statuts 
		String[] lesStatuts = {"À Confirmer", "Confirmé", "Annulé", "Honoré"};
		cbxStatut = new JComboBox<>(lesStatuts);
		
		// --- Panel Formulaire ---
		this.panelForm.setBounds(50, 120, 350, 300);
		this.panelForm.setBackground(Color.decode("#4D61F4"));
		this.panelForm.setLayout(new GridLayout(7, 2, 20, 20));
		
		this.panelForm.add(new JLabel("Patient (Saisie) : "));
		this.panelForm.add(this.cbxPatients);
		
		this.panelForm.add(new JLabel("Statut : "));
		this.panelForm.add(this.cbxStatut);
		
		this.panelForm.add(new JLabel("Motif : "));
		this.panelForm.add(this.txtMotif);
		
		this.panelForm.add(this.btAnnuler);
		this.panelForm.add(this.btValider);
		this.panelForm.add(this.btModifier);
		this.panelForm.add(this.btSupprimer);
		
		this.btModifier.setEnabled(false);
		this.btSupprimer.setEnabled(false);
		this.add(this.panelForm);
		
		// --- Saisie Prédictive Patient ---
		cbxPatients.setEditable(true);
		JTextField editor = (JTextField) cbxPatients.getEditor().getEditorComponent();
		
		editor.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN 
		            || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_LEFT 
		            || e.getKeyCode() == KeyEvent.VK_RIGHT) {
		            return;
		        }
		        String texteSaisi = editor.getText();
		        rafraichirCbxPatients(texteSaisi);
		        editor.setText(texteSaisi);
		        if (cbxPatients.getItemCount() > 0) {
		            cbxPatients.showPopup();
		        }
		    }
		});
		
		// Remplissage initial de la combobox
		rafraichirCbxPatients("");
				
		// --- Listeners ---
		this.btAnnuler.addActionListener(this);
		this.btValider.addActionListener(this);
		this.btModifier.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btFiltrer.addActionListener(this); // Ajout du listener manquant
		this.txtMotif.addKeyListener(this);
		
		// --- JTable ---
		// J'ai réaligné les en-têtes avec ta méthode obtenirDonnees()
		String entetes [] = {"ID Rdv", "Motif", "Origine", "Statut", "ID Patient", "ID Créneau"};
		
		this.unTableau = new Tableau(obtenirDonnees(""), entetes);
		this.tableRdv = new JTable(this.unTableau);
		
		this.scrollRdv = new JScrollPane(this.tableRdv);
		this.scrollRdv.setBackground(Color.decode("#4D61F4"));
		this.scrollRdv.setBounds(450, 160, 500, 220);
		this.add(this.scrollRdv);
		
		this.tableRdv.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int numLigne = tableRdv.getSelectedRow();
				
				// Remplissage du motif (Colonne 1)
				txtMotif.setText(unTableau.getValueAt(numLigne, 1).toString());

				// Remplissage du statut (Colonne 3) - Correction : setSelectedItem
				String statut = unTableau.getValueAt(numLigne, 3).toString();
				cbxStatut.setSelectedItem(statut);
				
				// Remplissage du combobox Patient (Colonne 4)
				String idPatientTable = unTableau.getValueAt(numLigne, 4).toString();
				for(int i=0; i < cbxPatients.getItemCount(); i++) {
					String item = cbxPatients.getItemAt(i);
					if(item != null && item.startsWith(idPatientTable + " -")) {
						cbxPatients.setSelectedIndex(i);
						break;
					}
				}
				
				btModifier.setEnabled(true);
				btSupprimer.setEnabled(true);
			}
		});
			
		// --- Compteur ---
		this.lbNbRdv.setBounds(500, 400, 400, 20);
		this.lbNbRdv.setText("Nombre de rendez-vous : " + unTableau.getRowCount());
		this.add(this.lbNbRdv);
		
		this.setVisible(true);
	}
	
	// --- Méthode ajoutée pour gérer la saisie prédictive ---
	public void rafraichirCbxPatients(String filtre) {
		cbxPatients.removeAllItems();
		// On suppose que cette méthode existe dans ton ControllerPatient
		ArrayList<Patient> lesPatients = ControllerPatient.selectAllPatientsFiltre(filtre);
		for (Patient unP : lesPatients) {
			// TRÈS IMPORTANT : On ajoute l'ID au début pour pouvoir le récupérer plus tard
			cbxPatients.addItem(unP.getIdPatient() + " - " + unP.getNom() + " " + unP.getPrenom());
		}
	}
	
	public Object [][] obtenirDonnees(String filtre){
		ArrayList<Rdv> lesRdv = ControllerRdv.selectAllRdv(filtre);
		Object matrice [][] = new Object[lesRdv.size()] [6];
		int i = 0;
		for (Rdv unRdv : lesRdv) {
			matrice[i][0] = unRdv.getIdRdv();
			matrice[i][1] = unRdv.getMotif();
			matrice[i][2] = unRdv.getOrigine();
			matrice[i][3] = unRdv.getStatut();
			matrice[i][4] = unRdv.getFkIdPatient();
			matrice[i][5] = unRdv.getFkIdCreneau(); 
			i++;
		}
		return matrice;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btAnnuler){
			this.viderChamps();
		}
		else if (e.getSource() == this.btValider) {
			this.traitement();
		}
		else if (e.getSource() == this.btFiltrer) {
			String filtre = this.txtFiltre.getText();
			this.unTableau.setDonnees(this.obtenirDonnees(filtre));
		}
		else if(e.getSource() == this.btModifier) {
			this.updateRdv();
		}
		else if(e.getSource() == this.btSupprimer) {
			this.deleteRdv();
		}
	}
	
	public void updateRdv() {
		int numLigne = tableRdv.getSelectedRow();
		int idRdv = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
		
		String motif = this.txtMotif.getText();
		String statut = this.cbxStatut.getSelectedItem().toString();
		
		// Récupération de l'id patient depuis le combobox
		String chainePatient = this.cbxPatients.getSelectedItem().toString();
		String tab[] = chainePatient.split(" - ");
		int idPatient = Integer.parseInt(tab[0]);
		
		if (motif.equals("")){
			JOptionPane.showMessageDialog(this, "Veuillez remplir le motif.");
		} else {
			// On suppose que tu récupères l'origine et l'idCreneau ailleurs (ou tu mets des valeurs par défaut)
			Rdv unRdv = new Rdv(idRdv, idPatient, 1, java.time.LocalDateTime.now(), motif, statut, "Cabinet");
			
			ControllerRdv.updateRdv(unRdv);
			JOptionPane.showMessageDialog(this, "Le RDV a été modifié avec succès !");
			
			this.unTableau.setDonnees(this.obtenirDonnees(""));
			this.viderChamps();
		}
	}
	
	public void deleteRdv() {
		int numLigne = tableRdv.getSelectedRow();
		int idRdv = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
		
		int retour = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer ce rendez-vous ?", "Suppression", JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			ControllerRdv.deleteRdv(idRdv);
			JOptionPane.showMessageDialog(this, "Le rendez-vous a été supprimé avec succès.");
			
			this.unTableau.setDonnees(this.obtenirDonnees(""));
			this.lbNbRdv.setText("Nombre de rendez-vous : " + unTableau.getRowCount());
			this.viderChamps();
		}
	}
	
	public void viderChamps() {
		this.txtMotif.setText("");
		if(this.cbxStatut.getItemCount() > 0) {
			this.cbxStatut.setSelectedIndex(0); // Remet sur "À confirmer"
		}
		if(this.cbxPatients.getItemCount() > 0) {
			this.cbxPatients.setSelectedIndex(0);
		}
		this.btModifier.setEnabled(false);
		this.btSupprimer.setEnabled(false);
	}
	
	public void traitement() {
		String motif = this.txtMotif.getText();
		String statut = this.cbxStatut.getSelectedItem().toString();
		
		// Récupération de l'id patient depuis le combobox
		String chainePatient = this.cbxPatients.getSelectedItem().toString();
		String tab[] = chainePatient.split(" - ");
		int idPatient = Integer.parseInt(tab[0]);
		
		if (motif.equals("")){
			JOptionPane.showMessageDialog(this, "Veuillez remplir le motif.");
		} else {
			// Création d'un nouveau RDV sans ID
			Rdv unRdv = new Rdv(0, idPatient, 1, java.time.LocalDateTime.now(), motif, statut, "Cabinet");
			
			ControllerRdv.insertRdv(unRdv);
			
			JOptionPane.showMessageDialog(this, "RDV inséré avec succès.");
			
			this.viderChamps();
			this.unTableau.setDonnees(this.obtenirDonnees(""));
			this.lbNbRdv.setText("Nombre de rendez-vous : " + unTableau.getRowCount());
		}
	}

	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource() == txtFiltre) {
				String filtre = this.txtFiltre.getText();
				this.unTableau.setDonnees(this.obtenirDonnees(filtre));
			} else {
				traitement();
			}
		}
	}

	@Override public void keyReleased(KeyEvent e) {}
}

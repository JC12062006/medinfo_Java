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
import controller.User;

public class VueGestionRdv extends JFrame implements ActionListener, KeyListener {
	
	private User unUser;
		
	private JPanel panelForm = new JPanel();

	private JTextField txtMotif = new JTextField();
	
	// J'ajoute les champs pour le filtre qui manquaient dans tes déclarations
	private JTextField txtFiltre = new JTextField(); 
	private JButton btFiltrer = new JButton("Filtrer");
	
	// Les comboboxs 
	private JComboBox<String> cbxPatients = new JComboBox<String>();
	private JComboBox<String> cbxStatut;
	
	private JButton btAnnuler = new JButton("Annuler");
	private JButton btModifier = new JButton("Modifier");
	private JButton btSupprimer = new JButton("Supprimer");
	private JButton btRetour = new JButton("Retour à l'accueil");
	
	private JTable tableRdv;
	private JScrollPane scrollRdv;
	private Tableau unTableau;
	
	private JLabel lbNbRdv = new JLabel();
	
	private ArrayList<Patient> tousLesPatients;
	
	public VueGestionRdv(User unUser) {
		
		this.unUser = unUser;
		
		this.setTitle("Gestion des Rendez-vous");
	    this.setBounds(200, 100, 1000, 500); // Taille et position de la fenêtre
	 // Mettre la fenêtre en plein écran
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setLayout(null);
	    this.getContentPane().setBackground(Color.decode("#4D61F4"));
	    
	    this.btRetour.setBounds(50, 20, 200, 40);		
		// Remplir la Combobox avec les status 
		String[] lesStatuts = {"Sélectionner un statut", "À Confirmer", "Confirmé", "Annulé", "Honoré"};
		cbxStatut = new JComboBox<>(lesStatuts);
		
		// --- Panel Formulaire ---
		this.panelForm.setBounds(50, 120, 500, 500);
		this.panelForm.setBackground(Color.decode("#4D61F4"));
		this.panelForm.setLayout(new GridLayout(7, 2, 20, 40));
		
		this.panelForm.add(new JLabel("Patient (Saisie) : "));
		this.panelForm.add(this.cbxPatients);
		
		this.panelForm.add(new JLabel("Statut : "));
		this.panelForm.add(this.cbxStatut);
		
		this.panelForm.add(new JLabel("Motif : "));
		this.panelForm.add(this.txtMotif);
		
		this.panelForm.add(this.btAnnuler);
		this.panelForm.add(this.btModifier);
		this.panelForm.add(this.btSupprimer);
		
		this.btModifier.setEnabled(false);
		this.btSupprimer.setEnabled(false);
		
		this.add(btRetour);
		this.add(this.panelForm);
		
		this.tousLesPatients = ControllerPatient.selectAllPatientsFiltre("");
		
		// --- Saisie Prédictive Patient ---
		cbxPatients.setEditable(true);
		JTextField editor = (JTextField) cbxPatients.getEditor().getEditorComponent();
		
		editor.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		    	
		        // On ignore les flèches de direction et la touche Entrée
		        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN 
		            || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_LEFT 
		            || e.getKeyCode() == KeyEvent.VK_RIGHT) {
		            return;
		        }
		        
		        // On sauvegarde le texte et la position du curseur
		        String texteSaisi = editor.getText();
		        int positionCurseur = editor.getCaretPosition(); 
		        
		        // On met à jour la liste
		        rafraichirCbxPatients(texteSaisi);
		        
		        // On restaure le texte et la position du curseur pour ne pas gêner la frappe
		        editor.setText(texteSaisi);
		        editor.setCaretPosition(positionCurseur);
		        
		        if (cbxPatients.getItemCount() > 0) {
		            cbxPatients.showPopup();
		        }
		    }
		});
		
		// Remplissage initial de la combobox
			
		// On force le texte affiché par défaut (astuce possible car la combobox est Editable)
		cbxPatients.setSelectedItem("Sélectionner un patient");
				
		// --- Listeners ---
		this.btAnnuler.addActionListener(this);
		this.btModifier.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btRetour.addActionListener(this); 
		this.txtMotif.addKeyListener(this);
		
		// --- Barre de recherche (Filtre) ---
		this.txtFiltre.setBounds(700, 80, 400, 40);
		this.btFiltrer.setBounds(1120, 80, 150, 40);
		this.btFiltrer.addActionListener(this); 
		this.add(this.txtFiltre);
		this.add(this.btFiltrer);
		
		// --- JTable ---
		String entetes [] = {"ID Rdv", "Motif", "Origine", "Statut", "Patient", "Créneau"};
		
		this.unTableau = new Tableau(obtenirDonnees(""), entetes);
		this.tableRdv = new JTable(this.unTableau);
		
		this.scrollRdv = new JScrollPane(this.tableRdv);
		this.scrollRdv.setBackground(Color.decode("#4D61F4"));
		this.scrollRdv.setBounds(700, 150, 800, 500);
		this.add(this.scrollRdv);
		this.tableRdv.setRowHeight(30);
		
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

			    // Remplissage du statut (Colonne 3)
			    String statutTable = unTableau.getValueAt(numLigne, 3).toString();
			    // Petite conversion car la BDD dit "a_confirmer" et l'interface "À Confirmer"
			    if (statutTable.equals("a_confirmer") || statutTable.equals("À Confirmer")) {
			        cbxStatut.setSelectedItem("À Confirmer");
			    } else {
			        // La première lettre en majuscule pour que ça matche (ex: "confirmé" -> "Confirmé")
			        statutTable = statutTable.substring(0, 1).toUpperCase() + statutTable.substring(1).toLowerCase();
			        cbxStatut.setSelectedItem(statutTable);
			    }
			    
			    // Remplissage du combobox Patient (Colonne 4)
			    String patientTable = unTableau.getValueAt(numLigne, 4).toString();
			    cbxPatients.setSelectedItem(patientTable);
			    
			    btModifier.setEnabled(true);
			    btSupprimer.setEnabled(true);
			}
		});
			
		// --- Compteur ---
		this.lbNbRdv.setBounds(700, 670, 400, 30);
		this.lbNbRdv.setForeground(Color.WHITE); // Pour qu'il soit lisible sur le fond bleu
		this.lbNbRdv.setText("Nombre de rendez-vous : " + unTableau.getRowCount());
		this.add(this.lbNbRdv);
		
		this.setVisible(true);
	}
	
	// --- Méthode ajoutée pour gérer la saisie prédictive ---
	public void rafraichirCbxPatients(String filtre) {
	    cbxPatients.removeAllItems();
	    
	    // On met le filtre en minuscules pour que la recherche ne soit pas sensible à la casse
	    String filtreLower = filtre.toLowerCase();
	    
	    // On filtre la liste chargée en mémoire (plus besoin d'interroger la BDD à chaque touche !)
	    for (Patient unP : this.tousLesPatients) {
	        String affichage = unP.getIdPatient() + " - " + unP.getNom() + " " + unP.getPrenom();
	        
	        // Si le filtre est vide, ou si le nom/prénom contient le texte tapé
	        if (filtre.isEmpty() || affichage.toLowerCase().contains(filtreLower)) {
	            cbxPatients.addItem(affichage);
	        }
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
            // On concatène l'ID et le nom pour coller au format de ta combobox !
			matrice[i][4] = unRdv.getNomCompletPatient();
            // On affiche la belle date
			matrice[i][5] = unRdv.getDateCreneauFormatee(); 
			i++;
		}
		return matrice;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btAnnuler){
			this.viderChamps();
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
		else if(e.getSource() == this.btRetour) {
			this.dispose();
			new VueGenerale(this.unUser);
		}
	}
	
	public void updateRdv() {
	    int numLigne = tableRdv.getSelectedRow();
	    int idRdv = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
	    
	    String motif = this.txtMotif.getText();
	    String statut = this.cbxStatut.getSelectedItem().toString();
	    String chainePatient = this.cbxPatients.getSelectedItem().toString();
	    
	    // --- VÉRIFICATIONS DE SÉCURITÉ ---
	    if (motif.equals("")){
	        JOptionPane.showMessageDialog(this, "Veuillez remplir le motif.");
	        return; // On arrête la méthode ici
	    } 
	    if (statut.equals("Sélectionner un statut")) {
	        JOptionPane.showMessageDialog(this, "Veuillez choisir un statut valide.");
	        return;
	    }
	    if (chainePatient.equals("Sélectionner un patient") || !chainePatient.contains(" - ")) {
	        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un patient dans la liste.");
	        return;
	    }
	    
	    // Si tout est bon, on continue...
	    String tab[] = chainePatient.split(" - ");
	    int idPatient = Integer.parseInt(tab[0]);
	    
	    Rdv unRdv = new Rdv(idRdv, idPatient, 1, java.time.LocalDateTime.now(), motif, statut, "Cabinet", "", "");	    
	    
	    ControllerRdv.updateRdv(unRdv);
	    JOptionPane.showMessageDialog(this, "Le RDV a été modifié avec succès !");
	    
	    this.unTableau.setDonnees(this.obtenirDonnees(""));
	    this.viderChamps();
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
	        // L'index 0 correspond maintenant à "-- Sélectionner un statut --"
	        this.cbxStatut.setSelectedIndex(0); 
	    }
	    
	    if(this.cbxPatients.getItemCount() > 0) {
	        // On remet le texte personnalisé pour les patients
	        this.cbxPatients.setSelectedItem("-- Sélectionner un patient --");
	    }
	    
	    this.btModifier.setEnabled(false);
	    this.btSupprimer.setEnabled(false);
	}

	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource() == txtFiltre) {
				String filtre = this.txtFiltre.getText();
				this.unTableau.setDonnees(this.obtenirDonnees(filtre));
			} else {
				updateRdv();
			}
		}
	}

	@Override public void keyReleased(KeyEvent e) {}
}
package controller;

import javax.swing.table.AbstractTableModel;


public class Tableau extends AbstractTableModel{
	
	private Object [][] donnees;
	private String entetes[];
	
	public Tableau(Object[][] donnees, String[] entetes) {
		super();
		this.donnees = donnees;
		this.entetes = entetes;
	}
	
	@Override
	public int getRowCount() {
	
		return this.donnees.length;
	}

	@Override
	public int getColumnCount() {
		
		return this.entetes.length; //nombre de colonnes
	}

	@Override
	public Object getValueAt(int i, int j) {
		
		return this.donnees[i][j];// retourne une element à la posiotion (i,j)
	}

	@Override
	public String getColumnName(int j) {
		
		return this.entetes[j]; //retourne le nom de la coonne
	}
	
	public void setDonnees(Object[][]matrice) {
		this.donnees = matrice;
		//on actualise l'affichage des données
		this.fireTableDataChanged();
	}
}

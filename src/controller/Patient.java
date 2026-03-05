package controller;

public class Patient extends User{

	
	private int idPatient;
    private int fkIdUtilisateur;

    private String adresse;
    private String numSecu;
    private String sexe;
	
    //Constructeur pour créer un patient 
	public Patient(int idUtilisateur, String nom, String prenom, String email, String telephone, String hashPassword,
			String dateNaissance, String role, String adresse, String numSecu, String sexe) {
		super(idUtilisateur, nom, prenom, email, telephone, hashPassword, dateNaissance, role);
		
		this.adresse = adresse;
		this.numSecu = numSecu;
		this.sexe = sexe;
		this.fkIdUtilisateur = idUtilisateur;
	}
	
	//Constructeur pour les patients combobox
	public Patient(String nom, String prenom, int idPatient){
		
		super(nom, prenom);
		
		this.idPatient = idPatient;
		this.fkIdUtilisateur = 0;
		this.adresse = "";
		this.numSecu = "";
		this.sexe = "";
	}

	public int getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(int idPatient) {
		this.idPatient = idPatient;
	}

	public int getFkIdUtilisateur() {
		return fkIdUtilisateur;
	}

	public void setFkIdUtilisateur(int fkIdUtilisateur) {
		this.fkIdUtilisateur = fkIdUtilisateur;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNumSecu() {
		return numSecu;
	}

	public void setNumSecu(String numSecu) {
		this.numSecu = numSecu;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	
}

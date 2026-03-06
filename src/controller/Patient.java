package controller;

public class Patient {

    private int idPatient;
    private String adresse;
    private String numSecu;
    private String sexe;
    private int fkIdUtilisateur;

    public Patient(String adresse, String numSecu, String sexe, int fkIdUtilisateur) {
        this.idPatient = 0;
        this.adresse = adresse;
        this.numSecu = numSecu;
        this.sexe = sexe;
        this.fkIdUtilisateur = fkIdUtilisateur;
    }

    public String getAdresse() { return adresse; }
    public String getNumSecu() { return numSecu; }
    public String getSexe() { return sexe; }
    public int getFkIdUtilisateur() { return fkIdUtilisateur; }
}

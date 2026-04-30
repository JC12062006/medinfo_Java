package controller;

public class Medecin {

    private int idMedecin;
    private String rpps;
    private int estConventionne;
    private String formations;
    private String languesParlees;
    private String experiences;
    private String description;
    private int fkIdUtilisateur;
    private int fkIdSpecialite;

    // Champs utilisateur (nécessaires pour l'affichage)
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String nomSpecialite;

    // -----------------------------
    // Constructeur utilisé à l'insertion
    // -----------------------------
    public Medecin(String rpps, int estConventionne, String formations,
                   String languesParlees, String experiences, String description,
                   int fkIdUtilisateur, int fkIdSpecialite) {

        this.idMedecin = 0;
        this.rpps = rpps;
        this.estConventionne = estConventionne;
        this.formations = formations;
        this.languesParlees = languesParlees;
        this.experiences = experiences;
        this.description = description;
        this.fkIdUtilisateur = fkIdUtilisateur;
        this.fkIdSpecialite = fkIdSpecialite;
    }

    // -----------------------------
    // Constructeur complet (pour SELECT)
    // -----------------------------
    public Medecin(int idMedecin, String rpps, int estConventionne, String formations,
                   String languesParlees, String experiences, String description,
                   int fkIdUtilisateur, int fkIdSpecialite,
                   String nom, String prenom, String email, String telephone) {

        this.idMedecin = idMedecin;
        this.rpps = rpps;
        this.estConventionne = estConventionne;
        this.formations = formations;
        this.languesParlees = languesParlees;
        this.experiences = experiences;
        this.description = description;
        this.fkIdUtilisateur = fkIdUtilisateur;
        this.fkIdSpecialite = fkIdSpecialite;

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // -----------------------------
    // Getters
    // -----------------------------
    public int getIdMedecin() { return idMedecin; }
    public String getRpps() { return rpps; }
    public int getEstConventionne() { return estConventionne; }
    public String getFormations() { return formations; }
    public String getLanguesParlees() { return languesParlees; }
    public String getExperiences() { return experiences; }
    public String getDescription() { return description; }
    public int getFkIdUtilisateur() { return fkIdUtilisateur; }
    public int getFkIdSpecialite() { return fkIdSpecialite; }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public String getNomSpecialite() { return nomSpecialite; }

    // -----------------------------
    // Setters
    // -----------------------------
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setNomSpecialite(String nomSpecialite) { this.nomSpecialite = nomSpecialite; }
}

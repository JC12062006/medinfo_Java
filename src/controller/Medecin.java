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

    public Medecin(int idMedecin, String rpps, int estConventionne, String formations,
                   String languesParlees, String experiences, String description,
                   int fkIdUtilisateur, int fkIdSpecialite) {

        this.idMedecin = idMedecin;
        this.rpps = rpps;
        this.estConventionne = estConventionne;
        this.formations = formations;
        this.languesParlees = languesParlees;
        this.experiences = experiences;
        this.description = description;
        this.fkIdUtilisateur = fkIdUtilisateur;
        this.fkIdSpecialite = fkIdSpecialite;
    }

    public Medecin(String rpps, int estConventionne, String formations,
                   String languesParlees, String experiences, String description,
                   int fkIdUtilisateur, int fkIdSpecialite) {

        this(0, rpps, estConventionne, formations, languesParlees, experiences,
                description, fkIdUtilisateur, fkIdSpecialite);
    }

    public int getFkIdUtilisateur() { return fkIdUtilisateur; }
    public int getFkIdSpecialite() { return fkIdSpecialite; }
    public String getRpps() { return rpps; }
    public int getEstConventionne() { return estConventionne; }
    public String getFormations() { return formations; }
    public String getLanguesParlees() { return languesParlees; }
    public String getExperiences() { return experiences; }
    public String getDescription() { return description; }
}

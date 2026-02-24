package controller;

public class User {

    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String hashPassword;
    private String dateNaissance;
    private String role;

    /**
     * Constructeur complet (utilisé lors de la connexion)
     */
    public User(int idUtilisateur, String nom, String prenom, String email,
                String telephone, String hashPassword, String dateNaissance, String role) {

        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.hashPassword = hashPassword;
        this.dateNaissance = dateNaissance;
        this.role = role;
    }

    /**
     * Constructeur pour l'inscription (rôle = Secretaire)
     */
    public User(String nom, String prenom, String email, String telephone,
                String hashPassword, String dateNaissance) {

        this.idUtilisateur = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.hashPassword = hashPassword;
        this.dateNaissance = dateNaissance;
        this.role = "Secretaire";
    }

    // ------------------ GETTERS ------------------

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getRole() {
        return role;
    }

    // ------------------ SETTERS ------------------

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

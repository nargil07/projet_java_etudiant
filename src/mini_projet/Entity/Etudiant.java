/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_projet.Entity;

/**
 *
 * @author antony
 */
public class Etudiant {
    private String identifiant;
    private String nom;
    private String prenom;
    private String groupe;

    public Etudiant() {
    }

    public Etudiant(String identifiant, String nom, String prenom, String groupe) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.groupe = groupe;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }
    
    
}

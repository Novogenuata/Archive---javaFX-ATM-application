package com.Classes;

public class Client {
    //Attributs
    private int codeClient;
    private boolean bloque;

    public boolean isBloque(){
        return bloque;
    }
    public void setBloque(boolean bloque){
        this.bloque = bloque;
    }

    private String nom;
    private String prenom;
    private String telephone;
    private String courriel;
    private int numeroNIP;


    Cheque cheque;
    Hypothecaire hypothecaire;
    Marge marge;
    Epargne epargne;

    //Constructeur
    public Client(int codeClient, String nom, String prenom, String telephone, String courriel, int numeroNIP) {
        this.codeClient = codeClient;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.courriel = courriel;
        this.numeroNIP = numeroNIP;
        this.cheque = new Cheque(numeroNIP, codeClient, 100, 1000);
    }

    //Accesseurs
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNumeroNIP() {
        return numeroNIP;
    }

    //Méthode d'affichage
    @Override
    public String toString() {
        return "Nom: " + nom + " Prénom: " + prenom + " Numéro NIP: " + numeroNIP;
    }

    public int getCodeClient() {
        return codeClient;
    }





    public boolean hasCheque() {
        return cheque != null;
    }

    public boolean hasHypothecaire() {
        return hypothecaire != null;
    }

    public boolean hasEpargne() {
        return epargne != null;
    }

    public boolean hasMarge() {
        return marge != null;
    }




    public Cheque getCheque() {
        return cheque;
    }
    public Hypothecaire getHypothecaire() {
        return hypothecaire;
    }
    public Epargne getEpargne() {
        return epargne;
    }
    public Marge getMarge() {
        return marge;
    }



    public void creerHypothecaire() {
        if (!hasHypothecaire()) {
            hypothecaire = new Hypothecaire(numeroNIP, codeClient, 0, 1000);
        }
    }
    public void creerEpargne() {
        if (!hasEpargne()) {
            epargne = new Epargne(numeroNIP, codeClient, 0, 1000);
        }
    }
    public void creerMarge() {
        if (!hasMarge()) {
            marge = new Marge(numeroNIP, codeClient, 0, 1000);
        }
    }
}

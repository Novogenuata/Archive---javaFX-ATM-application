package com.Classes;

public class Epargne extends Compte {
    public static final double tauxInteret = 0.01;

    //Constructeur
    public Epargne(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        super(numeroNIP, numeroCompte, soldeCompte, montantTransfertMaximum);
    }

    // Méthode de paiement d'intérêt
    public void paiementInteret() throws Exception {
        double interet = getSoldeCompte() * tauxInteret;
        retrait(0);
    }


    @Override
    public String getCompteType() {
        return "Epargne";
    }

    //Méthode d'affichage
    @Override
    public String toString() {
        return "Epargne{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}

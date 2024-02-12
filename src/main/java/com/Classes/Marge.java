package com.Classes;

public class Marge extends Compte {
    private static final double tauxInteret = 0.05;


    public Marge(int numeroNIP, int numeroCompte, double soldeCompte, double montantTransfertMaximum) {
        super(numeroNIP, numeroCompte, soldeCompte, montantTransfertMaximum);
    }

    public void augmenterSoldeMarge(double montantAugmentation) {
        double interet = soldeCompte * tauxInteret;
        soldeCompte += interet;

    }

    //Méthode pour retirer un montant x d'un compte


    //Méthode pour déposer un montant x dans un compte
    @Override
    public void depot(double montant) {
        System.out.println("here");
        soldeCompte -= montant;
        System.out.println(soldeCompte);
    }

    @Override
    public String getCompteType() {
        return "Marge";
    }

    @Override
    public String toString() {
        return "Marge{" +
                "numeroNIP=" + numeroNIP +
                ", numeroCompte=" + numeroCompte +
                ", soldeCompte=" + soldeCompte +
                '}';
    }
}

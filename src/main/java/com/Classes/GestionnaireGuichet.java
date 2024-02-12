package com.Classes;

import java.util.ArrayList;
import java.util.Random;

public class


GestionnaireGuichet {
    //Attributs
    private static Compte banque;
    ArrayList<Client> clients;
    static ArrayList<Cheque> comptesCheque;
    private static ArrayList<Epargne> comptesEpargne;
    private static ArrayList<Marge> comptesMarge;
    private ArrayList<Hypothecaire> comptesHypothecaire;
    private ArrayList<Transaction> transactions;
    public double soldeCompteCourant;


    private Random random = new Random();


    public GestionnaireGuichet() {
        clients = new ArrayList<Client>();
        comptesCheque = new ArrayList<Cheque>();
        comptesEpargne = new ArrayList<Epargne>();
        comptesHypothecaire = new ArrayList<Hypothecaire>();
        comptesMarge = new ArrayList<Marge>();
        transactions = new ArrayList<Transaction>();
        soldeCompteCourant = 0.0;
    }


    public Client validerUtilisateur(String nom, int nip) {
        Client clt = null;
        for (Client client : clients) {
            if (client.getNom().equals(nom) && client.getNumeroNIP() == nip) {
                clt = client;
            }
        }
        return clt;
    }

    public static double RetraitCheque(int nip, double montant) throws Exception {
        double nouveauSolde = 0;
        for (Cheque compte : comptesCheque) {
            if (nip == compte.getNumeroNIP()) {
                if (montant <= compte.soldeCompte) {
                    compte.retrait(0);
                    nouveauSolde += compte.getSoldeCompte();
                    banque.retrait(0);

                } else {
                    Marge marge = AvoirUneMarge(nip);
                    if (marge != null) {
                        double diff = montant - compte.soldeCompte;
                        if (diff <= marge.soldeCompte) {
                            marge.retrait(0);
                            compte.retrait(0);
                            banque.retrait(0);
                            nouveauSolde = 0;
                        }

                    } else
                        throw new Exception("Vous n'avez pas un solde suffisant pour cette transaction.");

                }

            }
        }
        return nouveauSolde;
    }

    public static double RetraitEpargne(int nip, double montant) throws Exception {
        double nouveauSolde = 0;
        for (Epargne compte : comptesEpargne) {
            if (nip == compte.getNumeroNIP()) {
                compte.retrait(0);
                nouveauSolde += compte.getSoldeCompte();
                banque.retrait(0);
            }
        }
        return nouveauSolde;
    }

    public double DepotCheque(int nip, double montant) throws Exception {
        double nouveauSolde = 0;
        for (Cheque compte : comptesCheque) {
            if (nip == compte.getNumeroNIP()) {
                compte.depot(montant);
                nouveauSolde += compte.getSoldeCompte();
            }
        }
        return nouveauSolde;
    }

    public double DepotEpargne(int nip, double montant) throws Exception {
        double nouveauSolde = 0;
        for (Epargne compte : comptesEpargne) {
            if (nip == compte.getNumeroNIP()) {
                compte.depot(montant);
                nouveauSolde += compte.getSoldeCompte();
            }
        }
        return nouveauSolde;
    }

    public boolean PaiementFacture(int nip, double montant) throws Exception {
        boolean reussi = false;
        for (Cheque compte : comptesCheque) {
            if (nip == compte.getNumeroNIP()) {
                compte.paiementFacture(montant);
                reussi = true;
            }
        }
        return reussi;
    }

    public boolean transfertFonds(int nip, double montant, String typeCompte) throws Exception {
        boolean reussi = false;
        Cheque compteSource = null;
        Compte compteDestinataire = null;
        for (Cheque compte : comptesCheque) {
            if (nip == compte.getNumeroNIP()) {
                compteSource = compte;
                break;
            }
        }

        if (typeCompte.equals("Epargne")) {
            for (Epargne compte : comptesEpargne) {
                if (nip == compte.getNumeroNIP()) {
                    compteDestinataire = (Epargne) compte;
                    break;
                }
            }
        } else if (typeCompte.equals("Hypothecaire")) {
            for (Hypothecaire compte : comptesHypothecaire) {
                if (nip == compte.getNumeroNIP()) {
                    compteDestinataire = (Hypothecaire) compte;
                    break;
                }
            }
        } else if (typeCompte.equals("Marge")) {
            for (Marge compte : comptesMarge) {
                if (nip == compte.getNumeroNIP()) {
                    compteDestinataire = (Marge) compte;
                    break;
                }
            }
        }

        if (compteSource != null && compteDestinataire != null) {
            compteSource.retrait(0);
            compteDestinataire.depot(montant);
            reussi = true;
        }

        return reussi;
    }

    public static Marge AvoirUneMarge(int nip) {

        for (Marge marge : comptesMarge)
            if (nip == marge.numeroNIP)
                return marge;
        return null;
    }

    public double AfficheSoldeCompte() {
        return soldeCompteCourant;
    }


    public Client creerClient(int codeClient, String nom, String prenom, String telephone, String courriel, int numeroNIP) {
        Client nouveauClient = new Client(codeClient, nom, prenom, telephone, courriel, numeroNIP);
        clients.add(nouveauClient);
        return nouveauClient;
    }

    private int genererNIP() {
        return 1000 + random.nextInt(9000);
    }

    public Compte creerCompte(String typeCompte, int numeroNIP, int numeroCompte, double soldeCompte, double montantTranfertMax) {
        Compte unCompte = null;
        if (typeCompte.equals("Cheque")) {
            Cheque compteCheque = new Cheque(numeroNIP, numeroCompte, soldeCompte, montantTranfertMax);
            comptesCheque.add(compteCheque);
            unCompte = compteCheque;
        } else if (typeCompte.equals("Epargne")) {
            Epargne compteEpargne = new Epargne(numeroNIP, numeroCompte, soldeCompte, montantTranfertMax);
            comptesEpargne.add(compteEpargne);
            unCompte = compteEpargne;
        } else if (typeCompte.equals("Hypothecaire")) {
            Hypothecaire compteHypothecaire = new Hypothecaire(numeroNIP, numeroCompte, soldeCompte, montantTranfertMax);
            comptesHypothecaire.add(compteHypothecaire);
            unCompte = compteHypothecaire;
        } else if (typeCompte.equals("Marge")) {
            Marge compteMarge = new Marge(numeroNIP, numeroCompte, soldeCompte, montantTranfertMax);
            comptesMarge.add(compteMarge);
            unCompte = compteMarge;
        }
        return unCompte;
    }

    @Override
    public String toString() {
        return "GestionnaireGuichet{" +
                "banque=" + banque +
                ", clients=" + clients +
                ", comptesCheque=" + comptesCheque +
                ", comptesEpargne=" + comptesEpargne +
                ", comptesMarge=" + comptesMarge +
                ", comptesHypothecaire=" + comptesHypothecaire +
                ", transactions=" + transactions +
                ", soldeCompteCourant=" + soldeCompteCourant +
                ", random=" + random +
                '}';
    }
}

package com;

import com.Classes.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.Classes.Epargne.tauxInteret;


public class AdminController{


    int currentAvailableMoney = 20000;
    int maxAvailableMoney = 20000;
    public static int currentTransaction = 0;



    @FXML
    private ToggleGroup Accounts2;
    @FXML
    private Button augmentmarge;
    @FXML
    private RadioButton RadDepot;

    @FXML
    private RadioButton RadRetraits;

    @FXML
    private RadioButton RadTransactions;

    @FXML
    private ToggleGroup TransactionType;


    @FXML
    private RadioButton EpargneToggle;

    @FXML
    private RadioButton HypoToggle;
    @FXML
    private Button listezComptes;

    @FXML
    private RadioButton MargeToggle;



    @FXML
    private Label soldeLabel;

    @FXML
    private TextField nomField;

    @FXML
    private void fermerGuichet() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de fermeture");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir fermer le guichet ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void seDeconnecter() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) nomField.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Compte compte;
    @FXML
    private TextField montantPrelevementField;

    @FXML
    private TextField montantTransactionField;

    @FXML
    private TextField montantAjoutField;

    @FXML
    private TextField codeClientTransactionField;

    private Banque banque;

    private Marge marge;

    @FXML
    private TextField prenomField;
    @FXML
    private TextField montantRetraitInteretField;

    @FXML
    private TextField montantAugmentationField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField courrielField;

    @FXML
    private TextField nipField;
    @FXML
    private TextField nipField1;

    @FXML
    private ListView<String> comptesListView;

    @FXML
    private ComboBox<Compte> comptesComboBox;


    private List<Compte> comptes = new ArrayList<>();


    private int numeroNIP;
    @FXML
    private ListView<String> clientsListView;

    @FXML
    private ChoiceBox<String> typeTransactionChoiceBox;



    private static boolean isInitialized = false;

    @FXML
    private TextField codeClientField;

    @FXML
    private ChoiceBox<String> typeCompteChoiceBox;

    @FXML
    private void initialize() {

        System.out.println(isInitialized);

        if (!isInitialized) {
            isInitialized = true;
        }
        try {
            updateClientsListView();
        }
        catch (Exception e){}
    }

    public static Map<Integer, Integer> getNips() {
        return Data.nips;
    }
    public static Map<Integer, Client> getiClients() {
        return Data.iClients;
    }

    @FXML
    private void creerClient() {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                telephoneField.getText().isEmpty() || courrielField.getText().isEmpty() ||
                nipField1.getText().isEmpty()) {

            showAlert("Erreur", "Veuillez remplir toutes les informations du client.", Alert.AlertType.ERROR);
            return; // Sortir de la méthode car les informations sont incomplètes
        }
        int codeClient = generateCodeClient();

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephone = telephoneField.getText();
        String courriel = courrielField.getText();
        String nip = nipField1.getText();

        int numeroNIP = Integer.parseInt(nip);

        Client nouveauClient = new Client(codeClient, nom, prenom, telephone, courriel, numeroNIP);
        Data.clients.add(nouveauClient);
        updateClientsListView();

        Data.nips.put(codeClient, numeroNIP);
        Data.iClients.put(codeClient, nouveauClient);

        System.out.println("Client créé : " + nouveauClient);
        System.out.println("NIP créé : " + codeClient);
        System.out.println("Client Code créé : " + numeroNIP);


        showAlert("Confirmation", "Client créé : " + nouveauClient, Alert.AlertType.INFORMATION);



    }
    private void updateClientsListView() {
        ObservableList<String> clientNames = FXCollections.observableArrayList();
        for (Client client : Data.clients) {
            clientNames.add("Code Client : " + client.getCodeClient() + " - Nom : " + client.getNom());
        }
        clientsListView.setItems(clientNames);
    }



    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void augmenterSoldeMarge() {
        double taux = 0.05;

        try {
            for (Client client : Data.clients) {
                if (client.hasMarge()) {
                    double total = client.getMarge().getSoldeCompte() * taux;
                    client.getMarge().depot(-total);
                }
            }
        }
        catch(Exception e) {}
    }


    @FXML
    private void creerCompte() {
        /*
        if (codeClientField.getText().isEmpty() || typeCompteChoiceBox.getValue() == null) {
            showAlert("Erreur", "Veuillez sélectionner un type de compte et entrer un code client.", Alert.AlertType.ERROR);
            return;
        }

        int codeClient = Integer.parseInt(codeClientField.getText());
        String typeCompte = typeCompteChoiceBox.getValue();

        Compte nouveauCompte = createCompte(typeCompte, codeClient);

        System.out.println("Compte créé : " + nouveauCompte);

        showAlert("Confirmation", "Compte créé : " + nouveauCompte, Alert.AlertType.INFORMATION);

         */
    }
    private Compte createCompte(String typeCompte, int codeClient) {
        Random random = new Random();
        int numeroCompte = 100000 + random.nextInt(900000); // Exemple, remplacez par votre logique

        double soldeCompte = 100 + random.nextInt(90000);
        Compte nouveauCompte;

        if ("Epargne".equals(typeCompte)) {
            nouveauCompte = new Epargne(numeroNIP, numeroCompte, 500, 5000);
        } else if ("Cheque".equals(typeCompte)) {
            nouveauCompte = new Cheque(numeroNIP, numeroCompte, 600, 1000);
        } else {
            return null;
        }

        comptes.add(nouveauCompte);

        updateComptesListView();

        return nouveauCompte;
    }
    private void updateComptesListView() {

    }

    @FXML
    public void ajouterArgent() {
        double montant = 0;
        try {
            montant = Double.parseDouble(montantAjoutField.getText());
            compte.depot(montant);
            System.out.println("Argent ajouté avec succès.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un montant valide.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Confirmation", "Argent ajouté avec succès : " + montant + "$ ", Alert.AlertType.CONFIRMATION);
        }
    }

    @FXML
    public void paiementInteret() {

        double taux = 0.01;
        System.out.println(Data.clients.size());

        for (Client client : Data.clients) {
            try {
                if (client.hasEpargne()) {
                    double montant = client.getEpargne().getSoldeCompte() * taux;
                    System.out.println(montant);

                    Epargne e = client.getEpargne();
                    e.depot(montant);
                }
            }catch(Exception e){}
        }
    }


    @FXML
    private void preleverMontant() {

        try {
            int nip = Integer.parseInt(codeClientField.getText());
            double montant = Double.parseDouble(montantPrelevementField.getText());

            System.out.println(nip);

            if (Data.iClients.get(nip) == null) {
                showAlert("Erreur","Le compte n'existe pas.", Alert.AlertType.ERROR);
                return;
            }

            if (!Data.iClients.get(nip).hasHypothecaire()) {
                showAlert("Erreur", "Le compte n'a aucun compte hypothecaire", Alert.AlertType.ERROR);
                return;
            }

            if (montant > Data.iClients.get(nip).getHypothecaire().getSoldeCompte() && Data.iClients.get(nip).hasMarge()) {
                double marge = montant - Data.iClients.get(nip).getHypothecaire().getSoldeCompte();

                Hypothecaire h = Data.iClients.get(nip).getHypothecaire();
                Marge m = Data.iClients.get(nip).getMarge();

                try {
                    h.retrait(h.getSoldeCompte());
                }
                catch(Exception e){}
                m.depot(-marge);
            }
            else if (Data.iClients.get(nip).getHypothecaire().getSoldeCompte() <= montant) {
                Data.iClients.get(nip).getHypothecaire().retrait(montant);
            }
            else {
                showAlert("Erreur", "Le compte n'a pas assez de fonds ni un compte marge pour effectuer le transaction", Alert.AlertType.ERROR);
            }
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    private void showConfirmation(String title, String content) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(title);
        confirmation.setHeaderText(null);
        confirmation.setContentText(content);
        confirmation.showAndWait();
    }

    @FXML
    private void updateSoldeLabel() {
        if (banque != null) {
            soldeLabel.setText("Solde: " + banque.getSoldeCompte());
        }
    }


    private int generateCodeClient() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }



    private int generateNumeroNIP() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
    @FXML
    private void bloquerClient() {
        String codeClientText = nipField.getText();

        if (codeClientText.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer le code client pour bloquer.", Alert.AlertType.ERROR);
            return;
        }

        int codeClient = Integer.parseInt(codeClientText);

        Client client = Data.iClients.get(codeClient);

        try {
        client.setBloque(true);
        showAlert("Confirmation", "Client bloqué avec succès.", Alert.AlertType.INFORMATION);
        } catch(Exception e) {
            showAlert("Erreur", "Client non trouvé.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void debloquerClient() {
        String codeClientText = nipField.getText();

        if (codeClientText.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer le code client pour débloquer.", Alert.AlertType.ERROR);
            return;
        }

        int codeClient = Integer.parseInt(codeClientText);

        Client client = Data.iClients.get(codeClient);

        try {
            client.setBloque(false);

            showAlert("Confirmation", "Client débloqué avec succès.", Alert.AlertType.INFORMATION);
        } catch(Exception e) {
            showAlert("Erreur", "Client non trouvé.", Alert.AlertType.ERROR);
        }
    }
    private Optional<Client> trouverClientParCode(int numeroNIP) {
        List<Client> listeClients = null ;

        for (Client client : listeClients) {
            if (client.getNumeroNIP() == numeroNIP) {
                return Optional.of(client);
            }
        }

        return Optional.empty();
    }

    @FXML
    private void transaction() {

        try {
            int nip = Integer.parseInt(codeClientTransactionField.getText());
            List<Transaction> transactions = Data.transactions.get(nip);

            String t = ((RadioButton)TransactionType.getSelectedToggle()).getText();

            ObservableList<String> transact = FXCollections.observableArrayList();

            if (t.contains("Depots")) {

                for (Transaction trans : transactions) {
                    if (trans.getTransactionType() == "Depot") {

                        transact.add("Depot - Montant: " + trans.getMontant() + " | Du compte: " + trans.getCompteSource().getCompteType());
                    }
                }

            }
            else if (t.contains("Retraits")) {

                for (Transaction trans : transactions) {
                    if (trans.getTransactionType() == "Retrait") {

                        transact.add("Retrait - Montant: " + trans.getMontant() + " | Du compte: " + trans.getCompteSource().getCompteType());
                    }
                }
            }
            else if (t.contains("Transferts")) {

                for (Transaction trans : transactions) {
                    if (trans.getTransactionType() == "Transfert") {

                        transact.add("Tranfsert - Montant: " + trans.getMontant() + " | Du compte: " + trans.getCompteSource().getCompteType() + " A: " + trans.getCompteDestination().getCompteType());
                    }
                }
            }

            comptesListView.setItems(transact);

        } catch (Exception e) {}
    }



    private int generateTransactionNumber() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

    }


    private void showSuccessMessage(String content) {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Succès");
        success.setHeaderText(null);
        success.setContentText(content);
        success.showAndWait();
    }


    @FXML
    private void reinitialiserInformations() {
        nomField.clear();
        prenomField.clear();
        telephoneField.clear();
        courrielField.clear();
        nipField.clear();
        codeClientField.clear();
        typeCompteChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void selectionnerCompte(ActionEvent event) {
        RadioButton button = (RadioButton) Accounts2.getSelectedToggle();
        String accountType = button.getText();

        int codeClient = 0;
        try {
            codeClient = Integer.parseInt(codeClientField.getText());
        } catch (Exception e) {
            showAlert("Erreur", "Le code client n'est pas un nombre!!", Alert.AlertType.ERROR);
        }

        if (Data.iClients.get(codeClient) != null) {
            Client client = Data.iClients.get(codeClient);

            if (accountType.contains("Epargne")) {
                client.creerEpargne();
            } else if (accountType.contains("Hypothecaire")) {
                client.creerHypothecaire();
            } else if (accountType.contains("Marge")) {
                client.creerMarge();
            }
        }
        else {
            showAlert("Erreur", "Le code client n'existe pas!!!!", Alert.AlertType.ERROR);
        }


    }




    public static void effectuerTransaction(int nip, Transaction transaction) {
        if (Data.transactions.get(nip) == null) {
            Data.transactions.put(nip, new ArrayList<>());
        }
        System.out.println(transaction.toString());
        Data.transactions.get(nip).add(transaction);
    }

    @FXML
    void ListComptes(ActionEvent event) {
        updateClientsListView();
    }

}

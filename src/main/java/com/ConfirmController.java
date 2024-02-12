package com;

import com.Classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ConfirmController {

    @FXML
    private RadioButton ChequeDest;

    @FXML
    private RadioButton ChequeStart;

    @FXML
    private RadioButton EpargneDest;

    @FXML
    private RadioButton EpargneStart;

    @FXML
    private RadioButton HypoDest;

    @FXML
    private TextField TransactionText;

    @FXML
    private ToggleGroup TransactionsEnd;

    @FXML
    private ToggleGroup TransationsStart;

    @FXML
    private Button closebutton;

    @FXML
    void FaireTransaction(ActionEvent event) {

        try {

            String start = ((RadioButton) TransationsStart.getSelectedToggle()).getText();
            String end = ((RadioButton) TransactionsEnd.getSelectedToggle()).getText();

            Client client = AdminController.getiClients().get(Data.currentNIP);
            double montant = Double.parseDouble(TransactionText.getText());

            if (end.contains("Hypothecaire") && !client.hasHypothecaire()) {
                return;
            }
            if (end.contains("Marge") && !client.hasMarge()) {
                return;
            }
            if (end.contains("Epargne") && !client.hasEpargne()) {
                return;
            }

            if (start.contains("Cheque")) {
                if (end.contains("Hypothecaire")) {
                    Hypothecaire h = client.getHypothecaire();
                    if (montant <= client.getCheque().getSoldeCompte()) {
                        client.getCheque().retrait(montant);
                        h.depot(montant);

                        Transaction transaction = new Transaction(
                                0, montant, client.getCheque(), h, "Transfert"
                        );
                        AdminController.effectuerTransaction(Data.currentNIP, transaction);
                    }
                }

                if (end.contains("Marge")) {
                    Marge h = client.getMarge();
                    if (montant <= client.getCheque().getSoldeCompte()) {
                        client.getCheque().retrait(montant);
                        h.depot(montant);

                        Transaction transaction = new Transaction(
                                0, montant, client.getCheque(), h, "Transfert"
                        );
                        AdminController.effectuerTransaction(Data.currentNIP, transaction);
                    }
                }

                if (end.contains("Epargne")) {
                    Epargne h = client.getEpargne();
                    if (montant <= client.getCheque().getSoldeCompte()) {
                        client.getCheque().retrait(montant);
                        h.depot(montant);

                        Transaction transaction = new Transaction(
                                0, montant, client.getCheque(), h, "Transfert"
                        );
                        AdminController.effectuerTransaction(Data.currentNIP, transaction);
                    }
                }
            }

            if (start.contains("Epargne")) {
                if (end.contains("Hypothecaire")) {
                    Hypothecaire h = client.getHypothecaire();
                    if (montant <= client.getEpargne().getSoldeCompte()) {
                        client.getEpargne().retrait(montant);
                        h.depot(montant);

                        Transaction transaction = new Transaction(
                                0, montant, client.getEpargne(), h, "Transfert"
                        );
                        AdminController.effectuerTransaction(Data.currentNIP, transaction);
                    }
                }

                if (end.contains("Marge")) {
                    Marge h = client.getMarge();
                    if (montant <= client.getEpargne().getSoldeCompte()) {
                        client.getEpargne().retrait(montant);
                        h.depot(montant);

                        Transaction transaction = new Transaction(
                                0, montant, client.getEpargne(), h, "Transfert"
                        );
                        AdminController.effectuerTransaction(Data.currentNIP, transaction);
                    }
                }

                if (end.contains("Cheque")) {
                    Cheque h = client.getCheque();
                    if (montant <= client.getEpargne().getSoldeCompte()) {
                        client.getEpargne().retrait(montant);
                        h.depot(montant);

                        Transaction transaction = new Transaction(
                                0, montant, client.getEpargne(), h, "Transfert"
                        );
                        AdminController.effectuerTransaction(Data.currentNIP, transaction);
                    }
                }
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void closeTransactions(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            closebutton.getScene().getWindow().hide();
        }
        catch(Exception e){}
    }

}

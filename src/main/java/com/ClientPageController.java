package com;

import com.Classes.Cheque;
import com.Classes.Client;
import com.Classes.Epargne;
import com.Classes.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class ClientPageController {


    String transactionType = "depot";
    String account = "Cheque";
    int nip;
    @FXML
    private Button transfer;
    @FXML
    private Label AccountLabel;

    @FXML
    private ToggleGroup Accounts;

    @FXML
    private Label BalanceLabel;
    @FXML
    private Button Transfert;
    @FXML
    private Label CodeLabel;

    @FXML
    private RadioButton Depot;

    @FXML
    private Button Disconnect;

    @FXML
    private TextField MoneyBox;

    @FXML
    private Label Namelabel;

    @FXML
    private RadioButton Paiement;

    @FXML
    private RadioButton RadioChque;

    @FXML
    private RadioButton RadioEpargne;

    @FXML
    private RadioButton RadioHypothecaire;

    @FXML
    private RadioButton RadioMarge;

    @FXML
    private RadioButton Retrait;

    @FXML
    private Button SubmitMoney;

    @FXML
    private ToggleGroup Transactions;

    @FXML
    private RadioButton Transactionz;

    @FXML
    private Button exitMoney;

    @FXML
    private Button money0;

    @FXML
    private Button money1;

    @FXML
    private Button money2;

    @FXML
    private Button money3;

    @FXML
    private Button money4;

    @FXML
    private Button money5;

    @FXML
    private Button money6;

    @FXML
    private Button money7;

    @FXML
    private Button money8;

    @FXML
    private Button money9;

    @FXML
    private Button moneyDot;

    private Client current;

    @FXML
    private void initialize() {
        int c = LoginController.getUserCode();
        nip = c;

        Data.currentNIP = c;

        current = AdminController.getiClients().get(c);
        Namelabel.setText(AdminController.getiClients().get(c).getNom());
        CodeLabel.setText(""+c);
        BalanceLabel.setText(""+AdminController.getiClients().get(c).getCheque().getSoldeCompte());

        if (AdminController.getiClients().get(c).isBloque()) {
            showAlert("Erreur", "vous êtes bloqué. Vous pouvez consulter votre compte mais vous ne pouvez effectuer aucune transaction.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void Click0(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "0");
    }
    @FXML
    private void Click1(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "1");
    }

    @FXML
    private void Click2(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "2");
    }

    @FXML
    private void Click3(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "3");
    }

    @FXML
    private void Click4(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "4");
    }

    @FXML
    private void Click5(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "5");
    }

    @FXML
   private void Click6(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "6");
    }

    @FXML
    private void Click7(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "7");
    }

    @FXML
    private void Click8(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "8");
    }

    @FXML
    private void Click9(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + "9");
    }

    @FXML
    private void ClickDot(ActionEvent event) {
        String currentText = MoneyBox.getText();
        MoneyBox.setText(currentText + ".");
    }

    @FXML
    private void SeDeconnecter(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Load the login screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) MoneyBox.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void annulerTransaction(ActionEvent event) {
    MoneyBox.clear();
    }

    @FXML
    private void changPaiement(ActionEvent event) {

    }

    @FXML
    private void changeCompte(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) Accounts.getSelectedToggle();

        if (selectedRadioButton != null) {
            String accountType = selectedRadioButton.getText();
            AccountLabel.setText(accountType);
            System.out.println(current.hasCheque());

            if (current.hasMarge() == false) {
                BalanceLabel.setText("N/A");
            }
            if (current.hasHypothecaire() == false) {
                BalanceLabel.setText("N/A");
            }
            if (current.hasEpargne() == false) {
                BalanceLabel.setText("N/A");
            }



            if (accountType.contains("Cheque") && current.hasCheque()) {
                BalanceLabel.setText(""+current.getCheque().getSoldeCompte());
            }
            if (accountType.contains("Hypothecaire") && current.hasHypothecaire()) {
                System.out.println("H");
                BalanceLabel.setText(""+current.getHypothecaire().getSoldeCompte());
            }
            if (accountType.contains("Epargne") && current.hasEpargne()) {
                System.out.println("E");
                BalanceLabel.setText(""+current.getEpargne().getSoldeCompte());
            }
            if (accountType.contains("Marge") && current.hasMarge()) {
                System.out.println("M");
                BalanceLabel.setText(""+current.getMarge().getSoldeCompte());
            }

            account = accountType;
        }
    }
    private String getAccountNameByType(String accountType) {
        switch (accountType) {
            case "Cheque":
                return "Cheque Account";
            case "Epargne":
                return "Epargne Account";
            case "Hypothecaire":
                return "Hypothecaire Account";
            default:
                return "";
        }
    }
    @FXML
    void openTransferPage(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Confirm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Namelabel.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void changeDepot(ActionEvent event) {
        transactionType = "depot";
    }

    @FXML
    private void changeEpargne(ActionEvent event) {

    }

    @FXML
    private void changeHypo(ActionEvent event) {

    }

    @FXML
    private void changeMarge(ActionEvent event) {

    }

    @FXML
    private void changeRetrait(ActionEvent event) {
        transactionType = "retrait";
    }

    @FXML
    private void soumettreArgent(ActionEvent event) throws Exception {

        if (AdminController.getiClients().get(nip).isBloque() == true) return;
        double arg = Double.parseDouble(MoneyBox.getText());

        if (transactionType == "depot") {
            if (account.contains("Cheque")) {
                AdminController.getiClients().get(nip).getCheque().depot(arg);
                BalanceLabel.setText(""+AdminController.getiClients().get(nip).getCheque().getSoldeCompte());

                AdminController.effectuerTransaction(nip, new Transaction(AdminController.currentTransaction, arg,
                        AdminController.getiClients().get(nip).getCheque(),
                        AdminController.getiClients().get(nip).getCheque(), "Depot"));
            }
            if (account.contains("Epargne") && AdminController.getiClients().get(nip).hasEpargne()) {
                AdminController.getiClients().get(nip).getEpargne().depot(arg);
                BalanceLabel.setText(""+AdminController.getiClients().get(nip).getEpargne().getSoldeCompte());

                AdminController.effectuerTransaction(nip, new Transaction(AdminController.currentTransaction, arg,
                        AdminController.getiClients().get(nip).getEpargne(),
                        AdminController.getiClients().get(nip).getEpargne(), "Depot"));
            }
            if (account.contains("Hypothecaire") && AdminController.getiClients().get(nip).hasHypothecaire()) {
                AdminController.getiClients().get(nip).getHypothecaire().depot(arg);
                BalanceLabel.setText(""+AdminController.getiClients().get(nip).getHypothecaire().getSoldeCompte());

                AdminController.effectuerTransaction(nip, new Transaction(AdminController.currentTransaction, arg,
                        AdminController.getiClients().get(nip).getHypothecaire(),
                        AdminController.getiClients().get(nip).getHypothecaire(), "Depot"));
            }

        } else if (transactionType == "retrait") {

            if (arg % 10 != 0 || arg > 1000) return;

            if (account.contains("Cheque")) {
                if (arg > AdminController.getiClients().get(nip).getCheque().getSoldeCompte()) return;
                Cheque c = AdminController.getiClients().get(nip).getCheque();
                c.retrait(arg);

                BalanceLabel.setText(""+AdminController.getiClients().get(nip).getCheque().getSoldeCompte());

                AdminController.effectuerTransaction(nip, new Transaction(AdminController.currentTransaction, arg,
                        c,
                        c, "Retrait"));
            }
            if (account.contains("Epargne") && AdminController.getiClients().get(nip).hasEpargne()) {

                if (arg > AdminController.getiClients().get(nip).getEpargne().getSoldeCompte()) return;
                Epargne c = AdminController.getiClients().get(nip).getEpargne();
                c.retrait(arg);

                AdminController.effectuerTransaction(nip, new Transaction(AdminController.currentTransaction, arg,
                        c,
                        c, "Retrait"));


                BalanceLabel.setText(""+AdminController.getiClients().get(nip).getEpargne().getSoldeCompte());
            }
        }
    }

    @FXML
    void OpenTransfert(ActionEvent event) {

    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

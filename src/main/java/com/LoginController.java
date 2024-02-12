package com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;


public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label userTypeLabel;

    private static int usercode;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isAdmin(username, password)) {
            userTypeLabel.setText("Administrateur");
            openAdminPage();
        } else  {
            userTypeLabel.setText("Client");
            Map<Integer, Integer> nips = AdminController.getNips();
            int nip = 0;
            try {
                nip = Integer.parseInt(username);

                if (nips.get(nip) != null)  {
                    if (nips.get(nip) == Integer.parseInt(password)) {
                        usercode = nip;
                        openClientPage();
                    }
                }
            }
            catch(Exception e) {}
        }
    }

    public static int getUserCode() {
        return usercode;
    }

    private boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }

    private boolean isClient(String username, String password) {
        return "client".equals(username) && "client".equals(password);
    }

    @FXML
    private void openAdminPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            loginButton.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openClientPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            loginButton.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

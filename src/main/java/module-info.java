module com.example.tp3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com to javafx.fxml;
    exports com;

}
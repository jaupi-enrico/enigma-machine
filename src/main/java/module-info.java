module com.enigma.enigmamachine {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.enigma.enigmamachine to javafx.fxml;
    exports com.enigma.enigmamachine;
}
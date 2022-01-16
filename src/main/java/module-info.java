module com.example.snakesandladdersgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.snakesandladdersgame to javafx.fxml;
    exports com.example.snakesandladdersgame;
}
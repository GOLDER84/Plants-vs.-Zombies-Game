module org.example.pvsz_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires java.sql;


    opens org.example.pvsz_game to javafx.fxml;
    exports org.example.pvsz_game;
}
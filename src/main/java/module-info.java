module org.example.photo_wizard {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.swing;

    opens org.example.photo_wizard to javafx.fxml;
    exports org.example.photo_wizard;
}
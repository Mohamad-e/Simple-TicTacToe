module myApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.desktop;

    opens myapp to javafx.fxml;
    exports myapp;
}

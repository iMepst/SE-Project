module com.plupper.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.plupper.gui to javafx.fxml;
    exports com.plupper.gui;
}
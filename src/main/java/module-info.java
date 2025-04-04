module com.mycompany.primerproyectobatallanaval {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.primerproyectobatallanaval to javafx.fxml;
    exports com.mycompany.primerproyectobatallanaval;
}

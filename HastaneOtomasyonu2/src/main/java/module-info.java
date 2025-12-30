module org.t3tracon.hastaneotomasyonu2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires org.kordamp.bootstrapfx.core;

    opens org.t3tracon.hastaneotomasyonu2 to javafx.fxml;
    opens org.t3tracon.hastaneotomasyonu2.controller to javafx.fxml;
    
    exports org.t3tracon.hastaneotomasyonu2;
    exports org.t3tracon.hastaneotomasyonu2.controller;
    exports org.t3tracon.hastaneotomasyonu2.model;
    exports org.t3tracon.hastaneotomasyonu2.util;
}
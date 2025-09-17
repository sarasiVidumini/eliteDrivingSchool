module lk.ijse.orm_final_coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires lombok;

    // Hibernate & JavaFX reflection access
    opens lk.ijse.orm_final_coursework.entity to org.hibernate.orm.core, javafx.base;
    opens lk.ijse.orm_final_coursework.dto.tm to javafx.base;

    // FXML controllers
    opens lk.ijse.orm_final_coursework.controller to javafx.fxml;

    // Exports
    exports lk.ijse.orm_final_coursework;
    exports lk.ijse.orm_final_coursework.controller;
    exports lk.ijse.orm_final_coursework.dto;
    exports lk.ijse.orm_final_coursework.dto.tm;
    exports lk.ijse.orm_final_coursework.entity;
}

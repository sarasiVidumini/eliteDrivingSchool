module lk.ijse.orm_final_coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;

    opens lk.ijse.orm_final_coursework.controller to javafx.fxml;
    opens lk.ijse.orm_final_coursework.dto.tm to javafx.base;

    exports lk.ijse.orm_final_coursework;
}
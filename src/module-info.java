module MytodoMaster {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    exports ToDo;
    opens ToDo to javafx.fxml;
    exports ToDo.controllers;
    opens ToDo.controllers to javafx.fxml;
    exports ToDo.models;
    opens ToDo.models to javafx.fxml;
}
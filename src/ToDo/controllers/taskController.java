package ToDo.controllers;

import ToDo.DB;
import ToDo.models.task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;

import java.util.Objects;
import java.util.ResourceBundle;

public class taskController implements Initializable {




    @FXML
    private ToggleButton gotasks;
    @FXML
    private Button goBack;
    @FXML
    private Button updateBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button delBtn;

    @FXML
    private DatePicker datePick;

    @FXML
    private TableColumn<task, LocalDate> dateCol;
    @FXML
    private TableColumn<task, String> descriptionCol;
    @FXML
    private TableColumn<task, String> detailCol;
    @FXML
    private TableColumn<task, Integer> idCol;
    @FXML
    private TableColumn<task, Boolean> impCol;
    @FXML
    private TextField descrip;
    @FXML
    private TextField details;
    @FXML
    private CheckBox impocheck;
    @FXML
    private TableView<task> taskTb = new TableView<>();
    ObservableList<task> tasks = FXCollections.observableArrayList();

    Connection cnx;

    public void connect() {
        DB d = new DB();
        cnx = d.connectDB();
    }

    @FXML
    public void gotasks(ActionEvent event) throws IOException {
        Stage stage = (Stage) gotasks.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ToDo/views/mytasks.fxml")));
        stage1.setTitle("My Tasks");
        stage1.setScene(new Scene(root));
        stage.centerOnScreen();
        stage1.show();
    }
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ToDo/views/home.fxml")));
        stage1.setTitle("Home Page");
        stage1.setScene(new Scene(root));
        stage.centerOnScreen();
        stage1.show();
    }


    public void addTask(ActionEvent event) {
        connect();
        String description = descrip.getText();
        String detail = details.getText();
        boolean imp = impocheck.isSelected();
        LocalDate d = datePick.getValue();
        String req = "insert into task(date,description,details,important) values(?,?,?,?)";
        try {
            PreparedStatement p = cnx.prepareStatement(req);
            p.setDate(1, java.sql.Date.valueOf(d));
            p.setString(2, description);
            p.setString(3, detail);
            p.setBoolean(4, imp);
            p.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Task Added", "Task has been added successfully.");
            clear();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add task: " + e.getMessage());
        }
        showAllTasks();
    }

    public void showAllTasks() {
        connect();
        try {
            String req = "Select * from task ";
            PreparedStatement p = cnx.prepareStatement(req);
            ResultSet r = p.executeQuery();

            tasks.clear();
            while (r.next()) {
                task t = new task();
                t.setId(r.getInt("id"));
                t.setDesc(r.getString("description"));
                t.setDet(r.getString("details"));
                t.setDate(r.getDate("date").toLocalDate());
                t.setImportant(r.getBoolean("important"));
                tasks.add(t);
            }
            taskTb.setItems(tasks);
            idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descProperty());
            detailCol.setCellValueFactory(cellData -> cellData.getValue().detProperty());
            dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            impCol.setCellValueFactory(cellData -> cellData.getValue().importantProperty().asObject());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        taskTb.setRowFactory(tv -> {
            TableRow<task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    int i = taskTb.getSelectionModel().getSelectedIndex();
                    descrip.setText(taskTb.getItems().get(i).getDesc());
                    details.setText(taskTb.getItems().get(i).getDet());
                    datePick.setValue(taskTb.getItems().get(i).getDate());
                    impocheck.setSelected(taskTb.getItems().get(i).isImportant());
                }
            });
            return row;
        });
    }
    public void update(ActionEvent event){
        int i=taskTb.getSelectionModel().getSelectedIndex();
        String description = descrip.getText();
        String detail = details.getText();
        boolean imp = impocheck.isSelected();
        LocalDate d = datePick.getValue();
        int id=taskTb.getItems().get(i).getId();
        try{
            String req ="update task set date=? ,description=?,details=?,important=? where id=?";
            PreparedStatement p=cnx.prepareStatement(req);
            p.setString(2,description);
            p.setString(3,detail);
            p.setDate(1, Date.valueOf(d));
            p.setBoolean(4,imp);
            p.setInt(5,id);
            p.executeUpdate();
            showAllTasks();
            showAlert(Alert.AlertType.INFORMATION, "Task Updated", "Task has been updated successfully.");
            clear();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update task: " + e.getMessage());
        }



    }
public void delete(ActionEvent event){
        int i=taskTb.getSelectionModel().getSelectedIndex();
        int id=taskTb.getItems().get(i).getId();
        try{
            String req="delete from task where id =?";
            PreparedStatement p =cnx.prepareStatement(req);
            p.setInt(1,id);
            p.executeUpdate();
            showAllTasks();

            showAlert(Alert.AlertType.INFORMATION, "Task Deleted", "Task has been deleted successfully.");
            clear();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete task: " + e.getMessage());
        }
}

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void clear() {
        descrip.clear();
        details.clear();
        datePick.setValue(null);
        impocheck.setSelected(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect();
        Platform.runLater(this::showAllTasks);
       }
}

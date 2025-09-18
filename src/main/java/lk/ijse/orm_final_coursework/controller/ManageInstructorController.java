package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_final_coursework.dto.InstructorDTO;
import lk.ijse.orm_final_coursework.dto.tm.InstructorTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageInstructorController implements Initializable {

    private final InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBo(BOTypes.INSTRUCTOR);

    public TableView<InstructorTM> tblInstructors;
    public TableColumn<InstructorTM , String> colInstructorId;
    public TableColumn<InstructorTM , String> colFirstName;
    public TableColumn<InstructorTM , String> colLastName;
    public TableColumn<InstructorTM , String> colEmail;
    public TableColumn<InstructorTM , String> colPhone;
    public TableColumn<InstructorTM , String> colSpecialization;
    public TableColumn<InstructorTM , String> colAvailabilityShedule;

    public TextField txtSearch;
    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public TextField txtAvailabilityShedule;
    public ComboBox cmbSpecialization;
    public TextField txtPhone;
    public TextField txtEmail;
    public TextField txtLastName;
    public TextField txtFirstName;
    public AnchorPane ancInstructorPage;
    public Label lblInstructorId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        try {
            loadAllInstructors();
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Initialization error: " + e.getMessage());
        }
    }

    private void setCellValueFactories() {
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colAvailabilityShedule.setCellValueFactory(new PropertyValueFactory<>("availability_schedule"));

        try {
            cmbSpecialization.setItems(FXCollections.observableArrayList("Light Vehicle" , "Motorcycle" , "Heavy Vehicle" , "Defensive Driving" , "Special Needs Training" , "Commercial License Training" , "Theory Instructor"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Failed to load status : " + e.getMessage());
        }
    }

    private void loadAllInstructors() throws Exception {
        tblInstructors.setItems(FXCollections.observableArrayList(
                instructorBO.getAll().stream().map(dto -> new InstructorTM(
                        dto.getInstructorId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getEmail(),
                        dto.getPhone(),
                        dto.getSpecialization(),
                        dto.getAvailability_schedule()
                )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = instructorBO.getNextId();
        lblInstructorId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            boolean isSaved = instructorBO.save(InstructorDTO.builder()
                            .instructorId(lblInstructorId.getText())
                            .firstName(txtFirstName.getText())
                            .lastName(txtLastName.getText())
                            .email(txtEmail.getText())
                            .phone(txtPhone.getText())
                            .specialization(cmbSpecialization.getValue().toString())
                            .availability_schedule(txtAvailabilityShedule.getText())
                            .build());
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Instructor saved successfully!");
                loadAllInstructors();
                resetForm();
                loadNextId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to save instructor!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error saving instructor: " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {

            boolean isUpdated = instructorBO.update(InstructorDTO.builder()
                    .instructorId(lblInstructorId.getText())
                    .firstName(txtFirstName.getText())
                    .lastName(txtLastName.getText())
                    .email(txtEmail.getText())
                    .phone(txtPhone.getText())
                    .specialization(cmbSpecialization.getValue().toString())
                    .availability_schedule(txtAvailabilityShedule.getText())
                    .build());
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Instructor updated successfully!");
                loadAllInstructors();
                resetForm();
                loadNextId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update instructor!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error updating instructor: " + e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblInstructorId.getText();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select an instructor to delete!");
            return;
        }

        try {
            boolean isDeleted = instructorBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!");
                loadAllInstructors();
                resetForm();
                loadNextId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete instructor!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error deleting instructor: " + e.getMessage());
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
        try {
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error generating ID: " + e.getMessage());
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        InstructorTM selected = tblInstructors.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblInstructorId.setText(selected.getInstructorId());
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtEmail.setText(selected.getEmail());
            txtPhone.setText(selected.getPhone());
            cmbSpecialization.setValue(selected.getSpecialization());
            txtAvailabilityShedule.setText(selected.getAvailability_schedule());
        }
    }

    public void goToDashboard(MouseEvent mouseEvent)throws IOException {
        try {
            Stage stage = (Stage) ancInstructorPage.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"))));
            stage.centerOnScreen();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation error: " + e.getMessage());
        }
    }

    private void resetForm() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPhone.clear();
        cmbSpecialization.getSelectionModel().clearSelection();
        txtAvailabilityShedule.clear();
        tblInstructors.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()
                || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty()
                || cmbSpecialization.getSelectionModel().isEmpty() || txtAvailabilityShedule.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields!");
            return false;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", txtEmail.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid email format!");
            return false;
        }

        if (!Pattern.matches("^(0\\d{9})$", txtPhone.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid phone number! Must be 10 digits starting with 0.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }
}

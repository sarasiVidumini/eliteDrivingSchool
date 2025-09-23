package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_final_coursework.dto.InstructorDTO;
import lk.ijse.orm_final_coursework.dto.tm.InstructorTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public ComboBox<String> cmbSpecialization;
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

        cmbSpecialization.setItems(FXCollections.observableArrayList(
                "Light Vehicle" , "Motorcycle" , "Heavy Vehicle" ,
                "Defensive Driving" , "Special Needs Training" ,
                "Commercial License Training" , "Theory Instructor"
        ));
    }

    private void loadAllInstructors() throws Exception {
        List<InstructorDTO> instructors = instructorBO.getAll();
        tblInstructors.setItems(FXCollections.observableArrayList(
                instructors.stream().map(dto -> new InstructorTM(
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
        lblInstructorId.setText(instructorBO.getNextId());
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
                    .specialization(cmbSpecialization.getValue())
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
                    .specialization(cmbSpecialization.getValue())
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

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        navigateTo("/view/DashBoard.fxml");
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

    private void navigateTo(String path) {
        try {
            ancInstructorPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancInstructorPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInstructorPage.heightProperty());

            ancInstructorPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        try {
            List<InstructorDTO> instructorList;
            if (search.isEmpty()) {
                instructorList = instructorBO.getAll();
            } else {
                instructorList = instructorBO.search(search);
            }
            tblInstructors.setItems(FXCollections.observableArrayList(
                    instructorList.stream()
                            .map(dto -> new InstructorTM(
                                    dto.getInstructorId(),
                                    dto.getFirstName(),
                                    dto.getLastName(),
                                    dto.getEmail(),
                                    dto.getPhone(),
                                    dto.getSpecialization(),
                                    dto.getAvailability_schedule()
                            )).toList()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to search! : " + e.getMessage());
        }
    }

    public void btnAssignCourseOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AssignCoursePopup.fxml"));
            AnchorPane pane = loader.load();

            ManageAssignCoursePopup controller = loader.getController();
            String instructorId = lblInstructorId.getText();
            if (instructorId == null || instructorId.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please select an instructor first!");
                return;
            }
            controller.setInstructorId(instructorId);

            Stage stage = new Stage();
            stage.setTitle("Assign Courses to Instructor");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(pane));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to open Assign Courses popup: " + e.getMessage());
        }
    }
}

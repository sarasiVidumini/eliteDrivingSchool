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
import javafx.stage.Stage;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.StudentBO;
import lk.ijse.orm_final_coursework.dto.InstructorDTO;
import lk.ijse.orm_final_coursework.dto.StudentDTO;
import lk.ijse.orm_final_coursework.dto.tm.PaymentTM;
import lk.ijse.orm_final_coursework.dto.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageStudentController implements Initializable {

    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBo(BOTypes.STUDENT);

    public AnchorPane ancStudentPage;
    public Label lblStudentId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtPhone;
    public TextField txtAddress;
    public TextField txtDateOfBirth;
    public TextField txtRegistrationDate;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public TextField txtSearch;

    public TableView<StudentTM> tblStudent;
    public TableColumn<StudentTM , String> colStudentId;
    public TableColumn<StudentTM , String> colFirstName;
    public TableColumn<StudentTM , String> colLastName;
    public TableColumn<StudentTM , String> colEmail;
    public TableColumn<StudentTM , String> colPhone;
    public TableColumn<StudentTM , String> colAddress;
    public TableColumn<StudentTM , Date> colDob;
    public TableColumn<StudentTM , Date> colRegistrationDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellFactories();
        try {
            loadAllStudents();
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Initialization Error :"+ e.getMessage());
        }

    }

    private void setCellFactories() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

    }

    private void loadAllStudents() throws Exception {
        tblStudent.setItems(FXCollections.observableArrayList(
                studentBO.getAll().stream().map(studentDTO -> new StudentTM(
                        studentDTO.getStudentId(),
                        studentDTO.getFirstName(),
                        studentDTO.getLastName(),
                        studentDTO.getEmail(),
                        studentDTO.getPhone(),
                        studentDTO.getAddress(),
                        studentDTO.getDob(),
                        studentDTO.getRegistrationDate()
                )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = studentBO.getNextId();
        lblStudentId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            boolean isSaved = studentBO.save(StudentDTO.builder()
                    .studentId(lblStudentId.getText())
                    .firstName(txtFirstName.getText())
                    .lastName(txtLastName.getText())
                    .email(txtEmail.getText())
                    .phone(txtPhone.getText())
                    .address(txtAddress.getText())
                    .dob(LocalDate.parse(txtDateOfBirth.getText()))
                    .registrationDate(LocalDate.parse(txtRegistrationDate.getText()))
                    .build());

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Student Saved Successfully");
                loadAllStudents();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR, "Failed to saving student");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error saving student : " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            boolean isUpdated = studentBO.update(StudentDTO.builder()
                    .studentId(lblStudentId.getText())
                    .firstName(txtFirstName.getText())
                    .lastName(txtLastName.getText())
                    .email(txtEmail.getText())
                    .phone(txtPhone.getText())
                    .address(txtAddress.getText())
                    .dob(LocalDate.parse(txtDateOfBirth.getText()))
                    .registrationDate(LocalDate.parse(txtRegistrationDate.getText()))
                    .build());

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Student Updated Successfully");
                loadAllStudents();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR, "Failed to update student");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error saving student : " + e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblStudentId.getText();
        if (id.isEmpty()){
            showAlert(Alert.AlertType.WARNING , "Please select student  to delete! ");
            return;
        }

        try {
            boolean isDeleted = studentBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Student Deleted Successfully");
                loadAllStudents();
                loadNextId();

            }else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete student");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR , "Error deleting student : " + e.getMessage());
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
        try{
            loadNextId();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR , "Error generating ID : " + e.getMessage());
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        StudentTM selected = tblStudent.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblStudentId.setText(selected.getStudentId());
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtEmail.setText(selected.getEmail());
            txtPhone.setText(selected.getPhone());
            txtAddress.setText(selected.getAddress());
            txtDateOfBirth.setText(selected.getDob().toString());
            txtRegistrationDate.setText(selected.getRegistrationDate().toString());
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        try {
            Stage stage = (Stage) ancStudentPage.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"))));
            stage.centerOnScreen();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR , "Navigation Error : " + e.getMessage());
        }
    }

    private void resetForm() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
        txtDateOfBirth.clear();
        txtRegistrationDate.clear();
        tblStudent.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty() || txtAddress.getText().isEmpty() || txtDateOfBirth.getText().isEmpty() || txtRegistrationDate.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Please fill out all fields");
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

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadAllStudents();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search!" + e.getMessage());
            }
        }else {
            try {
                ArrayList<StudentDTO> studentList = (ArrayList<StudentDTO>) studentBO.search(search);
                tblStudent.setItems(FXCollections.observableArrayList(
                        studentList.stream()
                                .map(studentDTO -> new StudentTM(
                                        studentDTO.getStudentId(),
                                        studentDTO.getFirstName(),
                                        studentDTO.getLastName(),
                                        studentDTO.getEmail(),
                                        studentDTO.getPhone(),
                                        studentDTO.getAddress(),
                                        studentDTO.getDob(),
                                        studentDTO.getRegistrationDate()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search!" + e.getMessage());
            }
        }
    }
}

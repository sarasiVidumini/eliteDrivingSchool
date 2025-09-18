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
import lk.ijse.orm_final_coursework.bo.custom.CourseBO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.dto.LessonsDTO;
import lk.ijse.orm_final_coursework.dto.PaymentDTO;
import lk.ijse.orm_final_coursework.dto.tm.CourseTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageCourseController implements Initializable {

    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBo(BOTypes.COURSE);

    public AnchorPane ancCoursePage;

    public Label lblCourseId;

    public ComboBox cmbCourseName;
    public TextField txtDuration;
    public TextField txtFee;
    public TextField txtDescription;
    public TextField txtInstructorId;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public TextField txtSearch;

    public TableView<CourseTM> tblCourses;
    public TableColumn<CourseTM , String> colCourseId;
    public TableColumn<CourseTM , String> colCourseName;
    public TableColumn<CourseTM , String> colDuration;
    public TableColumn<CourseTM , Double> colFee;
    public TableColumn<CourseTM , String> colDescription;
    public TableColumn<CourseTM , String> colInstructorId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        try {
            loadAllCourses();
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR , "Initialization Error : " + e.getMessage());
        }
    }

    private void setCellValueFactories() {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));

        try {
            cmbCourseName.setItems(FXCollections.observableArrayList("Manual Car Driving" , "Automatic Car Driving" , "Motorcycle / Scooter Driving" , "Three-Wheeler Driving" , "Heavy Vehicle Driving(Truck / Bus)"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Failed to load course : " + e.getMessage());
        }
    }

    private void loadAllCourses() throws Exception {
        tblCourses.setItems(FXCollections.observableArrayList(
                courseBO.getAll().stream().map(courseDTO -> new CourseTM(
                        courseDTO.getCourseId(),
                        courseDTO.getCourseName(),
                        courseDTO.getDuration(),
                        courseDTO.getFee(),
                        courseDTO.getDescription(),
                        courseDTO.getInstructorId()
                )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = courseBO.getNextId();
        lblCourseId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = courseBO.save(CourseDTO.builder()
                    .courseId(lblCourseId.getText())
                    .courseName(cmbCourseName.getValue().toString())
                    .duration(txtDuration.getText())
                    .fee(Double.parseDouble(txtFee.getText()))
                    .description(txtDescription.getText())
                    .instructorId(txtInstructorId.getText())
                    .build());

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION , " Course saving successfully!");
                loadAllCourses();
                resetForm();
                loadNextId();

            }else {
                showAlert(Alert.AlertType.ERROR , "Error saving course");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR , "Error saving course: " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = courseBO.update(CourseDTO.builder()
                    .courseId(lblCourseId.getText())
                    .courseName(cmbCourseName.getValue().toString())
                    .duration(txtDuration.getText())
                    .fee(Double.parseDouble(txtFee.getText()))
                    .description(txtDescription.getText())
                    .instructorId(txtInstructorId.getText())
                    .build());

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION , " Course updated successfully!");
                loadAllCourses();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR , "Error updating course");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR , "Error updating course: " + e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblCourseId.getText();
        if (id.isEmpty()){
            showAlert(Alert.AlertType.WARNING , "Please select a course to delete! ");
            return;
        }

        try {
            boolean isDeleted = courseBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION , " Course deleted successfully!");
                loadAllCourses();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR , "Error deleting course");
            }
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR , "Error deleting course: " + e.getMessage());
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
        try {
            loadNextId();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR , "Error generating ID: " + e.getMessage());
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        CourseTM selectedItem = (CourseTM) tblCourses.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblCourseId.setText(selectedItem.getCourseId());
            cmbCourseName.setValue(selectedItem.getCourseName());
            txtDuration.setText(selectedItem.getDuration());
            txtFee.setText(String.valueOf(selectedItem.getFee()));
            txtDescription.setText(selectedItem.getDescription());
            txtInstructorId.setText(selectedItem.getInstructorId());
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        try {
            Stage stage = (Stage) ancCoursePage.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"))));
            stage.centerOnScreen();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR , "Navigation Error : " + e.getMessage());
        }
    }

    private void resetForm() {
        cmbCourseName.getSelectionModel().clearSelection();
        txtDuration.clear();
        txtFee.clear();
        txtDescription.clear();
        tblCourses.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }


    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadAllCourses();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR , "Failed to search : " + e.getMessage());
            }
        }else {
            try {
                ArrayList<CourseDTO> courseList = (ArrayList<CourseDTO>) courseBO.search(search);
                tblCourses.setItems(FXCollections.observableArrayList(
                        courseList.stream()
                                .map(courseDTO -> new CourseTM(
                                        courseDTO.getCourseId(),
                                        courseDTO.getCourseName(),
                                        courseDTO.getDuration(),
                                        courseDTO.getFee(),
                                        courseDTO.getDescription(),
                                        courseDTO.getInstructorId()
                                )).toList()
                ));

            }catch (Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR , "Failed to search : " + e.getMessage());
            }
        }
    }
}

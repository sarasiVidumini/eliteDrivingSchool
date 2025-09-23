package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.CourseBO;
import lk.ijse.orm_final_coursework.bo.custom.StudentBO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.dto.StudentDTO;
import lk.ijse.orm_final_coursework.dto.tm.CourseTM;
import lk.ijse.orm_final_coursework.entity.Course;
import lk.ijse.orm_final_coursework.entity.Student;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageCourseController implements Initializable {

    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBo(BOTypes.COURSE);
    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBo(BOTypes.STUDENT);

    public AnchorPane ancCoursePage;

    public Label lblCourseId;

    @FXML
    private ComboBox<String> cmbCourseName;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtFee;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtInstructorId;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public TextField txtSearch;

    @FXML
    private TableView<CourseTM> tblCourses;
    @FXML
    private TableColumn<CourseTM , String> colCourseId;
    @FXML
    private TableColumn<CourseTM , String> colCourseName;
    @FXML
    private TableColumn<CourseTM , String> colDuration;
    @FXML
    private TableColumn<CourseTM , Double> colFee;
    @FXML
    private TableColumn<CourseTM , String> colDescription;
    @FXML
    private TableColumn<CourseTM , String> colInstructorId;
    @FXML
    private TableColumn<CourseTM , Integer> colEnrollmentCount;

    @FXML
    private ComboBox<String> cmbStudentIds;
    @FXML
    private ComboBox<String> cmbCourseIds;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        try {
            loadAllCourses();
            loadNextId();
            loadComboBoxes();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR , "Initialization Error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setCellValueFactories() {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colEnrollmentCount.setCellValueFactory(new PropertyValueFactory<>("enrollmentCount"));

        cmbCourseName.setItems(FXCollections.observableArrayList(
                "Manual Car Driving",
                "Automatic Car Driving",
                "Motorcycle / Scooter Driving",
                "Three-Wheeler Driving",
                "Heavy Vehicle Driving (Truck / Bus)"
        ));
    }

    private void loadAllCourses() {
        try {
            List<CourseDTO> courses = courseBO.getAll();
            List<CourseTM> tmList = new ArrayList<>();

            for (CourseDTO dto : courses) {
                tmList.add(new CourseTM(
                        dto.getCourseId(),
                        dto.getCourseName(),
                        dto.getDuration(),
                        dto.getFee(),
                        dto.getDescription(),
                        dto.getInstructorId(),
                        dto.getEnrollmentCount()
                ));
            }

            tblCourses.setItems(FXCollections.observableArrayList(tmList));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load courses: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadNextId() {
        try {
            lblCourseId.setText(courseBO.getNextId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate ID: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadComboBoxes() {
        try {
            cmbCourseIds.getItems().clear();
            cmbStudentIds.getItems().clear();

            // Load all course IDs safely
            List<String> courseIds = courseBO.getAllIds();
            cmbCourseIds.getItems().addAll(courseIds);

            // Load all student IDs
            List<String> studentIds = studentBO.getAllIds();
            cmbStudentIds.getItems().addAll(studentIds);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Failed to load ComboBoxes: " + e.getMessage());
        }
    }



    // ================= CRUD ====================

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        try {
            boolean saved = courseBO.save(CourseDTO.builder()
                    .courseId(lblCourseId.getText())
                    .courseName(cmbCourseName.getValue())
                    .duration(txtDuration.getText())
                    .fee(Double.parseDouble(txtFee.getText()))
                    .description(txtDescription.getText())
                    .instructorId(txtInstructorId.getText())
                    .build());
            if(saved) {
                showAlert(Alert.AlertType.INFORMATION, "Course saved successfully!");
                loadAllCourses();
                resetForm();
                loadNextId();
            } else showAlert(Alert.AlertType.ERROR, "Error saving course!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        try {
            boolean updated = courseBO.update(CourseDTO.builder()
                    .courseId(lblCourseId.getText())
                    .courseName(cmbCourseName.getValue())
                    .duration(txtDuration.getText())
                    .fee(Double.parseDouble(txtFee.getText()))
                    .description(txtDescription.getText())
                    .instructorId(txtInstructorId.getText())
                    .build());
            if(updated) {
                showAlert(Alert.AlertType.INFORMATION, "Course updated successfully!");
                loadAllCourses();
                resetForm();
                loadNextId();
            } else showAlert(Alert.AlertType.ERROR, "Error updating course!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        String id = lblCourseId.getText();
        if(id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Select a course!");
            return;
        }
        try {
            boolean deleted = courseBO.delete(id);
            if(deleted) {
                showAlert(Alert.AlertType.INFORMATION, "Course deleted successfully!");
                loadAllCourses();
                resetForm();
                loadNextId();
            } else showAlert(Alert.AlertType.ERROR, "Error deleting course!");
        } catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnResetOnAction(ActionEvent event) {
        resetForm();
        loadNextId();
    }

    private void resetForm() {
        cmbCourseName.getSelectionModel().clearSelection();
        txtDuration.clear();
        txtFee.clear();
        txtDescription.clear();
        txtInstructorId.clear();
        tblCourses.getSelectionModel().clearSelection();
    }


    // ================= Enrollment =================

    @FXML
    private void btnEnrollStudentOnAction(ActionEvent event) {
        try {
            String studentId = (String) cmbStudentIds.getValue();
            String courseId = (String) cmbCourseIds.getValue();

            if (studentId == null || courseId == null) {
                showAlert(Alert.AlertType.WARNING, "Select both Student ID and Course ID.");
                return;
            }

            boolean enrolled = courseBO.enrollStudent(studentId, courseId);

            if (enrolled) {
                showAlert(Alert.AlertType.INFORMATION, "Student enrolled successfully!");
                loadAllCourses(); // refresh table
            } else {
                showAlert(Alert.AlertType.ERROR, "Enrollment failed.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }




    // ================= Search =================
    @FXML
    private void search(KeyEvent keyEvent) {
        String text = txtSearch.getText().trim();
        if(text.isEmpty()) {
            loadAllCourses();
            return;
        }

        try {
            List<CourseDTO> courses = courseBO.search(text);
            List<CourseTM> tmList = new ArrayList<>();
            for(CourseDTO dto : courses) {
                tmList.add(new CourseTM(
                        dto.getCourseId(),
                        dto.getCourseName(),
                        dto.getDuration(),
                        dto.getFee(),
                        dto.getDescription(),
                        dto.getInstructorId(),
                        dto.getEnrollmentCount()
                ));
            }
            tblCourses.setItems(FXCollections.observableArrayList(tmList));
        } catch(Exception e) {
            showAlert(Alert.AlertType.ERROR, "Search failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= Helper =================
    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg).show();
    }

    public void goToDashboard(MouseEvent mouseEvent) {
        navigateTo("/view/DashBoard.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancCoursePage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancCoursePage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCoursePage.heightProperty());

            ancCoursePage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {

        CourseTM selected = tblCourses.getSelectionModel().getSelectedItem();
        if(selected != null) {
            lblCourseId.setText(selected.getCourseId());
            cmbCourseName.setValue(selected.getCourseName());
            txtDuration.setText(selected.getDuration());
            txtFee.setText(String.valueOf(selected.getFee()));
            txtDescription.setText(selected.getDescription());
            txtInstructorId.setText(selected.getInstructorId());
        }
    }
}

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

    @FXML
    public Label lblCourseId;
    @FXML
    public ComboBox<String> cmbDuration;
    @FXML
    public ComboBox cmbFee;

    @FXML
    private ComboBox<String> cmbCourseName;

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
                "Basic Learner Program",
                "Advanced Defensive Driving",
                "Motorcycle License Training",
                "Heavy Vehicle Training",
                "Refresher  Driving Course"
        ));

        cmbDuration.setItems(FXCollections.observableArrayList(
                "12 Weeks",
                "8 Weeks",
                "16 Weeks",
                "6 Months",
                "3 Months"
        ));

        try {
            cmbFee.setItems(FXCollections.observableArrayList(
                    "50000.00",
                    "65000.00",
                    "75000.00",
                    "150000.00",
                    "30000.00"
            ));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, " Failed to load Fee: " + e.getMessage());
        }
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


            List<String> courseIds = courseBO.getAllIds();
            cmbCourseIds.getItems().addAll(courseIds);


            List<String> studentIds = studentBO.getAllIds();
            cmbStudentIds.getItems().addAll(studentIds);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Failed to load ComboBoxes: " + e.getMessage());
        }
    }





    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        try {
            boolean saved = courseBO.save(CourseDTO.builder()
                    .courseId(lblCourseId.getText())
                    .courseName(cmbCourseName.getValue())
                    .duration(cmbDuration.getValue())
                    .fee(Double.parseDouble(cmbFee.getValue().toString()))
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
                    .duration(cmbDuration.getValue())
                    .fee(Double.parseDouble(cmbFee.getValue().toString()))
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
        cmbDuration.getSelectionModel().clearSelection();
        cmbFee.getSelectionModel().clearSelection();
        txtDescription.clear();
        txtInstructorId.clear();
        tblCourses.getSelectionModel().clearSelection();
    }




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
            cmbDuration.setValue(selected.getDuration());
            cmbFee.setValue(Double.valueOf(String.valueOf(selected.getFee())));
            txtDescription.setText(selected.getDescription());
            txtInstructorId.setText(selected.getInstructorId());
        }
    }
}

package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.dto.tm.CourseTM;

import java.util.ArrayList;
import java.util.List;

public class ManageAssignCoursePopup {

    @FXML
    private TextField txtCourseId;

    @FXML
    private TableView<CourseTM> tblCourses;

    @FXML
    private TableColumn<CourseTM, String> colCourseId;
    @FXML
    private TableColumn<CourseTM, String> colCourseName;
    @FXML
    private TableColumn<CourseTM, String> colDuration;
    @FXML
    private TableColumn<CourseTM, Double> colFee;
    @FXML
    private TableColumn<CourseTM, String> colDescription;
    @FXML
    private TableColumn<CourseTM, Integer> colEnrollmentCount;

    @FXML
    public Button btnAssignCourse;

    private final InstructorBO instructorBO =
            (InstructorBO) BOFactory.getInstance().getBo(BOTypes.INSTRUCTOR);

    private String instructorId;

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
        loadInstructorCourses();
    }

    @FXML
    public void initialize() {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colEnrollmentCount.setCellValueFactory(new PropertyValueFactory<>("enrollmentCount"));
    }

    @FXML
    public void btnAssignCourseOnAction(ActionEvent event) {
        String courseId = txtCourseId.getText().trim();

        if (courseId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a course ID!").show();
            return;
        }

        try {
            boolean success = instructorBO.assignCourse(instructorId, courseId);
            if (success) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Course assigned successfully!").show();
                txtCourseId.clear();
                loadInstructorCourses();
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Course not found or failed to assign.").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Error while assigning course: " + e.getMessage()).show();
        }
    }

    private void loadInstructorCourses() {
        try {
            List<CourseDTO> courses = instructorBO.getCoursesByInstructor(instructorId);

            // Convert to CourseTM objects
            List<CourseTM> courseTMs = new ArrayList<>();
            for (CourseDTO dto : courses) {
                courseTMs.add(new CourseTM(
                        dto.getCourseId(),
                        dto.getCourseName(),
                        dto.getDuration(),
                        dto.getFee(),
                        dto.getDescription(),
                        dto.getInstructorId(),
                        dto.getEnrollmentCount()
                ));
            }

            tblCourses.setItems(FXCollections.observableArrayList(courseTMs));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load courses: " + e.getMessage()).show();
        }
    }

}

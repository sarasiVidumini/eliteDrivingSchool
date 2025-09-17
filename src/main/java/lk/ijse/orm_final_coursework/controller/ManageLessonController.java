package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.LessonBO;
import lk.ijse.orm_final_coursework.dto.LessonsDTO;
import lk.ijse.orm_final_coursework.dto.tm.LessonsTM;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageLessonController implements Initializable {

    private final LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBo(BOTypes.LESSONS);

    public AnchorPane ancLessonsPage;
    public Label lblLessonId;
    public TextField txtLessonDate;
    public TextField txtStartTime;
    public TextField txtEndTime;
    public TextField txtStatus;
    public TextField txtStudentId;
    public TextField txtCourseId;
    public TextField txtInstructorId;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TextField txtSearch;

    public TableView<LessonsTM> tblLessons;
    public TableColumn<LessonsTM , String> colLessonId;
    public TableColumn<LessonsTM , Date> colLessonDate;
    public TableColumn<LessonsTM , Time> colStartTime;
    public TableColumn<LessonsTM , Time> colEndTime;
    public TableColumn<LessonsTM , String> colStatus;
    public TableColumn<LessonsTM , String> colStudentId;
    public TableColumn<LessonsTM , String> colCourseId;
    public TableColumn<LessonsTM , String> colInstructorId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        try {
            loadAllLessons();
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Initialization error : "+e.getMessage());
        }
    }

    private void setCellValueFactories() {
        colLessonId.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        colLessonDate.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
    }

    private void loadAllLessons() throws Exception {
        tblLessons.setItems(FXCollections.observableArrayList(
                lessonBO.getAll().stream().map(lessonsDTO -> new LessonsTM(
                        lessonsDTO.getLessonId(),
                        lessonsDTO.getLessonDate(),
                        lessonsDTO.getStartTime(),
                        lessonsDTO.getEndTime(),
                        lessonsDTO.getStatus(),
                        lessonsDTO.getStudentId(),
                        lessonsDTO.getCourseId(),
                        lessonsDTO.getInstructorId()
                )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = lessonBO.getNextId();
        lblLessonId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
    }

    public void goToDashboard(MouseEvent mouseEvent) {
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

}

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
import lk.ijse.orm_final_coursework.bo.custom.LessonBO;
import lk.ijse.orm_final_coursework.dto.InstructorDTO;
import lk.ijse.orm_final_coursework.dto.LessonsDTO;
import lk.ijse.orm_final_coursework.dto.tm.LessonsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public TableColumn<LessonsTM , String> colLessonDate;
    public TableColumn<LessonsTM , String> colStartTime;
    public TableColumn<LessonsTM , String> colEndTime;
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
        try {
            boolean isSaved = lessonBO.save(LessonsDTO.builder()
                    .lessonId(lblLessonId.getText())
                    .lessonDate(txtLessonDate.getText())
                    .startTime(txtStartTime.getText())
                    .endTime(txtEndTime.getText())
                    .status(txtStatus.getText())
                    .studentId(txtStudentId.getText())
                    .courseId(txtCourseId.getText())
                    .instructorId(txtInstructorId.getText())
                    .build());

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION," Lesson Saved Successfully!");
                loadAllLessons();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Error Saving Lesson!");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error saving lesson : "+e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = lessonBO.update(LessonsDTO.builder()
                    .lessonId(lblLessonId.getText())
                    .lessonDate(txtLessonDate.getText())
                    .startTime(txtStartTime.getText())
                    .endTime(txtEndTime.getText())
                    .status(txtStatus.getText())
                    .studentId(txtStudentId.getText())
                    .courseId(txtCourseId.getText())
                    .instructorId(txtInstructorId.getText())
                    .build());
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION," Lesson Updated Successfully!");
                loadAllLessons();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Error Updating Lesson!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error updating lesson : "+e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblLessonId.getText();
        if (id.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Please select a lesson to delete!");
            return;
        }

        try {
            boolean isDeleted = lessonBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION,"Lesson Deleted Successfully!");
                loadAllLessons();
                resetForm();
                loadNextId();

            }else {
                showAlert(Alert.AlertType.ERROR,"Error Deleting Lesson!");
            }

        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error deleting lesson : "+e.getMessage());
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
        try {
            loadNextId();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error generating ID : "+e.getMessage());
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        LessonsTM selectedItem = tblLessons.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblLessonId.setText(selectedItem.getLessonId());
            txtLessonDate.setText(selectedItem.getLessonDate());
            txtStartTime.setText(selectedItem.getStartTime());
            txtEndTime.setText(selectedItem.getEndTime());
            txtStatus.setText(selectedItem.getStatus());
            txtStudentId.setText(selectedItem.getStudentId());
            txtCourseId.setText(selectedItem.getCourseId());
            txtInstructorId.setText(selectedItem.getInstructorId());
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
        try {
            Stage stage = (Stage) ancLessonsPage.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"))));
            stage.centerOnScreen();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Navigation error : "+e.getMessage());
        }
    }

    private void resetForm() {
        txtLessonDate.clear();
        txtStartTime.clear();
        txtEndTime.clear();
        txtStatus.clear();
        txtStudentId.clear();
        txtCourseId.clear();
        txtInstructorId.clear();
        tblLessons.getSelectionModel().clearSelection();

    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

}

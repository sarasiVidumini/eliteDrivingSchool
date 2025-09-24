package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.CourseBO;
import lk.ijse.orm_final_coursework.bo.custom.StudentBO;
import lk.ijse.orm_final_coursework.bo.custom.PaymentBO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.dto.PaymentDTO;
import lk.ijse.orm_final_coursework.dto.StudentDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageStudentPaymentPopup implements Initializable {

    @FXML private TextField txtStudentId;
    @FXML private TextField txtTotalFee;
    @FXML private ComboBox<String> cmbPaymentMethod;
    @FXML private ComboBox<String> cmbCourses;
    @FXML public Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Label lblPaymentDate;

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBo(BOTypes.PAYMENT);
    private final CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBo(BOTypes.COURSE);
    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBo(BOTypes.STUDENT);

    private StudentDTO student;
    private double totalFee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbPaymentMethod.setItems(FXCollections.observableArrayList("Cash", "Credit Card"));
        cmbPaymentMethod.getSelectionModel().selectFirst();
        lblPaymentDate.setText(java.time.LocalDate.now().toString());
        try{
            List<String> courses = courseBO.getAll().stream().map(CourseDTO::getCourseName).toList();
            cmbCourses.setItems(FXCollections.observableArrayList(courses));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cmbCourses.setOnAction(event -> updateTotalFee());
    }

    public void setStudentId(String studentId) {
        try {
            student = studentBO.getStudentById(studentId);
            if (student != null) {
                txtStudentId.setText(student.getStudentId());
//                loadStudentCourses();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load student: " + e.getMessage());
        }
    }

//    private void loadStudentCourses() {
//        try {
//            List<CourseDTO> courses = courseBO.getAll(); // all courses
//            List<String> courseNames = new ArrayList<>();
//            for (CourseDTO dto : courses) {
//                boolean enrolled = student.getLessons().stream()
//                        .anyMatch(lesson -> lesson.getCourseId().equals(dto.getCourseId()));
////                if (enrolled) {
////                    courseNames.add(dto.getCourseName());
////                }
//            }
////            System.out.println(student);
////            cmbCourses.setItems(FXCollections.observableArrayList(courseNames));
////            if (!courseNames.isEmpty()) {
////                cmbCourses.getSelectionModel().selectFirst();
////                updateTotalFee();
////            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load courses: " + e.getMessage());
//        }
//    }

    private void updateTotalFee() {
        String selectedCourseName = cmbCourses.getSelectionModel().getSelectedItem();
        if (selectedCourseName == null) {
            totalFee = 0;
            txtTotalFee.setText("0");
            return;
        }
        try {
            CourseDTO course = courseBO.getCourseByName(selectedCourseName);
            totalFee = (course != null) ? course.getFee() : 0;
            txtTotalFee.setText(String.valueOf(totalFee));
        } catch (Exception e) {
            totalFee = 0;
            txtTotalFee.setText("0");
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to get course fee: " + e.getMessage());
        }
    }

    @FXML
    private void savePayment() {
        String selectedCourse = cmbCourses.getSelectionModel().getSelectedItem();
        String paymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();
        String paymentDate = lblPaymentDate.getText();

        if (selectedCourse == null || paymentMethod == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a course and payment method.");
            return;
        }

        try {
            PaymentDTO paymentDTO = PaymentDTO.builder()
                    .studentId(student.getStudentId())
                    .paymentDate(paymentDate)
                    .amount(totalFee)
                    .paymentMethod(paymentMethod)
                    .status("Paid")
                    .build();

            boolean isSaved = paymentBO.processPayment(paymentDTO);

            if (isSaved) {
                student.getPayments().add(paymentDTO);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment saved successfully!");
                closePopup();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to save payment.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void cancelPayment() {
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

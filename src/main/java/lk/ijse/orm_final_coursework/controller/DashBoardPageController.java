package lk.ijse.orm_final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.orm_final_coursework.controller.util.RoleManager;

public class DashBoardPageController {
    public AnchorPane ancDashBoardPage;
    public Button btnCourse;
    public Button btnInstructor;
    public Button btnLessons;
    public Button btnPayment;
    public Button btnStudent;
    public Button btnUser;

    private String userRole;

    public void initialize() {
       applyRoleAccess(RoleManager.getUserRole());
    }

    public void setUserRole(String role) {
        RoleManager.setUserRole(role);
        applyRoleAccess(role);
    }

    private void applyRoleAccess(String role) {
        if (role == null) {

            btnCourse.setDisable(true);
            btnInstructor.setDisable(true);
            btnUser.setDisable(true);
            btnStudent.setDisable(true);
            btnLessons.setDisable(true);
            btnPayment.setDisable(true);
            return;
        }

        if (role.equalsIgnoreCase("Admin")) {

            btnCourse.setDisable(false);
            btnInstructor.setDisable(false);
            btnUser.setDisable(false);
            btnStudent.setDisable(false);
            btnLessons.setDisable(false);
            btnPayment.setDisable(false);
        } else if (role.equalsIgnoreCase("Receptionist")) {

            btnCourse.setDisable(true);
            btnInstructor.setDisable(true);
            btnUser.setDisable(true);

            btnStudent.setDisable(false);
            btnLessons.setDisable(false);
            btnPayment.setDisable(false);
        }
    }

    public void btnGoCourseOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Course.fxml");
    }

    public void btnGoInstructorOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Instructor.fxml");
    }

    public void btnGoLessonsOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Lessons.fxml");
    }

    public void btnGoPaymentOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Payment.fxml");
    }

    public void btnGoStudentOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Student.fxml");
    }

    public void btnGoUserOnAction(ActionEvent actionEvent) {
        navigateTo("/view/User.fxml");
    }

    public void goToLoginPage(MouseEvent mouseEvent) {
        navigateTo("/view/Login.fxml");

    }

    private void navigateTo(String path) {
        try {
            ancDashBoardPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashBoardPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashBoardPage.heightProperty());

            ancDashBoardPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }
}

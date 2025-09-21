package lk.ijse.orm_final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DashBoardPageController {
    public AnchorPane ancDashBoardPage;

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

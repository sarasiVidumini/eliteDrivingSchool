package lk.ijse.orm_final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ManageLoginPageController {

    public AnchorPane ancLoginPage;

    public void goToMainPage(MouseEvent mouseEvent) throws IOException {
       navigateTo("/view/MainPage.fxml");
    }

    public void btnGoSignUpPage(ActionEvent actionEvent) {
        navigateTo("/view/SignUp.fxml");

    }

    public void btnGoSigninPage(ActionEvent actionEvent) {
        navigateTo("/view/Signin.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancLoginPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancLoginPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLoginPage.heightProperty());

            ancLoginPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }
}

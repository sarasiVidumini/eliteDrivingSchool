package lk.ijse.orm_final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.orm_final_coursework.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SigninController {
    public AnchorPane ancSigningPage;
    public TextField txtUserName;
    public TextField txtEmail;

    public void goToLoginPage(MouseEvent mouseEvent) {
        navigateTo("/view/Login.fxml");
    }

    public void btnGoDashBoardOnAction(ActionEvent actionEvent) {
        String name = txtUserName.getText().trim();
        String email = txtEmail.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Empty fields", "Please fill in all fields");
            return;
        }

        if (validateLogin(name, email)) {
            showAlert(Alert.AlertType.CONFIRMATION, "Login Successfully", "Welcome " + name + "!");
            navigateTo("/view/DashBoard.fxml");
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid Credentials . Please check your username or password");
        }
    }

    private boolean validateLogin(String name , String email) {
        String sql = "SELECT * FROM user WHERE username = ? AND email = ?";

        try(Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, email);

            ResultSet rs = statement.executeQuery();
            return rs.next();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Database Error" , "Please try again" + e.getMessage());
            return false;
        }


    }

    private void navigateTo(String path) {
        try {
            ancSigningPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancSigningPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancSigningPage.heightProperty());

            ancSigningPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}

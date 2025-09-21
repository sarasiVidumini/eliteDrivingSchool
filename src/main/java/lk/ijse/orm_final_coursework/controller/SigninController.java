package lk.ijse.orm_final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.db.DBConnection;
import lk.ijse.orm_final_coursework.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

    private boolean validateLogin(String name, String email) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "FROM User u WHERE u.userName = :userName AND u.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("userName", name);
            query.setParameter("email", email);

            User user = query.uniqueResult(); // null if no match
            return user != null;

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Please try again: " + e.getMessage());
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

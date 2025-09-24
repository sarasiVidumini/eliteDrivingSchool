package lk.ijse.orm_final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.UserBO;
import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.controller.util.PasswordUtil;
import lk.ijse.orm_final_coursework.controller.util.RoleManager;
import lk.ijse.orm_final_coursework.dto.UserDTO;
import lk.ijse.orm_final_coursework.entity.User;
import lk.ijse.orm_final_coursework.controller.util.RoleManager;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class SigninController {
    public AnchorPane ancSigningPage;
    public TextField txtUserName;
    public TextField txtEmail;
    public PasswordField txtPassword;
    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBo(BOTypes.USER);

    public void goToLoginPage(MouseEvent mouseEvent) {
        navigateTo("/view/Login.fxml");
    }

    public void btnGoDashBoardOnAction(ActionEvent actionEvent) {
        String name = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();

        if (name.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Empty fields", "Please fill in all fields");
            return;
        }

        UserDTO user = validateLogin(name);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {

            RoleManager.setUserRole(user.getRole());

            showAlert(Alert.AlertType.CONFIRMATION, "Login Successfully", "Welcome " + name + "!");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
                Scene scene = new Scene(loader.load());

                DashBoardPageController controller = loader.getController();
                controller.setUserRole(user.getRole());

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                ((Stage) txtUserName.getScene().getWindow()).close();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load dashboard.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid Credentials. Please check your username or password.");
        }
    }

    private UserDTO validateLogin(String name) {
        try {
            UserDTO user = userBO.getUserByName(name);
            System.out.println(user);
            if (user == null) {
                return null;
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Authentication Error", "Please try again: " + e.getMessage());
            return null;
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

package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import lk.ijse.orm_final_coursework.dao.SQLUtil;
import lk.ijse.orm_final_coursework.dto.UserDTO;
import lk.ijse.orm_final_coursework.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.util.regex.Pattern;

public class SignUpPageController {

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBo(BOTypes.USER);

    public AnchorPane ancSignUpPage;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public ComboBox cmbRole;
    public TextField txtEmail;
    public ComboBox cmbStatus;

    public Button btnGotIt;

    private final String emailPatten = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
    private final String passwordRegex = "^.{8,}$";
    private final String usernameRegex = "^[A-Za-z_-]+$";


    public void initialize() {
        txtUserName.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtConfirmPassword.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        cmbRole.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        cmbStatus.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());

        try {
            cmbRole.setItems(FXCollections.observableArrayList("Admin" , "Receptionist"));
        }catch (Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR," Failed to load role. "+ e.getMessage());
        }

        try {
            cmbStatus.setItems(FXCollections.observableArrayList("Active", "Inactive" , "Suspended" , "Pending Approval"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Failed to load status. " + e.getMessage());
        }
    }

    private void validateFields() {
        boolean isValidUserName = txtUserName.getText().matches(usernameRegex);
        boolean isValidPassword = txtPassword.getText().matches(passwordRegex);
        boolean isValidConfirmPassword = txtConfirmPassword.getText().matches(txtPassword.getText());
        boolean isValidEmail = txtEmail.getText().matches(emailPatten);

        if (!isValidUserName) txtUserName.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        if (!isValidPassword) txtPassword.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        if (!isValidConfirmPassword) txtConfirmPassword.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");
        if (!isValidEmail) txtEmail.setStyle("-fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 20px;");

        btnGotIt.setDisable(!isValidUserName && !isValidPassword && !isValidConfirmPassword && !isValidEmail);
    }

    public void goToLoginPage(MouseEvent mouseEvent) {
        loadPage("/view/Login.fxml");

    }

    public void btnGoDashBoardOnAction(ActionEvent actionEvent) {
        String inputUserName = txtUserName.getText();
        String inputPassword = txtPassword.getText();
        String inputConfirmPassword = txtConfirmPassword.getText();
        String chooseRole = cmbRole.getValue() != null ? cmbRole.getValue().toString() : "";
        String inputEmail = txtEmail.getText();
        String chooseStatus = cmbStatus.getValue() != null ? cmbStatus.getValue().toString() : "";


        if (inputUserName.isEmpty() || inputPassword.isEmpty() || inputConfirmPassword.isEmpty()
                || chooseRole.isEmpty() || chooseStatus.isEmpty()) {
            btnGotIt.setDisable(true);
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields.").show();
            return;
        }

        if (!inputUserName.matches(usernameRegex)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid User Name").show();
            return;
        }

        if (!inputPassword.matches(passwordRegex)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid password").show();
            return;
        }

        if (!inputPassword.equals(inputConfirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "FROM User u WHERE u.userName = :userName OR u.password = :password OR u.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("userName", inputUserName);
            query.setParameter("password", inputPassword);
            query.setParameter("email", inputEmail);

            User existingUser = query.uniqueResult();

            if (existingUser != null) {
                new Alert(Alert.AlertType.WARNING, "User already exists").show();
                return;
            }


            String userId = userBO.getNextId();


            boolean isSaved = userBO.save(new UserDTO(
                    userId,
                    inputUserName,
                    PasswordUtil.hashPassword(inputPassword),
                    chooseRole,
                    inputEmail,
                    chooseStatus
            ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User has been created").show();

                RoleManager.setUserRole(chooseRole);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
                AnchorPane root = loader.load();


                DashBoardPageController controller = loader.getController();
                controller.setUserRole(chooseRole);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                ((Stage) ancSignUpPage.getScene().getWindow()).close();

            } else {
                new Alert(Alert.AlertType.ERROR, "User could not be created").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to create user. " + e.getMessage());
        }



    }



    private void showAlert(Alert.AlertType alertType , String message) {
        new Alert(alertType , message).show();
    }

    private void loadPage(String path) {
        try {
            ancSignUpPage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(ancSignUpPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancSignUpPage.heightProperty());
            ancSignUpPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Page not found..!");
        }
    }

}

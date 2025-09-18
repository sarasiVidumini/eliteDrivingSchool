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
import lk.ijse.orm_final_coursework.bo.custom.UserBO;
import lk.ijse.orm_final_coursework.dto.UserDTO;
import lk.ijse.orm_final_coursework.dto.tm.UserTM;
import org.hibernate.cfg.AbstractPropertyHolder;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageUserController implements Initializable {
    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBo(BOTypes.USER);
    public AnchorPane ancUserPage;
    public Label lblUserId;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtRole;
    public TextField txtEmail;
    public TextField txtStatus;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TextField txtSearch;
    public TableView<UserTM> tblUsers;
    public TableColumn<UserTM , String> colUserId;
    public TableColumn<UserTM , String> colUserName;
    public TableColumn<UserTM , String> colPassword;
    public TableColumn<UserTM , String> colRole;
    public TableColumn<UserTM , String> colEmail;
    public TableColumn<UserTM , String> colStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        try {
            loadAllUsers();
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Initialization error" + e.getMessage());
        }

    }

    private void setCellValueFactories() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void loadAllUsers() throws Exception {
        tblUsers.setItems(FXCollections.observableArrayList(
                userBO.getAll().stream().map(userDTO -> new UserTM(
                        userDTO.getUserId(),
                        userDTO.getUserName(),
                        userDTO.getPassword(),
                        userDTO.getRole(),
                        userDTO.getEmail(),
                        userDTO.getStatus()
                )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = userBO.getNextId();
        lblUserId.setText(nextId);
    }
    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {
            boolean isSaved = userBO.save( UserDTO.builder()
                    .userId(lblUserId.getText())
                    .userName(txtUserName.getText())
                    .password(txtPassword.getText())
                    .role(txtRole.getText())
                    .email(txtEmail.getText())
                    .status(txtStatus.getText())
                    .build());

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "User saved successfully!");
                loadAllUsers();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Failed to save user!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error saving user:" + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        try {

            boolean isUpdated = userBO.update( UserDTO.builder()
                    .userId(lblUserId.getText())
                    .userName(txtUserName.getText())
                    .password(txtPassword.getText())
                    .role(txtRole.getText())
                    .email(txtEmail.getText())
                    .status(txtStatus.getText())
                    .build());

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "User updated successfully!");
                loadAllUsers();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Failed to update user!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error updating user:" + e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id  = lblUserId.getText();
        if (id.isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Please select an user to delete");
            return;
        }

        try {
            boolean isDeleted = userBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "User deleted successfully!");
                loadAllUsers();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Failed to delete user!");
            }

        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error deleting user:" + e.getMessage());
        }

    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
        try{
            loadNextId();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error generating ID:" + e.getMessage());
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        UserTM selectedItem = tblUsers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblUserId.setText(selectedItem.getUserId());
            txtUserName.setText(selectedItem.getUserName());
            txtPassword.setText(selectedItem.getPassword());
            txtRole.setText(selectedItem.getRole());
            txtEmail.setText(selectedItem.getEmail());
            txtStatus.setText(selectedItem.getStatus());
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) {
        try {
            Stage stage = (Stage) ancUserPage.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"))));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR,"Navigation error : " + e.getMessage());
        }
    }

    private void resetForm() {
        txtUserName.clear();
        txtPassword.clear();
        txtRole.clear();
        txtEmail.clear();
        txtStatus.clear();
        tblUsers.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()|| txtRole.getText().isEmpty() || txtEmail.getText().isEmpty() || txtStatus.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR , "please input all fields");
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", txtEmail.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid email format!");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType alertType , String message) {
        new Alert(alertType , message).show();
    }
}

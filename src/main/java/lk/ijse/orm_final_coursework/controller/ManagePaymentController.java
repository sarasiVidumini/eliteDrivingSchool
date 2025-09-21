package lk.ijse.orm_final_coursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.orm_final_coursework.bo.BOFactory;
import lk.ijse.orm_final_coursework.bo.BOTypes;
import lk.ijse.orm_final_coursework.bo.custom.PaymentBO;
import lk.ijse.orm_final_coursework.dto.LessonsDTO;
import lk.ijse.orm_final_coursework.dto.PaymentDTO;
import lk.ijse.orm_final_coursework.dto.tm.PaymentTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagePaymentController implements Initializable {

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBo(BOTypes.PAYMENT);

    public AnchorPane ancPaymentPage;

    public Label lblPaymentId;

    public TextField txtPaymentDate;
    public TextField txtAmount;
    public ComboBox cmbPaymentMethod;
    public ComboBox cmbStatus;
    public TextField txtStudentId;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public TextField txtSearch;

    public TableView<PaymentTM> tblPayments;
    public TableColumn<PaymentTM , String> colPaymentId;
    public TableColumn<PaymentTM , String> colPaymentDate;
    public TableColumn<PaymentTM , Double> colAmount;
    public TableColumn<PaymentTM , String> colPaymentMethod;
    public TableColumn<PaymentTM , String> colStatus;
    public TableColumn<PaymentTM , String> colStudentId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactories();
        try {
            loadAllPayments();
            loadNextId();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Initilization Error : " + e.getMessage());
        }
    }

    private void setCellValueFactories() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        try {
            cmbPaymentMethod.setItems(FXCollections.observableArrayList("Credit Card" , "Cash"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Failed to load payment method! :" + e.getMessage());
        }

        try {
            cmbStatus.setItems(FXCollections.observableArrayList("Pending" , "Partially Paid" , "Paid" , "Overdue"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Failed to load status : " + e.getMessage());
        }
    }

    private void loadAllPayments() throws Exception {
        tblPayments.setItems(FXCollections.observableArrayList(
                paymentBO.getAll().stream().map(paymentDTO -> new PaymentTM(
                        paymentDTO.getPaymentId(),
                        paymentDTO.getPaymentDate(),
                        paymentDTO.getAmount(),
                        paymentDTO.getPaymentMethod(),
                        paymentDTO.getStatus(),
                        paymentDTO.getStudentId()
                )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = paymentBO.getNextId();
        lblPaymentId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = paymentBO.save(PaymentDTO.builder()
                    .paymentId(lblPaymentId.getText())
                    .paymentDate(txtPaymentDate.getText())
                    .amount(Double.parseDouble(txtAmount.getText()))
                    .paymentMethod(cmbPaymentMethod.getValue().toString())
                    .status(cmbStatus.getValue().toString())
                    .studentId(txtStudentId.getText())
                    .build());
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Payment saved Successfully");
                loadAllPayments();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Error saving Payment!");
            }
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error saving payment : " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = paymentBO.update(PaymentDTO.builder()
                    .paymentId(lblPaymentId.getText())
                    .paymentDate(txtPaymentDate.getText())
                    .amount(Double.parseDouble(txtAmount.getText()))
                    .paymentMethod(cmbPaymentMethod.getValue().toString())
                    .status(cmbStatus.getValue().toString())
                    .studentId(txtStudentId.getText())
                    .build());
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Payment updated Successfully");
                loadAllPayments();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Error updating Payment!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error updating payment : " + e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblPaymentId.getText();
        if (id.isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Please Select a Payment to delete!");
            return;
        }

        try {
            boolean isDeleted = paymentBO.delete(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Payment deleted Successfully");
                loadAllPayments();
                resetForm();
                loadNextId();
            }else {
                showAlert(Alert.AlertType.ERROR,"Error deleting Payment!");
            }
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error deleting Payment : " + e.getMessage());
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetForm();
        try {
            loadNextId();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"Error generating ID : " + e.getMessage());
        }
    }

    public void OnClickedTable(MouseEvent mouseEvent) {
        PaymentTM selectedItem = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblPaymentId.setText(selectedItem.getPaymentId());
            txtPaymentDate.setText(selectedItem.getPaymentDate());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            cmbPaymentMethod.setValue(selectedItem.getPaymentMethod());
            cmbStatus.setValue(selectedItem.getStatus());
            txtStudentId.setText(selectedItem.getStudentId());
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) throws IOException {
       navigateTo("/view/DashBoard.fxml");
    }

    private void resetForm() {
        txtPaymentDate.clear();
        txtAmount.clear();
        cmbPaymentMethod.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
        txtStudentId.clear();
        tblPayments.getSelectionModel().clearSelection();

    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

    private void navigateTo(String path) {
        try {
            ancPaymentPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancPaymentPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPaymentPage.heightProperty());

            ancPaymentPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    public void search(KeyEvent keyEvent) {
        String search = txtSearch.getText();
        if (search.isEmpty()) {
            try {
                loadAllPayments();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search!").show();
            }
        }else {
            try {
                ArrayList<PaymentDTO> paymentList = (ArrayList<PaymentDTO>) paymentBO.search(search);
                tblPayments.setItems(FXCollections.observableArrayList(
                        paymentList.stream()
                                .map(paymentDTO -> new PaymentTM(
                                        paymentDTO.getPaymentId(),
                                        paymentDTO.getPaymentDate(),
                                        paymentDTO.getAmount(),
                                        paymentDTO.getPaymentMethod(),
                                        paymentDTO.getStatus(),
                                        paymentDTO.getStudentId()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search!").show();
            }
        }
    }
}

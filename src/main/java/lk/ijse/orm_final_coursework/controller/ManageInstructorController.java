//package lk.ijse.orm_final_coursework.controller;
//
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import lk.ijse.orm_final_coursework.bo.BOFactory;
//import lk.ijse.orm_final_coursework.bo.BOTypes;
//import lk.ijse.orm_final_coursework.bo.custom.InstructorBO;
//import lk.ijse.orm_final_coursework.dto.InstructorDTO;
//import lk.ijse.orm_final_coursework.dto.tm.InstructorTM;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class ManageInstructorController implements Initializable {
//
//    private final InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBo(BOTypes.INSTRUCTOR);
//
//    public TableView<InstructorTM> tblInstructors;
//    public TableColumn<InstructorTM , String> colInstructorId;
//    public TableColumn<InstructorTM , String> colFirstName;
//    public TableColumn<InstructorTM , String> colLastName;
//    public TableColumn<InstructorTM , String> colEmail;
//    public TableColumn<InstructorTM , String> colPhone;
//    public TableColumn<InstructorTM , String> colAvailabilityShedule;
//    public TextField txtSearch;
//    public Button btnReset;
//    public Button btnDelete;
//    public Button btnUpdate;
//    public Button btnSave;
//    public TextField txtAvailabilityShedule;
//    public TextField txtSpecialization;
//    public TextField txtPhone;
//    public TextField txtEmail;
//    public TextField txtLastName;
//    public TextField txtFirstName;
//    public AnchorPane ancInstructorPage;
//    public Label lblInstructorId;
//    public TableColumn colSpecialization;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        colInstructorId. setCellValueFactory(new PropertyValueFactory<>("InstructorId"));
//        colFirstName. setCellValueFactory(new PropertyValueFactory<>("FirstName"));
//        colLastName. setCellValueFactory(new PropertyValueFactory<>("LastName"));
//        colEmail. setCellValueFactory(new PropertyValueFactory<>("Email"));
//        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
//        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("Specialization"));
//        colAvailabilityShedule.setCellValueFactory(new PropertyValueFactory<>("AvailabilityShedule"));
//        try {
//            loadAllInstructors();
//            loadNextId();
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void loadAllInstructors() throws Exception{
//        try {
//            tblInstructors.setItems(FXCollections.observableArrayList(
//                    instructorBO.getAll().stream().map(instructorDTO -> new InstructorTM(
//                            instructorDTO.getInstructorId(),
//                            instructorDTO.getFirstName(),
//                            instructorDTO.getLastName(),
//                            instructorDTO.getEmail(),
//                            instructorDTO.getPhone(),
//                            instructorDTO.getSpecialization(),
//                            instructorDTO.getAvailability_schedule()
//                    )).toList()
//            ));
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void loadNextId() throws Exception{
//        String nextId = instructorBO.getNextId();
//    }
//
//    public void btnSaveOnAction(ActionEvent actionEvent) {
//
//    }
//
//    public void btnUpdateOnAction(ActionEvent actionEvent) {
//    }
//
//    public void btnDeleteOnAction(ActionEvent actionEvent) {
//    }
//
//    public void btnResetOnAction(ActionEvent actionEvent) {
//    }
//
//    public void OnClickedTable(MouseEvent mouseEvent) {
//    }
//
//    public void goToDashboard(MouseEvent mouseEvent) {
//    }
//}

package lk.ijse.orm_final_coursework.controller;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class MainPageController {

    public AnchorPane ancMainPage;
    @FXML
    private ImageView bigImage;

    public void initialize() {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(2), bigImage);
        scale.setFromX(1.0);  // normal width
        scale.setFromY(1.0);  // normal height
        scale.setToX(0.7);    // shrink to 70% width
        scale.setToY(0.7);    // shrink to 70% height
        scale.setAutoReverse(true);        // grow back
        scale.setCycleCount(ScaleTransition.INDEFINITE); // repeat forever
        scale.play();



    animateSmallImage();
//        animateRegisterButton();
    }

    @FXML
    private ImageView smallImage;

    public void animateSmallImage() {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), smallImage);
        scale.setByX(0.2);   // enlarge horizontally
        scale.setByY(0.2);   // enlarge vertically
        scale.setAutoReverse(true);
        scale.setCycleCount(ScaleTransition.INDEFINITE);
        scale.play();
    }

    public void btnGoLoginPageOnAction(ActionEvent actionEvent) throws IOException {
        ancMainPage.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        ancMainPage.getChildren().add(load);
    }

//    @FXML
//    private Button registerButton;
//
//    public void animateRegisterButton() {
//        // Fade effect
////        FadeTransition fade = new FadeTransition(Duration.seconds(2), registerButton);
////        fade.setFromValue(1.0);  // fully visible
////        fade.setToValue(0.5);    // half transparent
////        fade.setAutoReverse(true);
////        fade.setCycleCount(FadeTransition.INDEFINITE);
//
//        // Scale (pulse) effect
//
//
//        // Play together
////        fade.play();
////        scale.play();
//
//        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), registerButton);
//        fadeIn.setFromValue(0.0);
//        fadeIn.setToValue(1.0);
//        fadeIn.play();
//    }

}



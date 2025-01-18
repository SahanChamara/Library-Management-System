package edu.sc.lms.controller.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private Label cardBookAuthor;

    @FXML
    private Label cardBookAvalability;

    @FXML
    private Label cardBookTitle;

    @FXML
    private ImageView cardImg;

    @FXML
    private AnchorPane cardPane;

    @FXML
    private AnchorPane tabAnchorPane;

    @FXML
    private HBox hbox;

    private void loadCardPanes(){
        for (int i=0;i <10; i++){
            hbox.getChildren().add(cardPane);
            tabAnchorPane.getChildren().add(hbox);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCardPanes();
    }
}

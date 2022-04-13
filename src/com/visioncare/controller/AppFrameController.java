package com.visioncare.controller;

import animatefx.animation.ZoomIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppFrameController implements Initializable {

    @javafx.fxml.FXML
    private Button btn_appointments;
    @javafx.fxml.FXML
    private Button btn_reports;
    @javafx.fxml.FXML
    private Button btn_dashboard;
    @javafx.fxml.FXML
    private Button btn_patients;
    @javafx.fxml.FXML
    private Button btn_user_settings;
    @FXML
    private BorderPane bp_frame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane view = null;
        try {
            view = FXMLLoader.load(getClass().getResource("../view/DashboardView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp_frame.setCenter(view);
    }
    @FXML
    void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        if(event.getSource().equals(btn_dashboard)){
            AnchorPane view = FXMLLoader.load(getClass().getResource("../view/DashboardView.fxml"));
            bp_frame.setCenter(view);
        } else if(event.getSource().equals(btn_patients)) {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../view/PatientView.fxml"));
            bp_frame.setCenter(view);
            //
        } else if(event.getSource().equals(btn_appointments)) {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../view/AppointmentView.fxml"));
            bp_frame.setCenter(view);
        } else if(event.getSource().equals(btn_reports)) {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../view/GenerateReportView.fxml"));
            bp_frame.setCenter(view);
        } else if(event.getSource().equals(btn_user_settings)) {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../view/UserSettingsView.fxml"));
            bp_frame.setCenter(view);
        }
    }

}

package com.visioncare.controller;

import com.visioncare.DAO.PatientData;
import com.visioncare.model.Appointment;
import com.visioncare.service.AppointmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {
    AppointmentService appointmentService;
    ObservableList<Appointment> obsThisWeek;
    ObservableList<Appointment> obsMissed;


    @FXML
    private Label label_greeting;
    @FXML
    private TableView<Appointment> table_this_week;
    @FXML
    private TableColumn<Appointment, String> col_first_name;
    @FXML
    private TableColumn<Appointment,String> col_last_name;
    @FXML
    private TableColumn<Appointment,String> col_appt_date;
    @FXML
    private TableColumn<Appointment,String> col_appt_time;
    @FXML
    private TableView<Appointment> table_missed;
    @FXML
    private TableColumn<Appointment, String> col_first_name2;
    @FXML
    private TableColumn<Appointment,String> col_last_name2;
    @FXML
    private TableColumn<Appointment,String> col_appt_date2;
    @FXML
    private TableColumn<Appointment,String> col_appt_time2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listThisWeek();
            listMissedAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void listThisWeek() throws SQLException {
        try {
            appointmentService = new AppointmentService();
            obsThisWeek = FXCollections.observableArrayList(appointmentService.listThisWeek());
        } catch (SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }

        col_first_name.setCellValueFactory(cellData -> cellData.getValue().getAppointmentPatient().firstNameProperty());
        col_last_name.setCellValueFactory(cellData -> cellData.getValue().getAppointmentPatient().lastNameProperty());
        col_appt_date.setCellValueFactory(cellData -> cellData.getValue().appointmentDateProperty());
        col_appt_time.setCellValueFactory(cellData -> cellData.getValue().appointmentTimeProperty());


        table_this_week.setItems(obsThisWeek);

    }

    public void listMissedAppointments() throws SQLException {
        try {
            appointmentService = new AppointmentService();
            obsMissed = FXCollections.observableArrayList(appointmentService.listMissedAppointments());
        } catch (SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }

        col_first_name2.setCellValueFactory(cellData -> cellData.getValue().getAppointmentPatient().firstNameProperty());
        col_last_name2.setCellValueFactory(cellData -> cellData.getValue().getAppointmentPatient().lastNameProperty());
        col_appt_date2.setCellValueFactory(cellData -> cellData.getValue().appointmentDateProperty());
        col_appt_time2.setCellValueFactory(cellData -> cellData.getValue().appointmentTimeProperty());


        table_missed.setItems(obsMissed);

    }


}

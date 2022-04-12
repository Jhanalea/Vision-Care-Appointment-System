package com.visioncare.controller;

import com.visioncare.DAO.AppointmentData;
import com.visioncare.model.AppointmentStatus;
import com.visioncare.model.Appointment;
import com.visioncare.service.AppointmentService;

import com.visioncare.DAO.PatientData;
import com.visioncare.model.Patient;
import com.visioncare.service.PatientService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentController implements Initializable {
    AppointmentService appointmentService;
    ObservableList<Appointment> observableListOfAppointment;

    @FXML
    private TableView<?> appointment_table;
    @FXML
    private Button btn_add_appt;
    @FXML
    private Button btn_clear_fields;
    @FXML
    private Button btn_delete_appt;
    @FXML
    private Button btn_edit_appt;
    @FXML
    private Button btn_send_email;
    @FXML
    private ComboBox<AppointmentStatus> cb_appt_status;
    @FXML
    private ComboBox<String> cb_appt_time;
    @FXML
    private TableColumn<?, ?> col_appt_date;
    @FXML
    private TableColumn<?, ?> col_appt_id;
    @FXML
    private TableColumn<?, ?> col_appt_status;
    @FXML
    private TableColumn<?, ?> col_appt_time;
    @FXML
    private TableColumn<?, ?> col_first_name;
    @FXML
    private TableColumn<?, ?> col_last_name;
    @FXML
    private DatePicker dp_appt_date;
    @FXML
    private TextField tf_appt_id;
    @FXML
    private TextField tf_patient_fname;
    @FXML
    private TextField tf_patient_lname;
    @FXML
    private TextField tf_search_bar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_appt_status.getItems().addAll(AppointmentStatus.values());
        cb_appt_time.getItems().addAll("9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM");
        /* try {
            listAppointmentTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

    }
/*
    public void listAppointmentTable() throws SQLException {
        try {
            appointmentService = new AppointmentService();
            observableListOfAppointment = FXCollections.observableArrayList(appointmentService.listAppointmentTable());
        } catch (SQLException ex){
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }

        col_appt_id.setCellValueFactory(cellData -> cellData.getValue().appointmentIDProperty().asObject());
        col_first_name.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        col_last_name.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        col_appt_date.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        col_appt_time.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());
        col_appt_status.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());


        // TABLE SEARCH FUNCTION

        FilteredList<Appointment> filteredAppointment = new FilteredList<>(observableListOfAppointment, b ->true);
        //Patient patient = new Patient();

        tf_search_bar.textProperty().addListener((observable, oldValue, newValue) ->

        {
            filteredAppointment.setPredicate(appointment -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (String.valueOf(patient.getPatientID()).contains(searchKeyword)){
                    return true;

                }else if(patient.getFirstName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(patient.getLastName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(patient.getPhoneNumber().contains(searchKeyword)){
                    return true;
                }else if(patient.getEmailAddress().toLowerCase().contains(searchKeyword)){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<Patient> sortedPatients = new SortedList<>(filteredPatients);

        sortedPatients.comparatorProperty().bind(patient_table.comparatorProperty());

        patient_table.setItems(sortedPatients);


    }

    @FXML
    void handleButtonAction(ActionEvent event) {

    }

    @FXML
    void handleMouseAction(MouseEvent event) {

    }

}

    public Patient loadPatientDetails(String patientID) throws SQLException{
        appointmentService = new AppointmentService();
        return appointmentService.loadPatientDetails(patientID);
    }

    public List<Appointment> listAppointmentForPatient(String patientID) throws SQLException{
        appointmentService = new AppointmentService();
        return appointmentService.listAppointmentForPatient(patientID);
    }

    public List<Appointment> loadAppointmentListTable() throws SQLException{
        appointmentService = new AppointmentService();
        return appointmentService.listAppointmentTable();
    }


    public void createAppointment(Appointment appointment,String subject){
        appointmentService = new AppointmentService();
        appointmentService.createAppointment(appointment,subject);
    }

    public void deleteAppointment(List<String> appointmentIds){
        appointmentService = new AppointmentService();
        appointmentService.deleteAppointment(appointmentIds);
    }

    public void updateAppointment(Appointment appointment,String subject){
        appointmentService = new AppointmentService();
        appointmentService.updateAppointment(appointment,subject);
    }

    public Appointment loadSelectedAppointment(String patientID, String appointmentID) throws SQLException {
        appointmentService = new AppointmentService();
        return appointmentService.loadSelectedAppointment(patientID,appointmentID);
    }

*/
}
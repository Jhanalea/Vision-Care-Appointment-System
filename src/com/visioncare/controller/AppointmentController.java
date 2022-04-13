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
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;


import javax.mail.MessagingException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AppointmentController implements Initializable {
    AppointmentService appointmentService;
    PatientService patientService;
    ObservableList<Appointment> observableListOfAppointment;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yy");

    @FXML
    private TableView<Appointment> appointment_table;
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
    private TableColumn<Appointment, String> col_appt_date;
    @FXML
    private TableColumn<Appointment,Integer> col_appt_id;
    @FXML
    private TableColumn<Appointment,String> col_appt_status;
    @FXML
    private TableColumn<Appointment,String> col_appt_time;
    @FXML
    private TableColumn<Appointment,String> col_first_name;
    @FXML
    private TableColumn<Appointment,String> col_last_name;
    @FXML
    private TableColumn<Appointment,String> col_email_address;
    @FXML
    private DatePicker dp_appt_date;
    @FXML
    private TextField tf_appt_id;
    @FXML
    private TextField tf_patient_fname;
    @FXML
    private TextField tf_patient_lname;
    @FXML
    private TextField tf_patient_email;
    @FXML
    private TextField tf_search_bar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_appt_status.getItems().addAll(AppointmentStatus.values());
        cb_appt_time.getItems().addAll("9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM");
         try {
            listAppointmentTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void listAppointmentTable() throws SQLException {
        try {
            appointmentService = new AppointmentService();
            observableListOfAppointment = FXCollections.observableArrayList(appointmentService.listAppointmentTable());
        } catch (SQLException ex){
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }

        col_appt_id.setCellValueFactory(cellData -> cellData.getValue().appointmentIDProperty().asObject());
        col_first_name.setCellValueFactory(cellData -> cellData.getValue().getAppointmentPatient().firstNameProperty());
        col_last_name.setCellValueFactory(cellData -> cellData.getValue().getAppointmentPatient().lastNameProperty());
        col_email_address.setCellValueFactory(cellData -> cellData.getValue().getAppointmentPatient().emailAddressProperty());
        col_appt_date.setCellValueFactory(cellData -> cellData.getValue().appointmentDateProperty());
        col_appt_time.setCellValueFactory(cellData -> cellData.getValue().appointmentTimeProperty());
        col_appt_status.setCellValueFactory(cellData -> cellData.getValue().appointmentStatusProperty());


        // TABLE SEARCH FUNCTION

        FilteredList<Appointment> filteredAppointments = new FilteredList<>(observableListOfAppointment, b ->true);
        //Patient patient = new Patient();

        tf_search_bar.textProperty().addListener((observable, oldValue, newValue) ->

        {
            filteredAppointments.setPredicate(appointment -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (String.valueOf(appointment.getAppointmentID()).contains(searchKeyword)){
                    return true;
                }else if(appointment.getAppointmentPatient().getFirstName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(appointment.getAppointmentPatient().getLastName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(appointment.getAppointmentPatient().getEmailAddress().toLowerCase().contains(searchKeyword)){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredAppointments);

        sortedAppointments.comparatorProperty().bind(appointment_table.comparatorProperty());

        appointment_table.setItems(sortedAppointments);


    }

    public void createAppointment() throws SQLException {
        if (validateInputs() && duplicateAppointment() && patientExists()) {
            patientService = new PatientService();
            Patient patient = new Patient();
            Appointment appointment = new Appointment();
            appointment.setAppointmentDate(this.dp_appt_date.getValue().format(DateTimeFormatter.ofPattern("MM/d/yy")));
            appointment.setAppointmentTime(this.cb_appt_time.getValue());
            appointment.setAppointmentStatus(this.cb_appt_status.getValue().toString());
            appointment.setAppointmentPatient(patientService.listPatientTable().stream().filter(patient1 -> this.tf_patient_email.getText().equals(patient1.getEmailAddress())).findFirst().get());
            appointmentService = new AppointmentService();
            appointmentService.createAppointment(appointment);
            listAppointmentTable();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Appointment Created");
            alert.setHeaderText("Appointment Successfully Created");
            alert.setContentText("New Appointment Scheduled For : "+ dp_appt_date.getValue().toString() +" at: "+ cb_appt_time.getValue() + " ");
            alert.showAndWait();
        }
    }

    public void updateAppointment() throws SQLException {
        if (validateInputs() && duplicateAppointment()) {
            patientService = new PatientService();
            Patient patient = new Patient();
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(Integer.parseInt(this.tf_appt_id.getText()));
            appointment.setAppointmentDate(this.dp_appt_date.getValue().format(DateTimeFormatter.ofPattern("MM/d/yy")));
            appointment.setAppointmentTime(this.cb_appt_time.getValue());
            appointment.setAppointmentStatus(this.cb_appt_status.getValue().toString());
            appointment.setAppointmentPatient(patientService.listPatientTable().stream().filter(patient1 -> this.tf_patient_email.getText().equals(patient1.getEmailAddress())).findFirst().get());
            appointmentService = new AppointmentService();
            appointmentService.updateAppointment(appointment);
            listAppointmentTable();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Updated");
            alert.setHeaderText("Appointment Successfully Updated");
            alert.setContentText("Appointment Scheduled For : "+ dp_appt_date.getValue().toString() +" at: "+ cb_appt_time.getValue() + " has been updated.");
            alert.showAndWait();
        }
    }

    public void deleteAppointment() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete/Cancel Appointment");
        alert.setHeaderText("Are you Sure?");
        alert.setContentText("Appointment Scheduled For : "+ dp_appt_date.getValue().toString() +" at: "+ cb_appt_time.getValue() + " will be permanently deleted. This cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get()== ButtonType.OK) {
            patientService = new PatientService();
            Patient patient = new Patient();
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(Integer.parseInt(this.tf_appt_id.getText()));
            appointment.setAppointmentDate(this.dp_appt_date.getValue().format(DateTimeFormatter.ofPattern("MM/d/yy")));
            appointment.setAppointmentTime(this.cb_appt_time.getValue());
            appointment.setAppointmentStatus(this.cb_appt_status.getValue().toString());
            appointment.setAppointmentPatient(patientService.listPatientTable().stream().filter(patient1 -> this.tf_patient_email.getText().equals(patient1.getEmailAddress())).findFirst().get());
            appointmentService = new AppointmentService();
            appointmentService.deleteAppointment(appointment);
            listAppointmentTable();

            Alert alertnew = new Alert(Alert.AlertType.INFORMATION);
            alertnew.setTitle("Appointment Deleted");
            alertnew.setHeaderText("Appointment Successfully Deleted");
            alertnew.setContentText("Appointment Scheduled For : "+ dp_appt_date.getValue().toString() +" at: "+ cb_appt_time.getValue() + " has been deleted.");
            alertnew.showAndWait();
        }

    }

    public void sendEmailToPatient() throws SQLException , MessagingException {
        String subject = "Your Vision Care Centre Appointment";
        patientService = new PatientService();
        Patient patient = new Patient();
        Appointment appointment = new Appointment();
        appointment.setAppointmentID(Integer.parseInt(this.tf_appt_id.getText()));
        appointment.setAppointmentDate(this.dp_appt_date.getValue().format(DateTimeFormatter.ofPattern("MM/d/yy")));
        appointment.setAppointmentTime(this.cb_appt_time.getValue());
        appointment.setAppointmentStatus(this.cb_appt_status.getValue().toString());
        appointment.setAppointmentPatient(patientService.listPatientTable().stream().filter(patient1 -> this.tf_patient_email.getText().equals(patient1.getEmailAddress())).findFirst().get());
        appointmentService = new AppointmentService();
        appointmentService.sendEmailToPatient(appointment,subject);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email Sent");
        alert.setHeaderText("Email Reminder Successfully Sent");
        alert.setContentText("The patient has been sent an email reminder for their upcoming appointment.");
        alert.showAndWait();
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException, MessagingException {
        if (event.getSource() == btn_clear_fields){
            clearTextFields();
        } else if (event.getSource() == btn_add_appt){
            createAppointment();
            clearTextFields();
        } else if (event.getSource() == btn_edit_appt){
            updateAppointment();
            clearTextFields();
        } else if (event.getSource() == btn_delete_appt){
            deleteAppointment();
            clearTextFields();
        } else if (event.getSource() == btn_send_email){
            sendEmailToPatient();
        }

    }

    @FXML
    void handleMouseAction(MouseEvent event) {
        Appointment appointment = appointment_table.getSelectionModel().getSelectedItem();
        tf_appt_id.setText(""+appointment.getAppointmentID());
        tf_patient_email.setText(appointment.getAppointmentPatient().getEmailAddress());
        tf_patient_fname.setText(appointment.getAppointmentPatient().getFirstName());
        tf_patient_lname.setText(appointment.getAppointmentPatient().getLastName());
        cb_appt_time.setValue(appointment.getAppointmentTime());
        cb_appt_status.setValue(AppointmentStatus.valueOf(appointment.getAppointmentStatus()));
        dp_appt_date.setValue(LocalDate.parse(appointment.getAppointmentDate(),formatter));
    }
    public void clearTextFields() {
        tf_appt_id.clear();
        tf_patient_email.clear();
        tf_patient_fname.clear();
        tf_patient_lname.clear();
        tf_search_bar.clear();
        cb_appt_time.setValue(null);
        cb_appt_status.setValue(null);
        dp_appt_date.setValue(null);
    }


    public boolean validateInputs(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        String emailRegex = "^(.+)@(\\S+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);


        //Checking Empty Fields
        if (tf_patient_email.getText().isEmpty() || dp_appt_date.getValue()==null || cb_appt_time.getValue()==null || cb_appt_status.getValue()==null ) {
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Fields Cannot Be Empty");
            alert.setContentText("Please ensure all fields are filled out.");
            alert.showAndWait();
            return false;

        } else if(!emailPattern.matcher(tf_patient_email.getText()).matches()) {
            alert.setTitle("Invalid Email Address");
            alert.setHeaderText("Invalid Characters or Email Format");
            alert.setContentText("Please ensure patient's email address is formatted correctly");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public boolean duplicateAppointment(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(this.dp_appt_date.getValue().toString());
        appointment.setAppointmentTime(this.cb_appt_time.getValue());
        appointmentService = new AppointmentService();
        if (appointmentService.duplicateAppointment(appointment)){
            return true;
        }
        alert.setTitle("Appointment Slot is Booked.");
        alert.setHeaderText("This appointment slot has already been booked");
        alert.setContentText("Please select a new date or time for this appointment");
        alert.showAndWait();
        return false;
    }

    public boolean patientExists() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Appointment appointment = new Appointment();
        patientService = new PatientService();
        List<Patient> patientList = patientService.listPatientTable();
        for (Patient patient: patientList){
            if (patient.getEmailAddress().equals(this.tf_patient_email.getText())){
                return true;
            }
        }
        alert.setTitle("Patient Not Recognized");
        alert.setHeaderText("This Patient Does Not Exist In Database");
        alert.setContentText("Please create a patient before setting an appointment");
        alert.showAndWait();
        return false;
    }



}

package com.visioncare.controller;
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
import java.net.URL;

import java.sql.SQLException;


import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javafx.scene.control.*;
import javafx.scene.control.TextInputControl.*;
import javafx.scene.input.MouseEvent;


public class PatientController implements Initializable {
    PatientService patientService;
    ObservableList<Patient> observableListOfPatient;

    @javafx.fxml.FXML
    private TableView<Patient> patient_table;
    @javafx.fxml.FXML
    private TableColumn<Patient,String> col_tel_num;
    @javafx.fxml.FXML
    private TableColumn<Patient,String> col_email_address;
    @javafx.fxml.FXML
    private TableColumn<Patient,String> col_first_name;
    @javafx.fxml.FXML
    private TableColumn<Patient,String> col_last_name;
    @javafx.fxml.FXML
    private TableColumn<Patient,Integer> col_patient_id;
    @javafx.fxml.FXML
    private Button btn_delete_patient;
    @javafx.fxml.FXML
    private Button btn_add_patient;
    @javafx.fxml.FXML
    private Button btn_clear_fields;
    @javafx.fxml.FXML
    private TextField tf_patient_id;
    @javafx.fxml.FXML
    private TextField tf_first_name;
    @javafx.fxml.FXML
    private TextField tf_phone_number;
    @javafx.fxml.FXML
    private TextField tf_email_address;
    @javafx.fxml.FXML
    private Button btn_edit_patient;
    @javafx.fxml.FXML
    private TextField tf_last_name;
    @javafx.fxml.FXML
    private TextField tf_search_bar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listPatientTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void createPatient() throws SQLException {
        if (validateInputs() && duplicatePatient()) {
            Patient patient = new Patient();
            patient.setFirstName(this.tf_first_name.getText());
            patient.setLastName(this.tf_last_name.getText());
            patient.setPhoneNumber(this.tf_phone_number.getText());
            patient.setEmailAddress(this.tf_email_address.getText());
            patientService = new PatientService();
            patientService.createPatient(patient);
            listPatientTable();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Patient Created");
            alert.setHeaderText("Patient Successfully Created");
            alert.setContentText("New Patient: "+ tf_first_name.getText() +" "+ tf_last_name.getText() + " has been added.");
            alert.showAndWait();
        }
    }

    public void updatePatient() throws SQLException {
        if (validateInputs()) {
            Patient patient = new Patient();
            patient.setPatientID(Integer.parseInt(this.tf_patient_id.getText()));
            patient.setFirstName(this.tf_first_name.getText());
            patient.setLastName(this.tf_last_name.getText());
            patient.setPhoneNumber(this.tf_phone_number.getText());
            patient.setEmailAddress(this.tf_email_address.getText());
            patientService = new PatientService();
            patientService.updatePatient(patient);
            listPatientTable();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Patient Updated");
            alert.setHeaderText("Patient Successfully Updated");
            alert.setContentText("Patient: "+ tf_first_name.getText() +" "+ tf_last_name.getText() + " has been updated.");
            alert.showAndWait();
        }
    }

    public void deletePatient() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Patient");
        alert.setHeaderText("Are you Sure?");
        alert.setContentText("Patient: "+ tf_first_name.getText() +" "+ tf_last_name.getText() + " will be permanently deleted. This action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get()== ButtonType.OK) {
            Patient patient = new Patient();
            patient.setPatientID(Integer.parseInt(this.tf_patient_id.getText()));
            patient.setFirstName(this.tf_first_name.getText());
            patient.setLastName(this.tf_last_name.getText());
            patient.setPhoneNumber(this.tf_phone_number.getText());
            patient.setEmailAddress(this.tf_email_address.getText());
            patientService = new PatientService();
            patientService.deletePatient(patient);
            listPatientTable();

            Alert alertnew = new Alert(Alert.AlertType.INFORMATION);
            alertnew.setTitle("Patient Deleted");
            alert.setHeaderText("Patient Successfully Deleted");
            alertnew.setContentText("Patient: "+ tf_first_name.getText() +" "+ tf_last_name.getText() + " has been deleted.");
            alertnew.showAndWait();
        }

    }


    public void clearTextFields() {
        tf_patient_id.clear();
        tf_first_name.clear();
        tf_last_name.clear();
        tf_phone_number.clear();
        tf_email_address.clear();
        tf_search_bar.clear();
    }

    public void listPatientTable() throws SQLException {
        try {
            patientService = new PatientService();
            observableListOfPatient = FXCollections.observableArrayList(patientService.listPatientTable());
        } catch (SQLException ex){
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }

        col_patient_id.setCellValueFactory(cellData -> cellData.getValue().patientIDProperty().asObject());
        col_first_name.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        col_last_name.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        col_tel_num.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        col_email_address.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());

        // TABLE SEARCH FUNCTION

        FilteredList<Patient> filteredPatients = new FilteredList<>(observableListOfPatient, b ->true);

        tf_search_bar.textProperty().addListener((observable, oldValue, newValue) ->

        {
            filteredPatients.setPredicate(patient -> {
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


    public boolean validateInputs(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        String emailRegex = "^(.+)@(\\S+)$";
        String nameRegex = "^[a-zA-Z ,.'-]+$";
        String numberRegex = "[0-9]+";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern namePattern = Pattern.compile(nameRegex);
        Pattern phonePattern = Pattern.compile(numberRegex);


        //Checking Empty Fields
        if (tf_first_name.getText().isEmpty() || tf_last_name.getText().isEmpty() || tf_phone_number.getText().isEmpty() || tf_email_address.getText().isEmpty() ) {
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Fields Cannot Be Empty");
            alert.setContentText("Please ensure all fields are filled out.");
            alert.showAndWait();
            return false;

        } else if(!namePattern.matcher(tf_first_name.getText()).matches() || !namePattern.matcher(tf_last_name.getText()).matches()){
            alert.setTitle("Invalid Name");
            alert.setHeaderText("Invalid Characters in Patient Name");
            alert.setContentText("Please ensure no invalid characters are in the patient's name.");
            alert.showAndWait();
            return false;

        } else if(!phonePattern.matcher(tf_phone_number.getText()).matches()) {
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText("Invalid Characters in Phone Number");
            alert.setContentText("Please ensure no invalid characters are in the patient's phone number.");
            alert.showAndWait();
            return false;

        } else if(!emailPattern.matcher(tf_email_address.getText()).matches()) {
            alert.setTitle("Invalid Email Address");
            alert.setHeaderText("Invalid Characters or Email Format");
            alert.setContentText("Please ensure patient's email address is formatted correctly");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public boolean duplicatePatient(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Patient patient = new Patient();
        patient.setEmailAddress(this.tf_email_address.getText());
        patientService = new PatientService();
        if (patientService.duplicatePatient(patient)){
            return true;
        }
        alert.setTitle("Patient Already Exists");
        alert.setHeaderText("This patient has already been created");
        alert.setContentText("Please ensure patient's email address is unique");
        alert.showAndWait();
        return false;
    }



    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        if (event.getSource() == btn_add_patient){
            createPatient();
            clearTextFields();
        } else if (event.getSource() == btn_edit_patient){
            updatePatient();
            clearTextFields();
        } else if (event.getSource() == btn_delete_patient) {
            deletePatient();
            clearTextFields();

        } else if (event.getSource() == btn_clear_fields){
            clearTextFields();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Patient patient = patient_table.getSelectionModel().getSelectedItem();
        tf_patient_id.setText(""+patient.getPatientID());
        tf_first_name.setText(patient.getFirstName());
        tf_last_name.setText(patient.getLastName());
        tf_phone_number.setText(patient.getPhoneNumber());
        tf_email_address.setText(patient.getEmailAddress());
    }





}

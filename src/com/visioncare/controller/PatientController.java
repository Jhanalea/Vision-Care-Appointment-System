package com.visioncare.controller;
import com.visioncare.DAO.PatientData;
import com.visioncare.model.Patient;
import com.visioncare.service.PatientService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;

import java.sql.SQLException;


import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class PatientController implements Initializable {
    PatientData patientData;
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




    public void createPatient() throws SQLException {
        Patient patient = new Patient();
        patient.setFirstName(this.tf_first_name.getText());
        patient.setLastName(this.tf_last_name.getText());
        patient.setPhoneNumber(this.tf_phone_number.getText());
        patient.setEmailAddress(this.tf_email_address.getText());
        patientService = new PatientService();
        patientService.createPatient(patient);
        listPatientTable();
    }

    public void updatePatient() throws SQLException {
        Patient patient = new Patient();
        patient.setPatientID(Integer.parseInt(this.tf_patient_id.getText()));
        patient.setFirstName(this.tf_first_name.getText());
        patient.setLastName(this.tf_last_name.getText());
        patient.setPhoneNumber(this.tf_phone_number.getText());
        patient.setEmailAddress(this.tf_email_address.getText());
        patientService = new PatientService();
        patientService.updatePatient(patient);
        listPatientTable();
    }

    public void deletePatient() throws SQLException {
        Patient patient = new Patient();
        patient.setPatientID(Integer.parseInt(this.tf_patient_id.getText()));
        patient.setFirstName(this.tf_first_name.getText());
        patient.setLastName(this.tf_last_name.getText());
        patient.setPhoneNumber(this.tf_phone_number.getText());
        patient.setEmailAddress(this.tf_email_address.getText());
        patientService = new PatientService();
        patientService.deletePatient(patient);
        listPatientTable();
    }

    public void clearTextFields() {
        tf_patient_id.clear();
        tf_first_name.clear();
        tf_last_name.clear();
        tf_phone_number.clear();
        tf_email_address.clear();
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

        patient_table.setItems(observableListOfPatient);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listPatientTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        if (event.getSource() == btn_add_patient){
            createPatient();
            clearTextFields();
        } else if (event.getSource() == btn_edit_patient){
            updatePatient();
            clearTextFields();
        } else if (event.getSource() == btn_delete_patient){
            deletePatient();
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

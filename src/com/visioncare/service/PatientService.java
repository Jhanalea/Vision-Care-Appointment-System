package com.visioncare.service;

import com.visioncare.DAO.AppointmentData;
import com.visioncare.DAO.PatientData;
import com.visioncare.model.Appointment;
import com.visioncare.model.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PatientService implements PatientServiceInterface {

    PatientData patientData;

    @Override
    public void createPatient(Patient patient) {
        patientData = new PatientData();
        patientData.createPatient(patient);
    }

    public void updatePatient(Patient patient) {
        patientData = new PatientData();
        patientData.updatePatient(patient);
    }

    public void deletePatient(Patient patient) {
        patientData = new PatientData();
        patientData.deletePatient(patient);
    }
    public boolean duplicatePatient(Patient patient){
        patientData = new PatientData();
        if (patientData.duplicatePatient(patient)){
            return true;
        }
        return false;
    }

    public Patient loadPatientDetails(String patientID) throws SQLException {
        patientData = new PatientData();
        ResultSet rs = patientData.loadPatientDetails(patientID);
        rs.next();

        Patient patient = new Patient();
        patient.setPatientID(rs.getInt("patient_id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setPhoneNumber(rs.getString("tel_num"));
        patient.setEmailAddress(rs.getString("email_address"));
        return patient;
    }

    public List<Patient> listPatientTable() throws SQLException {
        patientData = new PatientData();
        ResultSet rs = patientData.loadPatients();
        List<Patient> patientList = new ArrayList<>();

        while (rs.next()) {
            Patient patient = new Patient();
            patient.setPatientID(rs.getInt("patient_id"));
            patient.setFirstName(rs.getString("first_name"));
            patient.setLastName(rs.getString("last_name"));
            patient.setPhoneNumber(rs.getString("tel_num"));
            patient.setEmailAddress(rs.getString("email_address"));
            patientList.add(patient);
        }
        return patientList;
    }


}

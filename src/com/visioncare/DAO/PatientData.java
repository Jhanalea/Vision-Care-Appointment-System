package com.visioncare.DAO;

import com.visioncare.model.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientData implements PatientDAOInterface {
    DBConnection dbConnection;

    @Override
    public void createPatient(Patient patient) {
        try {
            dbConnection = new DBConnection();
            int patient_id;
            dbConnection.update("INSERT INTO patients (first_name,last_name,tel_num,email_address) VALUES ('" + patient.getFirstName() + "','" + patient.getLastName() + "','" + patient.getPhoneNumber() + "','" + patient.getEmailAddress() + "')");
            dbConnection.closeConnection();
        }
        catch (SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void updatePatient(Patient patient) {
        try {
            dbConnection = new DBConnection();
            dbConnection.update("UPDATE patients SET first_name = '" + patient.getFirstName() + "',last_name = '" + patient.getLastName() + "',tel_num = '" + patient.getPhoneNumber() + "',email_address = '" + patient.getEmailAddress() + "' WHERE patient_id = '" + patient.getPatientID() + "'");
            dbConnection.closeConnection();
        }
        catch (SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deletePatient(Patient patient) {
        try {
            dbConnection = new DBConnection();
            dbConnection.update("Delete FROM patients WHERE patient_id = '" + patient.getPatientID() + "'");
            dbConnection.closeConnection();
        } catch(SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet loadPatients() throws SQLException {
        dbConnection = new DBConnection();
        return dbConnection.retrieve("Select * FROM patients");
    }


    public ResultSet loadPatientDetails(String patientID) throws SQLException {
        dbConnection = new DBConnection();
        return dbConnection.retrieve("Select * FROM patients WHERE patient_id = '" + patientID + "'");
    }

    public boolean duplicatePatient(Patient patient){
        try {
            dbConnection = new DBConnection();
            ResultSet result = dbConnection.retrieve("SELECT COUNT(*) from patients WHERE email_address='" + patient.getEmailAddress() + "' OR tel_num='" + patient.getPhoneNumber() + "' ");
            int count = result.getInt(1);
            dbConnection.closeConnection();
            if (count >= 1){
                return false;
            }

        } catch(SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }


}


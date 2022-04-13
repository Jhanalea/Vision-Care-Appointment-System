package com.visioncare.DAO;

import com.visioncare.model.Appointment;
import com.visioncare.model.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentData implements AppointmentDAOInterface {

    DBConnection dBConnection;

    public ResultSet listAppointmentForPatient(String patientID) throws SQLException {
        dBConnection = new DBConnection();
        return dBConnection.retrieve("Select patients.patient_id patient_id , patients.first_name first_name ,patients.last_name last_name , "
                + " patients.email_address email_address, patients.tel_num tel_num,"
                + " appointments.appt_id appt_id , appointments.appt_date appt_date , appointments.appt_time appt_time, appointments.appt_status appt_status "
                + " FROM patients,appointments"
                + " WHERE patients.patient_id = appointments.patient_id "
                + " AND patients.patient_id = '" + patientID + "' "
                + " ORDER BY appt_id ");
    }

    public ResultSet listAppointment() throws SQLException {
        dBConnection = new DBConnection();
        return dBConnection.retrieve("SELECT appointments.appt_id appt_id , patients.first_name first_name ,patients.last_name last_name , patients.email_address email_address, appointments.appt_date appt_date , appointments.appt_time appt_time, appointments.appt_status appt_status FROM patients,appointments WHERE patients.patient_id = appointments.patient_id ORDER BY appt_id");
    }

    public ResultSet loadSelectedAppointment(String patientID,String appointmentID) throws SQLException {
        dBConnection = new DBConnection();
        return dBConnection.retrieve("Select patients.patient_id patient_id , patients.first_name first_name ,patients.last_name last_name , "
                + " patients.email_address email_address, patients.tel_num tel_num,"
                + " appointments.appt_id appt_id , appointments.appt_date appt_date , appointments.appt_time appt_time, appointments.appt_status appt_status "
                + " FROM patients,appointments"
                + " WHERE patients.patient_id = appointments.patient_id "
                + " AND appointments.appt_id = '" + appointmentID + "' ");
    }


    @Override
    public void createAppointment(Appointment appointment) {
        try {
            dBConnection = new DBConnection();
            dBConnection.update("INSERT INTO appointments (patient_id,appt_date,appt_time,appt_status) VALUES ('" + appointment.getAppointmentPatient().getPatientID() + "','" + appointment.getAppointmentDate() + "','" + appointment.getAppointmentTime() + "','" + appointment.getAppointmentStatus() + "')");
            dBConnection.closeConnection();
        } catch(SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        try {
            dBConnection = new DBConnection();
            dBConnection.update("Delete FROM appointments WHERE appt_id = '" + appointment.getAppointmentID() + "'");
            dBConnection.closeConnection();
        } catch(SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        try {
            dBConnection = new DBConnection();
            dBConnection.update("UPDATE appointments SET appt_date ='" + appointment.getAppointmentDate() + "',appt_time='" + appointment.getAppointmentTime() + "',appt_status='" + appointment.getAppointmentStatus() +"' WHERE appt_id = '"+appointment.getAppointmentID()+"'");
            dBConnection.closeConnection();
        }
        catch (SQLException ex) {
            Logger.getLogger(PatientData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean duplicateAppointment(Appointment appointment){
        try {
            dBConnection = new DBConnection();
            ResultSet result = dBConnection.retrieve("SELECT COUNT(*) from appointments WHERE appt_date='" + appointment.getAppointmentDate() + "' AND appt_time ='" + appointment.getAppointmentTime() + "' ");
            int count = result.getInt(1);
            dBConnection.closeConnection();
            if (count >= 1){
                return false;
            }

        } catch(SQLException ex) {
            Logger.getLogger(AppointmentData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public ResultSet thisWeek() throws SQLException {
        dBConnection = new DBConnection();
        return dBConnection.retrieve("SELECT patients.first_name first_name ,patients.last_name last_name , appointments.appt_date appt_date , appointments.appt_time appt_time FROM patients,appointments WHERE patients.patient_id = appointments.patient_id AND appt_date = '04/13/22' OR appt_date = '04/14/22' OR appt_date = '04/15/22' OR appt_date = '04/16/22'  ORDER BY appt_id");
    }

    public ResultSet missedAppts() throws SQLException {
        dBConnection = new DBConnection();
        return dBConnection.retrieve("SELECT patients.first_name first_name ,patients.last_name last_name , appointments.appt_date appt_date , appointments.appt_time appt_time FROM patients,appointments WHERE patients.patient_id = appointments.patient_id AND appt_status = 'Missed' ORDER BY appt_id");
    }



}

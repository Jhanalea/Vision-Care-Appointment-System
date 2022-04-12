package com.visioncare.DAO;

import com.visioncare.model.Appointment;

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
        return dBConnection.retrieve("Select patients.patient_id patient_id , patients.first_name first_name ,patients.last_name last_name , "
                + " patients.email_address email_address, patients.tel_num tel_num,"
                + " appointments.appt_id appt_id , appointments.appt_date appt_date , appointments.appt_time appt_time, appointments.appt_status appt_status "
                + " FROM patients,appointments"
                + " WHERE patients.patient_id = appointments.patient_id "
                + "ORDER BY appt_id");
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
    public void deleteAppointment(String appointmentID) {
        try {
            dBConnection = new DBConnection();
            dBConnection.update("Delete FROM appointments WHERE id = '" + appointmentID + "'");
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

}

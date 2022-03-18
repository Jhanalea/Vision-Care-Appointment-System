package com.visioncare.controller;

import com.visioncare.model.Appointment;
import com.visioncare.model.Patient;
import com.visioncare.service.AppointmentService;
import java.sql.SQLException;
import java.util.List;

public class AppointmentController {

    AppointmentService appointmentService;

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


}
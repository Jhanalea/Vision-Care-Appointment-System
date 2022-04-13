package com.visioncare.service;

import com.visioncare.DAO.AppointmentData;
import com.visioncare.DAO.PatientData;
import com.visioncare.model.Appointment;
import com.visioncare.model.Patient;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentService implements AppointmentServiceInterface{
    AppointmentData appointmentData;
    PatientData patientData;

    public List<Appointment> listAppointmentForPatient(String patient) throws SQLException {
        appointmentData = new AppointmentData();
        ResultSet rs = appointmentData.listAppointmentForPatient(patient);
        List<Appointment> appointmentList = new ArrayList<>();

        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.getAppointmentPatient().setPatientID(rs.getInt("patient_id"));
            appointment.getAppointmentPatient().setFirstName(rs.getString("first_name"));
            appointment.getAppointmentPatient().setLastName(rs.getString("last_name"));
            appointment.getAppointmentPatient().setPhoneNumber(rs.getString("tel_num"));
            appointment.getAppointmentPatient().setEmailAddress(rs.getString("email_address"));
            appointment.setAppointmentID(rs.getInt("appt_id"));
            appointment.setAppointmentDate(rs.getString("appt_date"));
            appointment.setAppointmentTime(rs.getString("appt_time"));
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    public void sendEmailToPatient(Appointment appointment,String subject) throws SQLException, MessagingException {
        Patient emailPatient = this.loadPatientDetails(String.valueOf(appointment.getAppointmentPatient().getPatientID()));
        //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("comp2140project2021@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailPatient.getEmailAddress(), false));

        msg.setSubject(subject);
        msg.setText("Hello " + emailPatient.getFirstName() + "!" + "\n" + "Your upcoming appointment at Vision Care Centre has been scheduled for "
                + appointment.getAppointmentDate() + " at "
                + appointment.getAppointmentTime(), "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

        t.connect("smtp.gmail.com", "comp2140project2021", "softwareengineering");
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
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

    @Override
    public void createAppointment(Appointment appointment) {
        appointmentData = new AppointmentData();
        appointmentData.createAppointment(appointment);
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointmentData = new AppointmentData();
        appointmentData.deleteAppointment(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentData = new AppointmentData();
        appointmentData.updateAppointment(appointment);
    }

    public List<Appointment> listAppointmentTable() throws SQLException {
        appointmentData = new AppointmentData();

        ResultSet rs = appointmentData.listAppointment();
        List<Appointment> appointmentList = new ArrayList<>();

        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(rs.getInt("appt_id"));
            appointment.getAppointmentPatient().setFirstName(rs.getString("first_name"));
            appointment.getAppointmentPatient().setLastName(rs.getString("last_name"));
            appointment.getAppointmentPatient().setEmailAddress(rs.getString("email_address"));
            appointment.setAppointmentDate(rs.getString("appt_date"));
            appointment.setAppointmentTime(rs.getString("appt_time"));
            appointment.setAppointmentStatus(rs.getString("appt_status"));
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    public boolean duplicateAppointment(Appointment appointment){
        appointmentData = new AppointmentData();
        if (appointmentData.duplicateAppointment(appointment)){
            return true;
        }
        return false;
    }


    public Appointment loadSelectedAppointment(String patientID, String appointmentID) throws SQLException {
        appointmentData = new AppointmentData();
        ResultSet rs = appointmentData.loadSelectedAppointment(patientID, appointmentID);
        rs.next();

        Appointment appointment = new Appointment();
        appointment.getAppointmentPatient().setPatientID(rs.getInt("patient_id"));
        appointment.getAppointmentPatient().setFirstName(rs.getString("first_name"));
        appointment.getAppointmentPatient().setLastName(rs.getString("last_name"));
        appointment.getAppointmentPatient().setPhoneNumber(rs.getString("tel_num"));
        appointment.getAppointmentPatient().setEmailAddress(rs.getString("email_address"));
        appointment.setAppointmentDate(rs.getString("appt_date"));
        appointment.setAppointmentTime(rs.getString("appt_time"));
        appointment.setAppointmentStatus(rs.getString("appt_status"));
        appointment.setAppointmentID(rs.getInt("appt_id"));

        return appointment;
    }



}
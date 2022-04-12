package com.visioncare.model;

import javafx.beans.property.*;

import java.sql.Time;
import java.util.Date;


public class Appointment {



    private final IntegerProperty appointmentID = new SimpleIntegerProperty();
    private final ObjectProperty<Date> appointmentDate = new SimpleObjectProperty<>(this, "appointmentDate");
    private final StringProperty appointmentTime = new SimpleStringProperty();
    private final StringProperty appointmentStatus = new SimpleStringProperty();
    private final ObjectProperty<Patient> appointmentPatient = new SimpleObjectProperty<>(this, "appointmentPatient");

    public int getAppointmentID() {
        return appointmentID.get();
    }

    public IntegerProperty appointmentIDProperty() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID.set(appointmentID);
    }

    public Date getAppointmentDate() {
        return appointmentDate.get();
    }

    public ObjectProperty<Date> appointmentDateProperty() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate.set(appointmentDate);
    }

    public String getAppointmentTime() {
        return appointmentTime.get();
    }

    public StringProperty appointmentTimeProperty() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime.set(appointmentTime);
    }

    public String getAppointmentStatus() {
        return appointmentStatus.get();
    }

    public StringProperty appointmentStatusProperty() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus.set(appointmentStatus);
    }

    public Patient getAppointmentPatient() {
        return appointmentPatient.get();
    }

    public ObjectProperty<Patient> appointmentPatientProperty() {
        return appointmentPatient;
    }

    public void setAppointmentPatient(Patient appointmentPatient) {
        this.appointmentPatient.set(appointmentPatient);
    }
}












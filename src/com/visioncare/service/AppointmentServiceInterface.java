package com.visioncare.service;

import com.visioncare.model.Appointment;
import java.util.List;

public interface AppointmentServiceInterface {
    void createAppointment(Appointment appointment);

    void deleteAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

}

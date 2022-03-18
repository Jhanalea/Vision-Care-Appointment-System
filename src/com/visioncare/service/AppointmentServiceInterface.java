package com.visioncare.service;

import com.visioncare.model.Appointment;
import java.util.List;

public interface AppointmentServiceInterface {
    void createAppointment(Appointment appointment,String subject);

    void deleteAppointment(List<String> appointmentIDs);

    void updateAppointment(Appointment appointment,String subject);

}

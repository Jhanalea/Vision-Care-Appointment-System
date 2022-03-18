package com.visioncare.DAO;
import com.visioncare.model.Appointment;

public interface AppointmentDAOInterface {
        void createAppointment(Appointment appointment);
        void deleteAppointment(String appointmentId);
        void updateAppointment(Appointment appointment);

}


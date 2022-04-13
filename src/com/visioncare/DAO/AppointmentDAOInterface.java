package com.visioncare.DAO;
import com.visioncare.model.Appointment;

public interface AppointmentDAOInterface {
        void createAppointment(Appointment appointment);
        void deleteAppointment(Appointment appointment);
        void updateAppointment(Appointment appointment);

}


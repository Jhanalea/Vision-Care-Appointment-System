package com.visioncare.DAO;
import com.visioncare.model.Patient;

public interface PatientDAOInterface {
    void createPatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatient(Patient patient);

}

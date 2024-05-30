package com.example.hospital.daos;

import com.example.hospital.models.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientDao {
    List<Patient> getAllPatient();
    void save(Patient patient);
    void update(Patient patient);
    void delete(Patient patient);
    Optional<Patient> getPatientById(String patientId);
}

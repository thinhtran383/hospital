package com.example.hospital.daos;

import com.example.hospital.models.Doctor;
import com.example.hospital.models.Person;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

public interface IDoctorDao {
    List<Doctor> getAllDoctor();
    void save(Doctor doctor);
    void update(Doctor doctor);
    void delete(Doctor doctor);
    Optional<Doctor> getDoctorById(String doctorId);
}

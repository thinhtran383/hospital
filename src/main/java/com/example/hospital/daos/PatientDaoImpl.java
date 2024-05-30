package com.example.hospital.daos;

import com.example.hospital.models.Doctor;
import com.example.hospital.models.Patient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDaoImpl implements IPatientDao {
    private final String FILE_PATH = "src/main/resources/com/example/hospital/patient.txt";
    private final List<Patient> patients;

    public PatientDaoImpl() {
        patients = new ArrayList<>();
        readDataFromFile();
    }

    private void readDataFromFile() { // doc file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 8) {
                    Patient patient = getPatient(details);
                    patients.add(patient);

                }
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void writeDataToFile(List<Patient> patients) { // ghi file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Patient patient : patients) {
                String line = String.join(",",
                        patient.getName(),
                        patient.getAge(),
                        patient.getAddress(),
                        patient.getGender(),
                        patient.getPhoneNumber(),
                        patient.getPatientId(),
                        patient.getBloodGroup(),
                        patient.getMedicalHistory());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }


    @Override
    public List<Patient> getAllPatient() {
        return patients;
    } // lay danh sach benh nhan

    @Override
    public void save(Patient patient) { // them benh nhan
        patients.add(patient);
        writeDataToFile(patients);
    }

    @Override
    public void update(Patient patient) { // cap nhat benh nhan
        int index = patients.indexOf(patient);
        patients.set(index, patient);
        writeDataToFile(patients);
    }

    @Override
    public void delete(Patient patient) { // xoa benh nhan
        patients.remove(patient);
        writeDataToFile(patients);
    }

    @Override
    public Optional<Patient> getPatientById(String patientId) { // lay benh nhan theo id
        patients.clear();
        readDataFromFile();
        return patients.stream()
                .filter(patient -> patient.getPatientId().equals(patientId))
                .findFirst();
    }

    private Patient getPatient(String[] details) { // lay thong tin benh nhan
        String name = details[0];
        String age = details[1];
        String address = details[2];
        String gender = details[3];
        String phoneNumber = details[4];
        String patientId = details[5];
        String bloodGroup = details[6];
        String medialHistory = details[7];

        return new Patient(name, age, address, gender, phoneNumber, patientId, bloodGroup, medialHistory);
    }


}


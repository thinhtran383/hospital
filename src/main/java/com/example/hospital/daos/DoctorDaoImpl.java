package com.example.hospital.daos;

import com.example.hospital.models.Doctor;
import com.example.hospital.models.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDaoImpl implements IDoctorDao {
    private final List<Doctor> doctors; // danh sach cac bac si
    private final String FILE_PATH = "src/main/resources/com/example/hospital/doctor.txt"; // duong dan file chua thong tin bac si

    public DoctorDaoImpl() {
        this.doctors = new ArrayList<>();
        readDataFromFile();
    }

    private void readDataFromFile() { // doc file va them thong tin bac si vao danh sach
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 9) {
                    Doctor doctor = getDoctor(details);
                    doctors.add(doctor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Doctor getDoctor(String[] details) { // lay thong tin bac si tu file
        String name = details[0];
        String age = details[1];
        String address = details[2];
        String gender = details[3];
        String phoneNumber = details[4];
        String doctorId = details[5];
        String specialization = details[6];
        String department = details[7];
        String email = details[8];

        return new Doctor(name, age, address, gender, phoneNumber, doctorId, specialization, department, email);
    }

    private void writeDataToFile(List<Doctor> doctors) { // ghi danh sach bac si vao file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Doctor doctor : doctors) {
                String line = String.join(",",
                        doctor.getName(),
                        doctor.getAge(),
                        doctor.getAddress(),
                        doctor.getGender(),
                        doctor.getPhoneNumber(),
                        doctor.getDoctorId(),
                        doctor.getSpecialization(),
                        doctor.getDepartment(),
                        doctor.getEmail());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public List<Doctor> getAllDoctor() { // lay toan bo danh sach bs
        return doctors;
    }

    @Override
    public void save(Doctor doctor) { // luu thong tin bs
        doctors.add(doctor);
        writeDataToFile(doctors);
    }

    @Override
    public void update(Doctor doctor) { // cap nhat thong tin bs
        int index = doctors.indexOf(doctor);
        doctors.set(index, doctor);
        writeDataToFile(doctors);
    }

    @Override
    public void delete(Doctor doctor) { // xoa bs
        doctors.remove(doctor);
        writeDataToFile(doctors);
    }

    @Override
    public Optional<Doctor> getDoctorById(String doctorId) { // tim bs theo id
        doctors.clear();
        readDataFromFile();
        return doctors.stream()
                .filter(doctor -> doctor.getDoctorId().equals(doctorId))
                .findFirst();
    }
}

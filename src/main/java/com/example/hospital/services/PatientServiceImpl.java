package com.example.hospital.services;

import com.example.hospital.common.Regex;
import com.example.hospital.daos.IPatientDao;
import com.example.hospital.daos.IScheduleDao;
import com.example.hospital.daos.PatientDaoImpl;
import com.example.hospital.daos.ScheduleDaoImpl;
import com.example.hospital.models.Patient;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PatientServiceImpl implements IPatientService {
    private final IPatientDao patientDao;
    private final IScheduleDao scheduleDao;

    public PatientServiceImpl() {
        patientDao = new PatientDaoImpl();
        scheduleDao = new ScheduleDaoImpl();
    }

    @Override
    public void addPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        while (patientDao.getPatientById(patientId).isPresent()) {
            System.out.println("Patient ID already exists. Please enter a new Patient ID: ");
            patientId = scanner.nextLine();
        }

        System.out.println("Enter Patient Name: ");
        String name = scanner.nextLine();

        int age;
        while (true) {
            try {
                System.out.println("Enter Patient Age: ");
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a positive integer for age.");
            }
        }

        System.out.println("Enter Patient Gender: ");
        String gender = scanner.nextLine();
        System.out.println("Enter Patient Address: ");
        String address = scanner.nextLine();
        System.out.println("Enter Patient Phone Number: ");
        String phoneNumber = scanner.nextLine();

        while (true) {
            String finalPhoneNumber = phoneNumber;
            boolean isPhoneNumberExist = patientDao.getAllPatient().stream().anyMatch(patient -> patient.getPhoneNumber().equals(finalPhoneNumber));
            if (isPhoneNumberExist || !Regex.isValid(phoneNumber, "phone")) {
                System.out.println("Phone Number already exists or invalid. Please enter a new Phone Number: ");
                phoneNumber = scanner.nextLine();
            } else {
                break;
            }
        }

        System.out.println("Enter Patient Blood Group: ");
        String bloodGroup = scanner.nextLine();
        System.out.println("Enter Patient Medical History: ");
        String medicalHistory = scanner.nextLine();

        Patient newPatient = new Patient();
        newPatient.setPatientId(patientId);
        newPatient.setName(name);
        newPatient.setAge(Integer.toString(age));
        newPatient.setGender(gender);
        newPatient.setAddress(address);
        newPatient.setPhoneNumber(phoneNumber);
        newPatient.setBloodGroup(bloodGroup);
        newPatient.setMedicalHistory(medicalHistory);

        patientDao.save(newPatient);
    }

    @Override
    public void updatePatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Patient ID to update: ");
        String patientId = scanner.nextLine();
        Patient foundPatient = patientDao.getPatientById(patientId).orElse(null);

        if (foundPatient != null) {
            System.out.println("Enter new Patient Name: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                foundPatient.setName(name);
            }

            int age;
            while (true) {
                try {
                    System.out.println("Enter new Patient Age: ");
                    age = Integer.parseInt(scanner.nextLine());
                    if (age <= 0) {
                        throw new NumberFormatException();
                    }
                    foundPatient.setAge(Integer.toString(age));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age. Please enter a positive integer for age.");
                }
            }

            System.out.println("Enter new Patient Address: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                foundPatient.setAddress(address);
            }

            System.out.println("Enter new Patient Gender:");
            String gender = scanner.nextLine();
            if (!gender.isEmpty()) {
                foundPatient.setGender(gender);
            }

            System.out.println("Enter new Phone Number: ");
            String phoneNumber = scanner.nextLine();
            while (true) {
                String finalPhoneNumber = phoneNumber;
                boolean isPhoneNumberExist = patientDao.getAllPatient().stream().anyMatch(patient -> patient.getPhoneNumber().equals(finalPhoneNumber));
                if (finalPhoneNumber.isEmpty()) {
                    break;
                }
                if (isPhoneNumberExist || !Regex.isValid(phoneNumber, "phone")) {
                    System.out.println("Phone Number already exists. Please enter a new Phone Number: ");
                    phoneNumber = scanner.nextLine();
                } else {
                    break;
                }
            }

            if (!phoneNumber.isEmpty()) {
                foundPatient.setPhoneNumber(phoneNumber);
            }

            System.out.println("Enter new Blood Group: ");
            String bloodGroup = scanner.nextLine();
            if (!bloodGroup.isEmpty()) {
                foundPatient.setBloodGroup(bloodGroup);
            }

            System.out.println("Enter new Medical History: ");
            String medicalHistory = scanner.nextLine();
            if (!medicalHistory.isEmpty()) {
                foundPatient.setMedicalHistory(medicalHistory);
            }

            patientDao.update(foundPatient);
        } else {
            System.out.println("Patient not found.");
        }

    }


    @Override
    public void deletePatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Patient ID to delete: ");
        String patientId = scanner.nextLine();
        Optional<Patient> foundPatient = patientDao.getPatientById(patientId);

        if (foundPatient.isPresent()) {
            System.out.println("Are you sure you want to delete this patient and all their appointments? (y/n): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                scheduleDao.deleteScheduleByPatientId(patientId);

                patientDao.delete(foundPatient.get());
                System.out.println("Patient and all their appointments have been deleted.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }


    @Override
    public void getAllPatient() {
        List<Patient> patientList = patientDao.getAllPatient();
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Patient ID", "Patient Name", "Patient Age", "Gender", "Patient Address", "Phone Number", "Blood Group", "Medical History");
        table.addRule();

        patientList.forEach(patient -> {
            table.addRow(patient.getPatientId(), patient.getName(), patient.getAge(), patient.getGender(), patient.getAddress(), patient.getPhoneNumber(), patient.getBloodGroup(), patient.getMedicalHistory());
            table.addRule();
        });

        table.setTextAlignment(TextAlignment.CENTER);
        table.getRenderer().setCWC(new de.vandermeer.asciitable.CWC_LongestWordMin(new int[]{10, 20, 10, 20, 15, 15, 20, 20}));
        String renderedTable = table.render();
        System.out.println(renderedTable);
    }

}

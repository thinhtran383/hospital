package com.example.hospital.services;

import com.example.hospital.common.Regex;
import com.example.hospital.daos.DoctorDaoImpl;
import com.example.hospital.daos.IDoctorDao;
import com.example.hospital.daos.IScheduleDao;
import com.example.hospital.daos.ScheduleDaoImpl;
import com.example.hospital.models.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;


public class DoctorServiceImpl implements IDoctorServices {
    private final IDoctorDao doctorDao; // thao tac voi du lieu cua bs
    private final IScheduleDao scheduleDao; // thao tac voi du lieu cua lich lam viec

    public DoctorServiceImpl() {
        doctorDao = new DoctorDaoImpl();
        scheduleDao = new ScheduleDaoImpl();
    }

    @Override
    public void addDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        while (doctorDao.getDoctorById(doctorId).isPresent()) {
            System.out.println("Doctor ID already exists. Please enter a new Doctor ID: ");
            doctorId = scanner.nextLine();
        }

        System.out.println("Enter Doctor Name: ");
        String name = scanner.nextLine();

        int age;
        while (true) {
            try {
                System.out.println("Enter Doctor Age: ");
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a positive integer for age.");
            }
        }

        System.out.println("Enter Doctor Address: ");
        String address = scanner.nextLine();
        System.out.println("Enter Doctor Gender: ");
        String gender = scanner.nextLine();

        System.out.println("Enter Doctor Phone Number: ");
        String phoneNumber = scanner.nextLine();
        while (true) { // so dien thoai va email thi check = regex
            String finalPhoneNumber = phoneNumber;
            boolean isPhoneNumberExist = doctorDao.getAllDoctor().stream().anyMatch(doctor -> doctor.getPhoneNumber().equals(finalPhoneNumber));
            if (isPhoneNumberExist || !Regex.isValid(phoneNumber, "phone")) {
                System.out.println("Phone Number already exists or invalid. Please enter a new Phone Number: ");
                phoneNumber = scanner.nextLine();
            } else {
                break;
            }
        }

        System.out.println("Enter Doctor Specialization: ");
        String specialization = scanner.nextLine();
        System.out.println("Enter Doctor Department: ");
        String department = scanner.nextLine();
        System.out.println("Enter Doctor Email: ");
        String email = scanner.nextLine();

        while (true) {
            String finalEmail = email;
            boolean isEmailExist = doctorDao.getAllDoctor().stream().anyMatch(doctor -> doctor.getEmail().equals(finalEmail));
            if (isEmailExist || !Regex.isValid(email, "email")) {
                System.out.println("Email already exists or invalid. Please enter a new Email: ");
                email = scanner.nextLine();
            } else {
                break;
            }
        }

        Doctor doctor = new Doctor(name, Integer.toString(age), address, gender, phoneNumber, doctorId, specialization, department, email);
        doctorDao.save(doctor);
    }



    @Override
    public void updateDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter doctor ID to update: ");
        String doctorId = scanner.nextLine();
        Optional<Doctor> foundDoctor = doctorDao.getDoctorById(doctorId);

        if (foundDoctor.isPresent()) {
            Doctor doctor = foundDoctor.get();
            System.out.println("Enter new Doctor Name: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                doctor.setName(name);
            }

            int age;
            while (true) {
                try {
                    System.out.println("Enter new Doctor Age: ");
                    age = Integer.parseInt(scanner.nextLine());
                    if (age <= 0) {
                        throw new NumberFormatException();
                    }
                    doctor.setAge(Integer.toString(age));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age. Please enter a positive integer for age.");
                }
            }

            System.out.println("Enter new Doctor Address: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                doctor.setAddress(address);
            }

            System.out.println("Enter new gender: ");
            String gender = scanner.nextLine();
            if (!gender.isEmpty()) {
                doctor.setGender(gender);
            }

            System.out.println("Enter new Phone Number: ");
            String phoneNumber = scanner.nextLine();

            while (true) {
                String finalPhoneNumber = phoneNumber;
                boolean isPhoneNumberExist = doctorDao.getAllDoctor().stream().anyMatch(d -> d.getPhoneNumber().equals(finalPhoneNumber));

                if (phoneNumber.isEmpty()) {
                    break;
                }

                if (isPhoneNumberExist || !Regex.isValid(phoneNumber, "phone")) {
                    System.out.println("Phone Number already exists or invalid. Please enter a new Phone Number: ");
                    phoneNumber = scanner.nextLine();
                } else {
                    break;
                }
            }

            if (!phoneNumber.isEmpty()) {
                doctor.setPhoneNumber(phoneNumber);
            }

            System.out.println("Enter new Specialization: ");
            String specialization = scanner.nextLine();
            if (!specialization.isEmpty()) {
                doctor.setSpecialization(specialization);
            }

            System.out.println("Enter new Department: ");
            String department = scanner.nextLine();
            if (!department.isEmpty()) {
                doctor.setDepartment(department);
            }

            System.out.println("Enter new Email: ");
            String email = scanner.nextLine();

            while (true) {
                String finalEmail = email;

                if (email.isEmpty()) {
                    break;
                }

                boolean isEmailExist = doctorDao.getAllDoctor().stream().anyMatch(d -> d.getEmail().equals(finalEmail));
                if (isEmailExist || !Regex.isValid(email, "email")) {
                    System.out.println("Email already exists or invalid. Please enter a new Email: ");
                    email = scanner.nextLine();
                } else {
                    break;
                }
            }

            if (!email.isEmpty()) {
                doctor.setEmail(email);
            }

            doctorDao.update(doctor);
        } else {
            System.out.println("Doctor ID not existed!");
        }
    }


    @Override
    public void deleteDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter doctor ID to delete: ");
        String doctorId = scanner.nextLine();
        Optional<Doctor> foundDoctor = doctorDao.getDoctorById(doctorId);

        if(foundDoctor.isPresent()){
            System.out.println("Are you sure you want to delete this doctor? (Y/N)");
            String confirm = scanner.nextLine();

            if(confirm.equalsIgnoreCase("Y")){
                doctorDao.delete(foundDoctor.get());
                scheduleDao.deleteScheduleByDoctorId(doctorId);
                System.out.println("Doctor deleted successfully!");
            } else {
                System.out.println("Doctor not deleted!");
            }
        }

    }

    @Override
    public void getAllDoctor() {
        List<Doctor> doctors = doctorDao.getAllDoctor();

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Doctor ID", "Name", "Age", "Address", "Gender", "Phone Number",
                "Email", "Specialization", "Department");
        table.addRule();

        doctors.forEach(doctor -> {
            table.addRow(doctor.getDoctorId(), doctor.getName(), doctor.getAge(), doctor.getAddress(), doctor.getGender(),
                    doctor.getPhoneNumber(), doctor.getEmail(), doctor.getSpecialization(), doctor.getDepartment());
            table.addRule();
        });

        table.setTextAlignment(TextAlignment.CENTER);
        table.getRenderer().setCWC(new de.vandermeer.asciitable.CWC_LongestWordMin(new int[]{10, 5, 15, 10, 15, 10, 15, 15, 25}));
        String rend = table.render();
        System.out.println(rend);
    }


}

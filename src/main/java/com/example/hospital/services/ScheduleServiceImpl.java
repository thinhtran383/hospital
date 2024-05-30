package com.example.hospital.services;

import com.example.hospital.common.Regex;
import com.example.hospital.daos.*;
import com.example.hospital.models.Doctor;
import com.example.hospital.models.Patient;
import com.example.hospital.models.Schedule;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ScheduleServiceImpl implements IScheduleService {
    private final IScheduleDao scheduleDao;
    private final IDoctorDao doctorDao;
    private final IPatientDao patientDao;
    private final EmailService emailService;

    public ScheduleServiceImpl() {
        scheduleDao = new ScheduleDaoImpl();
        doctorDao = new DoctorDaoImpl();
        patientDao = new PatientDaoImpl();
        emailService = new EmailService("smtp.gmail.com");
    }

    @Override
    public void getAllSchedule() {
        List<Schedule> schedules = scheduleDao.getAllSchedule();
        List<Doctor> doctors = doctorDao.getAllDoctor();
        List<Patient> patients = patientDao.getAllPatient();

        schedules.forEach(schedule -> {
            doctors.stream()
                    .filter(doctor -> schedule.getDoctorId().equals(doctor.getDoctorId()))
                    .findFirst()
                    .ifPresent(doctor -> schedule.setDoctorName(doctor.getName()));

            patients.stream()
                    .filter(patient -> schedule.getPatientId().equals(patient.getPatientId()))
                    .findFirst()
                    .ifPresent(patient -> schedule.setPatientName(patient.getName()));
        });

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Schedule ID", "Patient Name", "Doctor Name", "Date", "Location");
        at.addRule();
        schedules.forEach(schedule -> {
            at.addRow(schedule.getScheduleId(), schedule.getPatientName(), schedule.getDoctorName(), schedule.getDatetime(), schedule.getLocation());
            at.addRule();
        });
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new de.vandermeer.asciitable.CWC_LongestWordMin(new int[]{10, 15, 15, 15, 15}));
        System.out.println(at.render());
    }

    @Override
    public void saveSchedule() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Schedule ID: ");
        String scheduleId = scanner.nextLine();
        System.out.println("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        while (patientDao.getPatientById(patientId.trim()).isEmpty()) {
            System.out.println("Patient ID does not exist. Please enter a valid Patient ID: ");
            patientId = scanner.nextLine();
        }

        System.out.println("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        while (doctorDao.getDoctorById(doctorId).isEmpty()) {
            System.out.println("Doctor ID does not exist. Please enter a valid Doctor ID: ");
            doctorId = scanner.nextLine();
        }

        System.out.println("Enter Date and Time(dd/MM/yyyy hh:mm): ");
        String datetime = scanner.nextLine();
        while (!Regex.isValid(datetime, "datetime")) {
            System.out.println("Invalid Date and Time format. Please enter a valid Date and Time(dd/MM/yyyy hh:mm): ");
            datetime = scanner.nextLine();
        }

        if (isPatientScheduleConflict(patientId, datetime)) {
            System.out.println("Patient already has a schedule at this date and time. Please enter a different Date and Time: ");
            return;
        }

        System.out.println("Enter Location: ");
        String location = scanner.nextLine();
        System.out.println("Schedule added successfully!");

        Schedule schedule = new Schedule(scheduleId, patientId, doctorId, datetime, location);
        scheduleDao.save(schedule);

        Optional<Doctor> doctorOptional = doctorDao.getDoctorById(doctorId);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            String subject = "New Schedule Added";
            String message = "A new schedule has been added with details: " +
                    "\nPatient ID: " + patientId +
                    "\nDate and Time: " + datetime +
                    "\nLocation: " + location;

            emailService.sendMail(doctor.getEmail(), subject, message);
            emailService.shutdown();
        }
    }

    @Override
    public void updateSchedule() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Schedule ID to update: ");
        String scheduleId = scanner.nextLine();
        while (scheduleDao.getScheduleById(scheduleId).isEmpty()) {
            System.out.println("Schedule ID does not exist. Please enter a valid Schedule ID: ");
            scheduleId = scanner.nextLine();
        }

        System.out.println("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        while (patientDao.getPatientById(patientId).isEmpty()) {
            System.out.println("Patient ID does not exist. Please enter a valid Patient ID: ");
            patientId = scanner.nextLine();
        }

        System.out.println("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        while (doctorDao.getDoctorById(doctorId).isEmpty()) {
            System.out.println("Doctor ID does not exist. Please enter a valid Doctor ID: ");
            doctorId = scanner.nextLine();
        }

        System.out.println("Enter new Date and Time(dd/MM/yyyy hh:mm): ");
        String datetime = scanner.nextLine();
        while (!Regex.isValid(datetime, "datetime")) {
            System.out.println("Invalid Date and Time format. Please enter a valid Date and Time(dd/MM/yyyy hh:mm): ");
            datetime = scanner.nextLine();
        }

        if (isPatientScheduleConflict(patientId, datetime, scheduleId)) {
            System.out.println("Patient already has a schedule at this date and time. Please enter a different Date and Time: ");
            return;
        }

        System.out.println("Enter new Location: ");
        String location = scanner.nextLine();
        System.out.println("Schedule updated successfully!");

        Schedule schedule = new Schedule(scheduleId, patientId, doctorId, datetime, location);
        scheduleDao.update(schedule);

        Optional<Doctor> doctorOpt = doctorDao.getDoctorById(doctorId);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            String subject = "Schedule Updated";
            String message = "A schedule has been updated with new details: " +
                    "\nPatient ID: " + patientId +
                    "\nDate and Time: " + datetime +
                    "\nLocation: " + location;
            emailService.sendMail(doctor.getEmail(), subject, message);
            emailService.shutdown();
        }
    }

    @Override
    public void deleteSchedule() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Schedule ID to delete: ");
        String scheduleId = scanner.nextLine();
        while (scheduleDao.getScheduleById(scheduleId).isEmpty()) {
            System.out.println("Schedule ID does not exist. Please enter a valid Schedule ID: ");
            scheduleId = scanner.nextLine();
        }
        Optional<Schedule> scheduleOpt = scheduleDao.getScheduleById(scheduleId);
        if (scheduleOpt.isPresent()) {
            Schedule schedule = scheduleOpt.get();
            String patientId = schedule.getPatientId();
            Optional<Patient> patientOpt = patientDao.getPatientById(patientId);
            if (patientOpt.isPresent()) {
                Patient patient = patientOpt.get();
                // Send email notification to doctor
                Optional<Doctor> doctorOpt = doctorDao.getDoctorById(schedule.getDoctorId());
                if (doctorOpt.isPresent()) {
                    Doctor doctor = doctorOpt.get();
                    String subject = "Schedule Deleted";
                    String message = "A schedule has been deleted for Patient: " +
                            patient.getName() +
                            "\nDate and Time: " + schedule.getDatetime() +
                            "\nLocation: " + schedule.getLocation();
                    emailService.sendMail(doctor.getEmail(), subject, message);
                    emailService.shutdown();
                }
            }
            scheduleDao.delete(schedule);

        }


    }

    private boolean isPatientScheduleConflict(String patientId, String datetime) {
        List<Schedule> schedules = scheduleDao.getAllSchedule();
        return schedules.stream()
                .anyMatch(schedule -> schedule.getPatientId().equals(patientId) && schedule.getDatetime().equals(datetime));
    }

    private boolean isPatientScheduleConflict(String patientId, String datetime, String scheduleId) {
        List<Schedule> schedules = scheduleDao.getAllSchedule();
        return schedules.stream()
                .anyMatch(schedule -> !schedule.getScheduleId().equals(scheduleId) && schedule.getPatientId().equals(patientId) && schedule.getDatetime().equals(datetime));
    }
}

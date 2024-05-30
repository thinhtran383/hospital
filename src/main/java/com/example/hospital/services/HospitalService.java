package com.example.hospital.services;


import java.util.Scanner;

public class HospitalService {
    private final IDoctorServices doctorServices;
    private final IPatientService patientServices;
    private final IScheduleService scheduleServices;

    public HospitalService() {
        doctorServices = new DoctorServiceImpl();
        patientServices = new PatientServiceImpl();
        scheduleServices = new ScheduleServiceImpl();
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            printHeader("Main Menu");
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Schedule Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    patientMenu(scanner);
                    break;
                case 2:
                    doctorMenu(scanner);
                    break;
                case 3:
                    scheduleMenu(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public void patientMenu(Scanner scanner) {
        int choice;
        do {
            printHeader("Patient Management");
            System.out.println("1. View Patients");
            System.out.println("2. Add Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    patientServices.getAllPatient();
                    break;
                case 2:
                    patientServices.addPatient();
                    break;
                case 3:
                    patientServices.updatePatient();
                    break;
                case 4:
                    patientServices.deletePatient();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    public void doctorMenu(Scanner scanner) {
        int choice;
        do {
            printHeader("Doctor Management");
            System.out.println("1. View Doctors");
            System.out.println("2. Add Doctor");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    doctorServices.getAllDoctor();
                    break;
                case 2:
                    doctorServices.addDoctor();
                    break;
                case 3:
                    doctorServices.updateDoctor();
                    break;
                case 4:
                    doctorServices.deleteDoctor();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    public void scheduleMenu(Scanner scanner) {
        int choice;
        do {
            printHeader("Schedule Management");
            System.out.println("1. View Schedule");
            System.out.println("2. Add Schedule");
            System.out.println("3. Update Schedule");
            System.out.println("4. Delete Schedule");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    scheduleServices.getAllSchedule();
                    break;
                case 2:
                    scheduleServices.saveSchedule();
                    break;
                case 3:
                    scheduleServices.updateSchedule();
                    break;
                case 4:
                    scheduleServices.deleteSchedule();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }



    private void printHeader(String title) {
        int width = 50;
        String border = "=".repeat(width);
        int paddingSize = (width - title.length()) / 2;
        String padding = " ".repeat(paddingSize);
        String header = padding + title + padding;
        if (header.length() < width) {
            header += " ";
        }
        System.out.println(border);
        System.out.println(header);
        System.out.println(border);
    }


}

package com.example.hospital;

import com.example.hospital.services.HospitalService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        HospitalService hospitalService = new HospitalService();
        hospitalService.mainMenu();
    }

}

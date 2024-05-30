package com.example.hospital.daos;


import com.example.hospital.models.Schedule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleDaoImpl implements IScheduleDao {
    private final String FILE_PATH = "src/main/resources/com/example/hospital/schedule.txt";
    private final List<Schedule> schedules;

    public ScheduleDaoImpl() {
        schedules = new ArrayList<>();
        readDataFromFile();
    }

    private void readDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 5) {
                    Schedule schedule = getSchedule(details);
                    schedules.add(schedule);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void writeDataToFile(List<Schedule> schedules) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Schedule schedule : schedules) {
                String line = String.join(",",
                        schedule.getScheduleId(),
                        schedule.getPatientId(),
                        schedule.getDoctorId(),
                        schedule.getDatetime(),
                        schedule.getLocation());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private Schedule getSchedule(String[] details) {
        String scheduleId = details[0];
        String patientId = details[1];
        String doctorId = details[2];
        String datetime = details[3];
        String location = details[4];

        return new Schedule(scheduleId,patientId, doctorId, datetime, location);
    }


    @Override
    public List<Schedule> getAllSchedule() { // lay toan bo lich kham
        return schedules;
    }

    @Override
    public void save(Schedule schedule) { // them lich kham
        schedules.add(schedule);
        writeDataToFile(schedules);
    }

    @Override
    public void delete(Schedule schedule) { // xoa lich kham
        schedules.remove(schedule);
        writeDataToFile(schedules);
    }

    @Override
    public void update(Schedule schedule) { // cap nhat lich kham
        int index = schedules.indexOf(schedule);
        schedules.set(index, schedule);
        writeDataToFile(schedules);
    }

    @Override
    public Optional<Schedule> getScheduleById(String scheduleId) { // lay lich kham theo id
        return schedules.stream()
                .filter(schedule -> schedule.getScheduleId().equals(scheduleId))
                .findFirst();
    }

    @Override
    public void deleteScheduleByPatientId(String patientId) { // xoa lich kham theo id benh nhan
        schedules.removeIf(schedule -> schedule.getPatientId().equals(patientId));
        writeDataToFile(schedules);
    }

    @Override
    public void deleteScheduleByDoctorId(String doctorId) { // xoa lich kham theo id bac si
        schedules.removeIf(schedule -> schedule.getDoctorId().equals(doctorId));
        writeDataToFile(schedules);
    }
}

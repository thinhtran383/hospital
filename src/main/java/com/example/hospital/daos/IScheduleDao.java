package com.example.hospital.daos;

import com.example.hospital.models.Schedule;

import java.util.List;
import java.util.Optional;

public interface IScheduleDao {
    List<Schedule> getAllSchedule();
    void save(Schedule schedule);
    void delete(Schedule schedule);
    void update(Schedule schedule);

   Optional<Schedule> getScheduleById(String scheduleId);

    void deleteScheduleByPatientId(String patientId);

    void deleteScheduleByDoctorId(String doctorId);
}

package com.hospitalmanagement.dao;

import com.hospitalmanagement.entity.Appointment;
import java.util.List;

public interface IHospitalService {

    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAppointmentsForPatient(int patientId);

    List<Appointment> getAppointmentsForDoctor(int doctorId);

    boolean scheduleAppointment(Appointment appointment);

    boolean updateAppointment(Appointment appointment);

    boolean cancelAppointment(int appointmentId);
}

package com.hospitalmanagement.dao;

import com.hospitalmanagement.entity.Appointment;
import com.hospitalmanagement.util.DBConnUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalServiceImpl implements IHospitalService {

    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        String query = "INSERT INTO appointments (patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            stmt.setString(4, appointment.getDescription());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        Appointment appointment = null;
        String query = "SELECT * FROM appointments WHERE appointmentId = ?";
        try (Connection connection = DBConnUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                appointment = new Appointment(
                        rs.getInt("appointmentId"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId"),
                        rs.getDate("appointmentDate").toLocalDate(),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving appointment: " + e.getMessage());
        }
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE patientId = ?";
        try (Connection connection = DBConnUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("appointmentId"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId"),
                        rs.getDate("appointmentDate").toLocalDate(),
                        rs.getString("description")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving appointments for patient: " + e.getMessage());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE doctorId = ?";
        try (Connection connection = DBConnUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("appointmentId"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId"),
                        rs.getDate("appointmentDate").toLocalDate(),
                        rs.getString("description")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving appointments for doctor: " + e.getMessage());
        }
        return appointments;
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        String query = "DELETE FROM appointments WHERE appointmentId = ?";
        try (Connection connection = DBConnUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error canceling appointment: " + e.getMessage());
            return false;
        }
    }

	@Override
	public boolean updateAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		return false;
	}
}

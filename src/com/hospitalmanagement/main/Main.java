package com.hospitalmanagement.main;

import com.hospitalmanagement.dao.HospitalServiceImpl;
import com.hospitalmanagement.dao.IHospitalService;
import com.hospitalmanagement.entity.Appointment;
import com.hospitalmanagement.exception.PatientNumberNotFoundException;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final IHospitalService hospitalService = new HospitalServiceImpl();

    public static void main(String[] args) {
        System.out.println("Welcome to Hospital Management System!");

        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> viewAppointmentById();
                case 2 -> viewAppointmentsForPatient();
                case 3 -> viewAppointmentsForDoctor();
                case 4 -> scheduleAppointment();
                case 5 -> updateAppointment();
                case 6 -> cancelAppointment();
                case 7 -> {
                    exit = true;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. View Appointment by ID");
        System.out.println("2. View Appointments for Patient");
        System.out.println("3. View Appointments for Doctor");
        System.out.println("4. Schedule Appointment");
        System.out.println("5. Update Appointment");
        System.out.println("6. Cancel Appointment");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); 
        }
        return scanner.nextInt();
    }

    private static void viewAppointmentById() {
        System.out.print("Enter Appointment ID: ");
        int appointmentId = scanner.nextInt();
        try {
            Appointment appointment = hospitalService.getAppointmentById(appointmentId);
            System.out.println("Appointment Details: " + appointment);
        } catch (Exception e) {
            System.err.println("Error retrieving appointment: " + e.getMessage());
        }
    }

    private static void viewAppointmentsForPatient() {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        try {
            List<Appointment> appointments = hospitalService.getAppointmentsForPatient(patientId);
            if (appointments.isEmpty()) {
                throw new PatientNumberNotFoundException("No appointments found for Patient ID: " + patientId);
            }
            appointments.forEach(System.out::println);
        } catch (PatientNumberNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error retrieving appointments for patient: " + e.getMessage());
        }
    }

    private static void viewAppointmentsForDoctor() {
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        try {
            List<Appointment> appointments = hospitalService.getAppointmentsForDoctor(doctorId);
            if (appointments.isEmpty()) {
                System.out.println("No appointments found for Doctor ID: " + doctorId);
            } else {
                appointments.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving appointments for doctor: " + e.getMessage());
        }
    }

    private static void scheduleAppointment() {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
        String date = scanner.next();
        System.out.print("Enter Description: ");
        scanner.nextLine(); 
        String description = scanner.nextLine();

        try {
            Appointment appointment = new Appointment(0, patientId, doctorId, java.time.LocalDate.parse(date), description);
            boolean isScheduled = hospitalService.scheduleAppointment(appointment);
            System.out.println(isScheduled ? "Appointment Scheduled Successfully!" : "Failed to schedule appointment.");
        } catch (Exception e) {
            System.err.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    private static void updateAppointment() {
        System.out.print("Enter Appointment ID to update: ");
        int appointmentId = scanner.nextInt();
        System.out.print("Enter new Description: ");
        scanner.nextLine(); 
        String newDescription = scanner.nextLine();

        try {
            Appointment appointment = hospitalService.getAppointmentById(appointmentId);
            appointment.setDescription(newDescription);
            boolean isUpdated = hospitalService.updateAppointment(appointment);
            System.out.println(isUpdated ? "Appointment Updated Successfully!" : "Failed to update appointment.");
        } catch (Exception e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.print("Enter Appointment ID to cancel: ");
        int appointmentId = scanner.nextInt();
        try {
            boolean isCancelled = hospitalService.cancelAppointment(appointmentId);
            System.out.println(isCancelled ? "Appointment Cancelled Successfully!" : "Failed to cancel appointment.");
        } catch (Exception e) {
            System.err.println("Error canceling appointment: " + e.getMessage());
        }
    }
}

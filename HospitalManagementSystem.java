import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * HospitalManagementSystem
 * -------------------------
 * Console-based application to manage patients, doctors, appointments and billing.
 * Uses Java Collections (ArrayList) to store and manage all hospital records,
 * and demonstrates OOP concepts: Encapsulation, Inheritance, Abstraction, Polymorphism.
 */
public class HospitalManagementSystem {

    private static final ArrayList<Patient> patients = new ArrayList<>();
    private static final ArrayList<Doctor> doctors = new ArrayList<>();
    private static final ArrayList<Appointment> appointments = new ArrayList<>();
    private static final ArrayList<Bill> bills = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedSampleData(); // demo data so the menu isn't empty on first run
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addPatient();
                case 2 -> addDoctor();
                case 3 -> viewAllPatients();
                case 4 -> viewAllDoctors();
                case 5 -> scheduleAppointment();
                case 6 -> viewAllAppointments();
                case 7 -> generateBill();
                case 8 -> viewAllBills();
                case 9 -> searchPatientById();
                case 10 -> dischargePatient();
                case 11 -> payBill();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting Hospital Management System. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n================ HOSPITAL MANAGEMENT SYSTEM ================");
        System.out.println(" 1. Add Patient");
        System.out.println(" 2. Add Doctor");
        System.out.println(" 3. View All Patients");
        System.out.println(" 4. View All Doctors");
        System.out.println(" 5. Schedule Appointment");
        System.out.println(" 6. View All Appointments");
        System.out.println(" 7. Generate Bill");
        System.out.println(" 8. View All Bills");
        System.out.println(" 9. Search Patient by ID");
        System.out.println("10. Discharge (Remove) Patient");
        System.out.println("11. Pay Bill");
        System.out.println(" 0. Exit");
        System.out.println("==============================================================");
    }

    // ---------------- Patient management ----------------

    private static void addPatient() {
        System.out.println("\n-- Add New Patient --");
        String name = readString("Name: ");
        int age = readInt("Age: ");
        String gender = readString("Gender: ");
        String contact = readString("Contact Number: ");
        String disease = readString("Disease/Reason for visit: ");

        Patient patient = new Patient(name, age, gender, contact, disease);
        patients.add(patient);
        System.out.println("Patient registered successfully with ID: " + patient.getPatientId());
    }

    private static void viewAllPatients() {
        System.out.println("\n-- All Registered Patients --");
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        // Polymorphism: calling displayDetails() on each Patient (a Person subtype)
        for (Patient p : patients) {
            p.displayDetails();
        }
    }

    private static void searchPatientById() {
        String id = readString("\nEnter Patient ID to search: ");
        Patient found = findPatientById(id);
        if (found != null) {
            found.displayDetails();
        } else {
            System.out.println("No patient found with ID: " + id);
        }
    }

    private static void dischargePatient() {
        String id = readString("\nEnter Patient ID to discharge/remove: ");
        Patient found = findPatientById(id);
        if (found == null) {
            System.out.println("No patient found with ID: " + id);
            return;
        }

        // Collect this patient's unpaid bills. A patient can only be discharged
        // once every one of their bills is marked PAID.
        List<Bill> unpaidBills = new ArrayList<>();
        double unpaidTotal = 0.0;
        for (Bill b : bills) {
            if (b.getPatient().getPatientId().equalsIgnoreCase(id) && !b.isPaid()) {
                unpaidBills.add(b);
                unpaidTotal += b.getAmount();
            }
        }

        if (!unpaidBills.isEmpty()) {
            System.out.println("Cannot discharge " + found.getName() + " (" + id + "): outstanding bills remain.");
            System.out.printf("Total unpaid amount: $%.2f%n", unpaidTotal);
            System.out.println("Unpaid bills:");
            for (Bill b : unpaidBills) {
                b.displayDetails();
            }
            System.out.println("Use option 11 (Pay Bill) to clear these dues, then try discharging again.");
            return;
        }

        patients.remove(found);
        System.out.println("All bills for " + found.getName() + " (" + id + ") are paid. Patient has been discharged.");
    }

    private static void payBill() {
        if (bills.isEmpty()) {
            System.out.println("\nNo bills found.");
            return;
        }
        int billId = readInt("\nEnter Bill ID to mark as paid: ");
        Bill found = null;
        for (Bill b : bills) {
            if (b.getBillId() == billId) {
                found = b;
                break;
            }
        }
        if (found == null) {
            System.out.println("No bill found with ID: " + billId);
            return;
        }
        if (found.isPaid()) {
            System.out.println("Bill " + billId + " is already marked as paid.");
            return;
        }
        found.markAsPaid();
        System.out.printf("Bill %d for %s marked as PAID ($%.2f).%n",
                billId, found.getPatient().getName(), found.getAmount());
    }

    private static Patient findPatientById(String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // ---------------- Doctor management ----------------

    private static void addDoctor() {
        System.out.println("\n-- Add New Doctor --");
        String name = readString("Name: ");
        int age = readInt("Age: ");
        String gender = readString("Gender: ");
        String contact = readString("Contact Number: ");
        String specialization = readString("Specialization: ");

        Doctor doctor = new Doctor(name, age, gender, contact, specialization);
        doctors.add(doctor);
        System.out.println("Doctor registered successfully with ID: " + doctor.getDoctorId());
    }

    private static void viewAllDoctors() {
        System.out.println("\n-- All Registered Doctors --");
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        // Sort doctors alphabetically by name using Collections/Comparator
        List<Doctor> sorted = new ArrayList<>(doctors);
        sorted.sort(Comparator.comparing(Doctor::getName));
        for (Doctor d : sorted) {
            d.displayDetails();
        }
    }

    private static Doctor findDoctorById(String id) {
        for (Doctor d : doctors) {
            if (d.getDoctorId().equalsIgnoreCase(id)) {
                return d;
            }
        }
        return null;
    }

    // ---------------- Appointment management ----------------

    private static void scheduleAppointment() {
        if (patients.isEmpty() || doctors.isEmpty()) {
            System.out.println("You need at least one patient and one doctor before scheduling.");
            return;
        }
        String patientId = readString("\nEnter Patient ID: ");
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        String doctorId = readString("Enter Doctor ID: ");
        Doctor doctor = findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        String date = readString("Appointment Date (DD-MM-YYYY): ");
        String time = readString("Appointment Time (HH:MM): ");

        Appointment appointment = new Appointment(patient, doctor, date, time);
        appointments.add(appointment);
        System.out.println("Appointment scheduled successfully with ID: " + appointment.getAppointmentId());
    }

    private static void viewAllAppointments() {
        System.out.println("\n-- All Appointments --");
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment a : appointments) {
            a.displayDetails();
        }
    }

    // ---------------- Billing management ----------------

    private static void generateBill() {
        if (patients.isEmpty()) {
            System.out.println("No patients available to bill.");
            return;
        }
        String patientId = readString("\nEnter Patient ID: ");
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        String description = readString("Bill Description (e.g., Consultation, Surgery, Medicines): ");
        double amount = readDouble("Amount: $");

        Bill bill = new Bill(patient, description, amount);
        bills.add(bill);
        System.out.println("Bill generated successfully with ID: " + bill.getBillId());
    }

    private static void viewAllBills() {
        System.out.println("\n-- All Bills --");
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }
        double totalBilled = 0;
        double totalPaid = 0;
        for (Bill b : bills) {
            b.displayDetails();
            totalBilled += b.getAmount();
            if (b.isPaid()) {
                totalPaid += b.getAmount();
            }
        }
        double totalOutstanding = totalBilled - totalPaid;
        System.out.println("=================================================");
        System.out.printf("Total Billed       : $%.2f%n", totalBilled);
        System.out.printf("Total Paid         : $%.2f%n", totalPaid);
        System.out.printf("Total Outstanding  : $%.2f%n", totalOutstanding);
    }

    // ---------------- Input helpers ----------------

    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // ---------------- Optional demo data ----------------

    private static void seedSampleData() {
        Patient p1 = new Patient("Ravi Kumar", 34, "Male", "9876543210", "Fever");
        Patient p2 = new Patient("Sita Reddy", 28, "Female", "9123456780", "Fracture");
        patients.add(p1);
        patients.add(p2);

        Doctor d1 = new Doctor("Anil Sharma", 45, "Male", "9988776655", "General Medicine");
        Doctor d2 = new Doctor("Priya Nair", 39, "Female", "9871122334", "Orthopedics");
        doctors.add(d1);
        doctors.add(d2);

        appointments.add(new Appointment(p1, d1, "01-10-2026", "10:30"));
        bills.add(new Bill(p2, "X-Ray + Consultation", 1500.0));
    }
}

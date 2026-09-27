/**
 * Appointment class links a Patient with a Doctor at a given date/time.
 * Demonstrates composition/association between objects.
 */
public class Appointment {
    private static int idCounter = 5000;

    private final int appointmentId;
    private final Patient patient;
    private final Doctor doctor;
    private final String date;
    private final String time;
    private String status; // Scheduled, Completed, Cancelled

    // Used when scheduling a brand-new appointment: auto-generates the next ID
    public Appointment(Patient patient, Doctor doctor, String date, String time) {
        this.appointmentId = ++idCounter;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = "Scheduled";
    }

    // Used when rebuilding an appointment from a saved file: keeps the original ID
    public Appointment(int appointmentId, Patient patient, Doctor doctor, String date, String time, String status) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = status;
        if (appointmentId > idCounter) {
            idCounter = appointmentId;
        }
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayDetails() {
        System.out.println("-----------------------------------------------");
        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Patient        : " + patient.getName() + " (" + patient.getPatientId() + ")");
        System.out.println("Doctor         : Dr. " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
        System.out.println("Date           : " + date);
        System.out.println("Time           : " + time);
        System.out.println("Status         : " + status);
        System.out.println("-----------------------------------------------");
    }
}

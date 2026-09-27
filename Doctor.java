/**
 * Doctor class extends Person (INHERITANCE).
 * Adds doctor-specific fields: ID and specialization.
 */
public class Doctor extends Person {
    private static int idCounter = 2000;

    private final String doctorId;
    private String specialization;

    // Used when registering a brand-new doctor: auto-generates the next ID
    public Doctor(String name, int age, String gender, String contactNumber, String specialization) {
        super(name, age, gender, contactNumber);
        this.doctorId = "D" + (++idCounter);
        this.specialization = specialization;
    }

    // Used when rebuilding a doctor from a saved file: keeps the original ID
    public Doctor(String doctorId, String name, int age, String gender, String contactNumber,
                  String specialization) {
        super(name, age, gender, contactNumber);
        this.doctorId = doctorId;
        this.specialization = specialization;
        bumpCounterPast(doctorId);
    }

    // Makes sure future auto-generated IDs never collide with one loaded from file
    private static void bumpCounterPast(String id) {
        try {
            int num = Integer.parseInt(id.substring(1));
            if (num > idCounter) {
                idCounter = num;
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
            // malformed id in the data file; skip counter update
        }
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // POLYMORPHISM: overriding the abstract method from Person
    @Override
    public void displayDetails() {
        System.out.println("-----------------------------------------------");
        System.out.println("Doctor ID      : " + doctorId);
        System.out.println("Name           : Dr. " + getName());
        System.out.println("Age            : " + getAge());
        System.out.println("Gender         : " + getGender());
        System.out.println("Contact        : " + getContactNumber());
        System.out.println("Specialization : " + specialization);
        System.out.println("-----------------------------------------------");
    }
}

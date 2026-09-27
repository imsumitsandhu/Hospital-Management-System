/**
 * Patient class extends Person (INHERITANCE).
 * Adds patient-specific fields: ID, disease, and admission/billing info.
 */
public class Patient extends Person {
    private static int idCounter = 1000;

    private final String patientId;
    private String disease;
    private double totalBillAmount;

    // Used when registering a brand-new patient: auto-generates the next ID
    public Patient(String name, int age, String gender, String contactNumber, String disease) {
        super(name, age, gender, contactNumber);
        this.patientId = "P" + (++idCounter);
        this.disease = disease;
        this.totalBillAmount = 0.0;
    }

    // Used when rebuilding a patient from a saved file: keeps the original ID
    public Patient(String patientId, String name, int age, String gender, String contactNumber,
                   String disease, double totalBillAmount) {
        super(name, age, gender, contactNumber);
        this.patientId = patientId;
        this.disease = disease;
        this.totalBillAmount = totalBillAmount;
        bumpCounterPast(patientId);
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

    public String getPatientId() {
        return patientId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public double getTotalBillAmount() {
        return totalBillAmount;
    }

    public void addToBill(double amount) {
        this.totalBillAmount += amount;
    }

    // POLYMORPHISM: overriding the abstract method from Person
    @Override
    public void displayDetails() {
        System.out.println("-----------------------------------------------");
        System.out.println("Patient ID     : " + patientId);
        System.out.println("Name           : " + getName());
        System.out.println("Age            : " + getAge());
        System.out.println("Gender         : " + getGender());
        System.out.println("Contact        : " + getContactNumber());
        System.out.println("Disease        : " + disease);
        System.out.printf ("Total Bill     : $%.2f%n", totalBillAmount);
        System.out.println("-----------------------------------------------");
    }
}

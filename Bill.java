/**
 * Bill class represents a billing record generated for a patient.
 */
public class Bill {
    private static int idCounter = 9000;

    private final int billId;
    private final Patient patient;
    private final String description;
    private final double amount;
    private boolean paid;

    // Used when generating a brand-new bill: auto-generates the next ID and
    // adds the amount onto the patient's running total.
    public Bill(Patient patient, String description, double amount) {
        this.billId = ++idCounter;
        this.patient = patient;
        this.description = description;
        this.amount = amount;
        this.paid = false;
        patient.addToBill(amount);
    }

    // Used when rebuilding a bill from a saved file: keeps the original ID.
    // Does NOT call patient.addToBill() again, because the patient's saved
    // totalBillAmount already includes this bill.
    public Bill(int billId, Patient patient, String description, double amount, boolean paid) {
        this.billId = billId;
        this.patient = patient;
        this.description = description;
        this.amount = amount;
        this.paid = paid;
        if (billId > idCounter) {
            idCounter = billId;
        }
    }

    public int getBillId() {
        return billId;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markAsPaid() {
        this.paid = true;
    }

    public void displayDetails() {
        System.out.println("-----------------------------------------------");
        System.out.println("Bill ID        : " + billId);
        System.out.println("Patient        : " + patient.getName() + " (" + patient.getPatientId() + ")");
        System.out.println("Description    : " + description);
        System.out.printf ("Amount         : $%.2f%n", amount);
        System.out.println("Status         : " + (paid ? "PAID" : "UNPAID"));
        System.out.println("-----------------------------------------------");
    }
}

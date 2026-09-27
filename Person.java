/**
 * Abstract base class representing a generic person in the hospital system.
 * Demonstrates ABSTRACTION (abstract class + abstract method) and
 * ENCAPSULATION (private fields with controlled access via getters/setters).
 */
public abstract class Person {
    private String name;
    private int age;
    private String gender;
    private String contactNumber;

    public Person(String name, int age, String gender, String contactNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Abstract method — every subclass must define how it displays its own details.
     * This is overridden differently by Patient and Doctor (POLYMORPHISM).
     */
    public abstract void displayDetails();
}

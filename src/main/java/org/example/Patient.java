package org.example;

public class Patient extends Person{

// FIELDS
    private String contact;
    private String sickness;

// CONSTRUCTOR
    public Patient(int id, String name, int age, String role, String contact, String sickness) {
        super(id, name, age, role);
        this.contact = contact;
        this.sickness = sickness;
    }

// METHOD 1
    public void work() {
        System.out.println("Patient: " + name + " is sick with the (" + sickness + ")");
    }

// METHOD 2
    public String getInfo() {
        return "[" + role + "] " + name + " (" + "ID: " + id + " Age: " + age + " Contact: " + contact + " Sickness: " + sickness + ")";
}


// METHOD 3 GETTERS/SETTERS
    public String getContact(){
        return contact;
    }
    public String getSickness(){
        return sickness;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public void setSickness(String sickness){
        this.sickness = sickness;
    }


// UNIC METHOD 1
    public boolean isSick() {
        return sickness != null && !sickness.isEmpty();
    }

// UNIC METHOD 2
    public void updateContact(String newContact) {
        this.contact = newContact;
        System.out.println("Patient " + name + " contact updated.");
    }
}




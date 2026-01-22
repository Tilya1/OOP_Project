package model;
import exception.InvalidInputException;

public class Doctor extends Person {

// FIELDS
    private String status;
    private int experience;

// CONSTRUCTOR
    public Doctor(int id, String name, int age, String role, String status, int experience) throws InvalidInputException {
        super(id, name, age, role);
        this.status = status;
        this.experience = experience;
    }

// METHOD 1
    public void work(){
        System.out.println("model.Doctor: " + name + " is treating patients (" + status + ")");
    }

// METHOD 2 GETTERS
    public String getInfo() {
        return "[" + role + "] " + name + " (" + "ID: " + id + " Age: " + age + " Status: " + status + " Experience: " + experience + ")";
    }

// METHOD 3 GETTERS/SETTERS
    public String getStatus(){
        return status;
    }
    public int getExperience(){
        return experience;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setExperience(int experience){
        this.experience = experience;
    }

    @Override
    public boolean isAdult(){
        return age >= 18;
    }

// UNIC METHOD 1
    public boolean isSenior(){
        return experience >= 10;
    }

// UNIC METHOD 2
    public void showExperience() {
        System.out.println("Experience: " + experience + " years");
}
}


package model;

import exception.InvalidInputException;

public abstract class Person {

// FIELDS
    protected int id;
    protected String name;
    protected int age;
    protected String role;

// CONSTRUCTOR
    public Person(int id, String name, int age, String role) throws InvalidInputException {
        setID(id);
        setName(name);
        setAge(age);
        setRole(role);
    }

// METHOD 1
    public void work(){
        System.out.println(name + " is in the hospital");
    }

// METHOD 2 GETTERS
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getRole(){
        return role;
    }
    public String getInfo(){
        return "ID: " + id + "\nName: " + name + "\nAge: " + age + "\nRole: " + role;
    }

// METHOD 3
    public abstract boolean isAdult();

// METHOD 4 SETTERS
    public void setID(int id) throws InvalidInputException {
        if (id > 0) {
            this.id = id;
        } else {
            throw new InvalidInputException("Invalid ID.");
        }
    }
    public void setName(String name) throws InvalidInputException{
        if (!name.equals("")) {
            this.name = name;
        } else {
            throw new InvalidInputException("Invalid name.");
        }
    }
    public void setAge(int age) throws InvalidInputException {
        if (age > 0) {
            this.age = age;
        } else {
            throw new InvalidInputException("Invalidsyn sen.");
        }
    }
    public void setRole(String role){
        this.role = role;
    }
}

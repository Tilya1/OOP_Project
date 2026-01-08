package org.example;

public class Person {

// FIELDS
    protected int id;
    protected String name;
    protected int age;
    protected String role;

// CONSTRUCTOR
    public Person(int id, String name, int age, String role){
        this.id = id;
        this.name = name;
        this.age = age;
        this.role = role;
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
    public boolean isAdult(){
        return age >= 18;
    }


// METHOD 4 SETTERS
    public void setID(int id) {
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setRole(String role){
        this.role = role;
    }
}

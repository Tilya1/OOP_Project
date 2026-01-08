package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    private static ArrayList<Person> allPeople = new ArrayList<>();

    public static void main(String[] args) {

        ArrayList<Person> people = new ArrayList<>();
        int choice;

        people.add(new Person(1, "Aibek", 40, "Visitor"));
        people.add(new Doctor(2, "Murat", 45, "Doctor", "On duty", 12));
        people.add(new Doctor(3, "Aidar", 35, "Doctor", "Surgeon", 7));
        people.add(new Patient(4, "Ali", 20, "Patient", "+77001234567", "Flu"));

        for(Person p : people) {
            p.work();
        }


        do {
            System.out.println("========================================");
            System.out.println("     HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add person (GENERAL)");
            System.out.println("2. Add doctor");
            System.out.println("3. Add patient");
            System.out.println("4. View all people (Polymorphic)");
            System.out.println("5. Make all people work (Polymorphic)");
            System.out.println("6. View doctors only");
            System.out.println("7. View patients only");
            System.out.println("0. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addPerson();
                    break;

                case 2:
                    addDoctor();
                    break;

                case 3:
                    addPatient();
                    break;

                case 4:
                    viewAllPeople();
                    break;

                case 5:
                    demonstratePolymorphism();
                    break;

                case 6:
                    viewDoctors();
                    break;

                case 7:
                    viewPatients();
                    break;

                case 0:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }

        } while (choice != 0);


    }
    private static void addPerson(){
        System.out.println("\n-----Add person-----");

        System.out.println("Enter person id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter name: ");
        String name = sc.nextLine();

        System.out.println("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter role: ");
        String role = sc.nextLine();

        Person person = new Person(id, name, age, role);
        allPeople.add(person);

        System.out.println("\n ✅Person added successfully!");
    }
    private static void addDoctor(){
        System.out.println("\n-----Add doctor-----");

        System.out.println("Enter doctor id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter name: ");
        String name = sc.nextLine();

        System.out.println("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        String role = "Doctor";

        System.out.println("Enter experience: ");
        int experience = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter status: ");
        String status = sc.nextLine();

        Person doctor = new Doctor(id, name, age, role, status, experience);
        allPeople.add(doctor);

        System.out.println("\n ✅Person added successfully!");
    }
    private static void addPatient(){
        System.out.println("\n-----Add patient-----");

        System.out.println("Enter patient id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter name: ");
        String name = sc.nextLine();

        System.out.println("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        String role = "Patient";

        System.out.println("Enter contact: ");
        String contact = sc.nextLine();

        System.out.println("Enter sickness: ");
        String sickness = sc.nextLine();

        Person patient = new Patient(id, name, age, role, contact, sickness);
        allPeople.add(patient);

        System.out.println("\n ✅Person added successfully!");
    }
    private static void viewAllPeople() {

        System.out.println("\n========================================");
        System.out.println(" ALL PEOPLE (POLYMORPHIC LIST)");
        System.out.println("========================================");

        if (allPeople.isEmpty()) {
            System.out.println("No people found.");
            return;
        }

        System.out.println("Total people: " + allPeople.size());
        System.out.println();

        for (int i = 0; i < allPeople.size(); i++) {
            Person p = allPeople.get(i);

            // basic info (polymorphic)
            System.out.println((i + 1) + ". " + p.getInfo());

            // child-specific info
            if (p instanceof Doctor) {
                Doctor d = (Doctor) p; // downcasting
                if (d.isSenior()) {
                    System.out.println(" Senior Doctor 👨‍⚕️");
                }
            }
            else if (p instanceof Patient) {
                Patient pat = (Patient) p; // downcasting
                if (pat.isSick()) {
                    System.out.println(" Patient needs treatment 🤒");
                }
            }

            System.out.println();
        }
    }
    private static void demonstratePolymorphism() {
        System.out.println("\n========================================");
        System.out.println(" POLYMORPHISM DEMONSTRATION");
        System.out.println("========================================");
        System.out.println("Calling work() on all people: ");
        System.out.println();

        for(Person p : allPeople){
            p.work();
        }
        System.out.println();
        System.out.println(" Notice: Same method name (work), different output!✨");
        System.out.println(" This is POLYMORPHISM in action!");
    }
    private static void viewDoctors() {

        System.out.println("\n========================================");
        System.out.println(" DOCTORS ONLY");
        System.out.println("========================================");

        int doctorCount = 0;

        for (int i = 0; i < allPeople.size(); i++) {
            Person p = allPeople.get(i);

            if (p instanceof Doctor) {          // filter by type
                Doctor d = (Doctor) p;          // downcasting
                doctorCount++;

                // polymorphic / overridden method
                System.out.println(doctorCount + ". " + d.getInfo());

                // doctor-specific logic
                if (d.isSenior()) {
                    System.out.println(" Senior Doctor 👨‍⚕️");
                } else {
                    System.out.println(" Junior Doctor");
                }

                // unique method
                d.showExperience();

                System.out.println();
            }
        }

        if (doctorCount == 0) {
            System.out.println("No doctors found.");
        }
    }
    private static void viewPatients() {

        System.out.println("\n========================================");
        System.out.println(" PATIENTS ONLY");
        System.out.println("========================================");

        int patientCount = 0;

        for (int i = 0; i < allPeople.size(); i++) {
            Person p = allPeople.get(i);

            if (p instanceof Patient) {          // filter by type
                Patient pat = (Patient) p;       // downcasting
                patientCount++;

                // polymorphic / overridden method
                System.out.println(patientCount + ". " + pat.getInfo());

                // patient-specific logic
                if (pat.isSick()) {
                    System.out.println(" Status: Sick 🤒");
                } else {
                    System.out.println(" Status: Healthy");
                }

                // unique getter usage
                System.out.println(" Contact: " + pat.getContact());

                System.out.println();
            }
        }

        if (patientCount == 0) {
            System.out.println("No patients found.");
        }
    }
    }
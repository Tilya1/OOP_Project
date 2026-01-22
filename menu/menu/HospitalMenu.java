package menu;

import java.util.ArrayList;
import java.util.Scanner;
import model.*;

public class HospitalMenu implements Menu {
    static Scanner sc = new Scanner(System.in);
    private static ArrayList<Person> allPeople = new ArrayList<>();

    @Override
    public void run() {
        int choice;

        try {
            allPeople.add(new Patient(1, "Aibek", 40, "model.Patient", "+77757060761", "Headache"));
            allPeople.add(new Doctor(2, "Murat", 45, "model.Doctor", "On duty", 12));
            allPeople.add(new Doctor(3, "Aidar", 35, "model.Doctor", "Surgeon", 7));
            allPeople.add(new Patient(4, "Ali", 20, "model.Patient", "+77001234567", "Flu"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        for(Person p : allPeople) {
            p.work();
        }

        do {
            System.out.println("========================================");
            System.out.println("     HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add doctor");
            System.out.println("2. Add patient");
            System.out.println("3. View all people (Polymorphic)");
            System.out.println("4. Make all people work (Polymorphic)");
            System.out.println("5. View doctors only");
            System.out.println("6. View patients only");
            System.out.println("0. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addDoctor();
                    break;

                case 2:
                    addPatient();
                    break;

                case 3:
                    viewAllPeople();
                    break;

                case 4:
                    demonstratePolymorphism();
                    break;

                case 5:
                    viewDoctors();
                    break;

                case 6:
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

    public static void addDoctor(){
        System.out.println("\n-----Add doctor-----");

        System.out.println("Enter doctor id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter name: ");
        String name = sc.nextLine();

        System.out.println("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        String role = "model.Doctor";

        System.out.println("Enter experience: ");
        int experience = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter status: ");
        String status = sc.nextLine();

        try {
            Person doctor = new Doctor(id, name, age, role, status, experience);
            allPeople.add(doctor);

            System.out.println("\n ✅model.Person added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void addPatient(){
        System.out.println("\n-----Add patient-----");

        System.out.println("Enter patient id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter name: ");
        String name = sc.nextLine();

        System.out.println("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        String role = "model.Patient";

        System.out.println("Enter contact: ");
        String contact = sc.nextLine();

        System.out.println("Enter sickness: ");
        String sickness = sc.nextLine();

        try {
            Person patient = new Patient(id, name, age, role, contact, sickness);

            allPeople.add(patient);

            System.out.println("\n ✅model.Person added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void demonstratePolymorphism() {
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
    public static void viewAllPeople() {

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
                    System.out.println(" Senior model.Doctor 👨‍⚕️");
                }
            }
            else if (p instanceof Patient) {
                Patient pat = (Patient) p; // downcasting
                if (pat.isSick()) {
                    System.out.println(" model.Patient needs treatment 🤒");
                }
            }

            System.out.println();
        }
    }
    public static void viewDoctors() {

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
                    System.out.println(" Senior model.Doctor 👨‍⚕️");
                } else {
                    System.out.println(" Junior model.Doctor");
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
    public static void viewPatients() {

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
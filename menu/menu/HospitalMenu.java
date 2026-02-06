package menu;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

import database.PersonDAO;

import database.DatabaseConnection;
import exception.InvalidInputException;
import model.*;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.print.Doc;
import javax.sound.midi.Soundbank;

import java.util.List;
import java.util.ArrayList;

public class HospitalMenu implements Menu {
    static Scanner sc = new Scanner(System.in);
    private PersonDAO personDAO = new PersonDAO();


    @Override
    public void run() {
        int choice;

        do {
                System.out.println("\n╔════════════════════════════════════════════╗");
                System.out.println("║        HOSPITAL MANAGEMENT SYSTEM          ║");
                System.out.println("╚════════════════════════════════════════════╝");
                System.out.println("┌─ PERSON MANAGEMENT ───────────────────────┐");
                System.out.println("│ 1. Add Doctor                             │");
                System.out.println("│ 2. Add Patient                            │");
                System.out.println("│ 3. View All People                        │");
                System.out.println("│ 4. View Doctors Only                      │");
                System.out.println("│ 5. View Patients Only                     │");
                System.out.println("│ 6. Update Person                          │");
                System.out.println("│ 7. Delete Person                          │");
                System.out.println("├─ SEARCH & FILTER ─────────────────────────┤");
                System.out.println("│ 8. Search by Name                         │");
                System.out.println("│ 9. Doctors by Experience Range            │");
                System.out.println("│10. Experienced Doctors (Experience ≥ X)   │");
                System.out.println("├─ DEMO & OTHER ────────────────────────────┤");
                System.out.println("│11. Polymorphism Demo                      │");
                System.out.println("│ 0. Exit                                   │");
                System.out.println("└───────────────────────────────────────────┘");
                System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1: addDoctor(); break;
                case 2: addPatient(); break;
                case 3: viewAllPeople(); break;
                case 4: viewDoctors(); break;
                case 5: viewPatients(); break;
                case 6: updatePerson(); break;
                case 7: deletePerson(); break;
                case 8: searchByName(); break;
                case 9: searchDoctorsByExperienceRange(); break;
                case 10: searchDoctorsByMinExperience(); break;
                case 11: personDAO.demonstratePolymorphism(); break;
                case 0: System.out.println("Exiting program..."); break;
                default: System.out.println("Invalid choice. Try again.");
            }
            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }
        } while (choice != 0);

    }

    public void addDoctor() {

        System.out.println("\n--- ADD DOCTOR ---");

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter experience years: ");
        int experience = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter status (On duty / Surgeon / etc): ");
        String status = sc.nextLine();

        try {
            Doctor doctor = new Doctor(1, name, age, "DOCTOR", status, experience);
            personDAO.insertDoctor(doctor);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addPatient() {

        System.out.println("\n--- ADD PATIENT ---");

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter contact: ");
        String contact = sc.nextLine();

        System.out.print("Enter sickness: ");
        String sickness = sc.nextLine();

        try {
            Patient patient = new Patient(1, name, age, "PATIENT", contact, sickness);
            personDAO.insertPatient(patient);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllPeople() {
        personDAO.displayAllPeople();
    }

    private void viewDoctors() {

        List<Doctor> doctors = personDAO.getAllDoctors();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          DOCTORS ONLY                  ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (doctors.isEmpty()) {
            System.out.println("📭 No doctors in database.");
        } else {
            for (int i = 0; i < doctors.size(); i++) {
                Doctor d = doctors.get(i);

                System.out.println((i + 1) + ". " + d.getInfo());
                System.out.println("   Status: " + d.getStatus());
                System.out.println("   Experience: " + d.getExperience() + " years");

                if (d.isSenior()) {
                    System.out.println("   ⭐ SENIOR DOCTOR (10+ years)");
                }

                System.out.println();
            }
            System.out.println("Total Doctors: " + doctors.size());
        }
    }

    private void viewPatients() {

        List<Patient> patients = personDAO.getAllPatients();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          PATIENTS ONLY                 ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (patients.isEmpty()) {
            System.out.println("📭 No patients in database.");
        } else {
            for (int i = 0; i < patients.size(); i++) {
                Patient p = patients.get(i);

                System.out.println((i + 1) + ". " + p.getInfo());
                System.out.println("   Contact: " + p.getContact());
                System.out.println("   Sickness: " + p.getSickness());

                if (p.isSick()) {
                    System.out.println("   ⚠ Needs treatment");
                }

                System.out.println();
            }
            System.out.println("Total Patients: " + patients.size());
        }
    }

    private void updatePerson() {

        System.out.print("\nEnter Person ID to update: ");

        try {
            int id = sc.nextInt();
            sc.nextLine();

            Person existing = personDAO.getPersonById(id);

            if (existing == null) {
                System.out.println("❌ No person found with ID: " + id);
                return;
            }

            System.out.println("Current Info: " + existing.getInfo());
            System.out.println("Press Enter to keep old value");

            System.out.print("New Name [" + existing.getName() + "]: ");
            String newName = sc.nextLine();
            if (newName.isEmpty()) newName = existing.getName();

            System.out.print("New Age [" + existing.getAge() + "]: ");
            String ageInput = sc.nextLine();
            int newAge = ageInput.isEmpty() ? existing.getAge() : Integer.parseInt(ageInput);

            if (existing instanceof Doctor) {
                Doctor d = (Doctor) existing;

                System.out.print("New Status [" + d.getStatus() + "]: ");
                String newStatus = sc.nextLine();
                if (newStatus.isEmpty()) newStatus = d.getStatus();

                System.out.print("New Experience [" + d.getExperience() + "]: ");
                String expInput = sc.nextLine();
                int newExp = expInput.isEmpty() ? d.getExperience() : Integer.parseInt(expInput);

                Doctor updated = new Doctor(id, newName, newAge, "DOCTOR", newStatus, newExp);
                personDAO.updateDoctor(updated);

            } else {
                Patient p = (Patient) existing;

                System.out.print("New Contact [" + p.getContact() + "]: ");
                String newContact = sc.nextLine();
                if (newContact.isEmpty()) newContact = p.getContact();

                System.out.print("New Sickness [" + p.getSickness() + "]: ");
                String newSickness = sc.nextLine();
                if (newSickness.isEmpty()) newSickness = p.getSickness();

                Patient updated = new Patient(id, newName, newAge, "PATIENT", newContact, newSickness);
                personDAO.updatePatient(updated);
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void deletePerson() {

        System.out.print("\nEnter Person ID to delete: ");

        try {
            int id = sc.nextInt();
            sc.nextLine();

            Person person = personDAO.getPersonById(id);

            if (person == null) {
                System.out.println("❌ No person found with ID: " + id);
                return;
            }

            System.out.println("Person to delete: " + person.getInfo());
            System.out.print("⚠️  Are you sure? (yes/no): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                personDAO.deletePerson(id);
            } else {
                System.out.println("❌ Deletion cancelled.");
            }

        } catch (Exception e) {
            System.out.println("❌ Invalid input!");
        }
    }

    private void searchByName() {

        System.out.println("\n┌─ SEARCH BY NAME ───────────────────────┐");
        System.out.print("│ Enter name to search: ");
        String name = sc.nextLine();
        System.out.println("└────────────────────────────────────────┘");

        List<Person> results = personDAO.searchByName(name);

        displaySearchResults(results, "Search: '" + name + "'");
    }

    private void displaySearchResults(List<Person> results, String title) {

        System.out.println("\n========================================");
        System.out.println("   " + title);
        System.out.println("========================================");

        if (results.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                Person p = results.get(i);

                System.out.print((i + 1) + ". ");
                System.out.print("[" + p.getRole() + "] ");
                System.out.println(p.getInfo());

                if (p instanceof Doctor d) {
                    System.out.println(" Status: " + d.getStatus());
                    System.out.println(" Experience: " + d.getExperience() + " years");
                } else if (p instanceof Patient pat) {
                    System.out.println(" Contact: " + pat.getContact());
                    System.out.println(" Sickness: " + pat.getSickness());
                }

                System.out.println("----------------------------------------");
            }
        }

        System.out.println("========================================\n");
    }

    private void searchDoctorsByExperienceRange() {

        try {
            System.out.println("\n┌─ SEARCH DOCTORS BY EXPERIENCE RANGE ───┐");
            System.out.print("│ Enter minimum experience: ");
            int minExp = sc.nextInt();

            System.out.print("│ Enter maximum experience: ");
            int maxExp = sc.nextInt();
            sc.nextLine();
            System.out.println("└────────────────────────────────────────┘");

            List<Doctor> results = personDAO.searchDoctorsByExperienceRange(minExp, maxExp);

            displayDoctorSearchResults(results, "Experience: " + minExp + " - " + maxExp + " years");

        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid number!");
            sc.nextLine();
        }
    }

    private void searchDoctorsByMinExperience() {

        try {
            System.out.println("\n┌─ EXPERIENCED DOCTORS ───────────────────┐");
            System.out.print("│ Enter minimum experience: ");
            int minExp = sc.nextInt();
            sc.nextLine();
            System.out.println("└────────────────────────────────────────┘");

            List<Doctor> results = personDAO.searchDoctorsByMinExperience(minExp);

            displayDoctorSearchResults(results, "Experience ≥ " + minExp + " years");

        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid number!");
            sc.nextLine();
        }
    }

    private void displayDoctorSearchResults(List<Doctor> doctors, String title) {

        System.out.println("\n========================================");
        System.out.println("   " + title);
        System.out.println("========================================");

        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            for (int i = 0; i < doctors.size(); i++) {
                Doctor d = doctors.get(i);
                System.out.println((i + 1) + ". " + d.getInfo());
                System.out.println("   Status: " + d.getStatus());
                System.out.println("   Experience: " + d.getExperience() + " years");

                if (d.isSenior()) {
                    System.out.println("   ⭐ Senior Doctor");
                }

                System.out.println();
            }
        }

        System.out.println("========================================\n");
    }

}
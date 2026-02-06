package database;

import model.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public boolean insertDoctor(Doctor doctor) {

        String sql = "INSERT INTO person (name, age, role, status, experience, contact, sickness) " +
                "VALUES (?, ?, 'DOCTOR', ?, ?, NULL, NULL)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, doctor.getName());
            statement.setInt(2, doctor.getAge());
            statement.setString(3, doctor.getStatus());
            statement.setInt(4, doctor.getExperience());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("✅ Doctor inserted: " + doctor.getName());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Insert Doctor failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean insertPatient(Patient patient) {

        String sql = "INSERT INTO person (name, age, role, status, experience, contact, sickness) " +
                "VALUES (?, ?, 'PATIENT', NULL, NULL, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getContact());
            statement.setString(4, patient.getSickness());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("✅ Patient inserted: " + patient.getName());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Insert Patient failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public void demonstratePolymorphism() {

        String sql = "SELECT * FROM person";
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            System.out.println("\n=== POLYMORPHISM FROM DATABASE ===");

            while (rs.next()) {

                String role = rs.getString("role");
                String name = rs.getString("name");

                if ("DOCTOR".equals(role)) {
                    String status = rs.getString("status");
                    System.out.println("Doctor: " + name + " is treating patients (" + status + ")");
                } else if ("PATIENT".equals(role)) {
                    String sickness = rs.getString("sickness");
                    System.out.println("Patient: " + name + " is sick with (" + sickness + ")");
                }
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public List<Person> getAllPeople() {

        List<Person> peopleList = new ArrayList<>();
        String sql = "SELECT * FROM person ORDER BY person_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return peopleList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person person = extractPersonFromResultSet(resultSet);
                if (person != null) {
                    peopleList.add(person);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + peopleList.size() + " people from database");

        } catch (SQLException e) {
            System.out.println("❌ Select all people failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return peopleList;
    }

    public void displayAllPeople() {

        List<Person> peopleList = getAllPeople();

        System.out.println("\n========================================");
        System.out.println("   ALL PEOPLE FROM DATABASE");
        System.out.println("========================================");

        if (peopleList.isEmpty()) {
            System.out.println("No people in database.");
        } else {
            for (int i = 0; i < peopleList.size(); i++) {
                Person p = peopleList.get(i);

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

    public List<Doctor> getAllDoctors() {

        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE role = 'DOCTOR' ORDER BY person_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return doctors;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Person person = extractPersonFromResultSet(rs);
                if (person instanceof Doctor) {
                    doctors.add((Doctor) person);
                }
            }

            rs.close();
            statement.close();

            System.out.println("✅ Retrieved " + doctors.size() + " doctors");

        } catch (SQLException e) {
            System.out.println("❌ Select doctors failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return doctors;
    }

    public List<Patient> getAllPatients() {

        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE role = 'PATIENT' ORDER BY person_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return patients;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Person person = extractPersonFromResultSet(rs);
                if (person instanceof Patient) {
                    patients.add((Patient) person);
                }
            }

            rs.close();
            statement.close();

            System.out.println("✅ Retrieved " + patients.size() + " patients");

        } catch (SQLException e) {
            System.out.println("❌ Select patients failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return patients;
    }

    public boolean updateDoctor(Doctor doctor) {

        String sql = "UPDATE person SET name = ?, age = ?, status = ?, experience = ? " +
                "WHERE person_id = ? AND role = 'DOCTOR'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, doctor.getName());
            statement.setInt(2, doctor.getAge());
            statement.setString(3, doctor.getStatus());
            statement.setInt(4, doctor.getExperience());
            statement.setInt(5, doctor.getId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ Doctor updated: " + doctor.getName());
                return true;
            } else {
                System.out.println("⚠️ No doctor found with ID: " + doctor.getId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update Doctor failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public boolean updatePatient(Patient patient) {

        String sql = "UPDATE person SET name = ?, age = ?, contact = ?, sickness = ? " +
                "WHERE person_id = ? AND role = 'PATIENT'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getContact());
            statement.setString(4, patient.getSickness());
            statement.setInt(5, patient.getId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ Patient updated: " + patient.getName());
                return true;
            } else {
                System.out.println("⚠️ No patient found with ID: " + patient.getId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update Patient failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public Person getPersonById(int personId) {

        String sql = "SELECT * FROM person WHERE person_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Person person = extractPersonFromResultSet(resultSet);

                resultSet.close();
                statement.close();

                if (person != null) {
                    System.out.println("✅ Found person with ID: " + personId);
                }

                return person;
            }

            System.out.println("⚠️ No person found with ID: " + personId);

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Select by ID failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return null;
    }

    private Person extractPersonFromResultSet(ResultSet rs) throws SQLException {

        int id = rs.getInt("person_id");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        String role = rs.getString("role");

        Person person = null;

        try {
            if ("DOCTOR".equals(role)) {

                String status = rs.getString("status");
                int experience = rs.getInt("experience");

                person = new Doctor(id, name, age, role, status, experience);

            } else if ("PATIENT".equals(role)) {

                String contact = rs.getString("contact");
                String sickness = rs.getString("sickness");

                person = new Patient(id, name, age, role, contact, sickness);
            }

        } catch (Exception e) {
            System.out.println("❌ Object creation failed!");
            e.printStackTrace();
        }

        return person;
    }

    public boolean deletePerson(int personId) {
        String sql = "DELETE FROM person WHERE person_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if(connection == null){
            System.out.println("Connection failed");
            return false;
        }

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);

            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0){
                System.out.println("Deleted successfully, id: " + personId);
                return true;
            }
        } catch(SQLException e){
            System.out.println("Delete failed");
            e.printStackTrace();
        } finally{
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public List<Person> searchByName(String name) {

        List<Person> peopleList = new ArrayList<>();

        // ILIKE = регистронезависимый поиск, % = частичное совпадение
        String sql = "SELECT * FROM person WHERE name ILIKE ? ORDER BY name";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return peopleList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person person = extractPersonFromResultSet(resultSet);
                if (person != null) {
                    peopleList.add(person);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + peopleList.size() + " people matching '" + name + "'");

        } catch (SQLException e) {
            System.out.println("❌ Search by name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return peopleList;
    }

    public List<Doctor> searchDoctorsByExperienceRange(int minExp, int maxExp) {

        List<Doctor> doctors = new ArrayList<>();

        String sql = "SELECT * FROM person WHERE role = 'DOCTOR' AND experience BETWEEN ? AND ? ORDER BY experience DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return doctors;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, minExp);
            statement.setInt(2, maxExp);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Person person = extractPersonFromResultSet(rs);
                if (person instanceof Doctor) {
                    doctors.add((Doctor) person);
                }
            }

            rs.close();
            statement.close();

            System.out.println("✅ Found " + doctors.size() + " doctors in experience range " +
                    minExp + " - " + maxExp);

        } catch (SQLException e) {
            System.out.println("❌ Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return doctors;
    }

    public List<Doctor> searchDoctorsByMinExperience(int minExp) {

        List<Doctor> doctors = new ArrayList<>();

        String sql = "SELECT * FROM person WHERE role = 'DOCTOR' AND experience >= ? ORDER BY experience DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return doctors;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, minExp);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Person person = extractPersonFromResultSet(rs);
                if (person instanceof Doctor) {
                    doctors.add((Doctor) person);
                }
            }

            rs.close();
            statement.close();

            System.out.println("✅ Found " + doctors.size() + " doctors with experience ≥ " + minExp);

        } catch (SQLException e) {
            System.out.println("❌ Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return doctors;
    }

}

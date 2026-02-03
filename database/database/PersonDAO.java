package database;

import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void viewAllPeople() {

        String sql = "SELECT * FROM person";
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            System.out.println("\n=== ALL PEOPLE FROM DATABASE ===");

            while (rs.next()) {

                int id = rs.getInt("person_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String role = rs.getString("role");

                System.out.println("ID: " + id + " | Name: " + name + " | Age: " + age + " | Role: " + role);

                if ("DOCTOR".equals(role)) {
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("Experience: " + rs.getInt("experience") + " years");
                } else {
                    System.out.println("Contact: " + rs.getString("contact"));
                    System.out.println("Sickness: " + rs.getString("sickness"));
                }

                System.out.println("----------------------");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void viewDoctors() {

        String sql = "SELECT * FROM person WHERE role = 'DOCTOR'";
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            System.out.println("\n=== DOCTORS ===");

            while (rs.next()) {
                System.out.println("Doctor: " + rs.getString("name"));
                System.out.println("Experience: " + rs.getInt("experience") + " years");
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("----------------------");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void viewPatients() {

        String sql = "SELECT * FROM person WHERE role = 'PATIENT'";
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            System.out.println("\n=== PATIENTS ===");

            while (rs.next()) {
                System.out.println("Patient: " + rs.getString("name"));
                System.out.println("Contact: " + rs.getString("contact"));
                System.out.println("Sickness: " + rs.getString("sickness"));
                System.out.println("----------------------");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
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
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            if (rowsDeleted > 0) {
                System.out.println("✅ Person deleted (ID: " + personId + ")");
                return true;
            } else {
                System.out.println("⚠️ No person found with ID: " + personId);
            }

        } catch (SQLException e) {
            System.out.println("❌ Delete failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    public void searchByName(String name) {

        String sql = "SELECT * FROM person WHERE name ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");

            ResultSet rs = statement.executeQuery();

            boolean found = false;

            System.out.println("\n=== SEARCH RESULTS ===");

            while (rs.next()) {
                found = true;

                int id = rs.getInt("person_id");
                String personName = rs.getString("name");
                int age = rs.getInt("age");
                String role = rs.getString("role");

                System.out.println("ID: " + id + " | Name: " + personName + " | Age: " + age + " | Role: " + role);

                if ("DOCTOR".equals(role)) {
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("Experience: " + rs.getInt("experience"));
                } else {
                    System.out.println("Contact: " + rs.getString("contact"));
                    System.out.println("Sickness: " + rs.getString("sickness"));
                }

                System.out.println("----------------------");
            }

            if (!found) {
                System.out.println("No people found matching '" + name + "'");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void searchDoctorsByExperienceRange(int minExp, int maxExp) {

        String sql = "SELECT * FROM person WHERE role = 'DOCTOR' AND experience BETWEEN ? AND ? ORDER BY experience DESC";
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, minExp);
            statement.setInt(2, maxExp);

            ResultSet rs = statement.executeQuery();

            boolean found = false;

            System.out.println("\n=== DOCTORS EXPERIENCE " + minExp + " - " + maxExp + " YEARS ===");

            while (rs.next()) {
                found = true;

                System.out.println("ID: " + rs.getInt("person_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Experience: " + rs.getInt("experience") + " years");
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("----------------------");
            }

            if (!found) {
                System.out.println("No doctors found in this experience range.");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void searchDoctorsByMinExperience(int minExp) {

        String sql = "SELECT * FROM person WHERE role = 'DOCTOR' AND experience >= ? ORDER BY experience DESC";
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, minExp);

            ResultSet rs = statement.executeQuery();

            boolean found = false;

            System.out.println("\n=== DOCTORS WITH EXPERIENCE >= " + minExp + " YEARS ===");

            while (rs.next()) {
                found = true;

                System.out.println("ID: " + rs.getInt("person_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Experience: " + rs.getInt("experience") + " years");
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("----------------------");
            }

            if (!found) {
                System.out.println("No doctors found.");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Search failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }


}

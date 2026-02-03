package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/hospital_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            //System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println("DRIVER NOT FOUND!");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println(" Connected to database successfully!");
        } catch (SQLException e) {
            System.out.println(" Connection failed!");
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

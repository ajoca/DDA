package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "reservas_db";
    private static final String USER = "root"; //
    private static final String PASSWORD = "admin"; //

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver no encontrado", e);
        }
    }

    public static void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Base de datos verificada/creada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear/verificar la base de datos: " + e.getMessage());
        }
    }
}

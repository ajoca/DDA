package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/reservas_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection conectar() throws SQLException {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a la base de datos establecida correctamente.");
            crearTablas(conexion);
            return conexion;
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static void cerrar(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada exitosamente.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    private static void crearTablas(Connection conexion) {
        String createHotelTable = """
                CREATE TABLE IF NOT EXISTS Hotel (
                    idHotel INT PRIMARY KEY AUTO_INCREMENT,
                    nombre VARCHAR(100) NOT NULL,
                    ciudad VARCHAR(100) NOT NULL,
                    pais VARCHAR(100) NOT NULL,
                    estrellas INT NOT NULL CHECK(estrellas >= 1 AND estrellas <= 5),
                    direccion VARCHAR(255) NOT NULL,
                    zona VARCHAR(100) NOT NULL,
                    amenities VARCHAR(255)
                );
                """;

        String createHabitacionTable = """
                CREATE TABLE IF NOT EXISTS Habitacion (
                    idHabitacion INT PRIMARY KEY AUTO_INCREMENT,
                    idHotel INT,
                    capacidad INT NOT NULL,
                    esMatrimonial BOOLEAN NOT NULL,
                    aireAcondicionado BOOLEAN NOT NULL,
                    balcon BOOLEAN NOT NULL,
                    vista BOOLEAN NOT NULL,
                    ocupada BOOLEAN DEFAULT FALSE,
                    jacuzzi BOOLEAN DEFAULT FALSE,
                    amenities VARCHAR(255),
                    FOREIGN KEY (idHotel) REFERENCES Hotel(idHotel) ON DELETE CASCADE
                );
                """;

        String createTarifaTable = """
                CREATE TABLE IF NOT EXISTS Tarifa (
                    idTarifa INT PRIMARY KEY AUTO_INCREMENT,
                    idHabitacion INT,
                    monto DOUBLE NOT NULL,
                    fechaInicio DATE NOT NULL,
                    fechaFin DATE,
                    FOREIGN KEY (idHabitacion) REFERENCES Habitacion(idHabitacion) ON DELETE CASCADE
                );
                """;

        String createHuespedTable = """
                CREATE TABLE IF NOT EXISTS Huesped (
                    idHuesped INT PRIMARY KEY AUTO_INCREMENT,
                    nombre VARCHAR(100) NOT NULL,
                    apaterno VARCHAR(100) NOT NULL,
                    amaterno VARCHAR(100) NOT NULL,
                    tipoDocumento VARCHAR(50) NOT NULL,
                    numDocumento VARCHAR(50) NOT NULL UNIQUE,
                    fechaNacimiento DATE NOT NULL,
                    telefono VARCHAR(20),
                    pais VARCHAR(100) NOT NULL
                );
                """;

        String createReservaTable = """
                CREATE TABLE IF NOT EXISTS Reserva (
                    idReserva INT PRIMARY KEY AUTO_INCREMENT,
                    idHabitacion INT,
                    idHuesped INT,
                    fechaInicio DATE NOT NULL,
                    fechaFin DATE NOT NULL,
                    cantidadPersonas INT NOT NULL,
                    fechaReserva DATE NOT NULL,
                    pagada BOOLEAN DEFAULT FALSE,
                    saldoPendiente DOUBLE,
                    observacion VARCHAR(255),
                    FOREIGN KEY (idHabitacion) REFERENCES Habitacion(idHabitacion) ON DELETE CASCADE,
                    FOREIGN KEY (idHuesped) REFERENCES Huesped(idHuesped) ON DELETE CASCADE
                );
                """;

        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(createHotelTable);
            stmt.execute(createHabitacionTable);
            stmt.execute(createTarifaTable);
            stmt.execute(createHuespedTable);
            stmt.execute(createReservaTable);
            System.out.println("Tablas creadas o verificadas correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }
}

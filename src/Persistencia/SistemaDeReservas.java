package Persistencia;
import Dominio.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaDeReservas {
    private List<Hotel> hoteles;
    private List<Huesped> huespedes;
    private List<Reserva> reservas;

    public SistemaDeReservas() {
        this.hoteles = new ArrayList<>();
        this.huespedes = new ArrayList<>();
        this.reservas = new ArrayList<>();
        cargarHotelesDesdeBaseDeDatos();
    }

    private void cargarHotelesDesdeBaseDeDatos() {
        String sqlHotel = "SELECT * FROM hotel";
        String sqlHabitacion = "SELECT * FROM Habitacion WHERE idHotel = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmtHotel = conexion.prepareStatement(sqlHotel);
             ResultSet rsHotel = stmtHotel.executeQuery()) {

            while (rsHotel.next()) {
                int idHotel = rsHotel.getInt("idHotel");
                String nombre = rsHotel.getString("nombre");
                String ciudad = rsHotel.getString("ciudad");
                String pais = rsHotel.getString("pais");
                int estrellas = rsHotel.getInt("estrellas");
                String direccion = rsHotel.getString("direccion");
                String zona = rsHotel.getString("zona");
                String amenities = rsHotel.getString("amenities");

                Hotel hotel = new Hotel(idHotel, nombre, ciudad, pais, estrellas, direccion, zona, amenities);

                try (PreparedStatement stmtHabitacion = conexion.prepareStatement(sqlHabitacion)) {
                    stmtHabitacion.setInt(1, idHotel);
                    try (ResultSet rsHabitacion = stmtHabitacion.executeQuery()) {
                        while (rsHabitacion.next()) {
                            int idHabitacion = rsHabitacion.getInt("idHabitacion");
                            int capacidad = rsHabitacion.getInt("capacidad");
                            boolean esMatrimonial = rsHabitacion.getBoolean("esMatrimonial");
                            boolean aireAcondicionado = rsHabitacion.getBoolean("aireAcondicionado");
                            boolean balcon = rsHabitacion.getBoolean("balcon");
                            boolean vista = rsHabitacion.getBoolean("vista");
                            boolean ocupada = rsHabitacion.getBoolean("ocupada");
                            boolean jacuzzi = rsHabitacion.getBoolean("jacuzzi");
                            String amenitiesHabitacion = rsHabitacion.getString("amenities");

                            Habitacion habitacion = new Habitacion(idHabitacion, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, amenitiesHabitacion);
                            habitacion.setOcupada(ocupada);
                            hotel.agregarHabitacion(habitacion);
                        }
                    }
                }

                hoteles.add(hotel);
            }
            System.out.println("Hoteles y habitaciones cargados desde la base de datos.");

        } catch (SQLException e) {
            System.out.println("Error al cargar los hoteles desde la base de datos: " + e.getMessage());
        }
    }
    public void eliminarHotelEnBaseDeDatos(int idHotel) {
        String sql = "DELETE FROM Hotel WHERE idHotel = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idHotel);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Hotel eliminado de la base de datos exitosamente.");
            } else {
                System.out.println("Error: No se encontró un hotel con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el hotel de la base de datos: " + e.getMessage());
        }
    }

    public void agregarHabitacion(int idHotel, Habitacion habitacion) {
        Hotel hotel = buscarHotelPorId(idHotel);
        if (hotel != null) {
            hotel.agregarHabitacion(habitacion);
            insertarHabitacionEnBaseDeDatos(habitacion, idHotel);
            System.out.println("Habitación agregada exitosamente al hotel.");
        } else {
            System.out.println("Error: No se encontró el hotel con el ID especificado.");
        }
    }
    public boolean eliminarHabitacion(int idHotel, int idHabitacion) {
        Hotel hotel = buscarHotelPorId(idHotel);
        if (hotel != null) {
            Habitacion habitacion = hotel.buscarHabitacionPorId(idHabitacion);
            if (habitacion != null && !habitacion.isOcupada()) {
                hotel.getHabitaciones().remove(habitacion);
                eliminarHabitacionEnBaseDeDatos(idHabitacion);
                System.out.println("Habitación eliminada exitosamente del hotel.");
                return true;
            } else {
                System.out.println("Error: La habitación está ocupada o no existe.");
            }
        } else {
            System.out.println("Error: No se encontró el hotel con el ID especificado.");
        }
        return false;
    }

    private void eliminarHabitacionEnBaseDeDatos(int idHabitacion) {
        String sql = "DELETE FROM Habitacion WHERE idHabitacion = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idHabitacion);
            stmt.executeUpdate();
            System.out.println("Habitación eliminada de la base de datos exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar la habitación en la base de datos: " + e.getMessage());
        }
    }
    public boolean modificarHabitacion(int idHotel, int idHabitacion, int nuevaCapacidad, boolean esMatrimonial, boolean aireAcondicionado, boolean balcon, boolean vista, String amenities) {
        Hotel hotel = buscarHotelPorId(idHotel);
        if (hotel != null) {
            Habitacion habitacion = hotel.buscarHabitacionPorId(idHabitacion);
            if (habitacion != null) {
                habitacion.setCapacidad(nuevaCapacidad);
                habitacion.setMatrimonial(esMatrimonial);
                habitacion.setAireAcondicionado(aireAcondicionado);
                habitacion.setBalcon(balcon);
                habitacion.setVista(vista);
                habitacion.setAmenities(amenities);
                modificarHabitacionEnBaseDeDatos(habitacion);
                System.out.println("Habitación modificada exitosamente.");
                return true;
            } else {
                System.out.println("Error: No se encontró la habitación con el ID especificado.");
            }
        } else {
            System.out.println("Error: No se encontró el hotel con el ID especificado.");
        }
        return false;
    }

    private void modificarHabitacionEnBaseDeDatos(Habitacion habitacion) {
        String sql = "UPDATE Habitacion SET capacidad = ?, esMatrimonial = ?, aireAcondicionado = ?, balcon = ?, vista = ?, amenities = ? WHERE idHabitacion = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, habitacion.getCapacidad());
            stmt.setBoolean(2, habitacion.isMatrimonial());
            stmt.setBoolean(3, habitacion.isAireAcondicionado());
            stmt.setBoolean(4, habitacion.isBalcon());
            stmt.setBoolean(5, habitacion.isVista());
            stmt.setString(6, habitacion.getAmenities());
            stmt.setInt(7, habitacion.getIdHabitacion());

            stmt.executeUpdate();
            System.out.println("Habitación modificada en la base de datos exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al modificar la habitación en la base de datos: " + e.getMessage());
        }
    }

    public void agregarHotel(Hotel hotel) {
        hoteles.add(hotel);
        insertarHotelEnBaseDeDatos(hotel);
    }

    private void insertarHotelEnBaseDeDatos(Hotel hotel) {
        String sql = "INSERT INTO hotel (nombre, ciudad, pais, estrellas, direccion, zona, amenities) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, hotel.getNombre());
            stmt.setString(2, hotel.getCiudad());
            stmt.setString(3, hotel.getPais());
            stmt.setInt(4, hotel.getEstrellas());
            stmt.setString(5, hotel.getDireccion());
            stmt.setString(6, hotel.getZona());
            stmt.setString(7, hotel.getAmenities());

            stmt.executeUpdate();
            System.out.println("Hotel guardado en la base de datos exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar el hotel en la base de datos: " + e.getMessage());
        }
    }

    public void registrarHuesped(Huesped huesped) {
        huespedes.add(huesped);
        insertarHuespedEnBaseDeDatos(huesped);
    }

    private void insertarHuespedEnBaseDeDatos(Huesped huesped) {
        String sql = "INSERT INTO huesped (nombre, apaterno, amaterno, tipoDocumento, numDocumento, fechaNacimiento, telefono, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, huesped.getNombre());
            stmt.setString(2, huesped.getApaterno());
            stmt.setString(3, huesped.getAmaterno());
            stmt.setString(4, huesped.getTipoDocumento());
            stmt.setString(5, huesped.getNumDocumento());
            stmt.setString(6, huesped.getFechaNacimiento());
            stmt.setString(7, huesped.getTelefono());
            stmt.setString(8, huesped.getPais());

            stmt.executeUpdate();
            System.out.println("Huesped guardado en la base de datos exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar el huesped en la base de datos: " + e.getMessage());
        }
    }

    public void registrarReserva(Reserva reserva) {
        reservas.add(reserva);
        insertarReservaEnBaseDeDatos(reserva);
    }


    private void insertarReservaEnBaseDeDatos(Reserva reserva) {
        String sql = "INSERT INTO reserva (idHabitacion, idHuesped, fechaInicio, fechaFin, pagada, saldoPendiente, observacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getHabitacion().getIdHabitacion());
            stmt.setInt(2, reserva.getHuesped().getId());
            stmt.setDate(3, java.sql.Date.valueOf(reserva.getFechaInicio()));
            stmt.setDate(4, java.sql.Date.valueOf(reserva.getFechaFin()));
            stmt.setBoolean(5, reserva.isPagada());
            stmt.setDouble(6, reserva.getSaldoPendiente());
            stmt.setString(7, reserva.getObservacion());

            stmt.executeUpdate();
            System.out.println("Reserva guardada en la base de datos exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar la reserva en la base de datos: " + e.getMessage());
        }
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public List<Huesped> getHuespedes() {
        return huespedes;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Hotel> consultarHotelesPorCiudad(String ciudad) {
        List<Hotel> resultado = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            if (hotel.getCiudad().equalsIgnoreCase(ciudad)) {
                resultado.add(hotel);
            }
        }
        return resultado;
    }

    public List<Hotel> consultarHotelesPorPais(String pais) {
        List<Hotel> resultado = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            if (hotel.getPais().equalsIgnoreCase(pais)) {
                resultado.add(hotel);
            }
        }
        return resultado;
    }

    public List<Hotel> consultarHotelesPorEstrellas(int estrellas) {
        List<Hotel> resultado = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            if (hotel.getEstrellas() == estrellas) {
                resultado.add(hotel);
            }
        }
        return resultado;
    }
    public Reserva buscarReservaPorId(int idReserva) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == idReserva) {
                return reserva;
            }
        }
        return null;
    }

    public Hotel buscarHotelPorId(int idHotel) {
        for (Hotel hotel : hoteles) {
            if (hotel.getIdHotel() == idHotel) {
                return hotel;
            }
        }
        return null; // Si no se encuentra el hotel, retorna null
    }
    public void cancelarReserva(int idReserva) {
        Reserva reservaEncontrada = buscarReservaPorId(idReserva);

        if (reservaEncontrada != null) {
            reservaEncontrada.getHabitacion().setOcupada(false);
            reservas.remove(reservaEncontrada);
            System.out.println("Reserva cancelada exitosamente.");
        } else {
            System.out.println("Reserva no encontrada.");
        }
    }

    public boolean modificarReserva(int idReserva, LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin, int nuevaCantidadPersonas) {
        Reserva reserva = buscarReservaPorId(idReserva);

        if (reserva == null) {
            System.out.println("Error: Reserva no encontrada.");
            return false;
        }

        for (Reserva otraReserva : reservas) {
            if (otraReserva.getHabitacion().equals(reserva.getHabitacion()) && otraReserva.getIdReserva() != idReserva) {
                if ((nuevaFechaInicio.isBefore(otraReserva.getFechaFin()) && nuevaFechaFin.isAfter(otraReserva.getFechaInicio()))) {
                    System.out.println("Error: Las fechas se superponen con otra reserva existente.");
                    return false;
                }
            }
        }

        reserva.setFechaInicio(nuevaFechaInicio);
        reserva.setFechaFin(nuevaFechaFin);
        reserva.setCantidadPersonas(nuevaCantidadPersonas);
        reserva.setSaldoPendiente(reserva.calcularSaldoPendiente());
        System.out.println("Reserva modificada exitosamente.");

        return true;
    }

    public boolean reservarHabitacion(Hotel hotel, Huesped huesped, int idHabitacion, LocalDate fechaInicio, LocalDate fechaFin, int cantidadPersonas, String observacion) {
        Habitacion habitacion = null;
        for (Habitacion hab : hotel.getHabitaciones()) {
            if (hab.getIdHabitacion() == idHabitacion) {
                if (hab.isPreContratadaExternamente() || hab.isOcupada()) {
                    System.out.println("La habitación no está disponible.");
                    return false;
                }
                habitacion = hab;
                break;
            }
        }

        if (habitacion == null) {
            System.out.println("Error: No se encontró la habitación solicitada.");
            return false;
        }

        Tarifa tarifaVigente = hotel.obtenerTarifaVigente(fechaInicio);
        if (tarifaVigente == null) {
            System.out.println("No hay una tarifa vigente para la fecha de inicio de la reserva.");
            return false;
        }

        double tarifaTotal = tarifaVigente.getPrecio() * cantidadDiasReservados(fechaInicio, fechaFin);
        Reserva reserva = new Reserva(reservas.size() + 1, habitacion, huesped, fechaInicio, fechaFin, cantidadPersonas, observacion);
        reserva.setSaldoPendiente(tarifaTotal);
        registrarReserva(reserva);
        habitacion.setOcupada(true);
        System.out.println("Reserva realizada exitosamente.");
        return true;
    }

    public void insertarHabitacionEnBaseDeDatos(Habitacion habitacion, int idHotel) {
        String sql = "INSERT INTO Habitacion (idHotel, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, ocupada, jacuzzi, amenities) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idHotel);
            stmt.setInt(2, habitacion.getCapacidad());
            stmt.setBoolean(3, habitacion.isMatrimonial());
            stmt.setBoolean(4, habitacion.isAireAcondicionado());
            stmt.setBoolean(5, habitacion.isBalcon());
            stmt.setBoolean(6, habitacion.isVista());
            stmt.setBoolean(7, habitacion.isOcupada());
            stmt.setBoolean(8, habitacion instanceof HabitacionDeluxe ? ((HabitacionDeluxe) habitacion).tieneJacuzzi() : false);
            stmt.setString(9, habitacion.getAmenities());

            stmt.executeUpdate();
            System.out.println("Habitación guardada en la base de datos exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar la habitación en la base de datos: " + e.getMessage());
        }
    }


    private int cantidadDiasReservados(LocalDate fechaInicio, LocalDate fechaFin) {
        return (int) (fechaFin.toEpochDay() - fechaInicio.toEpochDay());
    }

    public List<Hotel> consultarHotelesPorNombre(String nombre) {
        List<Hotel> hotelesEncontrados = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            if (hotel.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                hotelesEncontrados.add(hotel);
            }
        }
        return hotelesEncontrados;
    }
    public boolean eliminarHotel(int idHotel) {
        Hotel hotel = buscarHotelPorId(idHotel);
        if (hotel != null && hotel.obtenerHabitacionesOcupadas().isEmpty()) {
            hoteles.remove(hotel);
            return true;
        }
        return false;
    }
    public Huesped buscarHuespedPorId(int idHuesped) {
        for (Huesped huesped : huespedes) {
            if (huesped.getId() == idHuesped) {
                return huesped;
            }
        }
        return null;
    }
    public boolean eliminarHuesped(int idHuesped) {
        Huesped huesped = buscarHuespedPorId(idHuesped);
        if (huesped != null && !tieneReservasActivas(huesped)) {
            huespedes.remove(huesped);
            return true;
        }
        return false;
    }


    private boolean tieneReservasActivas(Huesped huesped) {
        for (Reserva reserva : reservas) {
            if (reserva.getHuesped().equals(huesped)) {
                return true;
            }
        }
        return false;
    }

    public List<Habitacion> listarHabitacionesDisponiblesEnPeriodo(int idHotel, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        Hotel hotelSeleccionado = buscarHotelPorId(idHotel);

        if (hotelSeleccionado == null) {
            System.out.println("Error: Hotel no encontrado.");
            return habitacionesDisponibles;
        }

        for (Habitacion habitacion : hotelSeleccionado.getHabitaciones()) {
            boolean disponible = true;
            for (Reserva reserva : reservas) {
                if (reserva.getHabitacion().equals(habitacion) &&
                        (fechaInicio.isBefore(reserva.getFechaFin()) && fechaFin.isAfter(reserva.getFechaInicio()))) {
                    disponible = false;
                    break;
                }
            }
            if (disponible) {
                habitacionesDisponibles.add(habitacion);
            }
        }

        return habitacionesDisponibles;

    }

}

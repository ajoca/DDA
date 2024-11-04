package Controladora;
import Dominio.*;
import Persistencia.ConexionBD;
import Persistencia.SistemaDeReservas;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ControladoraPrincipal {

    private SistemaDeReservas sistema;
    private Scanner scanner;

    public ControladoraPrincipal() {
        this.sistema = new SistemaDeReservas();
        this.scanner = new Scanner(System.in);
    }

    public Connection conectarBD() {
        try {
            Connection conexion = ConexionBD.conectar();
            System.out.println("Conexión a la base de datos exitosa.");
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public void agregarHotel() {
        try {
            System.out.print("Ingrese el nombre del hotel: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese la ciudad: ");
            String ciudad = scanner.nextLine();
            System.out.print("Ingrese el país: ");
            String pais = scanner.nextLine();
            System.out.print("Ingrese la cantidad de estrellas: ");
            int estrellas = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Ingrese la dirección: ");
            String direccion = scanner.nextLine();
            System.out.print("Ingrese la zona/barrio del hotel: ");
            String zona = scanner.nextLine();
            System.out.print("Ingrese los amenities del hotel (separados por comas): ");
            String amenities = scanner.nextLine();

            Hotel nuevoHotel = new Hotel(sistema.getHoteles().size() + 1, nombre, ciudad, pais, estrellas, direccion, zona, amenities);

            sistema.agregarHotel(nuevoHotel);

            System.out.print("Ingrese la cantidad de habitaciones que desea agregar: ");
            int numHabitaciones = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numHabitaciones; i++) {
                System.out.println("Habitación " + (i + 1) + ": ");
                System.out.print("Es una habitación deluxe? (true/false): ");
                boolean esDeluxe = scanner.nextBoolean();
                System.out.print("Capacidad de la habitación: ");
                int capacidad = scanner.nextInt();
                System.out.print("Es cama matrimonial? (true/false): ");
                boolean esMatrimonial = scanner.nextBoolean();
                System.out.print("Tiene aire acondicionado? (true/false): ");
                boolean aireAcondicionado = scanner.nextBoolean();
                System.out.print("Tiene balcón? (true/false): ");
                boolean balcon = scanner.nextBoolean();
                System.out.print("Tiene vista? (true/false): ");
                boolean vista = scanner.nextBoolean();
                scanner.nextLine();
                System.out.print("Ingrese los amenities (separados por comas): ");
                String amenitiesHabitacion = scanner.nextLine();

                Habitacion habitacion;
                if (esDeluxe) {
                    System.out.print("Tiene jacuzzi? (true/false): ");
                    boolean jacuzzi = scanner.nextBoolean();
                    habitacion = new HabitacionDeluxe(i + 1, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, amenitiesHabitacion, jacuzzi);
                    scanner.nextLine();
                } else {
                    habitacion = new Habitacion(i + 1, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, amenitiesHabitacion);
                }

                nuevoHotel.agregarHabitacion(habitacion);
            }

            System.out.println("Hotel agregado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta. Asegúrese de ingresar los valores correctos.");
            scanner.nextLine();
        }
    }

    public void registrarHuesped() {
        try {
            System.out.print("Ingrese el nombre del huésped: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el apellido paterno: ");
            String apaterno = scanner.nextLine();
            System.out.print("Ingrese el apellido materno: ");
            String amaterno = scanner.nextLine();
            System.out.print("Ingrese el tipo de documento: ");
            String tipoDocumento = scanner.nextLine();
            System.out.print("Ingrese el número de documento: ");
            String numDocumento = scanner.nextLine();
            System.out.print("Ingrese la fecha de nacimiento (yyyy-MM-dd): ");
            String fechaNacimiento = scanner.nextLine();
            System.out.print("Ingrese el teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Ingrese el país: ");
            String pais = scanner.nextLine();

            Huesped nuevoHuesped = new Huesped(sistema.getHuespedes().size() + 1, nombre, apaterno, amaterno, tipoDocumento, numDocumento, fechaNacimiento, telefono, pais);

            sistema.registrarHuesped(nuevoHuesped);

            System.out.println("Huésped registrado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta. Asegúrese de ingresar los valores correctos.");
            scanner.nextLine();
        }
    }

    public void verTodosLosHoteles() {
        System.out.println("\n--- Lista de todos los hoteles ---");
        for (Hotel hotel : sistema.getHoteles()) {
            System.out.println("ID: " + hotel.getIdHotel() + ", Nombre: " + hotel.getNombre() + ", Ciudad: " + hotel.getCiudad() + ", País: " + hotel.getPais() + ", Estrellas: " + hotel.getEstrellas() + ", Dirección: " + hotel.getDireccion());
        }
    }

    public void verTodosLosHuespedes() {
        System.out.println("\n--- Lista de todos los huéspedes ---");
        for (Huesped huesped : sistema.getHuespedes()) {
            System.out.println("ID: " + huesped.getId() + ", Nombre: " + huesped.getNombre() + " " + huesped.getApaterno() + " " + huesped.getAmaterno() + ", Tipo Documento: " + huesped.getTipoDocumento() + ", Número Documento: " + huesped.getNumDocumento() + ", Fecha de Nacimiento: " + huesped.getFechaNacimiento() + ", Teléfono: " + huesped.getTelefono() + ", País: " + huesped.getPais());
        }
    }

    public void verTodasLasHabitaciones() {
        System.out.println("\n--- Lista de todas las habitaciones ---");
        for (Hotel hotel : sistema.getHoteles()) {
            System.out.println("Hotel: " + hotel.getNombre());
            for (Habitacion habitacion : hotel.getHabitaciones()) {
                System.out.println("ID Habitación: " + habitacion.getIdHabitacion() + ", Capacidad: " + habitacion.getCapacidad() + ", Tarifa Vigente: " + habitacion.getTarifaVigente() + ", Matrimonial: " + habitacion.isMatrimonial() + ", Aire Acondicionado: " + habitacion.isAireAcondicionado() + ", Balcón: " + habitacion.isBalcon() + ", Vista: " + habitacion.isVista() + ", Ocupada: " + habitacion.isOcupada());
                if (habitacion instanceof HabitacionDeluxe) {
                    System.out.println("Habitación Deluxe con Jacuzzi: " + ((HabitacionDeluxe) habitacion).tieneJacuzzi());
                }
            }
        }
    }

    public void verHabitacionesDeluxe() {
        System.out.println("\n--- Lista de Habitaciones Deluxe en todos los hoteles ---");
        boolean encontrado = false;

        for (Hotel hotel : sistema.getHoteles()) {
            List<Habitacion> habitaciones = hotel.getHabitaciones();
            for (Habitacion habitacion : habitaciones) {
                if (habitacion instanceof HabitacionDeluxe) {
                    HabitacionDeluxe habitacionDeluxe = (HabitacionDeluxe) habitacion;
                    System.out.println("Hotel: " + hotel.getNombre() +
                            ", ID Habitación Deluxe: " + habitacionDeluxe.getIdHabitacion() +
                            ", Capacidad: " + habitacionDeluxe.getCapacidad() +
                            ", Tarifa Vigente: " + habitacionDeluxe.getTarifaVigente() +
                            ", Jacuzzi: " + (habitacionDeluxe.tieneJacuzzi() ? "Sí" : "No"));
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron habitaciones deluxe en ninguno de los hoteles.");
        }
    }

    public void realizarReserva() {
        try {

            System.out.println("\n--- Lista de Hoteles Disponibles ---");
            if (sistema.getHoteles().isEmpty()) {
                System.out.println("No hay hoteles registrados en el sistema. Registre un hotel primero.");
                return;
            }

            for (Hotel hotel : sistema.getHoteles()) {
                System.out.println("ID: " + hotel.getIdHotel() + " - Nombre: " + hotel.getNombre() + " - Ciudad: " + hotel.getCiudad() + " - País: " + hotel.getPais());
            }

            System.out.print("\nIngrese el ID del hotel donde desea reservar: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();

            if (idHotel <= 0 || idHotel > sistema.getHoteles().size()) {
                System.out.println("Error: El ID del hotel ingresado no es válido.");
                return;
            }

            Hotel hotelSeleccionado = sistema.getHoteles().get(idHotel - 1);

            System.out.println("\n--- Habitaciones Disponibles en el Hotel: " + hotelSeleccionado.getNombre() + " ---");
            List<Habitacion> habitacionesDisponibles = hotelSeleccionado.obtenerHabitacionesDisponibles();

            if (habitacionesDisponibles.isEmpty()) {
                System.out.println("No hay habitaciones disponibles en este hotel.");
                return;
            }

            for (Habitacion habitacion : habitacionesDisponibles) {
                System.out.println("ID: " + habitacion.getIdHabitacion() + " - Tarifa Vigente: " + habitacion.getTarifaVigente());
            }

            System.out.print("\nIngrese el ID de la habitación a reservar: ");
            int idHabitacion = scanner.nextInt();
            scanner.nextLine();

            Habitacion habitacionSeleccionada = null;
            for (Habitacion hab : habitacionesDisponibles) {
                if (hab.getIdHabitacion() == idHabitacion) {

                    if (hab.isPreContratadaExternamente() || hab.isOcupada()) {
                        System.out.println("La habitación no está disponible para reserva.");
                        return;
                    }
                    habitacionSeleccionada = hab;
                    break;
                }
            }

            if (habitacionSeleccionada == null) {
                System.out.println("Error: El ID de la habitación ingresado no es válido o la habitación no está disponible.");
                return;
            }

            System.out.print("Ingrese la fecha de inicio de la reserva (yyyy-MM-dd): ");
            String fechaInicioStr = scanner.nextLine();
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);

            System.out.print("Ingrese la fecha de fin de la reserva (yyyy-MM-dd): ");
            String fechaFinStr = scanner.nextLine();
            LocalDate fechaFin = LocalDate.parse(fechaFinStr);

            if (fechaInicio.isAfter(fechaFin)) {
                System.out.println("Error: La fecha de inicio debe ser anterior a la fecha de fin.");
                return;
            }

            System.out.print("Ingrese la cantidad de personas: ");
            int cantidadPersonas = scanner.nextInt();
            if (cantidadPersonas <= 0) {
                System.out.println("Error: La cantidad de personas debe ser mayor a cero.");
                return;
            }
            scanner.nextLine();

            System.out.print("Ingrese cualquier observación para la reserva: ");
            String observacion = scanner.nextLine();

            System.out.println("\n--- Lista de Huéspedes Registrados ---");
            if (sistema.getHuespedes().isEmpty()) {
                System.out.println("No hay huéspedes registrados en el sistema. Registre un huésped primero.");
                return;
            }

            for (Huesped huesped : sistema.getHuespedes()) {
                System.out.println("ID: " + huesped.getId() + " - Nombre: " + huesped.getNombre() + " " + huesped.getApaterno());
            }

            System.out.print("\nIngrese el ID del huésped que realizará la reserva: ");
            int idHuesped = scanner.nextInt();
            scanner.nextLine();

            if (idHuesped <= 0 || idHuesped > sistema.getHuespedes().size()) {
                System.out.println("Error: El ID del huésped ingresado no es válido.");
                return;
            }

            Huesped huespedSeleccionado = sistema.getHuespedes().get(idHuesped - 1);

            sistema.reservarHabitacion(hotelSeleccionado, huespedSeleccionado, idHabitacion, fechaInicio, fechaFin, cantidadPersonas, observacion);
            System.out.println("Reserva realizada exitosamente.");

        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta. Asegúrese de ingresar los valores correctos.");
            scanner.nextLine();
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha incorrecto. Asegúrese de ingresar la fecha en el formato yyyy-MM-dd.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: No se pudo encontrar el elemento solicitado. Verifique los IDs ingresados.");
        }
    }

    public void cancelarReserva() {
        System.out.println("Reservas registradas:");
        for (Reserva reserva : sistema.getReservas()) {
            System.out.println("id: " + reserva.getIdReserva() + " - habitación: " + reserva.getHabitacion().getIdHabitacion() + " - huésped: " + reserva.getHuesped().getNombre());
        }
        System.out.print("Ingrese el id de la reserva que desea cancelar: ");
        int idReserva = scanner.nextInt();
        sistema.cancelarReserva(idReserva);
    }
    public void modificarReserva() {
        try {
            System.out.print("Ingrese el ID de la reserva a modificar: ");
            int idReserva = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Ingrese la nueva fecha de inicio (yyyy-MM-dd): ");
            String nuevaFechaInicioStr = scanner.nextLine();
            System.out.print("Ingrese la nueva fecha de fin (yyyy-MM-dd): ");
            String nuevaFechaFinStr = scanner.nextLine();
            System.out.print("Ingrese la nueva cantidad de personas: ");
            int nuevaCantidadPersonas = scanner.nextInt();
            scanner.nextLine();

            LocalDate nuevaFechaInicio = LocalDate.parse(nuevaFechaInicioStr);
            LocalDate nuevaFechaFin = LocalDate.parse(nuevaFechaFinStr);

            boolean exito = sistema.modificarReserva(idReserva, nuevaFechaInicio, nuevaFechaFin, nuevaCantidadPersonas);
            if (exito) {
                System.out.println("Reserva modificada exitosamente.");
            } else {
                System.out.println("No se pudo modificar la reserva.");
            }
        } catch (InputMismatchException | DateTimeParseException e) {
            System.out.println("Error: Entrada incorrecta. Asegúrese de ingresar los valores correctos.");
            scanner.nextLine();
        }
    }


    public void listarHabitacionesDisponiblesEnPeriodo() {
        try {
            System.out.print("Ingrese el ID del hotel: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Ingrese la fecha de inicio del periodo (yyyy-MM-dd): ");
            String fechaInicioStr = scanner.nextLine();
            System.out.print("Ingrese la fecha de fin del periodo (yyyy-MM-dd): ");
            String fechaFinStr = scanner.nextLine();

            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
            LocalDate fechaFin = LocalDate.parse(fechaFinStr);

            List<Habitacion> habitacionesDisponibles = sistema.listarHabitacionesDisponiblesEnPeriodo(idHotel, fechaInicio, fechaFin);

            if (habitacionesDisponibles.isEmpty()) {
                System.out.println("No hay habitaciones disponibles en el periodo especificado.");
            } else {
                System.out.println("--- Habitaciones disponibles en el periodo ---");
                for (Habitacion habitacion : habitacionesDisponibles) {
                    System.out.println("ID: " + habitacion.getIdHabitacion() + ", Tarifa Vigente: " + habitacion.getTarifaVigente() + ", Capacidad: " + habitacion.getCapacidad() + ", Matrimonial: " + habitacion.isMatrimonial());
                }
            }
        } catch (InputMismatchException | DateTimeParseException e) {
            System.out.println("Error: Entrada incorrecta. Asegúrese de ingresar los valores correctos.");
            scanner.nextLine();
        }
    }
    public void filtrarHabitacionesConReserva() {
        System.out.println("Hoteles disponibles:");
        for (Hotel hotel : sistema.getHoteles()) {
            System.out.println("Hotel: " + hotel.getNombre());
            List<Habitacion> ocupadas = hotel.filtrarHabitacionesOcupadas();
            if (!ocupadas.isEmpty()) {
                System.out.println("Habitaciones con reserva:");
                for (Habitacion hab : ocupadas) {
                    System.out.println("ID: " + hab.getIdHabitacion() + " - Ocupada");
                }
            } else {
                System.out.println("No hay habitaciones con reserva en este hotel.");
            }
        }
    }

    public void filtrarHabitacionesSinReserva() {
        System.out.println("Hoteles disponibles:");
        for (Hotel hotel : sistema.getHoteles()) {
            System.out.println("Hotel: " + hotel.getNombre());
            List<Habitacion> disponibles = hotel.obtenerHabitacionesDisponibles();
            if (!disponibles.isEmpty()) {
                System.out.println("Habitaciones sin reserva:");
                for (Habitacion hab : disponibles) {
                    System.out.println("ID: " + hab.getIdHabitacion() + " - Disponible");
                }
            } else {
                System.out.println("No hay habitaciones sin reserva en este hotel.");
            }
        }
    }

    public void consultarHotelesPorCiudad() {
        System.out.print("Ingrese la ciudad para consultar hoteles: ");
        String ciudad = scanner.nextLine();
        List<Hotel> hotelesEnCiudad = sistema.consultarHotelesPorCiudad(ciudad);
        if (hotelesEnCiudad.isEmpty()) {
            System.out.println("No se encontraron hoteles en la ciudad especificada.");
        } else {
            System.out.println("Hoteles encontrados:");
            for (Hotel hotel : hotelesEnCiudad) {
                System.out.println("Hotel: " + hotel.getNombre() + " - Estrellas: " + hotel.getEstrellas());
            }
        }
    }

    public void consultarHotelesPorPais() {
        System.out.print("Ingrese el país para consultar hoteles: ");
        String pais = scanner.nextLine();
        List<Hotel> hotelesEnPais = sistema.consultarHotelesPorPais(pais);
        if (hotelesEnPais.isEmpty()) {
            System.out.println("No se encontraron hoteles en el país especificado.");
        } else {
            System.out.println("Hoteles encontrados:");
            for (Hotel hotel : hotelesEnPais) {
                System.out.println("Hotel: " + hotel.getNombre() + " - Ciudad: " + hotel.getCiudad() + " - Estrellas: " + hotel.getEstrellas());
            }
        }
    }

    public void consultarHotelesPorEstrellas() {
        System.out.print("Ingrese la cantidad de estrellas para consultar hoteles: ");
        int estrellas = scanner.nextInt();
        scanner.nextLine();
        List<Hotel> hotelesConEstrellas = sistema.consultarHotelesPorEstrellas(estrellas);
        if (hotelesConEstrellas.isEmpty()) {
            System.out.println("No se encontraron hoteles con " + estrellas + " estrellas.");
        } else {
            System.out.println("Hoteles encontrados:");
            for (Hotel hotel : hotelesConEstrellas) {
                System.out.println("Hotel: " + hotel.getNombre() + " - Ciudad: " + hotel.getCiudad() + " - Estrellas: " + hotel.getEstrellas());
            }
        }

    }
    public void modificarHotel() {
        try {
            System.out.print("Ingrese el ID del hotel que desea modificar: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();

            Hotel hotel = sistema.buscarHotelPorId(idHotel);
            if (hotel == null) {
                System.out.println("Error: No se encontró el hotel con el ID especificado.");
                return;
            }

            System.out.print("Ingrese el nuevo nombre del hotel: ");
            hotel.setNombre(scanner.nextLine());
            System.out.print("Ingrese la nueva ciudad: ");
            hotel.setCiudad(scanner.nextLine());
            System.out.print("Ingrese el nuevo país: ");
            hotel.setPais(scanner.nextLine());
            System.out.print("Ingrese la nueva cantidad de estrellas: ");
            hotel.setEstrellas(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Ingrese la nueva dirección: ");
            hotel.setDireccion(scanner.nextLine());
            System.out.print("Ingrese la nueva zona/barrio del hotel: ");
            hotel.setZona(scanner.nextLine());
            System.out.print("Ingrese los nuevos amenities del hotel (separados por comas): ");
            hotel.setAmenities(scanner.nextLine());

            System.out.println("Hotel modificado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta.");
            scanner.nextLine();
        }
    }
    public void eliminarHotel() {
        try {
            System.out.print("Ingrese el ID del hotel que desea eliminar: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();

            if (sistema.eliminarHotel(idHotel)) {
                sistema.eliminarHotelEnBaseDeDatos(idHotel);
                System.out.println("Hotel eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el hotel. Asegúrese de que no tenga reservas activas.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta.");
            scanner.nextLine();
        }
    }

    public void modificarHuesped() {
        try {
            System.out.print("Ingrese el ID del huésped que desea modificar: ");
            int idHuesped = scanner.nextInt();
            scanner.nextLine();

            Huesped huesped = sistema.buscarHuespedPorId(idHuesped);
            if (huesped == null) {
                System.out.println("Error: No se encontró el huésped con el ID especificado.");
                return;
            }

            System.out.print("Ingrese el nuevo nombre: ");
            huesped.setNombre(scanner.nextLine());
            System.out.print("Ingrese el nuevo apellido paterno: ");
            huesped.setApaterno(scanner.nextLine());
            System.out.print("Ingrese el nuevo apellido materno: ");
            huesped.setAmaterno(scanner.nextLine());
            System.out.print("Ingrese el nuevo tipo de documento: ");
            huesped.setTipoDocumento(scanner.nextLine());
            System.out.print("Ingrese el nuevo número de documento: ");
            huesped.setNumDocumento(scanner.nextLine());
            System.out.print("Ingrese la nueva fecha de nacimiento (yyyy-MM-dd): ");
            huesped.setFechaNacimiento(scanner.nextLine());
            System.out.print("Ingrese el nuevo teléfono: ");
            huesped.setTelefono(scanner.nextLine());
            System.out.print("Ingrese el nuevo país: ");
            huesped.setPais(scanner.nextLine());

            System.out.println("Huésped modificado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta.");
            scanner.nextLine();
        }
    }
    public void eliminarHuesped() {
        try {
            System.out.print("Ingrese el ID del huésped que desea eliminar: ");
            int idHuesped = scanner.nextInt();
            scanner.nextLine();

            if (sistema.eliminarHuesped(idHuesped)) {
                System.out.println("Huésped eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el huésped. Asegúrese de que no tenga reservas activas.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta.");
            scanner.nextLine();
        }
    }


    public void pagarReserva() {
        System.out.print("Ingrese el ID de la reserva que desea pagar: ");
        int idReserva = scanner.nextInt();
        scanner.nextLine();

        Reserva reserva = sistema.buscarReservaPorId(idReserva);
        if (reserva != null && !reserva.isPagada()) {
            reserva.pagarReserva();
            System.out.println("Reserva pagada exitosamente.");
        } else if (reserva == null) {
            System.out.println("Error: Reserva no encontrada.");
        } else {
            System.out.println("La reserva ya está pagada.");
        }
    }

    public void agregarHabitacionAHotel() {
        try {
            System.out.print("Ingrese el ID del hotel al que desea agregar una habitación: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();

            Hotel hotel = sistema.buscarHotelPorId(idHotel);
            if (hotel == null) {
                System.out.println("Error: Hotel no encontrado.");
                return;
            }

            System.out.print("Es una habitación deluxe? (true/false): ");
            boolean esDeluxe = scanner.nextBoolean();
            System.out.print("Capacidad de la habitación: ");
            int capacidad = scanner.nextInt();
            System.out.print("Es cama matrimonial? (true/false): ");
            boolean esMatrimonial = scanner.nextBoolean();
            System.out.print("Tiene aire acondicionado? (true/false): ");
            boolean aireAcondicionado = scanner.nextBoolean();
            System.out.print("Tiene balcón? (true/false): ");
            boolean balcon = scanner.nextBoolean();
            System.out.print("Tiene vista? (true/false): ");
            boolean vista = scanner.nextBoolean();
            scanner.nextLine();
            System.out.print("Ingrese los amenities de la habitación (separados por comas): ");
            String amenities = scanner.nextLine();

            Habitacion habitacion;
            if (esDeluxe) {
                System.out.print("Tiene jacuzzi? (true/false): ");
                boolean jacuzzi = scanner.nextBoolean();
                habitacion = new HabitacionDeluxe(hotel.getHabitaciones().size() + 1, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, amenities, jacuzzi);
                scanner.nextLine();
            } else {
                habitacion = new Habitacion(hotel.getHabitaciones().size() + 1, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, amenities);
            }

            hotel.agregarHabitacion(habitacion);
            sistema.insertarHabitacionEnBaseDeDatos(habitacion, idHotel);

            System.out.println("Habitación agregada exitosamente al hotel.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta. Asegúrese de ingresar los valores correctos.");
            scanner.nextLine(); // limpiar el buffer en caso de error
        }
    }


    public void modificarHabitacion() {
        try {
            System.out.print("Ingrese el ID del hotel donde se encuentra la habitación a modificar: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();

            Hotel hotel = sistema.buscarHotelPorId(idHotel);
            if (hotel == null) {
                System.out.println("Error: No se encontró el hotel con el ID especificado.");
                return;
            }

            System.out.print("Ingrese el ID de la habitación a modificar: ");
            int idHabitacion = scanner.nextInt();
            scanner.nextLine();

            Habitacion habitacion = hotel.buscarHabitacionPorId(idHabitacion);
            if (habitacion == null) {
                System.out.println("Error: No se encontró la habitación con el ID especificado.");
                return;
            }

            System.out.print("Ingrese la nueva capacidad de la habitación: ");
            int capacidad = scanner.nextInt();
            System.out.print("Es cama matrimonial? (true/false): ");
            boolean esMatrimonial = scanner.nextBoolean();
            System.out.print("Tiene aire acondicionado? (true/false): ");
            boolean aireAcondicionado = scanner.nextBoolean();
            System.out.print("Tiene balcón? (true/false): ");
            boolean balcon = scanner.nextBoolean();
            System.out.print("Tiene vista? (true/false): ");
            boolean vista = scanner.nextBoolean();
            scanner.nextLine();
            System.out.print("Ingrese los nuevos amenities de la habitación (separados por comas): ");
            String amenities = scanner.nextLine();

            habitacion.setCapacidad(capacidad);
            habitacion.setMatrimonial(esMatrimonial);
            habitacion.setAireAcondicionado(aireAcondicionado);
            habitacion.setBalcon(balcon);
            habitacion.setVista(vista);
            habitacion.setAmenities(amenities);

            System.out.println("Habitación modificada exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta.");
            scanner.nextLine(); // Limpiar el buffer en caso de error
        }
    }
    public void eliminarHabitacion() {
        try {
            System.out.print("Ingrese el ID del hotel donde se encuentra la habitación a eliminar: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();

            Hotel hotel = sistema.buscarHotelPorId(idHotel);
            if (hotel == null) {
                System.out.println("Error: No se encontró el hotel con el ID especificado.");
                return;
            }

            System.out.print("Ingrese el ID de la habitación a eliminar: ");
            int idHabitacion = scanner.nextInt();
            scanner.nextLine();

            if (hotel.eliminarHabitacion(idHabitacion)) {
                System.out.println("Habitación eliminada exitosamente.");
            } else {
                System.out.println("Error: No se pudo eliminar la habitación. Asegúrese de que no tenga reservas activas.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada incorrecta.");
            scanner.nextLine();
        }
    }


    public void registrarOcupacionHabitacion() {
        try {
            System.out.print("Ingrese el ID del hotel: ");
            int idHotel = scanner.nextInt();
            scanner.nextLine();


            if (idHotel <= 0 || idHotel > sistema.getHoteles().size()) {
                System.out.println("Error: El ID del hotel ingresado no es válido.");
                return;
            }

            Hotel hotel = sistema.getHoteles().get(idHotel - 1);

            System.out.print("Ingrese el ID de la habitación: ");
            int idHabitacion = scanner.nextInt();
            scanner.nextLine();


            if (idHabitacion <= 0 || idHabitacion > hotel.getHabitaciones().size()) {
                System.out.println("Error: El ID de la habitación ingresado no es válido.");
                return;
            }

            Habitacion habitacion = hotel.getHabitaciones().get(idHabitacion - 1);

            if (habitacion.isOcupada()) {
                System.out.println("La habitación ya está ocupada.");
            } else {
                System.out.print("Ingrese el ID del huésped que ocupará la habitación: ");
                int idHuesped = scanner.nextInt();
                scanner.nextLine();

                // Validar que el huesped existe
                if (idHuesped <= 0 || idHuesped > sistema.getHuespedes().size()) {
                    System.out.println("Error: El ID del huésped ingresado no es válido.");
                    return;
                }

                Huesped huesped = sistema.getHuespedes().get(idHuesped - 1);

                habitacion.setOcupada(true);
                habitacion.setResponsable(huesped); // asigna el hyuesped como responsable de habitacion
                System.out.println("Habitación marcada como ocupada exitosamente por el huésped: " + huesped.getNombre());
            }
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese datos correctos.");
            scanner.nextLine();
        }
    }


    public void consultarHotelesPorFecha() {
        System.out.print("Ingrese la fecha (yyyy-MM-dd) para consultar hoteles: ");
        String fechaStr = scanner.nextLine();
        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            boolean hotelesDisponiblesEncontrados = false;

            // Recorremos todos los hoteles para ver si alguno tiene habitaciones disponibles en la fecha indicada
            for (Hotel hotel : sistema.getHoteles()) {
                boolean tieneDisponibilidad = false;

                // Recorremos las habitaciones del hotel
                for (Habitacion habitacion : hotel.getHabitaciones()) {
                    // Si la habitacion no esta ocupada, la consideramos disponible en la fecha
                    if (!habitacion.isOcupada()) {
                        tieneDisponibilidad = true;
                        break; // No se necesita revisar mas habitaciones, se sabe que tiene al menos una disponible
                    }
                }

                // Si encontramos al menos una habitación disponible, mostramos el hotel
                if (tieneDisponibilidad) {
                    System.out.println("Hotel disponible: " + hotel.getNombre() + " - Ciudad: " + hotel.getCiudad() + " - País: " + hotel.getPais());
                    hotelesDisponiblesEncontrados = true;
                }
            }

            if (!hotelesDisponiblesEncontrados) {
                System.out.println("No se encontraron hoteles con habitaciones disponibles en la fecha especificada.");
            }

        } catch (Exception e) {
            System.out.println("Error: Por favor, ingrese una fecha válida en el formato yyyy-MM-dd.");
        }
    }

    public void consultarHotelesPorNombre() {
        System.out.print("Ingrese el nombre del hotel: ");
        String nombre = scanner.nextLine();
        try {
            List<Hotel> hotelesEncontrados = sistema.consultarHotelesPorNombre(nombre);
            if (hotelesEncontrados.isEmpty()) {
                System.out.println("No se encontraron hoteles con ese nombre.");
            } else {
                for (Hotel hotel : hotelesEncontrados) {
                    System.out.println("Hotel: " + hotel.getNombre() + " - Ciudad: " + hotel.getCiudad() + " - País: " + hotel.getPais());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Hubo un problema al intentar buscar el hotel. Por favor, intente nuevamente.");
        }
    }

    public Scanner getScanner() {
        return scanner;
    }

}
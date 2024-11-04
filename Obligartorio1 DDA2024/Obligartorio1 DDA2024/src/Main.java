import Controladora.ControladoraPrincipal;
import Persistencia.ConexionBD;
import java.sql.Connection;
import java.util.InputMismatchException;


// Main.java

public class Main {
    public static void main(String[] args) {

        ControladoraPrincipal controladora = new ControladoraPrincipal();
        Connection conexion = controladora.conectarBD();

        if (conexion == null) {
            return;
        }

        boolean continuar = true;
        while (continuar) {
            try {
                System.out.println("\n--- Sistema de reservas ---");

                System.out.println("1. Agregar hotel");
                System.out.println("2. Ver todos los hoteles");
                System.out.println("3. Consultar hoteles por ciudad");
                System.out.println("4. Consultar hoteles por país");
                System.out.println("5. Consultar hoteles por estrellas");
                System.out.println("6. Consultar hoteles por nombre");
                System.out.println("7. Consultar hoteles por fecha");
                System.out.println("8. Modificar hotel");
                System.out.println("9. Eliminar hotel");

                System.out.println("\n--- Gestión de Huéspedes ---");
                System.out.println("10. Registrar huésped");
                System.out.println("11. Ver todos los huéspedes");
                System.out.println("12. Modificar huésped");
                System.out.println("13. Eliminar huésped");

                System.out.println("\n--- Gestión de Habitaciones ---");
                System.out.println("14. Ver todas las habitaciones");
                System.out.println("15. Ver habitaciones deluxe");
                System.out.println("16. Filtrar habitaciones con reserva");
                System.out.println("17. Filtrar habitaciones sin reserva");
                System.out.println("18. Listar habitaciones disponibles en un periodo específico");
                System.out.println("19. Registrar ocupación de una habitación");
                System.out.println("20. Modificar habitación");
                System.out.println("21. Eliminar habitación");
                System.out.println("22. Agregar habitación");

                System.out.println("\n--- Gestión de Reservas ---");
                System.out.println("23. Reservar habitación por periodo de tiempo");
                System.out.println("24. Modificar reserva");
                System.out.println("25. Cancelar reserva");

                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = controladora.getScanner().nextInt();
                controladora.getScanner().nextLine();

                switch (opcion) {
                    case 1:
                        controladora.agregarHotel();
                        break;
                    case 2:
                        controladora.verTodosLosHoteles();
                        break;
                    case 3:
                        controladora.consultarHotelesPorCiudad();
                        break;
                    case 4:
                        controladora.consultarHotelesPorPais();
                        break;
                    case 5:
                        controladora.consultarHotelesPorEstrellas();
                        break;
                    case 6:
                        controladora.consultarHotelesPorNombre();
                        break;
                    case 7:
                        controladora.consultarHotelesPorFecha();
                        break;
                    case 8:
                        controladora.modificarHotel();
                        break;
                    case 9:
                        controladora.eliminarHotel();
                        break;
                    case 10:
                        controladora.registrarHuesped();
                        break;
                    case 11:
                        controladora.verTodosLosHuespedes();
                        break;
                    case 12:
                        controladora.modificarHuesped();
                        break;
                    case 13:
                        controladora.eliminarHuesped();
                        break;
                    case 14:
                        controladora.verTodasLasHabitaciones();
                        break;
                    case 15:
                        controladora.verHabitacionesDeluxe();
                        break;
                    case 16:
                        controladora.filtrarHabitacionesConReserva();
                        break;
                    case 17:
                        controladora.filtrarHabitacionesSinReserva();
                        break;
                    case 18:
                        controladora.listarHabitacionesDisponiblesEnPeriodo();
                        break;
                    case 19:
                        controladora.registrarOcupacionHabitacion();
                        break;
                    case 20:
                        controladora.modificarHabitacion();
                        break;
                    case 21:
                        controladora.eliminarHabitacion();
                        break;
                    case 22:
                        controladora.agregarHabitacionAHotel();
                        break;
                    case 23:
                        controladora.realizarReserva();
                        break;
                    case 24:
                        controladora.modificarReserva();
                        break;
                    case 25:
                        controladora.cancelarReserva();
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada incorrecta. Por favor, ingrese el tipo de dato correcto.");
                controladora.getScanner().nextLine();
            }
        }

        ConexionBD.cerrar(conexion);
    }
}

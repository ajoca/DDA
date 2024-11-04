package Utils;

public class MiExcepcion extends Exception {
    public MiExcepcion(String mensaje) {
        super(mensaje);
    }

    public static MiExcepcion error500() {
        return new MiExcepcion("Error 500: Error interno del servidor.");
    }

    public static MiExcepcion errorConexion() {
        return new MiExcepcion("Error: No se pudo establecer la conexión con la base de datos.");
    }

    public static MiExcepcion errorSQL() {
        return new MiExcepcion("Error en la consulta SQL.");
    }
}

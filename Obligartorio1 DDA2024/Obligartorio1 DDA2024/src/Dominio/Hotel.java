package Dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private int idHotel;
    private String nombre;
    private String ciudad;
    private String pais;
    private int estrellas;
    private String direccion;
    private String zona;
    private String amenities;
    private List<Habitacion> habitaciones;
    private List<Tarifa> tarifas; // Lista de tarifas del hotel

    public Hotel(int idHotel, String nombre, String ciudad, String pais, int estrellas, String direccion, String zona, String amenities) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estrellas = estrellas;
        this.direccion = direccion;
        this.zona = zona;
        this.amenities = amenities;
        this.habitaciones = new ArrayList<>();
        this.tarifas = new ArrayList<>(); // Inicializa la lista de tarifas
    }

    // Getters y Setters

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }



    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.isOcupada()) {
                disponibles.add(habitacion);
            }
        }
        return disponibles;
    }

    public List<Habitacion> filtrarHabitacionesOcupadas() {
        List<Habitacion> ocupadas = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.isOcupada()) {
                ocupadas.add(habitacion);
            }
        }
        return ocupadas;
    }
    public Habitacion buscarHabitacionPorId(int idHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdHabitacion() == idHabitacion) {
                return habitacion;
            }
        }
        return null;
    }
    public boolean eliminarHabitacion(int idHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getIdHabitacion() == idHabitacion) {
                if (!habitacion.isOcupada()) { // Asegurarse de que no tenga reservas activas
                    habitaciones.remove(habitacion);
                    return true;
                } else {
                    System.out.println("No se puede eliminar la habitación ya que está ocupada o reservada.");
                    return false;
                }
            }
        }
        return false;
    }


    public List<Habitacion> obtenerHabitacionesOcupadas() {
        List<Habitacion> ocupadas = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.isOcupada()) {
                ocupadas.add(habitacion);
            }
        }
        return ocupadas; // Retorna la lista de habitaciones ocupadas
    }

    // Métodos para tarifas

    public void agregarTarifa(Tarifa tarifa) {
        tarifas.add(tarifa);
    }

    public Tarifa obtenerTarifaVigente(LocalDate fecha) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.estaVigente(fecha)) {
                return tarifa;
            }
        }
        return null;
    }
}

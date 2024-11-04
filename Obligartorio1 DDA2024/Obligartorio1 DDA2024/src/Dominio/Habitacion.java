package Dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habitacion {
    private int idHabitacion;
    private int capacidad;
    private boolean esMatrimonial;
    private boolean aireAcondicionado;
    private boolean balcon;
    private boolean vista;
    private boolean ocupada;
    private double tarifaBase;
    private List<Tarifa> tarifas;
    private boolean preContratadaExternamente;
    private Huesped responsable;
    private String amenities;

    public Habitacion(int idHabitacion, int capacidad, boolean esMatrimonial, boolean aireAcondicionado, boolean balcon, boolean vista, double tarifaBase, String amenities) {
        this.idHabitacion = idHabitacion;
        this.capacidad = capacidad;
        this.esMatrimonial = esMatrimonial;
        this.aireAcondicionado = aireAcondicionado;
        this.balcon = balcon;
        this.vista = vista;
        this.tarifaBase = tarifaBase;
        this.ocupada = false;
        this.amenities = amenities;
        this.tarifas = new ArrayList<>();
        this.preContratadaExternamente = false;
    }

    public Habitacion(int idHabitacion, int capacidad, boolean esMatrimonial, boolean aireAcondicionado, boolean balcon, boolean vista, String amenitiesHabitacion) {
        this(idHabitacion, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, 0.0, amenitiesHabitacion);
    }

    public void agregarTarifa(Tarifa tarifa) {
        tarifas.add(tarifa);
    }

    public double getTarifaVigente(LocalDate fecha) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.estaVigente(fecha)) {
                return tarifa.getPrecio();
            }
        }
        return tarifaBase; // Si no hay tarifas vigentes, devuelve la tarifa base
    }

    public double getTarifaVigente() {
        return getTarifaVigente(LocalDate.now());
    }

    public double getTarifaVigenteParaFecha(LocalDate fecha) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.estaVigente(fecha)) {
                return tarifa.getPrecio();
            }
        }
        throw new IllegalStateException("No hay tarifa vigente para la fecha especificada.");
    }

    public Huesped getResponsable() {
        return responsable;
    }

    public void setResponsable(Huesped responsable) {
        this.responsable = responsable;
    }

    public boolean isPreContratadaExternamente() {
        return preContratadaExternamente;
    }

    public void setPreContratadaExternamente(boolean preContratadaExternamente) {
        this.preContratadaExternamente = preContratadaExternamente;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isMatrimonial() {
        return esMatrimonial;
    }

    public void setMatrimonial(boolean esMatrimonial) {
        this.esMatrimonial = esMatrimonial;
    }

    public boolean isAireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(boolean aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }

    public boolean isBalcon() {
        return balcon;
    }

    public void setBalcon(boolean balcon) {
        this.balcon = balcon;
    }

    public boolean isVista() {
        return vista;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return "Habitacion ID: " + idHabitacion + ", Capacidad: " + capacidad + ", Matrimonial: " + (esMatrimonial ? "Sí" : "No") +
                ", Aire Acondicionado: " + (aireAcondicionado ? "Sí" : "No") + ", Balcón: " + (balcon ? "Sí" : "No") +
                ", Vista: " + (vista ? "Sí" : "No") + ", Ocupada: " + ocupada + ", Responsable: " +
                (responsable != null ? responsable.getNombre() : "Sin asignar");
    }
}
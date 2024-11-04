package Dominio;

import java.time.LocalDate;

public class Tarifa {
    private int idTarifa;
    private double precio;
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinVigencia;

    public Tarifa(int idTarifa, double precio, LocalDate fechaInicioVigencia, LocalDate fechaFinVigencia) {
        this.idTarifa = idTarifa;
        this.precio = precio;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.fechaFinVigencia = fechaFinVigencia;
    }


    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(LocalDate fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public LocalDate getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(LocalDate fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    // vigencia por fecha
    public boolean estaVigente(LocalDate fecha) {
        return (fecha.isAfter(fechaInicioVigencia) || fecha.isEqual(fechaInicioVigencia)) &&
                (fecha.isBefore(fechaFinVigencia) || fecha.isEqual(fechaFinVigencia));
    }
}

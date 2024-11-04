package Dominio;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Reserva {
    private int idReserva;
    private Habitacion habitacion;
    private Huesped huesped;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean pagada;
    private int cantidadPersonas;
    private String observacion;
    private LocalDate fechaReserva;
    private double saldoPendiente;
    private List<ConsumoExtra> consumosExtras;

    public Reserva(int idReserva, Habitacion habitacion, Huesped huesped, LocalDate fechaInicio, LocalDate fechaFin, int cantidadPersonas, String observacion) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }
        this.idReserva = idReserva;
        this.habitacion = habitacion;
        this.huesped = huesped;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pagada = false;
        this.cantidadPersonas = cantidadPersonas;
        this.observacion = observacion;
        this.fechaReserva = LocalDate.now();
        this.consumosExtras = new ArrayList<>();
        this.saldoPendiente = calcularSaldoPendiente();
    }


    public void pagarReserva() {
        if (!pagada) {
            congelarPrecio();
            this.pagada = true;
            System.out.println("Reserva pagada exitosamente. El precio ha sido congelado.");
        } else {
            System.out.println("La reserva ya ha sido pagada.");
        }
    }


    public void agregarConsumoExtra(String descripcion, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del consumo extra debe ser mayor a cero.");
        }
        ConsumoExtra consumo = new ConsumoExtra(descripcion, monto);
        consumosExtras.add(consumo);
        this.saldoPendiente = calcularSaldoPendiente(); // Actualizar saldo pendiente al agregar consumo extra
    }

    // Calcular total de consumos extra
    public double calcularTotalConsumosExtra() {
        double total = 0.0;
        for (ConsumoExtra consumo : consumosExtras) {
            total += consumo.getMonto();
        }
        return total;
    }


    public double calcularSaldoPendiente() {
        double tarifaBase = habitacion.getTarifaVigente() * cantidadDiasReservados();
        return pagada ? calcularTotalConsumosExtra() : tarifaBase + calcularTotalConsumosExtra();
    }

    // Congelar el precio al pagar la reserva
    private void congelarPrecio() {
        this.saldoPendiente = calcularTotalConsumosExtra(); // Solo queda el saldo de consumos extra
    }

    // Calcular cantidad de dias reservados
    private int cantidadDiasReservados() {
        return (int) (fechaFin.toEpochDay() - fechaInicio.toEpochDay());
    }


    public void cancelarReserva() {
        if (pagada) {
            System.out.println("No se puede cancelar una reserva ya pagada.");
            return;
        }
        habitacion.setOcupada(false);
        consumosExtras.clear();
        System.out.println("Reserva cancelada exitosamente.");
    }


    public int getIdReserva() {
        return idReserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
        }
        this.fechaInicio = fechaInicio;
        this.saldoPendiente = calcularSaldoPendiente(); // Recalcular saldo pendiente
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
        this.saldoPendiente = calcularSaldoPendiente(); // Recalcular saldo pendiente
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", habitacion=" + habitacion +
                ", huesped=" + huesped +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", pagada=" + pagada +
                ", saldoPendiente=" + saldoPendiente +
                ", cantidadPersonas=" + cantidadPersonas +
                ", observacion='" + observacion + '\'' +
                ", consumosExtras=" + consumosExtras +
                '}';
    }
}

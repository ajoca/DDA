package Dominio;

public class HabitacionDeluxe extends Habitacion {
    private boolean jacuzzi;

    public HabitacionDeluxe(int idHabitacion, int capacidad, boolean esMatrimonial, boolean aireAcondicionado, boolean balcon, boolean vista, String amenities, boolean jacuzzi) {
        super(idHabitacion, capacidad, esMatrimonial, aireAcondicionado, balcon, vista, amenities);
        this.jacuzzi = jacuzzi;
    }

    public boolean tieneJacuzzi() {
        return jacuzzi;
    }

    @Override
    public double getTarifaVigente() {
        double tarifaBase = super.getTarifaVigente();
        return tarifaBase + (jacuzzi ? 50.0 : 0.0); // Añade un extra si tiene jacuzzi
    }

    @Override
    public String toString() {
        return super.toString() + ", Jacuzzi: " + (jacuzzi ? "Sí" : "No");
    }
}

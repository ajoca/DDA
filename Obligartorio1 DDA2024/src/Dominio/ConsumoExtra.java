package Dominio;

public class ConsumoExtra {
    private String descripcion;
    private double monto;

    public ConsumoExtra(String descripcion, double monto) {
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "ConsumoExtra{" +
                "descripcion='" + descripcion + '\'' +
                ", monto=" + monto +
                '}';
    }
}

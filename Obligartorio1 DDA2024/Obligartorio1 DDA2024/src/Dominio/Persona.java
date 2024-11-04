package Dominio;

public abstract class Persona {
    private int id;
    private String nombre;
    private String apaterno;
    private String amaterno;
    private String tipoDocumento;
    private String numDocumento;
    private String fechaNacimiento;
    private String telefono;
    private String pais;

    public Persona(int id, String nombre, String apaterno, String amaterno, String tipoDocumento, String numDocumento, String fechaNacimiento, String telefono, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.pais = pais;
    }


    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApaterno() { return apaterno; }
    public String getAmaterno() { return amaterno; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getNumDocumento() { return numDocumento; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public String getPais() { return pais; }


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}

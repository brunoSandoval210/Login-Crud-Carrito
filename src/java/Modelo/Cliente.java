package Modelo;

public class Cliente {

    private String Id;
    private String Apellido;
    private String Nombre;
    private String Direccion;
    private String DNI;
    private String Telefono;

    public Cliente() {
    }

    public Cliente(String Id, String Apellido, String Nombre, String Direccion, String DNI, String Telefono) {
        this.Id = Id;
        this.Apellido = Apellido;
        this.Nombre = Nombre;
        this.Direccion = Direccion;
        this.DNI = DNI;
        this.Telefono = Telefono;
    }
    

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellidos) {
        this.Apellido = Apellidos;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }
}

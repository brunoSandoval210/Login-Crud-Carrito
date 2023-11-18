package Modelo;

public class Usuario {
    private String id;
    private String nombre;
    private String ingreso;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String ingreso, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.ingreso = ingreso;
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
}

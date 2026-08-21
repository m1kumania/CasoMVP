package Modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private int idRol; // 1: ADMIN, 2: EMPLEADO
    private boolean activo;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String apellido, String correo, String contrasena, int idRol, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
        this.activo = activo;
    }

    // Constructor 
    public Usuario(String nombre, String apellido, String correo, String contrasena, int idRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
        this.activo = true;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
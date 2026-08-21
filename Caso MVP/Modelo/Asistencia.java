package Modelo;

import java.time.LocalDateTime;

public class Asistencia {
    private int idRegistro;
    private int idUsuario;
    private String tipo; // "ENTRADA" o "SALIDA"
    private LocalDateTime fechaHora;
    
    // Muestra el nombre del empleado 
    private String nombreCompleto;

    public Asistencia() {}

    public Asistencia(int idUsuario, String tipo) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.fechaHora = LocalDateTime.now();
    }

    public int getIdRegistro() { return idRegistro; }
    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
}
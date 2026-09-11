package Modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

public class AsistenciaTest {

    @Test
    public void constructorConUsuarioYTipoInicializaLaAsistencia() {
        LocalDateTime antes = LocalDateTime.now();

        Asistencia asistencia = new Asistencia(12, "ENTRADA");

        LocalDateTime despues = LocalDateTime.now();
        assertEquals(12, asistencia.getIdUsuario());
        assertEquals("ENTRADA", asistencia.getTipo());
        assertNotNull(asistencia.getFechaHora());
        assertFalse(asistencia.getFechaHora().isBefore(antes));
        assertFalse(asistencia.getFechaHora().isAfter(despues));
    }

    @Test
    public void constructorVacioInicializaLosValoresPorDefecto() {
        Asistencia asistencia = new Asistencia();

        assertEquals(0, asistencia.getIdRegistro());
        assertEquals(0, asistencia.getIdUsuario());
        assertEquals(null, asistencia.getTipo());
        assertEquals(null, asistencia.getFechaHora());
        assertEquals(null, asistencia.getNombreCompleto());
    }

    @Test
    public void settersActualizanTodosLosAtributos() {
        Asistencia asistencia = new Asistencia();
        LocalDateTime fechaHora = LocalDateTime.of(2026, 9, 11, 8, 30);

        asistencia.setIdRegistro(4);
        asistencia.setIdUsuario(9);
        asistencia.setTipo("SALIDA");
        asistencia.setFechaHora(fechaHora);
        asistencia.setNombreCompleto("Ana Perez");

        assertEquals(4, asistencia.getIdRegistro());
        assertEquals(9, asistencia.getIdUsuario());
        assertEquals("SALIDA", asistencia.getTipo());
        assertEquals(fechaHora, asistencia.getFechaHora());
        assertEquals("Ana Perez", asistencia.getNombreCompleto());
    }

    @Test
    public void constructorConUsuarioYTipoNoCreaUnaFechaFutura() {
        Asistencia asistencia = new Asistencia(1, "SALIDA");

        assertTrue(!asistencia.getFechaHora().isAfter(LocalDateTime.now()));
    }
}

package co.edu.unicauca.micro_reservas.capaAccesoADatos.models;

import java.sql.Date;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservaEntity {
    private Integer id;
    private String nombres;
    private String apellidos;
    private int cantidadPersonas;
    private Date fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estadoReserva;
    private SalonEntity objSalon; // Relación con el salón
    
    public ReservaEntity(){

    }
}

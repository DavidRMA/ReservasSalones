/* Clase DTO para recibir las respuestas para Reserva */
package co.edu.unicauca.micro_reservas.fachadaServices.DTO;

import java.sql.Date;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTORespuesta {
    private Integer id;
    private String nombres;
    private String apellidos;
    private int cantidadPersonas;
    private Date fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estadoReserva;
    private SalonDTORespuesta objSalon; // Relación con el salón
}

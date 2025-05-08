/*Clase DTO para recibir las entradas desde el front para la reserva */
package co.edu.unicauca.micro_reservas.fachadaServices.DTO;

import java.sql.Date;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservaDTOPeticion {
    private String nombres;
    private String apellidos;
    private int cantidadPersonas;
    private Date fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estadoReserva; 
    private SalonDTOPeticion objSalon; // Relación con el salón

    public ReservaDTOPeticion() {

    }    
}

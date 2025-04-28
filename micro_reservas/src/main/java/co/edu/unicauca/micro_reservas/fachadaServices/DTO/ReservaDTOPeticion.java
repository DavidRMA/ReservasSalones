/*Clase DTO para recibir las entradas desde el front para la reserva */
package co.edu.unicauca.micro_reservas.fachadaServices.DTO;

import java.sql.Date;

import co.edu.unicauca.micro_reservas.capaAccesoADatos.models.SalonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservaDTOPeticion {
    private String nombres;
    private String apellidos;
    private int cantidadPersonas;
    private Date fechaReserva;
    private String horaInicio;
    private String horaFin;
    private String estadoReserva;
    private SalonEntity objSalon; // Relación con el salón

    public ReservaDTOPeticion() {

    }    
}

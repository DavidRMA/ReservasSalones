/* Interface que expone los métodos para la gestion de reservas */
package co.edu.unicauca.micro_reservas.fachadaServices.services;

import java.util.List;

import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTOPeticion;
import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTORespuesta;

public interface IReservaService {
    public List<ReservaDTORespuesta> findAll();

    public ReservaDTORespuesta findById(Integer id);

    public ReservaDTORespuesta save(ReservaDTOPeticion reserva);

    public ReservaDTORespuesta update(Integer id, ReservaDTOPeticion reserva);

    public Boolean delete(Integer id);

    public ReservaDTORespuesta confirmarReserva(Integer id);

    public ReservaDTORespuesta cancelarReserva(Integer id);

    public Boolean verificarDisponibilidad(ReservaDTOPeticion reserva); 
}

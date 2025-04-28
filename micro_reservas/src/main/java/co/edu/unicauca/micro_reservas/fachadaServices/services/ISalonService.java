/* Interface que expone el método para listar salones */
package co.edu.unicauca.micro_reservas.fachadaServices.services;

import java.util.List;

import co.edu.unicauca.micro_reservas.fachadaServices.DTO.SalonDTORespuesta;

public interface ISalonService {
    public List<SalonDTORespuesta> findAll(); // Listar todos los salones
}

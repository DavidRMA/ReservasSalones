/* Clase DTO para las salidas al front */
package co.edu.unicauca.micro_reservas.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalonDTORespuesta {
    private Integer id;
    private String nombreSalon;
    private String ubicacion;
}

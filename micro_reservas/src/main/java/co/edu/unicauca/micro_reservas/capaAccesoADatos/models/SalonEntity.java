package co.edu.unicauca.micro_reservas.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalonEntity {
    private Integer id;
    private String nombreSalon;
    private String ubicacion;
}

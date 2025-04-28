package co.edu.unicauca.micro_reservas.capaControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.micro_reservas.fachadaServices.DTO.SalonDTORespuesta;
import co.edu.unicauca.micro_reservas.fachadaServices.services.ISalonService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET})
public class SalonRestController {
    @Autowired
    private ISalonService salonService;

    @GetMapping("/salones")
    public List<SalonDTORespuesta> listarSalones() {
        return salonService.findAll();
    }
}

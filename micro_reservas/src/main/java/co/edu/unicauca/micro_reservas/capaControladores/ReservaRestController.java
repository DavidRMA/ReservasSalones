package co.edu.unicauca.micro_reservas.capaControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTOPeticion;
import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTORespuesta;
import co.edu.unicauca.micro_reservas.fachadaServices.services.IReservaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ReservaRestController {

    @Autowired
    private IReservaService reservaService;

    @GetMapping("/reservas")
    public List<ReservaDTORespuesta> listarReservas() {
        return reservaService.findAll();
    }

    @GetMapping("/reservas/{id}")
    public ReservaDTORespuesta obtenerReservaPorId(@PathVariable Integer id) {
        ReservaDTORespuesta objReserva = null;
        objReserva = reservaService.findById(id);
        return objReserva;
    }

    @PostMapping("/reservas")
    public ReservaDTORespuesta crearReserva(@RequestBody ReservaDTOPeticion reserva) {
        ReservaDTORespuesta objReserva = null;
        objReserva = reservaService.save(reserva);
        return objReserva;
    }

    @PutMapping("/reservas/{id}")
    public ReservaDTORespuesta actualizarReserva(@PathVariable Integer id, @RequestBody ReservaDTOPeticion reserva) {
        ReservaDTORespuesta objReserva = null;
        ReservaDTORespuesta reservaActual = reservaService.findById(id);
        System.out.println("eco en el actualizar del rest");
        System.out.println(reservaActual);
        if(reservaActual == null) {
            objReserva = reservaService.update(id, reserva);
        }
        return objReserva;
    }

    @DeleteMapping("/reservas/{id}")
    public Boolean eliminarReserva(@PathVariable Integer id) {
        Boolean band = false;
        ReservaDTORespuesta reservaActual = reservaService.findById(id);
        if(reservaActual != null) {
            band = reservaService.delete(id);
        }
        return band;
    }
}

package co.edu.unicauca.micro_reservas.fachadaServices.services;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import co.edu.unicauca.micro_reservas.MicroReservasApplication;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.models.ReservaEntity;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories.ReservaRepositoryBaseDeDatos;
import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTOPeticion;
import co.edu.unicauca.micro_reservas.fachadaServices.DTO.ReservaDTORespuesta;

@Service
public class ReservaServiceImpl implements IReservaService{
    private ReservaRepositoryBaseDeDatos servicioAccesoBaseDatos;
    private ModelMapper modelMapper;

    public ReservaServiceImpl(ReservaRepositoryBaseDeDatos servicioAccesoBaseDatos, ModelMapper modelMapper, MicroReservasApplication microReservasApplication) {
        this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReservaDTORespuesta> findAll(){
        List<ReservaDTORespuesta> listaReservas;
        Optional<Collection<ReservaEntity>> reservasEntityOpt = this.servicioAccesoBaseDatos.findAll();
        /* Verifica si el optional esta vacío, si se confirma lo anterior se devuelve una lista vacía */
        if(reservasEntityOpt.isEmpty()){
            listaReservas = List.of();
        }else{
            /* Si el optional no está vacío, se obtiene el valor y se realiza el mapeo */
            Collection<ReservaEntity> reservasEntity = reservasEntityOpt.get();
            listaReservas = this.modelMapper.map(reservasEntity, new TypeToken<List<ReservaDTORespuesta>>() {}.getType());
        }
        return listaReservas;
    }

    @Override
    public ReservaDTORespuesta findById(Integer id){
        ReservaDTORespuesta reservaRetornar = null;
        Optional<ReservaEntity> reservaEntityOpt = this.servicioAccesoBaseDatos.findById(id);
        if(reservaEntityOpt.isPresent()){
            ReservaEntity reservaEntity = reservaEntityOpt.get();
            reservaRetornar = this.modelMapper.map(reservaEntity, ReservaDTORespuesta.class);
        }
        return reservaRetornar;
    }

    @Override
    public ReservaDTORespuesta save(ReservaDTOPeticion reserva){
        ReservaEntity reservaEntity = this.modelMapper.map(reserva, ReservaEntity.class);
        //reservaEntity.setEstadoReserva("Pendiente");
        ReservaEntity objReservaEntity = this.servicioAccesoBaseDatos.save(reservaEntity);
        System.out.println(objReservaEntity);
        ReservaDTORespuesta reservaDTO = this.modelMapper.map(objReservaEntity, ReservaDTORespuesta.class);
        return reservaDTO;
    }

    @Override
    public ReservaDTORespuesta update(Integer id, ReservaDTOPeticion reserva){
        ReservaEntity reservaActualizado = null;
        Optional<ReservaEntity> reservaEntityOpt = this.servicioAccesoBaseDatos.findById(id);
        if(reservaEntityOpt.isPresent()){
            ReservaEntity objReservaNueva = reservaEntityOpt.get();
            objReservaNueva.setNombres(reserva.getNombres());
            objReservaNueva.setApellidos(reserva.getApellidos());
            objReservaNueva.setCantidadPersonas(reserva.getCantidadPersonas());
            objReservaNueva.setFechaReserva(reserva.getFechaReserva());
            objReservaNueva.setHoraInicio(reserva.getHoraInicio());
            objReservaNueva.setHoraFin(reserva.getHoraFin());
            objReservaNueva.setEstadoReserva(reserva.getEstadoReserva());
            objReservaNueva.getObjSalon().setId(reserva.getObjSalon().getId());
            Optional<ReservaEntity> reservaOp = this.servicioAccesoBaseDatos.update(id, objReservaNueva);
            reservaActualizado = reservaOp.get();
        }
        return this.modelMapper.map(reservaActualizado, ReservaDTORespuesta.class);
    }

    @Override
    public Boolean delete(Integer id){
        return this.servicioAccesoBaseDatos.delete(id);
    }

    @Override
    public ReservaDTORespuesta confirmarReserva(Integer id){
        ReservaEntity reservaConfirmada = null;
        Optional<ReservaEntity> reservaEntityOpt = this.servicioAccesoBaseDatos.findById(id);
        if(reservaEntityOpt.isPresent()){
            ReservaEntity objReservaNueva = reservaEntityOpt.get();
            objReservaNueva.setEstadoReserva("Confirmada");
            Optional<ReservaEntity> reservaOp = this.servicioAccesoBaseDatos.update(id, objReservaNueva);
            reservaConfirmada = reservaOp.get();
        }
        return this.modelMapper.map(reservaConfirmada, ReservaDTORespuesta.class);
    }

    @Override
    public ReservaDTORespuesta cancelarReserva(Integer id){
        ReservaEntity reservaRechazada = null;
        Optional<ReservaEntity> reservaEntityOpt = this.servicioAccesoBaseDatos.findById(id);
        if(reservaEntityOpt.isPresent()){
            ReservaEntity objReservaNueva = reservaEntityOpt.get();
            objReservaNueva.setEstadoReserva("Rechazada");
            Optional<ReservaEntity> reservaOp = this.servicioAccesoBaseDatos.update(id, objReservaNueva);
            reservaRechazada = reservaOp.get();
        }
        return this.modelMapper.map(reservaRechazada, ReservaDTORespuesta.class);
    }

    @Override
    public Boolean verificarDisponibilidad(ReservaDTOPeticion reserva) {
        // Datos de la nueva reserva
        LocalTime horaInicioNueva = reserva.getHoraInicio();
        LocalTime horaFinNueva = reserva.getHoraFin();
        Date fechaReservaNueva = reserva.getFechaReserva();
        fechaReservaNueva = new Date(fechaReservaNueva.getTime() + (24 * 60 * 60 * 1000));
        Integer idSalonNuevo = reserva.getObjSalon().getId();

        // Imprimir la fecha recibida para ver si llega correctamente
        System.out.println("Fecha recibida en el método: " + fechaReservaNueva);
        // Consultar reservas con el mismo salón y fecha
        List<ReservaEntity> reservasEntityList = this.servicioAccesoBaseDatos.findBySalonIdAndFechaReserva(idSalonNuevo, fechaReservaNueva);


        if (!reservasEntityList.isEmpty()) {
            for (ReservaEntity reservaExistente : reservasEntityList) {
                // Imprimir la fecha de la reserva existente para verificar coincidencia
                System.out.println("Fecha de reserva existente: " + reservaExistente.getFechaReserva());

                // Convertir las horas de la reserva existente
                LocalTime horaInicioExistente = reservaExistente.getHoraInicio();
                LocalTime horaFinExistente = reservaExistente.getHoraFin();

                // Verificar solapamiento de horarios
                if (horaInicioNueva.isBefore(horaFinExistente) && horaFinNueva.isAfter(horaInicioExistente)) {
                    System.out.println("Reserva conflictiva encontrada con ID: " + reservaExistente.getId() + "\nSalon ocupado.");
                    return false; // El salón está ocupado
                }
            }
        }
        System.out.println("El salón está disponible para la reserva.");
        return true; // El salón está disponible
}


}

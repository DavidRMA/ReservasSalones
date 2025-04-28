package co.edu.unicauca.micro_reservas.fachadaServices.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.micro_reservas.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.micro_reservas.capaAccesoADatos.respositories.SalonRepository;
import co.edu.unicauca.micro_reservas.fachadaServices.DTO.SalonDTORespuesta;

@Service
public class SalonServiceImpl implements ISalonService {
    private SalonRepository servicioAccesoBaseDatos;
    private ModelMapper modelMapper;

    public SalonServiceImpl(SalonRepository servicioAccesoBaseDatos, ModelMapper modelMapper) {
        this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SalonDTORespuesta> findAll(){
        List<SalonDTORespuesta> listaRetornar;
        Optional<Collection<SalonEntity>> salonEntityOpt = this.servicioAccesoBaseDatos.findAll();
        if(salonEntityOpt.isEmpty()){
            listaRetornar = List.of();
        }else{
            Collection<SalonEntity> salones = salonEntityOpt.get();
            listaRetornar = this.modelMapper.map(salones, new TypeToken<List<SalonDTORespuesta>>() {}.getType());
        }
        return listaRetornar;
    }
}

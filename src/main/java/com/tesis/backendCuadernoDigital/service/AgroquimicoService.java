package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import com.tesis.backendCuadernoDigital.repository.AgroquimicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgroquimicoService {

    @Autowired
    AgroquimicoRepository agroquimicoRepository;

    public void guardar(Agroquimico agroquimico){
        agroquimicoRepository.save(agroquimico);
    }

    public Optional<Agroquimico> getUnoId(long id){
        return agroquimicoRepository.findById(id);
    }

    public List<Agroquimico> listarAgroquimico(){
        return  agroquimicoRepository.findAllByOrderByIdAsc();
    }

    public List<Agroquimico> listarAgroquimicoPorNombreUsuario(String nombreUsuario){
        return agroquimicoRepository.findByNombreEncargadoAgricola_NombreUsuario(nombreUsuario);
    }

    public Optional<Agroquimico> getByNombreComun(String nombreComun){
        return agroquimicoRepository.findByNombreComun(nombreComun);
    }

    public void borrar(long id){
        agroquimicoRepository.deleteById(id);
    }

    public boolean existeById(long id){
        return agroquimicoRepository.existsById(id);
    }

    public boolean existeByNombreComun(String nombreComun){
        return agroquimicoRepository.existsByNombreComun(nombreComun);
    }

}

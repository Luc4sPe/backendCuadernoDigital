package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import com.tesis.backendCuadernoDigital.repository.AgroquimicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tesis.backendCuadernoDigital.excepcion.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgroquimicoService {

    @Autowired
    AgroquimicoRepository agroquimicoRepository;

   public  boolean guardarAgroquimico(Agroquimico agroquimico){
       return  agroquimicoRepository.save(agroquimico).getId()!= null;
   }

   public  Agroquimico actualizarAgroquimico(Agroquimico agroquimico){
       return agroquimicoRepository.save(agroquimico);
   }

    public Optional<Agroquimico> getUnAgroquimico(Long idAgro){
       return agroquimicoRepository.findById(idAgro);
    }

    public Optional<Agroquimico> getByNombreComercial(String nombre){
       return agroquimicoRepository.findByNombreComercial(nombre);
    }

    public List<Agroquimico> listarAgroquimico(){
        return  agroquimicoRepository.findAllByOrderByIdAsc();
    }

    public boolean existsByNombreComercial(String nombre){
       return agroquimicoRepository.existsByNombreComercial(nombre);
    }

  

    public boolean existsByIdAgro(Long id){
        return agroquimicoRepository.existsById(id);
    }

    public Integer getCantidadDeAgroquimicos(){
       return agroquimicoRepository.countAgroquimicoBy();
    }

    public void borrar(Long id){
        agroquimicoRepository.deleteById(id);
    }



    public  Agroquimico getAgroquimico(Long id){
        Agroquimico agroquimico= getUnAgroquimico(id).orElseThrow(()-> new ResourceNotFoundException("EL agroquimico con ID: " + id + " no existe"));
        return agroquimico;
    }




}



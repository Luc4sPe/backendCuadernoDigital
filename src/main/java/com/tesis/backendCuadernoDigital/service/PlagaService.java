package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Plaga;
import com.tesis.backendCuadernoDigital.repository.PlagaRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlagaService {

    @Autowired
    PlagaRpository plagaRpository;

    public void save(Plaga plaga){
        plagaRpository.save(plaga);
    }

    public Optional<Plaga> getUnoByID(long id){
        return plagaRpository.findById(id);
    }

    public List<Plaga> listarPlaga(){
        return plagaRpository.findAllByOrderByIdAsc();
    }

    public List<Plaga> listarPlagaPorNombreDeUsuario(String nombreUsuario){
        return plagaRpository.findByNombreEncargadoAgricola_NombreUsuario(nombreUsuario);
    }

    public Optional<Plaga> getByNombre(String nombre){
        return plagaRpository.findByNombre(nombre);
    }

    public void borrar(long id){
         plagaRpository.deleteById(id);
    }
    public boolean existeById(long id){
        return plagaRpository.existsById(id);
    }
    public boolean existeByNombre(String nombre){
        return plagaRpository.existsByNombre(nombre);
    }
}

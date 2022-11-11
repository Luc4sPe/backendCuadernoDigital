package com.tesis.backendCuadernoDigital.service;
/*
import com.tesis.backendCuadernoDigital.entity.Riego;
import com.tesis.backendCuadernoDigital.repository.RiegoRepository;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RiegoService {

    @Autowired
    RiegoRepository riegoRepository;

    public void guardar(Riego riego){
        this.riegoRepository.save(riego);
    }

    public Optional<Riego> getUnoById(int id){
        return riegoRepository.findById(id);
    }

    public List <Riego> listaRiego(){
        return riegoRepository.findAllByOrderByIdAsc();
    }


    public List<Riego> listadoRiegoDeUnUsuario(int idUsuario){
        return riegoRepository.findByNombreUsuario_Id(idUsuario);
    }


    public List<Riego> listadoRiegoDeUnUsuarioPorNombre(String nombreUsuario){
        return riegoRepository.findByNombreUsuario_NombreUsuario(nombreUsuario);
    }


    public Optional <Riego> getUno(int id){
        return riegoRepository.findById(id);
    }

    public boolean existsById(int id){
        return riegoRepository.existsById(id);
    }
    public boolean existsByNumeroCuadro(int numeroCuadro){
        return riegoRepository.existsByNumeroDeCuadro(numeroCuadro);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return riegoRepository.existsByNombreUsuario(nombreUsuario);
    }


    public void save(Riego riego){
         riegoRepository.save(riego);
    }
}
*/
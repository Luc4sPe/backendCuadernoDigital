package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Finca;
import com.tesis.backendCuadernoDigital.excepcion.ResourceNotFoundException;
import com.tesis.backendCuadernoDigital.repository.FincaRepository;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FincaService {

    @Autowired
    FincaRepository fincaRepository;

    public boolean guardarFinca(Finca finca){
        return fincaRepository.save( finca).getIdFinca() !=null;
    }
    public Finca actualizarFinca( Finca finca){
        return fincaRepository.save(finca);
    }

    public Optional<Finca> getFinca(Long idFinca){
        return fincaRepository.findByIdFinca(idFinca);
    }

    public Optional<Finca> getByNombre(String nombreFinca){
        return fincaRepository.findByNombre(nombreFinca);
    }

    public Optional<Finca> getById(Long id){
        return fincaRepository.findByIdFinca(id);
    }

    public List<Finca> listarFinca(){
        return  fincaRepository.findAllByOrderByNombreAsc();
    }

    public List<Finca> listadoFincaDeUnUsuarioPorNombreUsuario(String nombreUsuario){
        return fincaRepository.findByProductor_NombreUsuario(nombreUsuario);
    }



    public boolean existsByNombreFinca(String nombreFinca){
        return fincaRepository.existsByNombre(nombreFinca);
    }
    public boolean existsByIdFinca(Long id){
        return fincaRepository.existsByIdFinca(id);
    }

    public Integer getCantidadDeFincas(){
        return fincaRepository.countFincaBy();
    }

    public  Finca getFincas(Long id){
        Finca finca = getById(id).orElseThrow(()-> new ResourceNotFoundException("La finca con ID: " + id + " no existe"));
        return finca;
    }

    public  Finca getFincasNombre(String nombre){
        Finca finca = getByNombre(nombre).orElseThrow(()-> new ResourceNotFoundException("La finca con el nombre: " + nombre + " no existe"));
        return finca;
    }


}

package com.tesis.backendCuadernoDigital.security.service;

import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> list(){
        return usuarioRepository.findAll();
    }
    public Optional<Usuario> getById(int id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail){
        return usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail);
    }

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }
    public boolean existsById(int id){
        return usuarioRepository.existsById(id);
    }
    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }
    public void delete(int id){
         usuarioRepository.deleteById(id);
    }
}

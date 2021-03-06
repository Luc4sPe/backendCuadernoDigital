package com.tesis.backendCuadernoDigital.security.service;

import com.tesis.backendCuadernoDigital.excepcion.ExcepcionSolicitudIncorrecta;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
        return usuarioRepository.findAllByOrderByIdAsc();
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

    public Optional<Usuario> getByTokenPassword(String tokenPassword){
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existsByTelefono(String telefono) {return  usuarioRepository.existsByTelefono(telefono);}

    public boolean existsById(int id){
        return usuarioRepository.existsById(id);
    }
    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void delete(int id){
         usuarioRepository.deleteById(id);
    }

    public void modificarEstado(int id){
        Usuario usuario = usuarioRepository.getById(id);
        usuario.modificarEstado();
        usuarioRepository.save(usuario);
    }


    public Usuario getUsuarioLogeado(Authentication aut){
        UserDetails userDetails = (UserDetails) aut.getPrincipal();
        Usuario usuario = usuarioRepository.findByNombreUsuario(userDetails.getUsername())
                .orElseThrow( () -> new ExcepcionSolicitudIncorrecta("El usuario no existe"));
        return usuario;
    }
}

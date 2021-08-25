package com.tesis.backendCuadernoDigital.security.service;

import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;
    //cargar usuario por nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String nombreUsuarioOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByNombreUsuarioOrEmail(nombreUsuarioOrEmail).get();
        return UsuarioPrincipal.build(usuario);
    }
}

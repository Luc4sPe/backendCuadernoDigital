package com.tesis.backendCuadernoDigital.crearRol;

import com.tesis.backendCuadernoDigital.security.entity.Rol;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.enums.RolNombre;
import com.tesis.backendCuadernoDigital.security.service.RolService;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
//Crea los roles, solo se ejecuta una sola vez
/*
@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;
    @Autowired
    UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
       Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        Rol rolEncargadoAgricola = new Rol(RolNombre.ROLE_ENCARGADO_AGRICOLA);
        Rol rolProductor = new Rol(RolNombre.ROLE_PRODUCTOR);
        Rol rolGerente = new Rol(RolNombre.ROLE_GERENTE);

        rolService.save(rolAdmin);
        rolService.save(rolUser);
        rolService.save(rolEncargadoAgricola);
        rolService.save(rolProductor);
        rolService.save(rolGerente);


        Usuario usuarioAdmin =  new Usuario();
        usuarioAdmin.setNombre("Lucas");
        usuarioAdmin.setApellido("Peña");
        usuarioAdmin.setDni("32.458.305");
        usuarioAdmin.setNombreUsuario("admin");
        usuarioAdmin.setEmail("luccas.a.pe@gmail.com");
        usuarioAdmin.setTelefono("3825411458");
        usuarioAdmin.setPassword("12345678");
        Set<Rol> roles = new HashSet<>();
        roles.add(rolAdmin);
        usuarioAdmin.setRoles(roles);

        usuarioService.save(usuarioAdmin);



    }



}

 */








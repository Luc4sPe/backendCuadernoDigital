package com.tesis.backendCuadernoDigital.security.repository;

import com.tesis.backendCuadernoDigital.security.entity.Rol;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findAllByOrderByIdAsc();
   // List<Usuario> findByRolesContains(Long num,String nombre);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);
    Optional<Usuario> findByTokenPassword(String tokenPassword);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    int countUsuariosByEstadoActivoIsTrue();
    int countUsuariosByEstadoActivoIsFalse();




}

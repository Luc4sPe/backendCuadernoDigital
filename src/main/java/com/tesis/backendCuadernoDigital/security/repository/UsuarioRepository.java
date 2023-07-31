package com.tesis.backendCuadernoDigital.security.repository;

import com.tesis.backendCuadernoDigital.security.entity.Rol;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.enums.RolNombre;
import org.hibernate.type.EnumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findAllByOrderByIdAsc();
   List<Usuario> findByRoles(Optional<Rol> nombre);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);
    Optional<Usuario> findByTokenPassword(String tokenPassword);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    Integer countUsuariosBy();
    Integer countUsuariosByEstadoActivoTrue();
    Integer countUsuariosByEstadoActivoFalse();
    Integer countUsuariosByRolesContains(Optional <Rol> rol);
    Integer countUsuariosByEstadoActivoTrueAndRolesContains(Optional <Rol> rol);
    Integer countUsuariosByEstadoActivoFalseAndRolesContains(Optional <Rol> rol);

}

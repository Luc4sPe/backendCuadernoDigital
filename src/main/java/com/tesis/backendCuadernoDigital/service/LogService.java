package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.*;
import com.tesis.backendCuadernoDigital.enums.LogAccion;
import com.tesis.backendCuadernoDigital.repository.LogRepository;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LogService {

    @Autowired
    LogRepository logRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


    public List<Log> obtenerListado(){
        List<Log> listado = logRepository.findAllByOrderByFechaDesc();
        return listado;
    }


    public List<String> obtenerListadoAcciones(){
        List<String> listado = Arrays.stream(LogAccion.values()).map(logAccion -> logAccion.name()).collect(Collectors.toList());
        return listado;
    }

    public List<Log> logsPorUsuario(Long id){
        List<Log> listadoLogs = logRepository.findByUsuario_IdOrderByFechaAsc(id);
        return listadoLogs;
    }


    public List<Log> logsPorNombreUsuario(String nombreUsuario){
        List<Log> listadoLogs= logRepository.findByUsuario_NombreUsuarioOrderByFechaDesc(nombreUsuario);
        return listadoLogs;
    }

    public Log ultimoLogDeUsuario(Long id){
        List<Log> listaLogs= logsPorUsuario(id);
        int size = listaLogs.size();
        Log ultimoLog = listaLogs.get(size -1);
        return ultimoLog;
    }

    public void elmininarActividadUsuario(Long id){
        List<Log> actividad = logsPorUsuario(id);
        logRepository.deleteAll(actividad);
    }

    // Metodos para Logs de Usuarios

    public void guardarLogLoginUsuario(Usuario usuario){
        Log log = new Log(usuario,LogAccion.USUARIO_LOGIN,"Acceso al Sistema",usuario.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void guardarLogCreacionUsuario(Usuario usuarioCreado, Usuario usuarioCreador){
        Log log = new Log(usuarioCreador,LogAccion.USUARIO_CREACION,"Se creo el Usuario : "+usuarioCreado.getNombreUsuario(),usuarioCreado.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void guardarLogErrorLogin(Usuario usuario){
        Log log = new Log(usuario,LogAccion.USUARIO_ERROR_LOGIN,"El Usuario no pudo ingresar al sistema", usuario.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void guardarLogErrorLoginUsuarioInactivo(Usuario usuario){
        Log log = new Log(usuario,LogAccion.USUARIO_ERROR_LOGIN_USUARIO_INACTIVO, "El usuario intento acceder estando inactivo al sistema", usuario.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodo cambio contraseña
    public void guardarCambioContrasenia(Usuario usuario){
        Log log = new Log(usuario,LogAccion.CAMBIO_CONTRASENIA,"El usuario "+usuario.getNombre()+" cambio la contraseña",usuario.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }
    //Actualizar perfil
    public void actualizarPerfil(Usuario usuario){
        Log log = new Log(usuario,LogAccion.ACTUALIZAR_PERFIL,"El usuario "+usuario.getNombre()+" actualizo su perfil",usuario.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    // Falta implementar en el login el metodo dar de baja despues de n intentos de accesos fallidos

    public void guardarLogBajaUsuarioLuegoNIntentoDeAccesoFAllido(Usuario usuario){
        Log log = new Log(usuario,LogAccion.USUARIO_BAJA,"El usuario fue dado de baja por N intentos fallidos de inicio de sesion",usuario.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    // En Prueba
    public List<Log> getUltimosNLogPorUsuario(Long idUsuario, Integer cantidadMaxima){
        List<Log> ultimosN = logRepository.findByUsuario_IdOrderByFechaDesc(idUsuario).stream().limit(cantidadMaxima).collect(Collectors.toList());
        return ultimosN;
    }

    // En Prueba
    public boolean cantidadLogUsuarioMayorOIgualAN(Usuario usuario, Integer n){
        Integer cantidad = logRepository.countByUsuario_Id(usuario.getId());
        return cantidad >= n;
    }

    // en Prueba
    public boolean ultimosNLogSonErrorLogin(Usuario usuario, Integer cantidadMax){
        boolean resultado = true;
        List<Log> ulitmos = getUltimosNLogPorUsuario(usuario.getId(), cantidadMax);
        //ulitmos.forEach( l -> System.out.println(l.getId()));
        for (Log log : ulitmos ) {
            resultado = resultado && log.getLogAccion().equals(LogAccion.USUARIO_ERROR_LOGIN);
            //System.out.println(resultado);
        }
        return resultado;
    }

    public void guardarAltaUsuario(Usuario usuarioDadoAlta, Usuario usuarioEncargado){
        Log log = new Log(usuarioEncargado,LogAccion.USUARIO_ALTA,"El UsuarioEncargado : "+usuarioEncargado.getNombreUsuario()+" dio de Alta al Usuario : "+usuarioDadoAlta.getNombreUsuario(),usuarioDadoAlta.getId());
        log.obtenerMacc();
        logRepository.save(log);

    }

    public void guardarBajaUsuario(Usuario usuarioDadoBaja, Usuario usuarioEncargado){
        Log log = new Log(usuarioEncargado,LogAccion.USUARIO_BAJA,"El UsuarioEncargado : "+usuarioEncargado.getNombreUsuario()+" dio de Baja al Usuario : "+usuarioDadoBaja.getNombreUsuario(),usuarioDadoBaja.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void guardarModificacionUsuario(Usuario usuarioModificado, Usuario usuarioEncargado){
        Log log = new Log(usuarioEncargado,LogAccion.USUARIO_MODIFICACION," El UsuarioEncargado "+usuarioEncargado.getNombreUsuario()+" Modifico al Usuario "+usuarioModificado.getNombreUsuario(),usuarioModificado.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos logs de Cultivo

    public void guardarAltaCultivo(Cultivo cultivo, Usuario usuarioCreador){
        Log log = new Log(usuarioCreador, LogAccion.CREACIÓN_CULTIVO,"El Encargado Agricola: "+usuarioCreador.getNombreUsuario()+" Creo el Cultivo: "+cultivo.getNombre(),cultivo.getIdCultivo());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarCultivo(Cultivo cultivo, Usuario usuarioModificador){
        Log log = new Log(usuarioModificador, LogAccion.MODIFICACIÓN_CULTIVO,"El Encargado Agricola:  "+usuarioModificador.getNombreUsuario()+" Modifico el Cultivo: "+cultivo.getNombre(), cultivo.getIdCultivo());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos Logs de Plantacion

    public void guardarPlantacion(Plantacion plantacion, Usuario usuarioProductor){
        Log log = new Log(usuarioProductor,LogAccion.CREACIÓN_PLANTACIÓN,"El productor: "+usuarioProductor.getNombreUsuario()+" Creao la Plantacion: "+plantacion.getNombreTipoCultivo().getNombre(),plantacion.getIdPlantacion());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarPlantacion(Plantacion plantacion, Usuario usuarioProductor){
        Log log = new Log(usuarioProductor,LogAccion.MODIFICACIÓN_PLANTACIÓN,"El productor: "+usuarioProductor.getNombreUsuario()+" Modifico la plantacion "+plantacion.getNombreTipoCultivo().getNombre(),plantacion.getIdPlantacion());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos Logs Cuadro

    public void guardarCuadro(Cuadro cuadro, Usuario usuarioEncargadoAgricola){
        Log log = new Log(usuarioEncargadoAgricola,LogAccion.ALTA_CUADRO, "El Encargado Agricola: "+usuarioEncargadoAgricola.getNombreUsuario()+" Creao el Cuadro: "+cuadro.getNumeroCuadro(),cuadro.getIdCuadro());
        log.obtenerMacc();
        logRepository.save(log);
    }
    public void modificarCuadro(Cuadro cuadro, Usuario usuarioEncargadoAgricola){
        Log log = new Log(usuarioEncargadoAgricola,LogAccion.MODIFICACIÓN_CUADRO,"El EngargadoAgricola: "+usuarioEncargadoAgricola.getNombreUsuario()+" Modifico el cuadro: "+cuadro.getNumeroCuadro(),cuadro.getIdCuadro());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void agregarCultivoAnterior(Cuadro cuadro, Usuario usuarioEncargadoAgricola){
        Log log = new Log(usuarioEncargadoAgricola,LogAccion.CREACIÓN_CULTIVO_ANTERIOR,"El EngargadoAgricola: "+usuarioEncargadoAgricola.getNombreUsuario()+" Agrego el Cultivo Anterior: "+cuadro.getCultivoAnterior(),cuadro.getIdCuadro());
        log.obtenerMacc();
        logRepository.save(log);
    }

     //Metodos Logs Finca
    public void guardarFinca(Finca finca, Usuario usuarioEcargadoAgricola){
        Log log = new Log(usuarioEcargadoAgricola, LogAccion.CREACIÓN_FINCA,"El Encargado Agricola: "+usuarioEcargadoAgricola.getNombreUsuario()+" Creao la Finca: "+finca.getNombre(),finca.getIdFinca());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarFinca(Finca finca, Usuario usuarioEcargadoAgricola){
        Log log = new Log(usuarioEcargadoAgricola, LogAccion.MODIFICACIÓN_FINCA,"El Encargado Agricola: "+usuarioEcargadoAgricola.getNombreUsuario()+" Modifico la Finca: "+finca.getNombre(),finca.getIdFinca());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos Logs Labor de Suelo

    public void guardarLaborSuelo(LaborSuelo laborSuelo, Usuario productor){
        Log log = new Log(productor,LogAccion.CREACIÓN_LABOR_SUELO," El productor: "+productor.getNombreUsuario()+" Creao la Labor de Suelo: "+laborSuelo.getLabor(),laborSuelo.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarLaborSuelo(LaborSuelo laborSuelo, Usuario productor){
        Log log = new Log(productor,LogAccion.MODIFICACIÓN_LABOR_SUELO," El productor: "+productor.getNombreUsuario()+" Modifico la Labor de Suelo: "+laborSuelo.getLabor(),laborSuelo.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }


    //Metodos Logs Riego

    public void guardarRiego(Riego riego, Usuario productor){
        Log log = new Log(productor,LogAccion.CREACIÓN_RIEGO," El productor: "+productor.getNombreUsuario()+" Realizo un riego de : "+riego.getDuracionEnHoras(),riego.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarRiego(Riego riego, Usuario productor){
        Log log = new Log(productor,LogAccion.MODIFICACIÓN_RIEGO," El productor: "+productor.getNombreUsuario()+" Modifico el riego de: "+riego.getMilimetrosAplicados(),riego.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos Logs Agroquimico

    public void guardarAgroquimico(Agroquimico agroquimico, Usuario encargadoAgricola){
        Log log = new Log(encargadoAgricola,LogAccion.CREACIÓN_AGROQUÍMICO," El Encargado Agricola: "+encargadoAgricola.getNombreUsuario()+" Creo el agroquimico : "+agroquimico.getNombreComercial(),agroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarAgroquimico(Agroquimico agroquimico, Usuario encargadoAgricola){
        Log log = new Log(encargadoAgricola,LogAccion.MODIFICACIÓN_AGROQUÍMICO," El Encargado Agricola: "+encargadoAgricola.getNombreUsuario()+" Creo el agroquimico : "+agroquimico.getNombreComercial(),agroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos Logs AplicacionAgroquimico

    public void guardarAplicacionAgroquimico(AplicacionDeAgroquimico agroquimico, Usuario productor){
        Log log = new Log(productor,LogAccion.APLICACIÓN_AGROQUÍMICO," El productor: "+productor.getNombreUsuario()+" Realiso la aplicacion de  : "+agroquimico.getAgroquimico().getNombreComercial(),agroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarAplicacionAgroquimico(AplicacionDeAgroquimico agroquimico, Usuario productor){
        Log log = new Log(productor,LogAccion.MODIFICACIÓN_APLICACIÓN_AGROQUÍMICO," El productor: "+productor.getNombreUsuario()+" Modifico la aplicación de  : "+agroquimico.getAgroquimico().getNombreComercial(),agroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos Logs Asesoramiento de los Riegos

    public void guardarAsesoriaRiego(AsesoriaRiego asesoriaRiego, Usuario productor){
        Log log = new Log(productor,LogAccion.CREACIÓN_ASESORAMIENTO_RIEGO," El Encargado Agricola: "+productor.getNombreUsuario()+" Realizo la asesoria del riego de  : "+asesoriaRiego.getCuadro().getNumeroCuadro(),asesoriaRiego.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarAsesoriaRiego(AsesoriaRiego asesoriaRiego, Usuario productor){
        Log log = new Log(productor,LogAccion.MODIFICAR_ASESORÍA_RIEGO," El Encargado Agricola: "+productor.getNombreUsuario()+" Modifico la asesoria del riego  : "+asesoriaRiego.getCuadro().getNumeroCuadro(),asesoriaRiego.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }


    public void modificarEstadoAsesoriaRiegoTrue(AsesoriaRiego asesoriaRiego, Usuario productor){
        Log log = new Log(productor,LogAccion.SE_APLICÓ_ASESORÍA_RIEGO," El productor: "+productor.getNombreUsuario()+" Aplicó la asesoría del riego  : "+asesoriaRiego.getCuadro().getNumeroCuadro(),asesoriaRiego.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarEstadoAsesoriaRiegoFalse(AsesoriaRiego asesoriaRiego, Usuario productor){
        Log log = new Log(productor,LogAccion.CANCELO_APLICACIÓN_ASESORIA_RIEGO," El productor: "+productor.getNombreUsuario()+" Canceló la aplicación de la asesoría del riego  : "+asesoriaRiego.getCuadro().getNumeroCuadro(),asesoriaRiego.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    //Metodos Logs Asesoramiento de los agroquímico


    public void guardarAsesoriaAgroquimico(AsesoriaAgroquimico asesoriaAgroquimico, Usuario productor){
        Log log = new Log(productor,LogAccion.CREACIÓN_ASESORAMIENTO_AGROQUÍMICO," El Encargado Agricola: "+productor.getNombreUsuario()+" Realizo la asesoría de aplicación de agroquímico de  : "+asesoriaAgroquimico.getCuadro().getNumeroCuadro(),asesoriaAgroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarAsesoriaAgroquimico(AsesoriaAgroquimico asesoriaAgroquimico, Usuario productor){
        Log log = new Log(productor,LogAccion.MODIFICACIÓN_ASESORAMIENTO_AGROQUÍMICO," El Encargado Agricola: "+productor.getNombreUsuario()+" Modificó la asesoría de la aplicación de agroquímico : "+asesoriaAgroquimico.getCuadro().getNumeroCuadro(),asesoriaAgroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarEstadoAsesoriaAgroTrue(AsesoriaAgroquimico asesoriaAgroquimico, Usuario productor){
        Log log = new Log(productor,LogAccion.SE_APLIÓ_ASESORÍA_AGROQUÍMICO," El productor: "+productor.getNombreUsuario()+" Aplicó la asesoría del riego  : "+asesoriaAgroquimico.getCuadro().getNumeroCuadro(),asesoriaAgroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }

    public void modificarEstadoAsesoriaAgroFalse(AsesoriaAgroquimico asesoriaAgroquimico, Usuario productor){
        Log log = new Log(productor,LogAccion.CANCELO_APLICACIÓN_ASESORÍA_AGROÍMICO," El productor: "+productor.getNombreUsuario()+" Canceló la aplicación de la asesoría del riego  : "+asesoriaAgroquimico.getCuadro().getNumeroCuadro(),asesoriaAgroquimico.getId());
        log.obtenerMacc();
        logRepository.save(log);
    }



}

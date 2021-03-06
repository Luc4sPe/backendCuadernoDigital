package com.tesis.backendCuadernoDigital.emailpassword.controller;


import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.emailpassword.dto.ChangePasswordDTO;
import com.tesis.backendCuadernoDigital.emailpassword.dto.EmailValuesDTO;
import com.tesis.backendCuadernoDigital.emailpassword.service.EmailService;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/email-password")
@CrossOrigin("*")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private static final String subject = "Cambio de Contraseña";

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO emailValuesDto) {
        Optional<Usuario> usuarioOpt = usuarioService.getByNombreUsuarioOrEmail(emailValuesDto.getMailTo());

        if(!usuarioOpt.isPresent()) {
            return new ResponseEntity(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);

        }
        try {
            Usuario usuario = usuarioOpt.get();
            emailValuesDto.setMailFrom(mailFrom);
            emailValuesDto.setMailTo(usuario.getEmail());
            emailValuesDto.setSubject(subject);
            emailValuesDto.setUserName(usuario.getNombreUsuario());
            UUID uuid = UUID.randomUUID();
            String tokenPassword = uuid.toString();
            emailValuesDto.setTokenPassword(tokenPassword);
            usuario.setTokenPassword(tokenPassword);
            usuarioService.save(usuario);
            //usuarioService.save(usuario);
            emailService.sendEmail(emailValuesDto);
            return new ResponseEntity(new Mensaje("Te hemos enviado un correo exitosamente"), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Error al eviar el mail"), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/change-Password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        if(!dto.getPassword().equals(dto.getConfirmPassword()))
            return new ResponseEntity(new Mensaje("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuarioOpt = usuarioService.getByTokenPassword(dto.getTokenPassword());
        if(!usuarioOpt.isPresent())
            return new ResponseEntity(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);

        try {
            Usuario usuario = usuarioOpt.get();
            String newPassword = passwordEncoder.encode(dto.getPassword());
            usuario.setPassword(newPassword);
            usuario.setTokenPassword(null);
            usuarioService.save(usuario);
            return new ResponseEntity(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("La contraseña no fue cambiada"), HttpStatus.BAD_REQUEST);
        }



    }


}

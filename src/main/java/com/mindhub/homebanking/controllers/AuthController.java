package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.servicesSecurity.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder; // Codificador de contraseñas para encriptar y verificar contraseñas.

    @Autowired
    private ClientRepository clientRepository; // Repositorio para manejar operaciones CRUD de clientes.

    @Autowired
    private AuthenticationManager authenticationManager; // Administrador de autenticación para manejar el proceso de autenticación.

    @Autowired
    private UserDetailsService userDetailsService; // Servicio para cargar los detalles del usuario.

    @Autowired
    private JwtUtilService jwtUtilService; // Servicio para manejar la generación y validación de JWT (JSON Web Tokens).

    // Endpoint para iniciar sesión y generar un token JWT.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Verifica si el nombre y apellido no están vacíos.
            if (loginDTO.email().isBlank()) {
                return new ResponseEntity<>("Email field cannot be empty", HttpStatus.BAD_REQUEST);
            }

            // Verifica si la contraseña cumple con el requisito mínimo de longitud.
            if (loginDTO.password().length() < 8) {
                return new ResponseEntity<>("Password must be at least 8 characters long", HttpStatus.BAD_REQUEST);
            }
            // Autentica al usuario usando el email y la contraseña proporcionados.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            // Carga los detalles del usuario después de la autenticación.
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            // Genera un token JWT para el usuario autenticado.
            final String jwt = jwtUtilService.generateToken(userDetails);
            // Retorna el token JWT en la respuesta.
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            // Retorna un error si la autenticación falla.
            return new ResponseEntity<>("Email or password invalid", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para registrar un nuevo cliente.
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        // Verifica si el email ya está registrado en la base de datos.
        if (clientRepository.findByEmail(registerDTO.email()) != null) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        // Verifica si el nombre y apellido no están vacíos.
        if (registerDTO.firstName().isBlank()) {
            return new ResponseEntity<>("First name field cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (registerDTO.lastName().isBlank()) {
            return new ResponseEntity<>("Last name field cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (registerDTO.password().isBlank()) {
            return new ResponseEntity<>("Password field cannot be empty", HttpStatus.BAD_REQUEST);
        }

        if (registerDTO.email().isBlank()) {
            return new ResponseEntity<>("The email field cannot be empty", HttpStatus.BAD_REQUEST);
        }

        // Verifica si la contraseña cumple con el requisito mínimo de longitud.
        if (registerDTO.password().length() < 8) {
            return new ResponseEntity<>("Password must be at least 8 characters long", HttpStatus.BAD_REQUEST);
        }

        // Codifica la contraseña antes de almacenarla.
        String encodedPassword = passwordEncoder.encode(registerDTO.password());

        // Crea un nuevo cliente con la información proporcionada.
        Client client = new Client(registerDTO.firstName(), registerDTO.lastName(), registerDTO.email(), encodedPassword);

        // Guarda el nuevo cliente en la base de datos.
        clientRepository.save(client);

        // Retorna una respuesta exitosa con un mensaje de confirmación.
        return new ResponseEntity<>("Client registered successfully", HttpStatus.CREATED);
    }

    // Endpoint para obtener los detalles del cliente autenticado.
    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication) {
        // Obtiene el cliente basado en el nombre de usuario autenticado.
        Client client = clientRepository.findByEmail(authentication.getName());
        // Retorna los detalles del cliente en la respuesta.
        return ResponseEntity.ok(new ClientDTO(client));
    }
}

package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.AuthService;
import com.mindhub.homebanking.services.ClientServices;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.mindhub.homebanking.utils.AccountNumberGenerator.makeAccountNumber;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {

        if (loginDTO.email().isBlank()) {
            return new ResponseEntity<>("Email field cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (loginDTO.password().length() < 8) {
            return new ResponseEntity<>("Password must be at least 8 characters long", HttpStatus.BAD_REQUEST);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
        final String jwt = jwtUtilService.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<?> registerVerifications(RegisterDTO registerDTO) {
        if (clientServices.findClientByEmail(registerDTO.email()) != null) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
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
        if (registerDTO.password().length() < 8) {
            return new ResponseEntity<>("Password must be at least 8 characters long", HttpStatus.BAD_REQUEST);
        }

         saveClientAndAddAccount(registerDTO) ;

         return ResponseEntity.ok("OK") ;
    }


    @Override
    public void saveClientAndAddAccount(RegisterDTO registerDTO) {
        String encodedPassword = passwordEncoder.encode(registerDTO.password());
        Client newClient = new Client(registerDTO.firstName(), registerDTO.lastName(), registerDTO.email(), encodedPassword);
        clientServices.saveClient(newClient);
        Account newAccount = new Account(makeAccountNumber(), LocalDate.now(), 0.0);
        newClient.addAccount(newAccount);

        accountServices.saveAccount(newAccount);

    }
    @Override
    public ResponseEntity<?> getClientDetails(Authentication authentication) {
        Client client = clientServices.getAuthenticatedClient(authentication);
        return ResponseEntity.ok(new ClientDTO(client));
    }
}

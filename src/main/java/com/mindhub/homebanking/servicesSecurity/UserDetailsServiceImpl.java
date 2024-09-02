package com.mindhub.homebanking.servicesSecurity;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //marco esta clase como un componente especifico(logica de nogocios, especializacion de componente de Spring Security)
public class UserDetailsServiceImpl implements UserDetailsService {

    // Inyección de la dependencia del repositorio de clientes.
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// Busca un cliente en el repositorio usando el correo electrónico como nombre de usuario.
        Client client = clientRepository.findByEmail(username);

        // Si el cliente no se encuentra, lanza una excepción indicando que el usuario no se ha encontrado.
        if (client == null) {
            throw new UsernameNotFoundException(username);
        }
        if (username.contains("admin")) {

            return User.withUsername(username)
                    .password(client.getPassword()) // Contraseña del cliente (debería ser segura, idealmente cifrada).
                    .roles("ADMIN") // Rol del usuario (en este caso, 'CLIENT').
                    .build(); // Construye el objeto UserDetails.
        }
        return User.withUsername(username)
                .password(client.getPassword()) // Contraseña del cliente (debería ser segura, idealmente cifrada).
                .roles("CLIENT") // Rol del usuario (en este caso, 'CLIENT').
                .build(); // Construye el objeto UserDetails.
    }
}

//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.Client;
//import com.mindhub.homebanking.repositories.ClientRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasProperty;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.not;
//import static org.hamcrest.Matchers.empty;
//
//import java.util.List;
//
//@SpringBootTest
//@ActiveProfiles("test") // Utiliza un perfil de configuración para pruebas
//public class ClientIntegrationTest {
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Test
//    public void existsClients() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, not(empty()));
//    }
//
//    @Test
//    public void existsClientByEmail() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, hasItem(hasProperty("email", is("juan.perez@example.com"))));
//    }
//}
//

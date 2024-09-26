package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("/api/clients")
    public class ClientController {

        @Autowired
        private ClientServices clientServices;

        @GetMapping("/")
        public ResponseEntity<List<ClientDTO>> getAllClients() {
            return new ResponseEntity<>( clientServices.getAllClientDTO(), HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getClientById(@PathVariable Long id) {
            if (clientServices.getClientById(id) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(clientServices.getClientDTO(clientServices.getClientById(id)), HttpStatus.OK);
            }
        }




//    @Autowired
//    private ClientRepository clientRepository;

//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private CardRepository cardRepository;


//    @GetMapping
//    public List<ClientDTO> getAllClients() {
//        return clientRepository.findAll()
//                .stream()
//                .filter(Client::isActive)
//                .map(ClientDTO::new)
//                .collect(toList());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
//        Client client = clientRepository.findById(id).orElse(null);
//        if (client == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
//    }
//
//    @GetMapping("/{clientId}/cards")
//    public ResponseEntity<List<CardDTO>> getClientCards(@PathVariable Long clientId) {
//        Client client = clientRepository.findById(clientId).orElse(null);
//        if (client == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        List<CardDTO> cards = client.getClientCards().stream().map(CardDTO::new).collect(toList());
//        return new ResponseEntity<>(cards, HttpStatus.OK);
//    }
//
//
//    @PostMapping("/create")
//    public ResponseEntity<Client> createClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
//        if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        Client newClient = new Client(firstName, lastName, email, password);
//        clientRepository.save(newClient);
//        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
//        Client client = clientRepository.findById(id).orElse(null);
//        if (client == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        if (!firstName.isBlank()) client.setFirstName(firstName);
//        if (!lastName.isBlank()) client.setLastName(lastName);
//        if (!email.isBlank()) client.setEmail(email);
//
//        clientRepository.save(client);
//        return new ResponseEntity<>(client, HttpStatus.OK);
//    }
//
//    @PatchMapping("/update/{id}")
//    public ResponseEntity<Client> partialUpdateClient(
//            @PathVariable Long id,
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) String email) {
//
//        Client client = clientRepository.findById(id).orElse(null);
//        if (client == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        if (firstName != null && !firstName.isBlank()) client.setFirstName(firstName);
//        if (lastName != null && !lastName.isBlank()) client.setLastName(lastName);
//        if (email != null && !email.isBlank()) client.setEmail(email);
//
//        clientRepository.save(client);
//        return new ResponseEntity<>(client, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
//        Client client = clientRepository.findById(id).orElse(null);
//        if (client == null) {
//            return new ResponseEntity<>("Client not found with id " + id, HttpStatus.NOT_FOUND);
//        }
//
//        client.setActive(false);
//        clientRepository.save(client);
//
//        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
//    }

}

package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.http.HttpStatus; //Importa el enum HttpStatus que contiene los códigos de estado HTTP que puedes usar para devolver respuestas HTTP con diferentes estados.
import org.springframework.http.ResponseEntity;//mporta la clase ResponseEntity, que representa una respuesta HTTP.
import org.springframework.beans.factory.annotation.Autowired; //imposta anotacion para inyectar las dependencias, aca el client repository
import org.springframework.web.bind.annotation.PutMapping; //para manejar solicitudes PUT
import org.springframework.web.bind.annotation.RequestParam; //anotacion para extraer parámetros de consulta de una solicitud HTTP.
import org.springframework.web.bind.annotation.*; //mporta varias anotaciones de Spring MVC @RequestMapping, @PathVariable, y @GetMapping
import org.springframework.web.bind.annotation.GetMapping; //para manejar solicitudes GET
import org.springframework.web.bind.annotation.PathVariable;//que se usa para extraer variables de la URL.
import org.springframework.web.bind.annotation.RestController;//importa la anotación @RestController,  que se usa para definir un controlador RESTful.
import java.util.List;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;

import static java.util.stream.Collectors.toList;



@RestController   //porq quiero que sea restful (se encarga de recibir las peticiones genericas y hacer algo)servlet, componente del controlador que escucha peticiones especificas
@RequestMapping("/api/clients")  //para definir la ruta de acceso /api/clients/ a ese controlador .asocio

//propiedADES Y METODOS. dependenciassd y servlets

public class ClientController {

    @Autowired   //cablear a la interfaz clientRepository,para poder usar los metodos de jpa repo. cableado automatico
    private ClientRepository clientRepository; //se instancia client repository, interfaz, lo va a implementar hibernate. inyeccion de dependencias

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/hello")    //Esta anotación mapea la ruta "/hola" a la getClients() método. Significa que cuando recibimos una solicitud GET en la ruta "/hola" (la ruta completa sería http://localhost:8080/api/clients/hello), este método será invocado.
    public String getClients() { //mapping, asociado
        return "Hello clients!";
    }

    @GetMapping   //para mapear la ruta /api/clients/ a un método que devuelva todos los clientes de la base de datos.por defecto uso la ruta de arriba
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    //mappear la ruta a una solicitud de tipo GET
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    //crud crear cliente
    @PostMapping("/create")  //tipo post para crear un cliente
    public Client createClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {  //request param le dice a spring que solicito ese parametro
        return clientRepository.save(new Client(firstName, lastName, email)); //validar q no sean string vacios, if first name.isBlank() return false
    }

    //crud para actualizar un cliente
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return new ResponseEntity<>("Client not found with id " + id, HttpStatus.NOT_FOUND);
        }
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setEmail(email);

        Client updatedClient = clientRepository.save(client);  //sobreescribo el cliente que ya tenia
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> partialUpdateClient(
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {

        Client client = clientRepository.findById(id).orElse(null);

        if (client == null) {
            return new ResponseEntity<>("Client not found with id " + id, HttpStatus.NOT_FOUND);
        }

        if (firstName != null) {
            client.setFirstName(firstName);
        }
        if (lastName != null) {
            client.setLastName(lastName);
        }
        if (email != null) {
            client.setEmail(email);
        }

        Client updatedClient = clientRepository.save(client);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }


    //crud para borrar un cliente
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <String> deleteClient(@PathVariable Long id) {

        clientRepository.deleteById(id);

        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }

}


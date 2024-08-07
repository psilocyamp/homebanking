package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Importa tu repositorio y entidad Cliente
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;

import java.time.LocalDate;


@SpringBootApplication
public class HomebankingApplication {


//aca arranca la app
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);   //le cedo el control a spring
	}


	// Método que se ejecuta al iniciar la aplicación. esta en el contexto de spring, porq no teiene anotaciones
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) { //init data es el nombre del metodo 	//inyeccion de dependencias. corredor de comandos en linea
		return (args) -> {
			// Crear y guardar los clientes utilizando DTOs

			Client melba = new Client("Melba", "Molina", "jTqFP@example.com");
			Client john = new Client("John", "Doe", "lVJZd@example.com");
			Client jane = new Client("Jane", "Doe", "lVJZd@example.com");

			ClientDTO melbaDTO = new ClientDTO(melba);
			ClientDTO johnDTO = new ClientDTO(john);
			ClientDTO janeDTO = new ClientDTO(jane);

			clientRepository.save(melba);
			clientRepository.save(john);
			clientRepository.save(jane);

			// Crear y guardar las cuentas asociadas a Melba utilizando DTOs
			Account account1 = new Account("VIN001",LocalDate.now(),5000);
			Account account2 = new Account("VIN002",LocalDate.now(),7500);

			AccountDTO account1DTO = new AccountDTO(account1);
			AccountDTO account2DTO = new AccountDTO(account2);

			melba.addAccount(account1);
			melba.addAccount(account2);
			accountRepository.save(account1);
			accountRepository.save(account2);

			// Crear y guardar cuentas para el segundo cliente (John)
			Account account3 = new Account("VIN003", LocalDate.now(), 10000);
			Account account4 = new Account("VIN004", LocalDate.now(),12000);

			AccountDTO account3DTO = new AccountDTO(account3);
			AccountDTO account4DTO = new AccountDTO(account4);

			john.addAccount(account3);
			john.addAccount(account4);

			accountRepository.save(account3);
			accountRepository.save(account4);

			// Crear y guardar cuentas para el tercer cliente (Jane)

			Account account5 = new Account("VIN005", LocalDate.now() ,15000);
			Account account6 = new Account("VIN006", LocalDate.now(), 18000);

			AccountDTO account5DTO = new AccountDTO(account5);
			AccountDTO account6DTO = new AccountDTO(account6);

			jane.addAccount(account5);
			jane.addAccount(account6);

			accountRepository.save(account5);
			accountRepository.save(account6);


		};
	}
}

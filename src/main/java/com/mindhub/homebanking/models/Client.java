
//aca creo la entidad en la base de datos llamada cliente

package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


    @Entity   //es para que Spring cree la tabla en la base de datos
    public class Client {

    @Id   //aca indico que esta va a ser la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //y aca le pido a la base de datos que genere el id
    private long id;

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account>accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ClientLoan> clientLoans = new ArrayList<>();

    public Client() { }  //este lo usa hibernate por defecto para validar. tener espacio en memoria

    public Client(String first, String last, String email) {
        this.firstName = first;
        this.lastName = last;
        this.email= email;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public long getId() {
        return id;
    }

    public Set<Account> getAccounts() { //coleccion de cuentas

        return accounts;
    }

    public List<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.setClient(this);
    }

    public void addClientLoan(ClientLoan clientLoan) {
        this.clientLoans.add(clientLoan);
        clientLoan.setClient(this);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}

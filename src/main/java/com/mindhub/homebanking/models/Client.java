
//aca creo la entidad en la base de datos llamada cliente

package com.mindhub.homebanking.models;

import com.mindhub.homebanking.models.utils.CardNumberGenerator;
import jakarta.persistence.*;

import java.util.*;


@Entity   //es para que Spring cree la tabla en la base de datos
    public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //y aca le pido a la base de datos que genere el id
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private boolean active = true;


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account>accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ClientLoan> clientLoans = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Card> clientCards = new ArrayList<>();

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

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public List<Card> getClientCards() {
            return clientCards;
    }

        @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                ", clientLoans=" + clientLoans +
                ", ClientCards=" + clientCards +
                '}';
    }

        public void addAccount(Account account) {
            this.accounts.add(account);
            account.setClient(this);
        }

        public void addClientLoan(ClientLoan clientLoan) {
            this.clientLoans.add(clientLoan);
            clientLoan.setClient(this);
        }

        public void addClientCard(Card card) {
            this.clientCards.add(card);
            card.setNumber(new CardNumberGenerator().generateCardNumber());
            card.setClient(this);
            card.setCardHolder(this.firstName + " " + this.lastName);
            card.setCvv(new Random().nextInt((999 - 100) + 1) + 100);
        }


    }

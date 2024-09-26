//package com.mindhub.homebanking;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//public class ClientRegistrationAndAuthenticationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void registerAndAuthenticateClient() {
//        // Registro de un nuevo cliente
//        String registerUrl = "/api/auth/register";
//        MultiValueMap<String, String> registrationParams = new LinkedMultiValueMap<>();
//        registrationParams.add("firstName", "Juan");
//        registrationParams.add("lastName", "Perez");
//        registrationParams.add("email", "juan.perez@example.com");
//        registrationParams.add("password", "password123");
//
//        ResponseEntity<String> registrationResponse = restTemplate.postForEntity(registerUrl, registrationParams, String.class);
//        assertThat(registrationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//        // Autenticación del cliente registrado
//        String loginUrl = "/api/auth/login";
//        MultiValueMap<String, String> loginParams = new LinkedMultiValueMap<>();
//        loginParams.add("email", "juan.perez@example.com");
//        loginParams.add("password", "password123");
//
//        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginParams, String.class);
//        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        // Verificar que la respuesta del login contenga un token de acceso
//        String responseBody = loginResponse.getBody();
//        assertThat(responseBody).contains("accessToken");
//    }
//}
//

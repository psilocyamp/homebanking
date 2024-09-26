//package com.mindhub.homebanking;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
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
//public class LoanApplicationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void applyForLoanAsAuthenticatedClient() {
//        // Autenticación del cliente para obtener el token
//        String loginUrl = "/api/auth/login";
//        MultiValueMap<String, String> loginParams = new LinkedMultiValueMap<>();
//        loginParams.add("email", "juan.perez@example.com");
//        loginParams.add("password", "password123");
//
//        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, loginParams, String.class);
//        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        String authToken = loginResponse.getBody().split(":")[1].replace("\"", "").trim(); // Extraer el token, ajustar según el formato real
//
//        // Solicitar un préstamo
//        String applyLoanUrl = "/api/loans/";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + authToken);
//
//        MultiValueMap<String, String> loanParams = new LinkedMultiValueMap<>();
//        loanParams.add("loanAmount", "5000");
//        loanParams.add("loanDuration", "12");
//
//        HttpEntity<MultiValueMap<String, String>> applyLoanRequest = new HttpEntity<>(loanParams, headers);
//        ResponseEntity<String> loanResponse = restTemplate.exchange(applyLoanUrl, HttpMethod.POST, applyLoanRequest, String.class);
//        assertThat(loanResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//        // Ver que la respuesta de la solicitud de préstamo contenga una confirmación
//        String responseBody = loanResponse.getBody();
//        assertThat(responseBody).contains("Loan approved");
//    }
//}
//

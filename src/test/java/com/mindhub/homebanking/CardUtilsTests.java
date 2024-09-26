//package com.mindhub.homebanking;
//import org.junit.jupiter.api.Test;
//import static org.hamcrest.MatcherAssert.assertThat;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
//import com.mindhub.homebanking.utils.CardNumberGenerator;
//import com.mindhub.homebanking.utils.CvvGenerator;
//
//
//
//@SpringBootTest
//public class CardUtilsTests {
//
//    @Test
//    public void cardNumberIsCreated() {
//        String cardNumber = CardNumberGenerator.generateCardNumber();
//        assertThat(cardNumber, is(not(emptyOrNullString())));
//    }
//
//    @Test
//    public void cvvIsCreated() {
//        int cvv = CvvGenerator.cvvNumber();
//        assertThat(cvv, is(greaterThanOrEqualTo(100)));
//        assertThat(cvv, is(lessThanOrEqualTo(999)));
//    }
//}

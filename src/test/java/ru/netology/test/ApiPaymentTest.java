package ru.netology.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.ApiHelper.paymentStatus;
import static ru.netology.data.DataHelper.*;

public class ApiPaymentTest {


    @Test
    public void approvedCardTransactionApiTest() {
        int statusCode = 200;
        String status = "APPROVED";
        String paymentStatus = paymentStatus(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0), getHolder(),
                getCvc()), statusCode);
        assertEquals(status, paymentStatus);
    }

    @Test
    public void declinedCardTransactionApiTest() {
        int statusCode = 200;
        String status = "DECLINED";
        String paymentStatus = paymentStatus(getCardInfo(getDeclinedCardNumber(), getYear(0), getMonth(0), getHolder(),
                getCvc()), statusCode);

        assertEquals(status, paymentStatus);
    }


    @Test
    public void invalidCardTransactionApiTest() {
        int statusCode = 400;
        String status = "400 Bad Request";
        String paymentStatus = paymentStatus(getCardInfo(getInvalidCardNumber(), getYear(0), getMonth(0), getHolder(),
                getCvc()), statusCode);

        assertEquals(status, paymentStatus);
    }

    @Test
    public void sendingFormWithZerosInMonthFieldApiTest() {
        String value = "00";
        int statusCode = 400;
        String status = "400 Bad Request";
        String paymentStatus = paymentStatus(getCardInfo(getApprovedCardNumber(), getYear(0), getWrongFormatMonth(value),
                getHolder(), getCvc()), statusCode);

        assertEquals(status, paymentStatus);
    }

    @Test
    public void sendingFormWithNumbersInHolderFieldApiTest() {
        String value = "123";
        int statusCode = 400;
        String status = "400 Bad Request";
        String paymentStatus = paymentStatus(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                getWrongFormatHolder(value), getCvc()), statusCode);
        assertEquals(status, paymentStatus);
    }
}
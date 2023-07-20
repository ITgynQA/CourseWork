package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.ApiHelper.paymentStatus;
import static ru.netology.data.DataHelper.*;

public class ApiPaymentTest {

    StartPage startPage;
    PaymentPage paymentPage;

    @BeforeEach
    public void setup() {
        open("http://localhost:8080");
        int index = 0;
        startPage = new StartPage();
        paymentPage = startPage.selectButton(index);

    }

    @Test
    public void approvedCardTransactionApiTest() {
        int statusCode = 200;
        String paymentStatus = paymentStatus(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0), getHolder(),
                getCvc()), statusCode);
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0), getHolder(), getCvc()));

        assertEquals(paymentStatus, DBHelper.getStatusPayment());


    }

    @Test
    public void declinedCardTransactionApiTest() {
        int statusCode = 200;
        String paymentStatus = paymentStatus(getCardInfo(getDeclinedCardNumber(), getYear(0), getMonth(0), getHolder(),
                getCvc()), statusCode);
        paymentPage
                .cardPayment(getCardInfo(getDeclinedCardNumber(), getYear(0), getMonth(0), getHolder(), getCvc()));

        assertEquals(paymentStatus, DBHelper.getStatusPayment());
    }


    @Test
    public void invalidCardTransactionApiTest() {
        int statusCode = 400;
        String status = "400 Bad Request";
        String paymentStatus = paymentStatus(getCardInfo(getInvalidCardNumber(), getYear(0), getMonth(0), getHolder(),
                getCvc()), statusCode);

        assertEquals(status, paymentStatus);
    }


}
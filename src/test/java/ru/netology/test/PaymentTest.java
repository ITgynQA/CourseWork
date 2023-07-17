package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;


public class PaymentTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setup() {
        open("http://localhost:8080");
        int index = 0;
        StartPage StartPage;
        StartPage = new StartPage();
        StartPage.selectButton(index);

    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void approvedCardTransactionTest1() {
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.successMessage();
        assertEquals("APPROVED", DBHelper.getStatusPayment());

    }

    @Test
    public void declinedCardTransactionTest2() {
        PaymentPage
                .cardPayment(getCardInfo(getDeclinedCardNumber(), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.errorMessage();
        assertEquals("DECLINED", DBHelper.getStatusPayment());

    }

    @Test
    public void invalidCardTransactionTest3() {
        PaymentPage
                .cardPayment(getCardInfo(getInvalidCardNumber(), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.errorMessage();

    }

    @Test
    public void sendingFormWith15numbersInCardNumberFieldTest4() {
        var value = "1111 2222 3333 444";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(value), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWith17numbersInCardNumberFieldTest5() {
        String value = "1111 2222 3333 4444 5";
        String text = "1111 2222 3333 4444";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(value), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.lengthFieldToFill(index, text);

    }

    @Test
    public void sendingFormWithTextInCardNumberFieldTest6() {
        String value = "Ivan";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(value), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.emptyFieldToFill(index);
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithSpecialCharactersInCardNumberFieldTest7() {
        String value = "@#$%&";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(value), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.emptyFieldToFill(index);
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormIsEmptyCardNumberFieldTest8() {
        String value = "";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(value), getYear(0), getMonth(0), getHolder(), getCvc()));
        PaymentPage.emptyFieldToFill(index);
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormWithNonExistentValueInMonthFieldTest9() {
        String value = "13";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getWrongFormatMonth(value),
                        getHolder(), getCvc()));
        PaymentPage.wrongValidityPeriodMessage(index);
        PaymentPage.invalidMessage(index);


    }

    @Test
    public void sendingFormWithZerosInMonthFieldTest10() {
        String value = "00";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getWrongFormatMonth(value),
                        getHolder(), getCvc()));
        PaymentPage.wrongValidityPeriodMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithMeaningFromPastInMonthFieldTest11() {
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0),
                        getMonth(-1), getHolder(), getCvc()));
        PaymentPage.wrongValidityPeriodMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneDigitInMonthFieldTest12() {
        String value = "1";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getWrongFormatMonth(value),
                        getHolder(), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyMonthFieldTest13() {
        String value = "";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getWrongFormatMonth(value),
                        getHolder(), getCvc()));
        PaymentPage.emptyFieldToFill(1);
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormWithMeaningFromPastInYearFieldTest14() {
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(-1), getMonth(0),
                        getHolder(), getCvc()));
        PaymentPage.expiredValidityPeriodMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithMeaningFromFutureInYearFieldTest15() {
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(6), getMonth(0),
                        getHolder(), getCvc()));
        PaymentPage.wrongValidityPeriodMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneDigitInYearFieldTest16() {
        String value = "1";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getWrongFormatYear(value), getMonth(0),
                        getHolder(), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyYearFieldTest17() {
        String value = "";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getWrongFormatYear(value), getMonth(0),
                        getHolder(), getCvc()));
        PaymentPage.emptyFieldToFill(2);
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithNumbersInHolderFieldTest18() {
        String value = "123";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(value), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithCyrillicInHolderFieldTest19() {
        String value = "Иванов";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(value), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneLetterInHolderFieldTest20() {
        String value = "J";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(value), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneHundredThirtyLettersInHolderFieldTest21() {
        String value = "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
                "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(value), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithSpecialCharactersInHolderFieldTest22() {
        String value = "@#$%&";
        int index = 0;
        PaymentPage.cardPayment
                (getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(value), getCvc()));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyHolderFieldTest23() {
        String value = "";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(value), getCvc()));
        PaymentPage.emptyFieldToFill(3);
        PaymentPage.isEmptyFieldMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithTextInCvcFieldTest24() {
        String value = "Ivan";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(value)));
        PaymentPage.emptyFieldToFill(4);
        PaymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormWithTwoNumbersInCvcFieldTest25() {
        String value = "99";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(value)));
        PaymentPage.wrongFormatMessage(index);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithFourNumbersInCvcFieldTest26() {
        String value = "9999";
        String text = "999";
        int index = 4;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(value)));
        PaymentPage.lengthFieldToFill(index, text);

    }

    @Test
    public void sendingFormWithSpecialCharactersInCvcFieldTest27() {
        String value = "@#$";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(value)));
        PaymentPage.emptyFieldToFill(4);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyCvcFieldTest28() {
        String value = "";
        int index = 0;
        PaymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(value)));
        PaymentPage.emptyFieldToFill(4);
        PaymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyAllFieldsTest29() {
        String value = "";
        PaymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(value), getWrongFormatYear(value), getWrongFormatMonth(value),
                        getWrongFormatHolder(value), getWrongFormatCvc(value)));
        PaymentPage.wrongFormatMessage(0);
        PaymentPage.wrongFormatMessage(1);
        PaymentPage.wrongFormatMessage(2);
        PaymentPage.isEmptyFieldMessage(3);
        PaymentPage.wrongFormatMessage(4);
        PaymentPage.invalidMessage(0);
        PaymentPage.invalidMessage(1);
        PaymentPage.invalidMessage(2);
        PaymentPage.invalidMessage(3);
        PaymentPage.invalidMessage(4);

    }
}
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
    StartPage startPage;
    PaymentPage paymentPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setup() {
        open("http://localhost:8080");
        int index = 0;
        startPage = new StartPage();
        paymentPage = startPage.selectButton(index);

    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void approvedCardTransactionTest1() {
        paymentPage.cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0), getHolder(), getCvc()));
        paymentPage.successMessage();
        assertEquals("APPROVED", DBHelper.getStatusPayment());

    }

    @Test
    public void declinedCardTransactionTest2() {
        paymentPage
                .cardPayment(getCardInfo(getDeclinedCardNumber(), getYear(0), getMonth(0), getHolder(), getCvc()));
        paymentPage.errorMessage();
        assertEquals("DECLINED", DBHelper.getStatusPayment());

    }

    @Test
    public void invalidCardTransactionTest3() {
        paymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(generateValue("ru").numerify("#### #### #### ####")),
                        getYear(0), getMonth(0), getHolder(), getCvc()));
        paymentPage.errorMessage();

    }

    @Test
    public void sendingFormWith15numbersInCardNumberFieldTest4() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(generateValue("ru").numerify("#### #### #### ###")),
                        getYear(0), getMonth(0), getHolder(), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWith17numbersInCardNumberFieldTest5() {
        paymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(generateValue("ru").numerify("#### #### #### #### #")),
                        getYear(0), getMonth(0), getHolder(), getCvc()));
        paymentPage.errorMessage();
    }

    @Test
    public void sendingFormWithTextInCardNumberFieldTest6() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(generateName("en")), getYear(0), getMonth(0),
                        getHolder(), getCvc()));
        paymentPage.emptyFieldToFill(index);
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithSpecialCharactersInCardNumberFieldTest7() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getInvalidCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getCvc()));
        paymentPage.emptyFieldToFill(index);
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormIsEmptyCardNumberFieldTest8() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(generateValue("ru").numerify("")),
                        getYear(0), getMonth(0), getHolder(), getCvc()));
        paymentPage.emptyFieldToFill(index);
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormWithNonExistentValueInMonthFieldTest9() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getWrongFormatMonth("13"),
                        getHolder(), getCvc()));
        paymentPage.wrongValidityPeriodMessage(index);
        paymentPage.invalidMessage(index);


    }

    @Test
    public void sendingFormWithZerosInMonthFieldTest10() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(1), getWrongFormatMonth("00"),
                        getHolder(), getCvc()));
        paymentPage.wrongValidityPeriodMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithMeaningFromPastInMonthFieldTest11() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0),
                        getMonth(-1), getHolder(), getCvc()));
        paymentPage.wrongValidityPeriodMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneDigitInMonthFieldTest12() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0),
                        getWrongFormatMonth(generateValue("ru").numerify("#")), getHolder(), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyMonthFieldTest13() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0),
                        getWrongFormatMonth(generateValue("ru").numerify("")),
                        getHolder(), getCvc()));
        paymentPage.emptyFieldToFill(1);
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormWithMeaningFromPastInYearFieldTest14() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(-1), getMonth(0),
                        getHolder(), getCvc()));
        paymentPage.expiredValidityPeriodMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithMeaningFromFutureInYearFieldTest15() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(6), getMonth(0),
                        getHolder(), getCvc()));
        paymentPage.wrongValidityPeriodMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneDigitInYearFieldTest16() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getWrongFormatYear(generateValue("ru")
                                .numerify("#")), getMonth(0),
                        getHolder(), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyYearFieldTest17() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getWrongFormatYear(generateValue("ru")
                                .numerify("")), getMonth(0),
                        getHolder(), getCvc()));
        paymentPage.emptyFieldToFill(2);
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithNumbersInHolderFieldTest18() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(generateValue("ru").numerify("#####")), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithCyrillicInHolderFieldTest19() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(generateName("ru")), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneLetterInHolderFieldTest20() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(generateValue("en").letterify("?")), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithOneHundredThirtyLettersInHolderFieldTest21() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(generateValue("en").
                                letterify("???????????????????????????????????????????????????????????????????" +
                                        "???????????????????????????????????????????????????????????")), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithSpecialCharactersInHolderFieldTest22() {
        int index = 0;
        paymentPage.cardPayment
                (getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getInvalidHolder(), getCvc()));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyHolderFieldTest23() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getWrongFormatHolder(generateValue("ru").numerify("")), getCvc()));
        paymentPage.emptyFieldToFill(3);
        paymentPage.isEmptyFieldMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithTextInCvcFieldTest24() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(generateValue("en").letterify("???"))));
        paymentPage.emptyFieldToFill(4);
        paymentPage.invalidMessage(index);
    }

    @Test
    public void sendingFormWithTwoNumbersInCvcFieldTest25() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(generateValue("ru").numerify("##"))));
        paymentPage.wrongFormatMessage(index);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormWithFourNumbersInCvcFieldTest26() {
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(generateValue("ru").numerify("####"))));
        paymentPage.successMessage();

    }

    @Test
    public void sendingFormWithSpecialCharactersInCvcFieldTest27() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(generateValue("en").letterify("???"))));
        paymentPage.emptyFieldToFill(4);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyCvcFieldTest28() {
        int index = 0;
        paymentPage
                .cardPayment(getCardInfo(getApprovedCardNumber(), getYear(0), getMonth(0),
                        getHolder(), getWrongFormatCvc(generateValue("ru").numerify(""))));
        paymentPage.emptyFieldToFill(4);
        paymentPage.invalidMessage(index);

    }

    @Test
    public void sendingFormIsEmptyAllFieldsTest29() {
        paymentPage
                .cardPayment(getCardInfo(getWrongFormatCard(generateValue("ru").numerify("")),
                        getWrongFormatYear(generateValue("ru").numerify("")),
                        getWrongFormatMonth(generateValue("ru").numerify("")),
                        getWrongFormatHolder(generateValue("ru").numerify("")),
                        getWrongFormatCvc(generateValue("ru").numerify(""))));
        paymentPage.wrongFormatMessage(0);
        paymentPage.wrongFormatMessage(1);
        paymentPage.wrongFormatMessage(2);
        paymentPage.isEmptyFieldMessage(3);
        paymentPage.wrongFormatMessage(4);
        paymentPage.invalidMessage(0);
        paymentPage.invalidMessage(1);
        paymentPage.invalidMessage(2);
        paymentPage.invalidMessage(3);
        paymentPage.invalidMessage(4);

    }
}
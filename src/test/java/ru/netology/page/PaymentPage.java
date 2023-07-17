package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    SelenideElement header = $$(".heading").get(2);
    static ElementsCollection fieldToFill = $$(".input__control");
    static SelenideElement cardNumberField = $$(".input__control").get(0);
    static SelenideElement monthField = $$(".input__control").get(1);
    static SelenideElement yearField = $$(".input__control").get(2);
    static SelenideElement holderField = $$(".input__control").get(3);
    static SelenideElement cvcField = $$(".input__control").get(4);
    static SelenideElement continueButton = $$(".button__text").get(2);
    static SelenideElement successMessage = $$(".notification__content").get(0);
    static SelenideElement errorMessage = $$(".notification__content").get(1);
    static ElementsCollection wrongFormatMessage = $$(".input__sub");
    static ElementsCollection invalidField = $$(".input_invalid");

    public PaymentPage() {
        header.shouldBe(visible);
    }

    public static void successMessage() {
        successMessage.shouldHave(text("Операция одобрена банком"),
                Duration.ofSeconds(13)).shouldBe(visible);
    }

    public static void errorMessage() {
        errorMessage.shouldHave(text("Банк отказал в проведении операции"),
                Duration.ofSeconds(13)).shouldBe(visible);
    }

    public static void wrongFormatMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Неверный формат"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public static void wrongValidityPeriodMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Неверно указан срок действия карты"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public static void expiredValidityPeriodMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Истёк срок действия карты"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public static void isEmptyFieldMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public static void invalidMessage(int index) {
        invalidField.get(index).shouldBe(exist);

    }

    public static void emptyFieldToFill(int index) {
        fieldToFill.get(index).shouldBe(empty);

    }

    public static void lengthFieldToFill(int index, String text) {
        fieldToFill.get(index).shouldHave(value(text));

    }

    public static void cardPayment(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getNumber());
        yearField.setValue(cardInfo.getYear());
        monthField.setValue(cardInfo.getMonth());
        holderField.setValue(cardInfo.getHolder());
        cvcField.setValue(cardInfo.getCvc());
        continueButton.click();
    }


}

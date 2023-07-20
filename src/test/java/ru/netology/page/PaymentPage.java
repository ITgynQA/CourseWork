package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private final SelenideElement header = $$(".heading").get(2);
    private final ElementsCollection fieldToFill = $$(".input__control");
    private final SelenideElement cardNumberField = $$(".input__control").get(0);
    private final SelenideElement monthField = $$(".input__control").get(1);
    private final SelenideElement yearField = $$(".input__control").get(2);
    private final SelenideElement holderField = $$(".input__control").get(3);
    private final SelenideElement cvcField = $$(".input__control").get(4);
    private final SelenideElement continueButton = $$(".button__text").get(2);
    private final SelenideElement successMessage = $$(".notification__content").get(0);
    private final SelenideElement errorMessage = $$(".notification__content").get(1);
    private final ElementsCollection wrongFormatMessage = $$(".input__sub");
    private final ElementsCollection invalidField = $$(".input_invalid");

    public PaymentPage() {
        header.shouldBe(visible);
    }

    public void successMessage() {
        successMessage.shouldHave(text("Операция одобрена банком"),
                Duration.ofSeconds(13)).shouldBe(visible);
    }

    public void errorMessage() {
        errorMessage.shouldHave(text("Банк отказал в проведении операции"),
                Duration.ofSeconds(13)).shouldBe(visible);
    }

    public void wrongFormatMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Неверный формат"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public void wrongValidityPeriodMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Неверно указан срок действия карты"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public void expiredValidityPeriodMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Истёк срок действия карты"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public void isEmptyFieldMessage(int index) {
        wrongFormatMessage.get(index).shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(10)).shouldBe(visible);
    }

    public void invalidMessage(int index) {
        invalidField.get(index).shouldBe(exist);

    }

    public void emptyFieldToFill(int index) {
        fieldToFill.get(index).shouldBe(empty);

    }

    public void cardPayment(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getNumber());
        yearField.setValue(cardInfo.getYear());
        monthField.setValue(cardInfo.getMonth());
        holderField.setValue(cardInfo.getHolder());
        cvcField.setValue(cardInfo.getCvc());
        continueButton.click();
    }


}

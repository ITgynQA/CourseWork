package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private final ElementsCollection buttons = $$(".button__text");

    public PaymentPage selectButton(int index) {
        buttons.get(index).click();
        return new PaymentPage();
    }
}

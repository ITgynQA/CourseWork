package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataHelper {

    public static Faker generateValue(String locale) {
        return new Faker(new Locale(locale));
    }


    @Value
    public static class CardInfo {
        String number;
        String year;
        String month;
        String holder;
        String cvc;
    }

    public static CardInfo getCardInfo(CardNumber number, Year year, Month month, Holder holder, Cvc cvc) {
        return new CardInfo(number.getNumber(), year.getYear(), month.getMonth(), holder.getHolder(), cvc.getCvc());

    }

    @Value
    public static class CardNumber {
        String number;
    }

    public static CardNumber getApprovedCardNumber() {
        return new CardNumber("1111 2222 3333 4444");

    }

    public static CardNumber getDeclinedCardNumber() {
        return new CardNumber("5555 6666 7777 8888");
    }

    public static CardNumber getInvalidCardNumber() {
        return new CardNumber("@@@@ #### $$$$ &&&&");
    }

    public static CardNumber getWrongFormatCard(String value) {
        return new CardNumber(value);
    }


    @Value
    public static class Month {
        String month;
    }

    public static String generateMonth(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static Month getMonth(int shift) {
        return new Month(generateMonth(shift));
    }

    public static Month getWrongFormatMonth(String value) {
        return new Month(value);
    }

    @Value
    public static class Year {
        String year;
    }

    public static String generateYear(int shift) {
        return LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static Year getYear(int shift) {
        return new Year(generateYear(shift));
    }

    public static Year getWrongFormatYear(String value) {
        return new Year(value);
    }

    @Value
    public static class Holder {
        String holder;

    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static Holder getHolder() {
        return new Holder(generateName("en"));
    }

    public static Holder getInvalidHolder() {
        return new Holder("$$$$$$");
    }

    public static Holder getWrongFormatHolder(String value) {
        return new Holder(value);
    }

    @Value
    public static class Cvc {
        String cvc;
    }

    public static Cvc getCvc() {
        return new Cvc(generateValue("en").numerify("###"));
    }

    public static Cvc getWrongFormatCvc(String value) {
        return new Cvc(value);
    }

}

package ru.netology;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryPositiveTest {

    private static Faker faker;

    @BeforeAll
    static void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999");
    }


    public String setDays(int days) {
        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldTestAllValidInfo() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
// TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        $("[data-test-id='city'] [class='input__control']").setValue("Казань");
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();
        String cardNumber = faker.finance().creditCard(CreditCardType.MASTERCARD);
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        String days = setDays(3);
        $("[data-test-id='date'] [class='input__control']").setValue(days);
        $("[data-test-id='name'] [class='input__control']").setValue(name);
        $("[data-test-id='phone'] [class='input__control']").setValue(phone);
        $(".checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + days));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + days));

    }


//    @Test
//    void shouldTestAllValidInfo() {
//        $("[data-test-id='city'] [class='input__control']").setValue("Казань");
//        String name = faker.name().fullName();
//        String phone = faker.phoneNumber().phoneNumber();
//        String cardNumber = faker.finance().creditCard(CreditCardType.MASTERCARD);
//        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
//        String days = setDays(3);
//        $("[data-test-id='date'] [class='input__control']").setValue(days);
//        $("[data-test-id='name'] [class='input__control']").setValue(name);
//        $("[data-test-id='phone'] [class='input__control']").setValue(phone);
//        $(".checkbox__box").click();
//        $$("button").find(exactText("Запланировать")).click();
//        $("[data-test-id='success-notification'] .notification__title")
//                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
//        $("[data-test-id='success-notification'] .notification__content")
//                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + days));
//        $$("button").find(exactText("Запланировать")).click();
//        $("[data-test-id='replan-notification'] .notification__title")
//                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Необходимо подтверждение"));
//        $("[data-test-id='replan-notification'] .notification__content")
//                .shouldBe(visible, Duration.ofSeconds(15))
//                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
//        $$("button").find(exactText("Перепланировать")).click();
//        $("[data-test-id='success-notification'] .notification__title")
//                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
//        $("[data-test-id='success-notification'] .notification__content")
//                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + days));

//    }

}

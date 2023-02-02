package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Condition.text;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id=login]").setValue(registeredUser.getLogin());
        $("[data-test-id=password]").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login] ").click();

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id=login]").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password]").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login] ").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.appear);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=login]").setValue(blockedUser.getLogin());
        $("[data-test-id=password]").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login] ").click();

    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id=login]").setValue(wrongLogin);
        $("[data-test-id=password]").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login] ").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.appear);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id=login]").setValue(registeredUser.getLogin());
        $("[data-test-id=password]").setValue(wrongPassword);
        $("[data-test-id=action-login] ").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.appear);
    }
}

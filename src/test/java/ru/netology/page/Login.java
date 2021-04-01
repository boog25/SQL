package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Data;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class Login {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement titleError = $(withText("Ошибка"));


    public Verification validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new Verification();
    }


    public void invalidLogin() {
        loginField.setValue(Data.getInvalidLoginUser());
        passwordField.setValue(Data.getPasswordUser());
        loginButton.click();
        titleError.shouldBe(Condition.visible);
    }

    public void invalidPassword() {
        loginField.setValue(Data.getLoginUser());
        passwordField.setValue(Data.getInvalidPasswordUser());
        loginButton.click();
        titleError.shouldBe(Condition.visible);

    }

    public void error() {
        titleError.shouldBe(Condition.visible);
    }

}

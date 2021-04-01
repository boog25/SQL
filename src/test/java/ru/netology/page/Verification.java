package ru.netology.page;


import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Data;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Verification {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public Verification() {
        codeField.shouldBe(visible);
    }


    public Account validVerify(String code) {
        codeField.setValue(code);
        verifyButton.click();
        return new Account();
    }

    public Account invalidVerify() {
        codeField.setValue(Data.getInvalidCodeVerify());
        verifyButton.click();
        return new Account();
    }

}

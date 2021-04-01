package ru.netology.test;

import ru.netology.data.Data;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.page.Account;
import ru.netology.page.Login;
import ru.netology.page.Verification;
import org.junit.jupiter.api.Test;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestData {


    @Test
    @Order(1)
    void shouldCorrectedUserRegistered() throws SQLException {
        Data.insertFakeUser();
        open("http://localhost:9999");
        Login login = new Login();
        Verification verificationPage = login.validLogin(Data.getLoginUser(), Data.getPasswordUser());
        Account accountPage = verificationPage.validVerify(Data.getVerificationCode());
        accountPage.checkIfVisible();
    }

    @Test
    @Order(2)
    void shouldEnteredInvalidLogin() {
        open("http://localhost:9999");
        Login loginPage = new Login();
        loginPage.invalidLogin();
    }

    @Test
    @Order(3)
    void shouldEnteredInvalidPassword() {
        open("http://localhost:9999");
        Login loginPage = new Login();
        loginPage.invalidPassword();

    }

    @Test
    @Order(4)
    void shouldEnteredInvalidVerifyCode() {
        open("http://localhost:9999");
        Login loginPage = new Login();
        Verification verificationPage = loginPage.validLogin(Data.getLoginUser(), Data.getPasswordUser());
        Account accountPage = verificationPage.invalidVerify();
        accountPage.checkError();

    }

    @AfterAll
    static void cleanTable() {
        val codes = "DELETE FROM auth_codes";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";
        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val prepareStatCode = connect.prepareStatement(codes);
                val prepareStatCard = connect.prepareStatement(cards);
                val prepareStatUser = connect.prepareStatement(users);
        ) {
            prepareStatCode.executeUpdate(codes);
            prepareStatCard.executeUpdate(cards);
            prepareStatUser.executeUpdate(users);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}

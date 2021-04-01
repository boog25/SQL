package ru.netology.data;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.javafaker.Faker;
import lombok.val;

import java.sql.*;

public class Data {


    public static String getLoginUser() {
        val getUser = "SELECT login FROM users WHERE status='active';";
        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(getUser)) {
                if (resultSet.next()) {
                    val userLogin = resultSet.getString(1);

                    return userLogin;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


    public static void insertFakeUser() throws SQLException {
        val faker = new Faker();

        String password = "1234";
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        val dataSQL = "INSERT INTO users(id, login, password, status) VALUES (?, ?, ?, ?);";

        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val prepareStmt = connect.prepareStatement(dataSQL);
        ) {
            prepareStmt.setString(1, faker.idNumber().valid());
            prepareStmt.setString(2, faker.name().firstName());
            prepareStmt.setString(3, bcryptHashString);
            prepareStmt.setString(4, "active");
            prepareStmt.executeUpdate();


        }

    }

    public static String getUserId() {
        val getUserId = "SELECT id FROM users WHERE status='active';";
        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(getUserId)) {
                if (resultSet.next()) {
                    val userId = resultSet.getString(1);
                    return userId;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String getVerificationCode() {
        val requestCode = "SELECT code FROM auth_codes WHERE user_id=? ORDER BY created DESC LIMIT 1;";

        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val prepareStmt = connect.prepareStatement(requestCode);
        ) {
            prepareStmt.setString(1, getUserId());
            try (val resultSet = prepareStmt.executeQuery()) {
                if (resultSet.next()) {
                    val code = resultSet.getString(1);
                    return code;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


    public static String getPasswordUser() {
        return "1234";
    }

    public static String getInvalidPasswordUser() {
        return "password";
    }

    public static String getInvalidLoginUser() {
        return "Andry";
    }

    public static String getInvalidCodeVerify() {
        return "12345";
    }

}

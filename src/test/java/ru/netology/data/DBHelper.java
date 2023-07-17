package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
    public static QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection getConn() {
        return DriverManager
                .getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");

    }

    @SneakyThrows
    public static String getStatusPayment() {
        var select = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = DBHelper.getConn();
        var status = runner.query(conn, select, new ScalarHandler<String>());
        return status;

    }


}

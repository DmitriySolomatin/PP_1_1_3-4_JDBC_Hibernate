package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection = null;
    private static final String HOST = "jdbc:mysql://localhost:3306/kata_jdbc";
    private static final String USER = "root";
    private static final String PASS = "root";

    static {
        try {
            connection = DriverManager.getConnection(HOST, USER, PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Ошибка соединения с базой данных.");
        }
    }
    public static Connection getConnection() {
        return connection;
    }
}

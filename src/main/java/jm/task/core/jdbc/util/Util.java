package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;
    private static final String HOST = "jdbc:mysql://localhost:3306/kata_jdbc";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(HOST, USER, PASS);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                System.out.println("Ошибка соединения с базой данных.");
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.put(Environment.URL, HOST);
            properties.put(Environment.USER, USER);
            properties.put(Environment.PASS, PASS);
            properties.put(Environment.FORMAT_SQL, "true");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//        properties.put(Environment.SHOW_SQL, "true");
//        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//        properties.put(Environment.HBM2DDL_AUTO, "create");
//        properties.put(Environment.POOL_SIZE, "5");

            Configuration configuration = new Configuration().setProperties(properties).addAnnotatedClass(User.class);
            try {
                sessionFactory = configuration.buildSessionFactory();
            } catch (HibernateException e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }
    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement();) {
            statement.execute("CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "                      `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "                      `name` VARCHAR(45) NULL,\n" +
                    "                      `lastName` VARCHAR(45) NULL,\n" +
                    "                      `age` TINYINT NULL,\n" +
                    "                      PRIMARY KEY (`id`),\n" +
                    "                      UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement();) {
            statement.execute("DROP TABLE IF EXISTS users;");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (`name`, `lastName`, `age`) VALUES (?, ?, ?);");) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE `id` = ?;");) {
            statement.setLong(1, id);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement();) {
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            connection.commit();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = new User(
                        result.getString("name"),
                        result.getString("lastName"),
                        result.getByte("age")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement();) {
            statement.execute("TRUNCATE TABLE users;");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}

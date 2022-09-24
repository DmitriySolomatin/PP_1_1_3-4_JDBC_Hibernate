package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.createUsersTable();

        service.saveUser("Иван", "Иванов", (byte) 20);
        service.saveUser("Пётр", "Петров", (byte) 30);
        service.saveUser("Александр", "Савин", (byte) 40);
        service.saveUser("Василий", "Васечкин", (byte) 50);

        List<User> users = service.getAllUsers();
        users.forEach(System.out::println);

        service.cleanUsersTable();

        service.dropUsersTable();
    }
}

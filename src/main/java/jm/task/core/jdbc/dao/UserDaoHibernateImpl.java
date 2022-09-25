package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    @Transactional
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "                      `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "                      `name` VARCHAR(45) NULL,\n" +
                    "                      `lastName` VARCHAR(45) NULL,\n" +
                    "                      `age` TINYINT NULL,\n" +
                    "                      PRIMARY KEY (`id`),\n" +
                    "                      UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users;";
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.load(User.class, id));
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        // В задании написано, что методы создания и удаления таблицы должны быть написаны с использованием SQL.
        // Про очистку ничего не сказано. Какой-то встроенный функционал для очистки не нашёл, только SQL.
        // Читать всех пользователей, а потом каждого удалять, как-то не логично.
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "TRUNCATE TABLE users;";
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }
}

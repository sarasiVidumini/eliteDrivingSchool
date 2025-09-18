package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.UserDAO;
import lk.ijse.orm_final_coursework.entity.Instructor;
import lk.ijse.orm_final_coursework.entity.Student;
import lk.ijse.orm_final_coursework.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        String lastId = (String) session.createQuery(
                        "SELECT u.userId FROM User u ORDER BY u.userId DESC")
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1)); //
            num++;
            return String.format("U%03d", num);
        } else {
            return "U001";
        }
    }




    public List<User> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<User> query = session.createQuery("from User ",User.class);
            List<User> userList = query.list();
            return userList;
        }finally {
            session.close();
        }
    }

    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            Query<String> query = session.createQuery(
                    "SELECT u.userId FROM User u ORDER BY u.userId DESC ",
                    String.class
            ).setMaxResults(1);
            List<String> idList = query.list();
            if (idList.isEmpty()) {
                return null;
            }
            return idList.getFirst();
        }finally {
            session.close();
        }
    }

    public boolean save(User user) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(user);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean update(User user) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(user);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if(user != null) {
                session.remove(user);
                transaction.commit();
                return true;
            }
            return false;
        }catch (Exception e) {
           if (transaction != null) transaction.rollback();
           e.printStackTrace();
           return false;
        }finally {
            session.close();
        }
    }

    public Optional<User> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }finally {
            session.close();
        }
    }

    public List<User> search(String search) throws SQLException {
        String searchText = "%" + search + "%";

        try (Session session= factoryConfiguration.getSession()) {
            Query<User> query = session.createQuery(
                    "FROM User u" +
                            " WHERE u.userId LIKE  :search OR " +
                            "u.userName LIKE  :search OR " +
                            "u.password LIKE  :search OR " +
                            "u.role LIKE  :search OR " +
                            "u.email LIKE  :search OR " +
                            "u.status LIKE  :search" ,
                    User.class
            );
            query.setParameter("search",searchText);

            return query.list();
        }
    }

    public List<String> getAllIds() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT u.userId FROM User u", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }
}

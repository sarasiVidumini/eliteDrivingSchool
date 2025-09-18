package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.LessonDAO;
import lk.ijse.orm_final_coursework.entity.Instructor;
import lk.ijse.orm_final_coursework.entity.Lessons;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonDAOImpl implements LessonDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        String lastId = (String) session.createQuery(
                        "SELECT l.lessonId FROM Lessons l ORDER BY l.lessonId DESC")
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1));
            num++;
            return String.format("L%03d", num);
        } else {
            return "L001";
        }
    }

    @Override
    public List<Lessons> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Lessons> query = session.createQuery("from Lessons ",Lessons.class);
            List<Lessons> lessonsList = query.list();
            return lessonsList;
        }finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                        "SELECT l.lessonId FROM Lessons l ORDER BY l.lessonId DESC",
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

    @Override
    public boolean save(Lessons lessons) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(lessons);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Lessons lessons) throws SQLException {
       Session session = factoryConfiguration.getSession();
       Transaction transaction = session.beginTransaction();

        try {
            session.merge(lessons);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Lessons lessons = session.get(Lessons.class, id);
            if (lessons != null) {
                session.remove(lessons);
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

    @Override
    public Optional<Lessons> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            Lessons lessons = session.get(Lessons.class, id);
            return Optional.ofNullable(lessons);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Lessons> search(String search) throws SQLException {
       String searchText = "%" + search + "%";
       Session session = factoryConfiguration.getSession();

       try {
           Query<Lessons> query = session.createQuery(
                   "FROM Lessons l" +
                           " WHERE l.lessonId LIKE  :search OR" +
                           " l.lessonDate LIKE  :search OR " +
                           "l.startTime LIKE  :search OR" +
                           " l.endTime LIKE  :search OR " +
                           "l.status LIKE  :search  " ,
                   Lessons.class
           );
           query.setParameter("search", searchText);
           List<Lessons> lessonsList = query.getResultList();
           return lessonsList;
       }finally {
           session.close();
       }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT l.lessonId FROM Lessons l", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }
}

package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.LessonDAO;
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
    public String getNextId(Session session) throws SQLException {
        char tableCharacter = 'L';

        String lastId = session.createQuery(
                "SELECT l.id FROM Lessons l ORDER BY l.id DESC",
                String.class
        )
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);

            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableCharacter+"%d", nextIdNumber);
        }
        return tableCharacter + "001";
    }

    @Override
    public List<Lessons> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            List<Lessons> list = session.createQuery("FROM Lessons " , Lessons.class)
                    .getResultList();
            return list;
        }finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                        "SELECT l.id FROM Lessons l ORDER BY l.id DESC",
                    String.class
            ).setMaxResults(1);
            List<String> idList = query.list();
            if (idList.isEmpty()) {
                return null;
            }
            return idList.get(0);
        }finally {
            session.close();
        }
    }

    @Override
    public boolean save(Lessons lessons) throws SQLException {
        Session curentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            curentSession.persist(lessons);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Lessons lessons) throws SQLException {
        Session curentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            curentSession.merge(lessons);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Session curentSession = factoryConfiguration.getSession();

        try {
            Lessons lessons = curentSession.get(Lessons.class, id);
            if (lessons != null) {
                curentSession.delete(lessons);
                return true;
            }
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
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
                           " WHERE l.id LIKE  :search OR" +
                           " l.lessonDate LIKE  :search OR " +
                           "l.startTime LIKE  :search OR" +
                           " l.endTime LIKE  :search OR " +
                           "l.status LIKE  :search OR " +
                           "l.student LIKE  :search OR" +
                           " l.course LIKE  :search OR " +
                           "l.instructor LIKE  :search",
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
        Transaction transaction = null;
        List<String> idList = new ArrayList<>();

        try {
            Session session = factoryConfiguration.getSession().getSessionFactory().openSession();
            transaction = session.beginTransaction();

            idList = session.createQuery("SELECT l.id FROM Lessons l" , String.class).list();
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return idList;
    }
}

package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.InstructorDAO;
import lk.ijse.orm_final_coursework.entity.Instructor;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    public String getNextId()  throws SQLException {
        Session session = factoryConfiguration.getSession();
        char tableCharacter = 'I';

        String lastId = session.createQuery("SELECT i.id FROM Instructor i ORDER BY i.id DESC ",
                String.class
        )
                .setMaxResults(1)
                .uniqueResult();

        if(lastId!= null){
            String lastNumberString = lastId.substring(1);
            int lastNumber = Integer.parseInt(lastNumberString);

            int nextIdNumber = lastNumber + 1;
            return String.format(tableCharacter+"3%d", nextIdNumber);
        }
        return tableCharacter+"001";
    }

    public List<Instructor> getAll()  throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            List<Instructor> list = session.createQuery("from Instructor", Instructor.class)
                    .getResultList();
            return list;
        }finally {
            session.close();
        }
    }

    public String getLastId()  throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT i.id FROM Instructor i ORDER BY i.id DESC ",
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

    public boolean save(Instructor instructor)  throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.persist(instructor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Instructor instructor)  throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.merge(instructor);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id)  throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            Instructor instructor = currentSession.get(Instructor.class, id);
            if(instructor != null){
                currentSession.remove(instructor);
                return true;
            }
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllIds()  throws SQLException {
        Transaction transaction = null;
        List<String> idList = new ArrayList<>();

        try {
            Session session = factoryConfiguration.getSession().getSessionFactory().openSession();
            transaction = session.beginTransaction();

            idList = session.createQuery("SELECT i.id FROM Instructor i" , String.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return idList;
    }

    public Optional<Instructor> findById(String id)  throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            Instructor instructor = session.get(Instructor.class, id);
            return Optional.ofNullable(instructor);
        }finally {
            session.close();
        }
    }

    public List<Instructor> search(String search)  throws SQLException {
        String searchText = "%" + search + "%";
        Session session = factoryConfiguration.getSession();

        try {
            Query<Instructor> query= session.createQuery("FROM Instructor i" +
                    " WHERE i.id LIKE :searchText OR " +
                    "i.firstName LIKE  :search OR" +
                    " i.lastName LIKE  :search OR" +
                    " i.email LIKE  :search OR" +
                    " i.phone LIKE  :search OR " +
                    "i.specialization LIKE  :search OR " +
                    "i.availability_schedule LIKE  :search",
                    Instructor.class
            );
            query.setParameter("search", searchText);
            List<Instructor> instructorList = query.list();
            return instructorList;
        }finally {
            session.close();
        }
    }
}

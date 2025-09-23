package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.InstructorDAO;
import lk.ijse.orm_final_coursework.entity.Course;
import lk.ijse.orm_final_coursework.entity.Instructor;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Hibernate;
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
        String lastId = (String) session.createQuery(
                        "SELECT i.instructorId FROM Instructor i ORDER BY i.instructorId DESC")
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1));
            num++;
            return String.format("I%03d", num);
        } else {
            return "I001";
        }
    }

    public List<Instructor> getAll()  throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Instructor> query = session.createQuery("from Instructor ",Instructor.class);
            List<Instructor> instructorList = query.list();
            return instructorList;
        }finally {
            session.close();
        }
    }

    public String getLastId()  throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT i.instructorId FROM Instructor i ORDER BY i.instructorId DESC ",
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

    public boolean save(Instructor instructor)  throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(instructor);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Instructor instructor)  throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(instructor);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id)  throws SQLException {
       Session session = factoryConfiguration.getSession();
       Transaction transaction = session.beginTransaction();

        try {
            Instructor instructor = session.get(Instructor.class, id);
            if(instructor != null){
                session.remove(instructor);
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

    public List<String> getAllIds()  throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT i.instructorId FROM Instructor i", String.class);
            return query.list();
        } finally {
            session.close();
        }
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

    @Override
    public List<Instructor> search(String search) throws SQLException {
        String searchText = "%" + search + "%";

        try (Session session = factoryConfiguration.getSession()) {
            Query<Instructor> query = session.createQuery(
                    "FROM Instructor i " +
                            "WHERE i.instructorId LIKE :search OR " +
                            "i.firstName LIKE :search OR " +
                            "i.lastName LIKE :search OR " +
                            "i.email LIKE :search OR " +
                            "i.phone LIKE :search OR " +
                            "i.specialization LIKE :search OR " +
                            "i.availability_schedule LIKE :search",
                    Instructor.class
            );
            query.setParameter("search", searchText);

            return query.list();
        }
    }

    @Override
    public List<Course> getCoursesByInstructor(String instructorId) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = null;
        List<Course> courses = null;

        try {
            tx = session.beginTransaction();
            courses = session.createQuery(
                    "FROM Course c WHERE c.instructor.instructorId = :instructorId",
                    Course.class
            ).setParameter("instructorId", instructorId).list();

            for (Course c : courses) {
                Hibernate.initialize(c.getStudents());
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return courses;
    }


    public Instructor get(String instructorId) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Instructor instructor = null;

        try {
            transaction = session.beginTransaction();
            instructor = session.get(Instructor.class, instructorId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return instructor;
    }

}


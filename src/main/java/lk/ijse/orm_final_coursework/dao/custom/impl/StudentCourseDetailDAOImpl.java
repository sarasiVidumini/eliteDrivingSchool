package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.StudentCourseDetailDAO;
import lk.ijse.orm_final_coursework.entity.StudentCourseDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentCourseDetailDAOImpl implements StudentCourseDetailDAO {
    private FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId(Session session) throws SQLException {
       String tableString = "SC";

       String lastId = session.createQuery(
               "SELECT sc.id FROM StudentCourseDetail sc ORDER BY sc.id DESC ",
               String.class
       )
               .setMaxResults(1)
               .uniqueResult();

       if(lastId != null) {
          String lastNumberString = lastId.substring(1);
          int lastNumber = Integer.parseInt(lastNumberString);

          int nextIdNumber = lastNumber + 1;
          return  String.format(tableString + "%d", nextIdNumber);
       }
       return tableString + "001";
    }

    @Override
    public List<StudentCourseDetail> getAll() throws SQLException {
       Session session = factoryConfiguration.getSession();

       try {
           List<StudentCourseDetail> list = session.createQuery("FROM StudentCourseDetail" , StudentCourseDetail.class)
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
                    "SELECT sc.id FROM StudentCourseDetail sc ORDER BY  sc.id DESC ",
                    String.class
            ).setMaxResults(1);
            List<String> idList = query.list();
            if ((idList.isEmpty())) {
                return null;
            }
            return idList.get(0);
        }finally {
            session.close();
        }
    }

    @Override
    public boolean save(StudentCourseDetail studentCourseDetail) throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.persist(studentCourseDetail);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(StudentCourseDetail studentCourseDetail) throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.merge(studentCourseDetail);
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
       Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

       try {
           StudentCourseDetail studentCourseDetail = currentSession.get(StudentCourseDetail.class, id);
           if (studentCourseDetail != null) {
               currentSession.remove(studentCourseDetail);
               return true;
           }
           return false;
       }catch (Exception e) {
           e.printStackTrace();
           return false;
       }
    }

    @Override
    public Optional<StudentCourseDetail> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            StudentCourseDetail studentCourseDetail = session.get(StudentCourseDetail.class, id);
            return Optional.ofNullable(studentCourseDetail);
        }finally {
            session.close();
        }
    }

    @Override
    public List<StudentCourseDetail> search(String search) throws SQLException {
        String searchText = "%" + search + "%";
        Session session = factoryConfiguration.getSession();

        try {
            Query<StudentCourseDetail> query = session.createQuery(
                    "FROM StudentCourseDetail sc " +
                            "WHERE sc.id LIKE  :search OR" +
                            " sc.enrollmentDate LIKE :search OR" +
                            " sc.status LIKE  :search OR" +
                            " sc.grade LIKE  :search OR" +
                            " sc.student LIKE  :search OR" +
                            " sc.course LIKE  :search " ,
                    StudentCourseDetail.class
            );
            query.setParameter("search", searchText);
            List<StudentCourseDetail> studentCourseDetailList = query.getResultList();
            return studentCourseDetailList;
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

            idList = session.createQuery("SELECT sc.id FROM StudentCourseDetail sc" , String.class).list();
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return idList;
    }


}

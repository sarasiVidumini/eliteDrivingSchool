package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.dao.SQLUtil;
import lk.ijse.orm_final_coursework.dao.custom.StudentDAO;
import org.hibernate.query.Query;
import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {
       private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

       public String getNextId() throws SQLException {
           Session session = factoryConfiguration.getSession();
           char tableCharacter = 'S';

           String lastId = session.createQuery(
                   "SELECT  s.id FROM Student s ORDER BY s.id DESC ",
                   String.class
           )
                   .setMaxResults(1)
                   .uniqueResult();

           if (lastId !=null){
               String lastNumberString = lastId.substring(1);
               int lastNumber = Integer.parseInt(lastNumberString);

               int nextIdNumber = lastNumber + 1;
               return  String.format(tableCharacter + "3%d", nextIdNumber);
           }

           return tableCharacter + "001";
       }

    @Override
    public List<Student> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
//               List<Student> studentList = query.list();
//               return studentList;
            List<Student> list = session.createQuery("FROM Student" , Student.class)
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
                    "SELECT s.id FROM Student s ORDER BY s.id DESC" ,
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
    public boolean save(Student student) throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.persist(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Student student) throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.merge(student);
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
            Student student = currentSession.get(Student.class, id);
            if (student != null) {
                currentSession.remove(student);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
       public List<String> getAllIds() throws SQLException{
           Transaction transaction = null;
           List<String> idList = new ArrayList<>();

           try {
               Session session = factoryConfiguration.getSession().getSessionFactory().openSession();
               transaction = session.beginTransaction();

               idList = session.createQuery("SELECT s.id FROM Student s" , String.class).list();
               transaction.commit();
           }catch (Exception e) {
               if (transaction != null) transaction.rollback();{
                   e.printStackTrace();
               }

           }

           return idList;

       }

       @Override
       public Optional<Student> findById(String id) throws SQLException {
           Session session = factoryConfiguration.getSession();

           try{
               Student student = session.get(Student.class, id);
               return Optional.ofNullable(student);
           }finally {
               session.close();
           }
       }

       @Override
       public List<Student>  search(String search) throws SQLException {
           String searchText = "%" + search + "%";
           Session session = factoryConfiguration.getSession();

           try {
              Query<Student> query = session.createQuery(
                      "FROM Student s " +
                              "WHERE s.id LIKE  :search OR" +
                              " s.firstName LIKE  :search OR " +
                              " s.lastName LIKE  :search OR " +
                              "s.email LIKE  :search OR " +
                              "s.phone LIKE  : search OR " +
                              "s.address LIKE  :search" ,
                      Student.class
              );
              query.setParameter("search", searchText);
              List<Student> studentList= query.list();
              return studentList;
           }finally {
               session.close();
           }
       }

@Override
    public String getStudentIdByContact(String phone) throws SQLException {
           try {
               Session session = factoryConfiguration.getSession().getSessionFactory().openSession();
              String hql = "SELECT s.id FROM Student s WHERE s.phone =  :contact";

               String studentId = session.createQuery(hql , String.class).setParameter("contact", phone).uniqueResult();

               if (studentId !=null){
                   return studentId;
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           return "Student Not Found";
       }

@Override
       public String getStudentFirstNameById(String studentId) throws SQLException {
           try {
               Session session = factoryConfiguration.getSession().getSessionFactory().openSession();
               String hql = "SELECT s.firstName FROM Student s WHERE s.id = :id";

               String firstName = session.createQuery(hql , String.class).setParameter("id", studentId).uniqueResult();

               if (firstName !=null){
                   return firstName;
               }
           }catch (Exception e) {
               e.printStackTrace();
           }
           return "Student Not Found";
       }

       @Override
    public String getStudentLastNameById(String studentId) throws SQLException {
        try {
            Session session = factoryConfiguration.getSession().getSessionFactory().openSession();
            String hql = "SELECT s.lastName FROM Student s WHERE s.id = :id";

            String lastName = session.createQuery(hql , String.class).setParameter("id", studentId).uniqueResult();

            if (lastName !=null){
                return lastName;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "Student Not Found";
    }


}

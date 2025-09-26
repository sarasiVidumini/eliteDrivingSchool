package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.dao.custom.StudentDAO;
import org.hibernate.query.Query;
import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {
       private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

       public String getNextId() throws SQLException {
           Session session = factoryConfiguration.getSession();
           String lastId = (String) session.createQuery(
                           "SELECT s.studentId FROM Student s ORDER BY s.studentId DESC")
                   .setMaxResults(1)
                   .uniqueResult();

           if (lastId != null) {
               int num = Integer.parseInt(lastId.substring(1)); // remove 'S' prefix
               num++;
               return String.format("S%03d", num);
           } else {
               return "S001";
           }
       }

    @Override
    public List<Student> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Student> query = session.createQuery("from Student ",Student.class);
            List<Student> studentList = query.list();
            return studentList;
        }finally {
            session.close();
        }

    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT s.studentId FROM Student s ORDER BY s.studentId DESC" ,
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
    public boolean save(Student student) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(student);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Student student) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(student);
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
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.remove(student);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }

    }

    @Override
       public List<String> getAllIds() throws SQLException{
            Session session = factoryConfiguration.getSession();
            try {
                Query<String> query = session.createQuery("SELECT s.studentId FROM Student s", String.class);
                return query.list();
            } finally {
                session.close();
            }
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

           try (Session session = factoryConfiguration.getSession()) {
              Query<Student> query = session.createQuery(
                      "FROM Student s " +
                              "WHERE s.studentId LIKE  :search OR" +
                              " s.firstName LIKE  :search OR " +
                              " s.lastName LIKE  :search OR " +
                              "s.email LIKE  :search OR " +
                              "s.phone LIKE  : search OR " +
                              "s.address LIKE  :search" ,
                      Student.class
              );
             query.setParameter("search", searchText);

             return query.list();
           }
       }

@Override
    public String getStudentIdByContact(String phone) throws SQLException {
           try {
               Session session = factoryConfiguration.getSession();
              String hql = "SELECT s.studentId FROM Student s WHERE s.phone =  :contact";

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
               Session session = factoryConfiguration.getSession();
               String hql = "SELECT s.firstName FROM Student s WHERE s.studentId = :studentId";

               String firstName = session.createQuery(hql , String.class).setParameter("studentId", studentId).uniqueResult();

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
            Session session = factoryConfiguration.getSession();
            String hql = "SELECT s.lastName FROM Student s WHERE s.studentId= :studentId";

            String lastName = session.createQuery(hql , String.class).setParameter("studentId", studentId).uniqueResult();

            if (lastName !=null){
                return lastName;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "Student Not Found";
    }

    @Override
    public Student get(String studentId) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Student student = session.get(Student.class, studentId);
        session.close();
        return student;
    }


}

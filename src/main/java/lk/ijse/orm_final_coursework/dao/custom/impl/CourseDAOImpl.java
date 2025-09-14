package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.CourseDAO;
import lk.ijse.orm_final_coursework.entity.Course;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    public String getNextId(Session session) {
        char tableCharacter = 'C';

        String lastId = session.createQuery(
                "SELECT c.id FROM Course c ORDER BY c.id DESC " ,
                String.class
        )
                .setMaxResults(1)
                .getSingleResult();

        if (lastId != null) {
            String lastNumberString = lastId.substring(1);
            int lastNumber = Integer.parseInt(lastNumberString);

            int nextIdNumber = lastNumber + 1;
            return String.format(tableCharacter+"3%d", nextIdNumber);
        }

        return tableCharacter+"001";
    }

    public List<Course> getAll()throws SQLException{
        Session session = factoryConfiguration.getSession();
        try {
            List<Course> list = session.createQuery("FROM Course" , Course.class).getResultList();
            return list;
        }finally {
            session.close();
        }
    }

    public String getLastId(){
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT c.id FROM Course c ORDER BY c.id DESC " ,
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

    public boolean save(Course course)throws SQLException{
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();
        try {
            currentSession.persist(course);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(Course course)throws SQLException{
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.merge(course);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id)throws SQLException{
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            Student student = currentSession.get(Student.class, id);
            if (student != null) {
                currentSession.remove(student);
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllIds()throws SQLException{
        Transaction transaction = null;
        List<String> idList = new ArrayList<>();

        try {
            Session session = factoryConfiguration.getSession();
            transaction = session.beginTransaction();

            idList = session.createQuery("SELECT c.id FROM Course c" , String.class ).list();
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return idList;

    }

    public Optional<Course> findById(String id)throws SQLException{
        Session session = factoryConfiguration.getSession();

        try {
            Course course = session.get(Course.class, id);
            return Optional.ofNullable(course);
        }finally {
            session.close();
        }
    }

    public List<Course> search(String search)throws SQLException{
        String searchText="%"+search+"%";
        Session session = factoryConfiguration.getSession();

        try {
            Query<Course> query = session.createQuery("FROM Course c " +
                    "WHERE c.id LIKE :search OR " + "c.courseName LIKE  :search OR " + " c.duration LIKE  :search OR" + " c.fee LIKE  :search OR" + " c.description LIKE  :search", Course.class
            );
            query.setParameter("search",searchText);
            List<Course> courseList = query.getResultList();
            return courseList;
        }finally {
            session.close();
        }
    }

    public boolean saveNewCourse(Course course)throws SQLException{
        Session currentSession = factoryConfiguration.getSession();

        try {
            currentSession.persist(course);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

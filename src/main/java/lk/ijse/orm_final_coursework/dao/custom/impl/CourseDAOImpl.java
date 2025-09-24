package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.CourseDAO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.entity.Course;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() {
        try (Session session = factoryConfiguration.getSession()) {
            String lastId = (String) session.createQuery(
                            "SELECT c.courseId FROM Course c ORDER BY c.courseId DESC")
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("C%03d", num);
            } else {
                return "C001";
            }
        }
    }

    @Override
    public List<Course> getAll() {
        try (Session session = factoryConfiguration.getSession()) {
            Query<Course> query = session.createQuery(
                    "SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.students LEFT JOIN FETCH c.instructor",
                    Course.class
            );
            return query.list();
        }
    }

    @Override
    public String getLastId() throws SQLException {
        return "";
    }

    @Override
    public List<String> getAllIds() {
        try (Session session = factoryConfiguration.getSession()) {
            Query<String> query = session.createQuery("SELECT c.courseId FROM Course c", String.class);
            return query.list();
        }
    }

    @Override
    public Optional<Course> findById(String id) {
        try (Session session = factoryConfiguration.getSession()) {
            Course course = session.get(Course.class, id);
            if (course != null) Hibernate.initialize(course.getStudents());
            return Optional.ofNullable(course);
        }
    }

    @Override
    public boolean save(Course course) {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();
            session.persist(course);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Course course) {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();
            session.merge(course);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                session.remove(course);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<Course> search(String search)throws SQLException{
        String searchText="%"+search+"%";
        try(Session session = factoryConfiguration.getSession()) {
            Query<Course> query = session.createQuery(
                    "FROM Course c " +
                            "WHERE c.courseId LIKE :search OR " +
                            "c.courseName LIKE :search OR " +
                            " c.duration LIKE :search OR" +
                            " c.fee LIKE :search OR" +
                            " c.description LIKE :search "
                    , Course.class );
            query.setParameter("search", searchText);
            return query.list();
        }
    }


    @Override
    public int getEnrollmentCount(String courseId) {
        try (Session session = factoryConfiguration.getSession()) {
            Course course = session.get(Course.class, courseId);
            if (course != null) {
                Hibernate.initialize(course.getStudents()); // ensure students loaded
                return course.getStudents().size();
            }
            return 0;
        }
    }

    @Override
    public boolean enrollStudent(String studentId, String courseId) throws Exception {
        Transaction tx = null;
        try (Session session = factoryConfiguration.getSession()) {
            tx = session.beginTransaction();

            Student student = session.get(Student.class, studentId);
            Course course = session.get(Course.class, courseId);

            if (student == null || course == null) return false;

            Hibernate.initialize(student.getCourses());
            Hibernate.initialize(course.getStudents());


            if (!student.getCourses().contains(course)) {
                student.getCourses().add(course);
            }
            if (!course.getStudents().contains(student)) {
                course.getStudents().add(student);
            }

            session.merge(student);
            session.merge(course);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Course get(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Course course = session.get(Course.class, id);
        session.close();
        return course;
    }

    @Override
    public CourseDTO getCourseByName(String courseName) throws Exception {
        // Open Hibernate session
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            session.beginTransaction();

            // HQL query to fetch Course entity by courseName
            String hql = "FROM Course c WHERE c.courseName = :name";
            Course courseEntity = session.createQuery(hql, Course.class)
                    .setParameter("name", courseName)
                    .uniqueResult();

            session.getTransaction().commit();

            if (courseEntity != null) {
                // Convert Course entity to CourseDTO
                return CourseDTO.builder()
                        .courseId(courseEntity.getCourseId())
                        .courseName(courseEntity.getCourseName())
                        .duration(courseEntity.getDuration())
                        .fee(courseEntity.getFee())
                        .description(courseEntity.getDescription())
                        .instructorId(courseEntity.getInstructorId())
                        .enrollmentCount(courseEntity.getEnrollmentCount())
                        .lessons(new ArrayList<>()) // You can map lessons if needed
                        .build();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get course by name: " + e.getMessage(), e);
        }
    }

}

package lk.ijse.orm_final_coursework.dao.custom;

import lk.ijse.orm_final_coursework.dao.CrudDAO;
import lk.ijse.orm_final_coursework.entity.Course;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Optional;

public interface CourseDAO extends CrudDAO<Course> {
    public int getEnrollmentCount(String courseId) throws Exception;
    public boolean enrollStudent(String courseId, String studentId) throws Exception;
}

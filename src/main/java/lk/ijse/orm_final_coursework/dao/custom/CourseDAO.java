package lk.ijse.orm_final_coursework.dao.custom;

import lk.ijse.orm_final_coursework.dao.CrudDAO;
import lk.ijse.orm_final_coursework.entity.Course;
import org.hibernate.Session;

import java.sql.SQLException;

public interface CourseDAO extends CrudDAO<Course> {
    public boolean saveNewCourse(Course course)throws SQLException;
}

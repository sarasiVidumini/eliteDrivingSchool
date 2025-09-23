package lk.ijse.orm_final_coursework.bo.custom;

import lk.ijse.orm_final_coursework.bo.SuperBO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.dto.InstructorDTO;
import lk.ijse.orm_final_coursework.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface InstructorBO extends SuperBO {
    public String getNextId()  throws SQLException;
    public List<InstructorDTO> getAll()  throws SQLException;
    public String getLastId()  throws SQLException;
    public boolean save(InstructorDTO instructorDTO)  throws SQLException;
    public boolean update(InstructorDTO instructorDTO)  throws SQLException;
    public boolean delete(String id)  throws SQLException;
    public List<String> getAllIds()  throws SQLException;
    public Optional<InstructorDTO> findById(String id)  throws SQLException;
    public List<InstructorDTO> search(String search)  throws SQLException;
    public boolean assignCourse(String instructorId, String courseId) throws Exception;
    public List<CourseDTO>  getCoursesByInstructor(String instructorId)  throws SQLException;
    public int getEnrollmentCount(String courseId) throws Exception;

}

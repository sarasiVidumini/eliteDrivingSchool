package lk.ijse.orm_final_coursework.dao.custom;

import lk.ijse.orm_final_coursework.dao.CrudDAO;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    public List<String> getAllIds() throws SQLException;
    public String getStudentIdByContact(String phone) throws SQLException;
    public String getStudentFirstNameById(String studentId) throws SQLException;
    public String getStudentLastNameById(String studentId) throws SQLException;
}

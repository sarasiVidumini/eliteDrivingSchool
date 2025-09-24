package lk.ijse.orm_final_coursework.bo.custom;

import lk.ijse.orm_final_coursework.bo.SuperBO;
import lk.ijse.orm_final_coursework.dto.StudentDTO;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface StudentBO extends SuperBO {

    public String getNextId() throws SQLException;
    public List<StudentDTO> getAll() throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(StudentDTO studentDTO) throws SQLException;
    public boolean update(StudentDTO studentDTO) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public List<String> getAllIds() throws SQLException;
    public Optional<StudentDTO> findById(String id) throws SQLException;
    public List<StudentDTO>  search(String search) throws SQLException;
    public String getStudentIdByContact(String phone) throws SQLException;
    public String getStudentFirstNameById(String studentId) throws SQLException;
    public String getStudentLastNameById(String studentId) throws SQLException;
    public StudentDTO getStudentById(String studentId) throws Exception;


}

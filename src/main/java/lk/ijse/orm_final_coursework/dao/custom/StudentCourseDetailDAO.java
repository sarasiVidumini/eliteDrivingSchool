package lk.ijse.orm_final_coursework.dao.custom;

import lk.ijse.orm_final_coursework.dao.CrudDAO;
import lk.ijse.orm_final_coursework.entity.Payment;
import lk.ijse.orm_final_coursework.entity.StudentCourseDetail;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StudentCourseDetailDAO {
    public String getNextId() throws SQLException;
    public List<StudentCourseDetail> getAll() throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(StudentCourseDetail t) throws SQLException;
    public boolean update(StudentCourseDetail t) throws SQLException;
    public boolean delete(Long id ) throws SQLException;
    public List<StudentCourseDetail>  search(String search) throws SQLException;
    public List<String> getAllIds() throws SQLException;
    public Optional<StudentCourseDetail> findById(Long id) throws SQLException;
}

package lk.ijse.orm_final_coursework.bo.custom;

import lk.ijse.orm_final_coursework.bo.SuperBO;
import lk.ijse.orm_final_coursework.dto.StudentCourseDetailDTO;
import lk.ijse.orm_final_coursework.entity.StudentCourseDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface StudentCourseDetailBO extends SuperBO {

    public String getNextId() throws SQLException;
    public List<StudentCourseDetailDTO> getAll() throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(StudentCourseDetailDTO studentCourseDetailDTO) throws SQLException;
    public boolean update(StudentCourseDetailDTO studentCourseDetailDTO) throws SQLException;
    public boolean delete(Long id) throws SQLException;
    public Optional<StudentCourseDetailDTO> findById(Long id) throws SQLException;
    public List<StudentCourseDetailDTO> search(String search) throws SQLException;
    public List<String> getAllIds() throws SQLException;

}

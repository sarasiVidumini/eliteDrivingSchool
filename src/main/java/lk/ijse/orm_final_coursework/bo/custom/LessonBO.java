package lk.ijse.orm_final_coursework.bo.custom;

import lk.ijse.orm_final_coursework.bo.SuperBO;
import lk.ijse.orm_final_coursework.dto.LessonsDTO;
import lk.ijse.orm_final_coursework.entity.Lessons;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface LessonBO extends SuperBO {
    public String getNextId() throws SQLException;
    public List<LessonsDTO> getAll() throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(LessonsDTO lessonsDTO) throws SQLException;
    public boolean update(LessonsDTO lessonsDTO) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public Optional<LessonsDTO> findById(String id) throws SQLException;
    public List<LessonsDTO> search(String search) throws SQLException;
    public List<String> getAllIds() throws SQLException;
    public boolean scheduleLesson(LessonsDTO lessonsDTO) throws SQLException;
    public boolean rescheduleLesson(String lessonId , LessonsDTO dto) throws SQLException;
}

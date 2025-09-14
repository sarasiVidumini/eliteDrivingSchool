package lk.ijse.orm_final_coursework.bo.custom.Impl;

import lk.ijse.orm_final_coursework.bo.custom.CourseBO;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.DuplicateException;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.NotFoundException;
import lk.ijse.orm_final_coursework.bo.utils.EntityDTOConverter;
import lk.ijse.orm_final_coursework.dao.DAOFactory;
import lk.ijse.orm_final_coursework.dao.DAOTypes;
import lk.ijse.orm_final_coursework.dao.custom.CourseDAO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.entity.Course;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseBOImpl implements CourseBO {
    private final CourseDAO courseDAO =(CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public String getNextId(Session session) throws SQLException {
        return courseDAO.getNextId(session);
    }

    @Override
    public List<CourseDTO> getAll() throws SQLException {
        List<Course> courses = courseDAO.getAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (Course course : courses) {
            courseDTOs.add(converter.getCourseDTO(course));
        }
        return courseDTOs;
    }

    @Override
    public String getLastId() throws SQLException {
        return courseDAO.getLastId();
    }

    @Override
    public boolean save(CourseDTO courseDTO) throws SQLException {
        Optional<Course> course = courseDAO.findById(courseDTO.getCourseId());
        if (course.isPresent()) {
            throw new DuplicateException("course already exist");
        }

        return courseDAO.save(converter.getCourse(courseDTO));
    }

    @Override
    public boolean update(CourseDTO courseDTO) throws SQLException {
        Optional<Course> course = courseDAO.findById(courseDTO.getCourseId());
        if (course.isEmpty()){
            throw new NotFoundException("Course not found");
        }
        return courseDAO.update(converter.getCourse(courseDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<Course> course = courseDAO.findById(id);
        if (course.isEmpty()){
            throw new NotFoundException("Course not found");
        }
        return courseDAO.delete(id);
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return courseDAO.getAllIds();
    }

    @Override
    public Optional<CourseDTO> findById(String id) throws SQLException {
        Optional<Course> course = courseDAO.findById(id);
        if (course.isPresent()){
            return Optional.of(converter.getCourseDTO(course.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<CourseDTO> search(String search) throws SQLException {
        ArrayList<Course> courses = (ArrayList<Course>) courseDAO.search(search);
        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (Course course : courses) {
            courseDTOs.add(converter.getCourseDTO(course));
        }
        return courseDTOs;
    }

    @Override
    public boolean saveNewCourse(CourseDTO courseDTO) throws SQLException {
        Optional<Course> course = courseDAO.findById(courseDTO.getCourseId());
        if (course.isPresent()) {
            throw new DuplicateException("course already exist");
        }

        return courseDAO.save(converter.getCourse(courseDTO));
    }
}

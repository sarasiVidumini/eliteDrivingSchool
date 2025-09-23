package lk.ijse.orm_final_coursework.bo.custom.Impl;

import lk.ijse.orm_final_coursework.bo.custom.CourseBO;
import lk.ijse.orm_final_coursework.bo.custom.InstructorBO;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.DuplicateException;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.NotFoundException;
import lk.ijse.orm_final_coursework.bo.utils.EntityDTOConverter;
import lk.ijse.orm_final_coursework.dao.DAOFactory;
import lk.ijse.orm_final_coursework.dao.DAOTypes;
import lk.ijse.orm_final_coursework.dao.custom.CourseDAO;
import lk.ijse.orm_final_coursework.dao.custom.InstructorDAO;
import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.dto.InstructorDTO;
import lk.ijse.orm_final_coursework.entity.Course;
import lk.ijse.orm_final_coursework.entity.Instructor;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InstructorBOImpl implements InstructorBO {
    private final InstructorDAO instructorDAO = DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTOR);
    private final CourseDAO courseDAO = DAOFactory.getInstance().getDAO(DAOTypes.COURSE);
    private final EntityDTOConverter converter = new EntityDTOConverter();
    @Override
    public String getNextId() throws SQLException {
        return instructorDAO.getNextId();
    }

    @Override
    public List<InstructorDTO> getAll() throws SQLException {
        List<Instructor> instructors = instructorDAO.getAll();
        List<InstructorDTO> instructorDTOS = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorDTOS.add(converter.getInstructorDTO(instructor));
        }
        return instructorDTOS;
    }

    @Override
    public String getLastId() throws SQLException {
        return instructorDAO.getLastId();
    }

    @Override
    public boolean save(InstructorDTO instructorDTO) throws SQLException {
        Optional<Instructor> instructor = instructorDAO.findById(instructorDTO.getInstructorId());
        if (instructor.isPresent()) {
            throw new DuplicateException("instructor already exists");
        }
        return instructorDAO.save(converter.getInstructor(instructorDTO));
    }

    @Override
    public boolean update(InstructorDTO instructorDTO) throws SQLException {
        Optional<Instructor> instructor = instructorDAO.findById(instructorDTO.getInstructorId());
        if(instructor.isEmpty()){
            throw new NotFoundException("instructor not found");
        }

        return instructorDAO.update(converter.getInstructor(instructorDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<Instructor> instructor = instructorDAO.findById(id);
        if(instructor.isEmpty()){
            throw new NotFoundException("instructor not found");
        }
        return instructorDAO.delete(id);
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return instructorDAO.getAllIds();
    }

    @Override
    public Optional<InstructorDTO> findById(String id) throws SQLException {
       Optional<Instructor> instructor = instructorDAO.findById(id);
       if (instructor.isPresent()) {
           return Optional.of(converter.getInstructorDTO(instructor.get()));
       }
       return Optional.empty();
    }

    @Override
    public List<InstructorDTO> search(String search) throws SQLException {
       ArrayList<Instructor> instructors = (ArrayList<Instructor>) instructorDAO.search(search);
       List<InstructorDTO> instructorDTOS = new ArrayList<>();
       for (Instructor instructor : instructors) {
           instructorDTOS.add(converter.getInstructorDTO(instructor));
       }
       return instructorDTOS;
    }

    public boolean assignCourse(String instructorId, String courseId) throws Exception {
        Course course = courseDAO.get(courseId);
        if (course != null) {
            Instructor instructor = instructorDAO.get(instructorId);
            if (instructor == null) {
                throw new RuntimeException("Instructor not found with ID: " + instructorId);
            }

            course.setInstructor(instructor);
            return courseDAO.update(course);
        }
        return false;
    }


    @Override
    public List<CourseDTO> getCoursesByInstructor(String instructorId) throws SQLException {
        List<Course> courses = instructorDAO.getCoursesByInstructor(instructorId);
        return courses.stream().map(converter::getCourseDTO).collect(Collectors.toList());
    }

    @Override
    public int getEnrollmentCount(String courseId) throws Exception {
       return courseDAO.getEnrollmentCount(courseId);
    }

}

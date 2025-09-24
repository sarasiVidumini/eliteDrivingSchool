package lk.ijse.orm_final_coursework.bo.custom.Impl;

import lk.ijse.orm_final_coursework.bo.custom.StudentBO;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.DuplicateException;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.NotFoundException;
import lk.ijse.orm_final_coursework.bo.utils.EntityDTOConverter;
import lk.ijse.orm_final_coursework.dao.DAOFactory;
import lk.ijse.orm_final_coursework.dao.DAOTypes;
import lk.ijse.orm_final_coursework.dao.custom.StudentDAO;
import lk.ijse.orm_final_coursework.dto.StudentDTO;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentBOImpl implements StudentBO {
        private final StudentDAO studentDAO =(StudentDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENT);
        private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public String getNextId() throws SQLException {
        return studentDAO.getNextId();
    }

    @Override
    public List<StudentDTO> getAll() throws SQLException {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(converter.getStudentDTO(student));
        }
        return studentDTOS;
    }

    @Override
    public String getLastId() throws SQLException {
        return studentDAO.getLastId();
    }

    @Override
    public boolean save(StudentDTO studentDTO) throws SQLException {
        Optional<Student> student = studentDAO.findById(studentDTO.getStudentId());
        if (student.isPresent()) {
            throw new DuplicateException("Student already exists");
        }
        return studentDAO.save(converter.getStudent(studentDTO));
    }

    @Override
    public boolean update(StudentDTO studentDTO) throws SQLException {
        Optional<Student> student = studentDAO.findById(studentDTO.getStudentId());
        if (student.isEmpty()){
            throw new NotFoundException("Student not found");
        }
        return studentDAO.update(converter.getStudent(studentDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<Student> student = studentDAO.findById(id);
        if (student.isEmpty()){
            throw new NotFoundException("Student not found");
        }
        return studentDAO.delete(id);
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return studentDAO.getAllIds();
    }

    @Override
    public Optional<StudentDTO> findById(String id) throws SQLException {
        Optional<Student> student = studentDAO.findById(id);
        if (student.isPresent()){
            return Optional.of(converter.getStudentDTO(student.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<StudentDTO> search(String search) throws SQLException {
        ArrayList<Student> students = (ArrayList<Student>) studentDAO.search(search);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(converter.getStudentDTO(student));
        }
        return studentDTOS;
    }

    @Override
    public String getStudentIdByContact(String phone) throws SQLException {
        return studentDAO.getStudentIdByContact(phone);
    }

    @Override
    public String getStudentFirstNameById(String studentId) throws SQLException {
        return studentDAO.getStudentFirstNameById(studentId);
    }

    @Override
    public String getStudentLastNameById(String studentId) throws SQLException {
        return studentDAO.getStudentLastNameById(studentId);
    }


    @Override
    public StudentDTO getStudentById(String studentId) throws Exception {
        // Fetch Student entity from DAO
        Student studentEntity = studentDAO.get(studentId); // studentDAO.get() should return Student entity by ID
        if (studentEntity == null) {
            return null;
        }

        // Map entity to DTO
        return StudentDTO.builder()
                .studentId(studentEntity.getStudentId())
                .firstName(studentEntity.getFirstName())
                .lastName(studentEntity.getLastName())
                .email(studentEntity.getEmail())
                .phone(studentEntity.getPhone())
                .address(studentEntity.getAddress())
                .dob(studentEntity.getDob())
                .registrationDate(studentEntity.getRegistrationDate())

                .build();
    }


}

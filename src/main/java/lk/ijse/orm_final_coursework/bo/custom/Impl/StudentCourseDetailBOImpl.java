package lk.ijse.orm_final_coursework.bo.custom.Impl;

import lk.ijse.orm_final_coursework.bo.custom.StudentCourseDetailBO;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.DuplicateException;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.NotFoundException;
import lk.ijse.orm_final_coursework.bo.utils.EntityDTOConverter;
import lk.ijse.orm_final_coursework.dao.DAOFactory;
import lk.ijse.orm_final_coursework.dao.DAOTypes;
import lk.ijse.orm_final_coursework.dao.custom.StudentCourseDetailDAO;
import lk.ijse.orm_final_coursework.dto.StudentCourseDetailDTO;
import lk.ijse.orm_final_coursework.entity.StudentCourseDetail;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentCourseDetailBOImpl implements StudentCourseDetailBO {
    private final StudentCourseDetailDAO studentCourseDetailDAO = (StudentCourseDetailDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENTCOURSEDETAIL);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public String getNextId(Session session) throws SQLException {
        return studentCourseDetailDAO.getNextId(session);
    }

    @Override
    public List<StudentCourseDetailDTO> getAll() throws SQLException {
        List<StudentCourseDetail> studentCourseDetails = studentCourseDetailDAO.getAll();
        List<StudentCourseDetailDTO> studentCourseDetailDTOS = new ArrayList<>();
        for (StudentCourseDetail studentCourseDetail : studentCourseDetails) {
            studentCourseDetailDTOS.add(converter.getStudentCourseDetailDTO(studentCourseDetail));
        }
        return studentCourseDetailDTOS;
    }

    @Override
    public String getLastId() throws SQLException {
        return studentCourseDetailDAO.getLastId();
    }

    @Override
    public boolean save(StudentCourseDetailDTO studentCourseDetailDTO) throws SQLException {
       Optional<StudentCourseDetail> studentCourseDetail = studentCourseDetailDAO.findById(studentCourseDetailDTO.getStudentCourseId());
       if (studentCourseDetail.isPresent()) {
           throw new DuplicateException("Student course detail already exist");
       }
       return studentCourseDetailDAO.save(converter.getStudentCourseDetail(studentCourseDetailDTO));
    }

    @Override
    public boolean update(StudentCourseDetailDTO studentCourseDetailDTO) throws SQLException {
        Optional<StudentCourseDetail> studentCourseDetail = studentCourseDetailDAO.findById(studentCourseDetailDTO.getStudentCourseId());
        if (studentCourseDetail.isEmpty()){
            throw new NotFoundException("Student course detail not found");
        }
        return studentCourseDetailDAO.update(converter.getStudentCourseDetail(studentCourseDetailDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<StudentCourseDetail> studentCourseDetail = studentCourseDetailDAO.findById(id);
        if (studentCourseDetail.isEmpty()){
            throw new NotFoundException("Student course detail not found");
        }
        return studentCourseDetailDAO.delete(id);
    }

    @Override
    public Optional<StudentCourseDetailDTO> findById(String id) throws SQLException {

        Optional<StudentCourseDetail> studentCourseDetail = studentCourseDetailDAO.findById(id);
        if (studentCourseDetail.isPresent()){
            return Optional.of(converter.getStudentCourseDetailDTO(studentCourseDetail.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<StudentCourseDetailDTO> search(String search) throws SQLException {
        ArrayList<StudentCourseDetail> studentCourseDetails = (ArrayList<StudentCourseDetail>) studentCourseDetailDAO.search(search);
        List<StudentCourseDetailDTO> studentCourseDetailDTOS = new ArrayList<>();
        for (StudentCourseDetail studentCourseDetail : studentCourseDetails) {
            studentCourseDetailDTOS.add(converter.getStudentCourseDetailDTO(studentCourseDetail));
        }
        return studentCourseDetailDTOS;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return studentCourseDetailDAO.getAllIds();
    }
}

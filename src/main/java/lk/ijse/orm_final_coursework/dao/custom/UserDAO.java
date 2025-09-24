package lk.ijse.orm_final_coursework.dao.custom;

import lk.ijse.orm_final_coursework.dao.CrudDAO;
import lk.ijse.orm_final_coursework.dto.UserDTO;
import lk.ijse.orm_final_coursework.entity.Student;
import lk.ijse.orm_final_coursework.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO extends CrudDAO<User> {
    public User getUserByName(String userName) throws SQLException;

}

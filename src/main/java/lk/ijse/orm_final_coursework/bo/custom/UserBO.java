package lk.ijse.orm_final_coursework.bo.custom;

import lk.ijse.orm_final_coursework.bo.SuperBO;
import lk.ijse.orm_final_coursework.dto.UserDTO;
import lk.ijse.orm_final_coursework.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserBO extends SuperBO {

    public String getNextId() throws SQLException;
    public List<UserDTO> getAll() throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(UserDTO userDTO) throws SQLException;
    public boolean update(UserDTO userDTO) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public Optional<UserDTO> findById(String id) throws SQLException;
    public List<UserDTO> search(String search) throws SQLException;
    public List<String> getAllIds() throws SQLException;

}

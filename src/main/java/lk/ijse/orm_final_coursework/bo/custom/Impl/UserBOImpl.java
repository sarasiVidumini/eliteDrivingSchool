package lk.ijse.orm_final_coursework.bo.custom.Impl;

import lk.ijse.orm_final_coursework.bo.custom.UserBO;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.DuplicateException;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.NotFoundException;
import lk.ijse.orm_final_coursework.bo.utils.EntityDTOConverter;
import lk.ijse.orm_final_coursework.dao.DAOFactory;
import lk.ijse.orm_final_coursework.dao.DAOTypes;
import lk.ijse.orm_final_coursework.dao.custom.UserDAO;
import lk.ijse.orm_final_coursework.dto.UserDTO;
import lk.ijse.orm_final_coursework.entity.User;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOTypes.USER);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public String getNextId() throws SQLException {
        return userDAO.getNextId();
    }

    @Override
    public List<UserDTO> getAll() throws SQLException {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(converter.getUserDTO(user));
        }
        return userDTOS;
    }

    @Override
    public String getLastId() throws SQLException {
        return userDAO.getLastId();
    }

    @Override
    public boolean save(UserDTO userDTO) throws SQLException {
        Optional<User> user = userDAO.findById(userDTO.getUserId());
        if (user.isPresent()) {
            throw new DuplicateException("User already exists");
        }
        return userDAO.save(converter.getUser(userDTO));
    }

    @Override
    public boolean update(UserDTO userDTO) throws SQLException {
       Optional<User> user = userDAO.findById(userDTO.getUserId());
       if (user.isEmpty()){
           throw new NotFoundException("User not found");
       }
       return userDAO.update(converter.getUser(userDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<User> user = userDAO.findById(id);
        if (user.isEmpty()){
            throw new NotFoundException("User not found");
        }
        return userDAO.delete(id);
    }

    @Override
    public Optional<UserDTO> findById(String id) throws SQLException {
       Optional<User> user = userDAO.findById(id);
       if (user.isPresent()){
           return Optional.of(converter.getUserDTO(user.get()));
       }
       return Optional.empty();
    }

    @Override
    public List<UserDTO> search(String search) throws SQLException {
       ArrayList<User> users = (ArrayList<User>) userDAO.search(search);
       List<UserDTO> userDTOS = new ArrayList<>();
       for (User user : users) {
           userDTOS.add(converter.getUserDTO(user));
       }
       return userDTOS;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return userDAO.getAllIds();
    }
}

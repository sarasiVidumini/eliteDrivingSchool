package lk.ijse.orm_final_coursework.dao;

import lk.ijse.orm_final_coursework.entity.Payment;
import lk.ijse.orm_final_coursework.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> extends SuperDAO{
    public String getNextId() throws SQLException;
    public List<T> getAll() throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(T t) throws SQLException;
    public boolean update(T t) throws SQLException;
    public boolean delete(String id ) throws SQLException;
    public List<T>  search(String search) throws SQLException;
    public List<String> getAllIds() throws SQLException;
    public Optional<T> findById(String id) throws SQLException;
}

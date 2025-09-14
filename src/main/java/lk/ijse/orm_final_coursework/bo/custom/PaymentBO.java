package lk.ijse.orm_final_coursework.bo.custom;

import lk.ijse.orm_final_coursework.bo.SuperBO;
import lk.ijse.orm_final_coursework.dto.PaymentDTO;
import lk.ijse.orm_final_coursework.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PaymentBO extends SuperBO {
    public String getNextId(Session session) throws SQLException;
    public List<PaymentDTO> getAll() throws SQLException;
    public String getLastId() throws SQLException;
    public boolean save(PaymentDTO paymentDTO) throws SQLException;
    public boolean update(PaymentDTO paymentDTO) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public Optional<PaymentDTO> findById(String id) throws SQLException;
    public List<PaymentDTO> search(String search) throws SQLException;
    public List<String> getAllIds() throws SQLException;
}

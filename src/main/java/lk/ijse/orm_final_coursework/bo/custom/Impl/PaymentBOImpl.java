package lk.ijse.orm_final_coursework.bo.custom.Impl;

import lk.ijse.orm_final_coursework.bo.custom.PaymentBO;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.DuplicateException;
import lk.ijse.orm_final_coursework.bo.exceptionHandling.NotFoundException;
import lk.ijse.orm_final_coursework.bo.utils.EntityDTOConverter;
import lk.ijse.orm_final_coursework.dao.DAOFactory;
import lk.ijse.orm_final_coursework.dao.DAOTypes;
import lk.ijse.orm_final_coursework.dao.custom.PaymentDAO;
import lk.ijse.orm_final_coursework.dto.PaymentDTO;
import lk.ijse.orm_final_coursework.entity.Payment;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {
        private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOTypes.PAYMENT);
        private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public String getNextId() throws SQLException {
        return paymentDAO.getNextId();
    }

    @Override
    public List<PaymentDTO> getAll() throws SQLException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOS.add(converter.getPaymentDTO(payment));
        }
        return paymentDTOS;
    }

    @Override
    public String getLastId() throws SQLException {
        return paymentDAO.getLastId();
    }

    @Override
    public boolean save(PaymentDTO paymentDTO) throws SQLException {
        Optional<Payment> payment = paymentDAO.findById(paymentDTO.getPaymentId());
        if (payment.isPresent()) {
            throw new DuplicateException("payment already exists");
        }
        return paymentDAO.save(converter.getPayment(paymentDTO));
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws SQLException {
       Optional<Payment> payment = paymentDAO.findById(paymentDTO.getPaymentId());
       if (payment.isEmpty()){
           throw new NotFoundException("payment not found");
       }
       return paymentDAO.update(converter.getPayment(paymentDTO));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Optional<Payment> payment = paymentDAO.findById(id);
        if (payment.isEmpty()){
            throw new NotFoundException("payment not found");
        }
        return paymentDAO.delete(id);
    }

    @Override
    public Optional<PaymentDTO> findById(String id) throws SQLException {
        Optional<Payment> payment = paymentDAO.findById(id);
        if (payment.isPresent()){
            return Optional.of(converter.getPaymentDTO(payment.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<PaymentDTO> search(String search) throws SQLException {
        ArrayList<Payment> payments = (ArrayList<Payment>) paymentDAO.search(search);
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOS.add(converter.getPaymentDTO(payment));
        }
        return paymentDTOS;
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        return paymentDAO.getAllIds();
    }
}

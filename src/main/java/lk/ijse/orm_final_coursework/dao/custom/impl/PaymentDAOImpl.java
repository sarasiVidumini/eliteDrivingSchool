package lk.ijse.orm_final_coursework.dao.custom.impl;

import lk.ijse.orm_final_coursework.config.FactoryConfiguration;
import lk.ijse.orm_final_coursework.dao.custom.PaymentDAO;
import lk.ijse.orm_final_coursework.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        char tableCharacter = 'P';

        String lastId = session.createQuery(
                "SELECT p.id FROM Payment p ORDER BY p.id DESC",
                String.class
        )
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            String lastNumberString = lastId.substring(1);
            int lastNumber = Integer.parseInt(lastNumberString);

            int nextIdNumber = lastNumber + 1;
            return String.format(tableCharacter+"3%d", nextIdNumber);
        }
        return tableCharacter+"001";
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            List<Payment> list = session.createQuery("FROM Payment" , Payment.class)
                    .getResultList();
            return list;
        }finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            Query<String> query = session.createQuery(
                    "SELECT p.id FROM Payment p ORDER BY p.id DESC",
                    String.class
            ).setMaxResults(1);
            List<String> idList = query.list();
            if (idList.isEmpty()) {
                return null;
            }
            return idList.get(0);
        }finally {
            session.close();
        }
    }

    @Override
    public boolean save(Payment payment) throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.persist(payment);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            currentSession.merge(payment);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Session currentSession = factoryConfiguration.getInstance().getCurrentSession();

        try {
            Payment payment = currentSession.get(Payment.class, id);
            currentSession.delete(payment);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Payment> findById(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();

        try {
            Payment payment = session.get(Payment.class, id);
            return Optional.ofNullable(payment);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Payment> search(String search) throws SQLException {
       String searchText = "%" + search + "%";
       Session session = factoryConfiguration.getSession();

       try {
           Query<Payment> query = session.createQuery(
                   "FROM Payment p " +
                           "WHERE p.id LIKE :search OR" +
                           " p.paymentDate LIKE  :search OR " +
                           " p.amount LIKE  :search OR " +
                           "p.paymentMethod LIKE  :search OR " +
                           "p.status LIKE  :search  OR  " +
                           "p.student LIKE  :search",
                   Payment.class
           );
           query.setParameter("search", searchText);
           List<Payment> paymentList = query.getResultList();
           return paymentList;
       }finally {
           session.close();
       }
    }

    @Override
    public List<String> getAllIds() throws SQLException {
        Transaction transaction = null;
        List<String> idList = new ArrayList<>();

        try {
            Session session = factoryConfiguration.getSession().getSessionFactory().openSession();
            transaction = session.beginTransaction();

            idList = session.createQuery("SELECT p.id FROM Payment p" , String.class).list();
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return idList;

    }
}

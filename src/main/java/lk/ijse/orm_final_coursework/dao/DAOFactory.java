package lk.ijse.orm_final_coursework.dao;

import lk.ijse.orm_final_coursework.dao.custom.impl.StudentDAOImpl;
import lk.ijse.orm_final_coursework.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

   public static DAOFactory getInstance() {
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
   }

   public <T extends  SuperDAO> T getDAO(DAOTypes daoType) {
        return switch (daoType){
            case STUDENT -> (T) new StudentDAOImpl();
            case USER ->  (T) new UserDAOImpl();
        };
   }
}

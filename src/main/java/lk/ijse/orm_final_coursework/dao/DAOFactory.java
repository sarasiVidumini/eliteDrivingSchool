package lk.ijse.orm_final_coursework.dao;

import lk.ijse.orm_final_coursework.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

   public static DAOFactory getInstance() {
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
   }

   public <T extends  SuperDAO> T getDAO(DAOTypes daoType) {
        return switch (daoType){
            case COURSE ->  (T) new CourseDAOImpl();
            case INSTRUCTOR ->  (T) new InstructorDAOImpl();
            case LESSONS ->  (T) new LessonDAOImpl();
            case PAYMENT -> (T) new PaymentDAOImpl();
            case STUDENT -> (T) new StudentDAOImpl();
            case STUDENTCOURSEDETAIL ->  (T) new StudentCourseDetailDAOImpl();
            case USER ->  (T) new UserDAOImpl();
        };
   }
}

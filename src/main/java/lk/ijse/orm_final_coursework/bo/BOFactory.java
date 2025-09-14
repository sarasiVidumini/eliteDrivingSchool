package lk.ijse.orm_final_coursework.bo;

import lk.ijse.orm_final_coursework.bo.custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}
    public static BOFactory getInstance(){
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    public SuperBO getBo(BOTypes boType){
        switch (boType){
            case COURSE:
                return new CourseBOImpl();
            case INSTRUCTOR:
                return new InstructorBOImpl();
            case LESSONS:
                return new LessonBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case STUDENTCOURSEDETAIL:
                return new StudentCourseDetailBOImpl();
            case USER:
                return new UserBOImpl();

                default:
                    return null;
        }
    }
}

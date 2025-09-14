package lk.ijse.orm_final_coursework.bo;

import lk.ijse.orm_final_coursework.bo.custom.Impl.CourseBOImpl;
import lk.ijse.orm_final_coursework.bo.custom.Impl.InstructorBOImpl;
import lk.ijse.orm_final_coursework.bo.custom.Impl.LessonBOImpl;
import lk.ijse.orm_final_coursework.bo.custom.Impl.PaymentBOImpl;

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

                default:
                    return null;
        }
    }
}

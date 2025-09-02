package lk.ijse.orm_final_coursework.bo.exceptionHandling;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

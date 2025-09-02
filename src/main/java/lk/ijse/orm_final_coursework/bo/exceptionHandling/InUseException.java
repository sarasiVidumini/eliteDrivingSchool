package lk.ijse.orm_final_coursework.bo.exceptionHandling;

public class InUseException extends RuntimeException {
    public InUseException(String message) {
        super(message);
    }
}

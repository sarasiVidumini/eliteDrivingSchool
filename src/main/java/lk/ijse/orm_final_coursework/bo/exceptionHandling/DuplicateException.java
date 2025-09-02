package lk.ijse.orm_final_coursework.bo.exceptionHandling;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}

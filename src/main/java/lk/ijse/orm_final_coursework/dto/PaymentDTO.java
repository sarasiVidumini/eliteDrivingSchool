package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PaymentDTO {
    String paymentId;
    String studentId;
    Date paymentDate;
    double amount;
    String paymentMethod;
    String status;

}

package lk.ijse.orm_final_coursework.dto.tm;


import lombok.*;

import java.sql.Date;

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

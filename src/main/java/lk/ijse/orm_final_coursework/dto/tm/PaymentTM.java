package lk.ijse.orm_final_coursework.dto.tm;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PaymentTM {
    private String paymentId;
    private String studentId;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;
    private String status;
}

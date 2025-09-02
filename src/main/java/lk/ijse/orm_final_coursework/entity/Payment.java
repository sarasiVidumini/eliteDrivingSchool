package lk.ijse.orm_final_coursework.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Payment {
    String paymentId;
    String studentId;
    Date paymentDate;
    double amount;
    String paymentMethod;
    String status;

}

package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PaymentDTO {
    private String paymentId;
    private String paymentDate;
    private double amount;
    private String paymentMethod;
    private String status;
    private String studentId;

}

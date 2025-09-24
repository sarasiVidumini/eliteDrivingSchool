package lk.ijse.orm_final_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "payment")
public class Payment {
    @Id
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "student_id" , referencedColumnName = "studentId")
//    private String studentId;
    private Student student;

    @Column
    private String paymentDate;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String status;

}

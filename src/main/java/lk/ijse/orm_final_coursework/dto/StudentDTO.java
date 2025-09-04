package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Date dob;
    private Date registrationDate;
    private ArrayList<StudentCourseDetailDTO> studentCourseDetail;
    private ArrayList<LessonsDTO> lessons;
    private ArrayList<PaymentDTO> payments;
}

package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
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
    private LocalDate dob;
    private LocalDate registrationDate;
//    @Builder.Default
//    private ArrayList<StudentCourseDetailDTO> studentCourseDetail = new ArrayList<>();
    @Builder.Default
    private ArrayList<LessonsDTO> lessons = new ArrayList<>();
    @Builder.Default
    private ArrayList<PaymentDTO> payments = new ArrayList<>();
}

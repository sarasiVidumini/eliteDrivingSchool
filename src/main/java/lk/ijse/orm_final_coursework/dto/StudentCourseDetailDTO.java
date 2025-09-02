package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StudentCourseDetailDTO {
    String studentCourseId;
    String studentId;
    String courseId;
    Date enrollmentDate;
    String status;
    String grade;

}

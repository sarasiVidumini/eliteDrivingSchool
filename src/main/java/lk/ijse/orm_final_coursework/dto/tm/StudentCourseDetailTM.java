package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StudentCourseDetailTM {
    String studentCourseId;
    String studentId;
    String courseId;
    Date enrollmentDate;
    String status;
    String grade;
}

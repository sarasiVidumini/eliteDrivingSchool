package lk.ijse.orm_final_coursework.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StudentCourseDetail {
    String studentCourseId;
    String studentId;
    String courseId;
    Date enrollmentDate;
    String status;
    String grade;

}

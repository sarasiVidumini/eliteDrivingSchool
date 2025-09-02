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
    private String studentCourseId;
    private String studentId;
    private String courseId;
    private Date enrollmentDate;
    private String status;
    private String grade;

}

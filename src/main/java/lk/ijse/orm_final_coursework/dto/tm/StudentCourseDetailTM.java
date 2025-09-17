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
    private Long studentCourseId;
    private Date enrollmentDate;
    private String status;
    private String grade;
    private String studentId;
    private String courseId;
}

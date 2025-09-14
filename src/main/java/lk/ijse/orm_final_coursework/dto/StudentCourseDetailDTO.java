package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StudentCourseDetailDTO {
    private String studentCourseId;
    private Date enrollmentDate;
    private String status;
    private String grade;
    private String studentId;
    private String courseId;

}

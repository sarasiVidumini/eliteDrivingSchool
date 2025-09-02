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
    private String studentCourseId;
    private String studentId;
    private String courseId;
    private Date enrollmentDate;
    private String status;
    private String grade;

}

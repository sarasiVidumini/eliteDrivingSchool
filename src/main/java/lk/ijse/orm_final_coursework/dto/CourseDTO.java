package lk.ijse.orm_final_coursework.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CourseDTO {
    private String courseId;
    private String instructorId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;
}

package lk.ijse.orm_final_coursework.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CourseDTO {
    String courseId;
    String instructorId;
    String courseName;
    String duration;
    double fee;
    String description;
}

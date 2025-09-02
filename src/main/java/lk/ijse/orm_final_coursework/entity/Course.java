package lk.ijse.orm_final_coursework.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Course {
    String courseId;
    String instructorId;
    String courseName;
    String duration;
    double fee;
    String description;
}

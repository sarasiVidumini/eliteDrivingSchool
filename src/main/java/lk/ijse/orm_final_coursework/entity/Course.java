package lk.ijse.orm_final_coursework.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Course {
    private String courseId;
    private String instructorId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;
}

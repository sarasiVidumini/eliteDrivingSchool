package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CourseTM {
    private String courseId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;
    private String instructorId;

}

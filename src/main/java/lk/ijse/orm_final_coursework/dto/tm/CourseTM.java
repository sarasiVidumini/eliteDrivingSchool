package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CourseTM {
    String courseId;
    String instructorId;
    String courseName;
    String duration;
    double fee;
    String description;

}

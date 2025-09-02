package lk.ijse.orm_final_coursework.entity;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Lessons {
    String lessonId;
    String studentId;
    String courseId;
    String instructorId;
    Date lessonDate;
    Time startTime;
    Time endTime;
    String status;

}

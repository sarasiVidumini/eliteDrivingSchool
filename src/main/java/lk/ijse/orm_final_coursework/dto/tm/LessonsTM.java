package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LessonsTM {
    String  lessonId;
    String  studentId;
    String courseId;
    String instructorId;
    Date lessonDate;
    Time startTime;
    Time endTime;
    String status;

}

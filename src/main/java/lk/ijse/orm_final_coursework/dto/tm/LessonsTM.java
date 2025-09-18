package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LessonsTM {
    private String  lessonId;
    private String lessonDate;
    private String startTime;
    private String endTime;
    private String status;
    private String  studentId;
    private String courseId;
    private String instructorId;

}

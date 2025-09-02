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
    private String lessonId;
    private String studentId;
    private String instructorId;
    private Date lessonDate;
    private Time startTime;
    private Time endTime;
    private String status;
    private String courseId;

}

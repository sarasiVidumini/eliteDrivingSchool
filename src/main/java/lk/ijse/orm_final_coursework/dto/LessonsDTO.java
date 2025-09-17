package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LessonsDTO {
    private String lessonId;
    private LocalDate lessonDate;
    private Time startTime;
    private Time endTime;
    private String status;
    private String studentId;
    private String courseId;
    private String instructorId;

}

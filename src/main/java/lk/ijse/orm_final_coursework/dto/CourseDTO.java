package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CourseDTO {
    private String courseId;
    private String instructorId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;
    private ArrayList<StudentCourseDetailDTO> student_course_detail;
    private ArrayList<LessonsDTO> lessons;
}

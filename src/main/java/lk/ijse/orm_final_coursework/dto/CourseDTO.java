package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CourseDTO {
    private String courseId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;
    private String instructorId;
//    @Builder.Default
//    private ArrayList<StudentCourseDetailDTO> student_course_detail = new ArrayList<>();
    @Builder.Default
    private ArrayList<LessonsDTO> lessons = new ArrayList<>();
}

package lk.ijse.orm_final_coursework.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InstructorDTO {
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private String availability_schedule;
    @Builder.Default
    private ArrayList<LessonsDTO> lessons = new ArrayList<>();
    @Builder.Default
    private ArrayList<CourseDTO> courses = new ArrayList<>();
}

package lk.ijse.orm_final_coursework.dto.tm;

import lk.ijse.orm_final_coursework.dto.CourseDTO;
import lk.ijse.orm_final_coursework.dto.LessonsDTO;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InstructorTM {
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private String availability_schedule;
}

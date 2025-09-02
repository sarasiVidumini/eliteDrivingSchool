package lk.ijse.orm_final_coursework.dto;

import lombok.*;

@Getter
@Setter
@ToString
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
    private boolean availability_schedule;
}

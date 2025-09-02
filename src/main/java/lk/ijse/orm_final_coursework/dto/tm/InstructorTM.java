package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InstructorTM {
    String instructorId;
    String firstName;
    String lastName;
    String email;
    String phone;
    String specialization;
    boolean availability_schedule;
}

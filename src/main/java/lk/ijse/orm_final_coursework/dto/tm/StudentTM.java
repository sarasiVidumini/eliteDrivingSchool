package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StudentTM {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dob;
    private LocalDate registrationDate;

}

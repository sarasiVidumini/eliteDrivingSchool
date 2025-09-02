package lk.ijse.orm_final_coursework.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StudentTM {
    String studentId;
    String firstName;
    String lastName;
    String email;
    String phone;
    String address;
    Date dob;
    Date registrationDate;

}

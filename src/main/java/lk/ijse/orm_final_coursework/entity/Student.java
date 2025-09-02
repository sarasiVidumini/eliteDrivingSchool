package lk.ijse.orm_final_coursework.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Student {
    String studentId;
    String firstName;
    String lastName;
    String email;
    String phone;
    String address;
    Date dob;
    Date registrationDate;

}

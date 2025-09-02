package lk.ijse.orm_final_coursework.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    String userId;
    String userName;
    String password;
    String role;
    String email;
    String status;
}

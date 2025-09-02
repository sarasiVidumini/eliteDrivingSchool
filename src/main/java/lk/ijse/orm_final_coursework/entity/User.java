package lk.ijse.orm_final_coursework.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    private String userId;
    private String userName;
    private String password;
    private String role;
    private String email;
    private String status;
}

package lk.ijse.orm_final_coursework.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {
    String userId;
    String userName;
    String password;
    String role;
    String email;
    String status;

}

package lk.ijse.orm_final_coursework.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {
    private String userId;
    private String userName;
    private String password;
    private String role;
    private String email;
    private String status;

}

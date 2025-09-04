package lk.ijse.orm_final_coursework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column
    private String userId;

    @Column(nullable = false , unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String status;
}

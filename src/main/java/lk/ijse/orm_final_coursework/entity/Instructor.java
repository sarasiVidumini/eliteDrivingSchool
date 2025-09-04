package lk.ijse.orm_final_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Builder

@Entity
@Table(name = "instructor")

public class Instructor {

    @Id
    @Column(name = "instructor_id")
    private String instructorId;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(length = 15 , nullable = false , unique = true)
    private String phone;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private boolean availability_schedule;

    @OneToMany(
            mappedBy = "instructor",
            cascade = CascadeType.ALL
    )

    private List<Lessons> lessons;

    @OneToMany(
            mappedBy = "instructor",
            cascade = CascadeType.ALL
    )

    private List<Course> courses;
}

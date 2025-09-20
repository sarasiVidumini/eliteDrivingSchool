package lk.ijse.orm_final_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")

public class Student {
    @Id
    @Column
    private String studentId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(length = 15 , nullable = false , unique = true)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private LocalDate registrationDate;

//    @OneToMany(
//            mappedBy = "student",
//            cascade = CascadeType.ALL
//    )
//    private List<StudentCourseDetail> studentCourseDetails;

    @ManyToMany
    @JoinTable(
            name = "student_course_detail",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )

    private List<Course> courses;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private List<Lessons> lessons;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private List<Payment> payments;

}

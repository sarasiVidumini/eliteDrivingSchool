package lk.ijse.orm_final_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Builder

@Entity
@Table(name = "student_course_detail")

public class StudentCourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long studentCourseId;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
//    private String studentId;
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id" , referencedColumnName = "course_id")
//    private String courseId;
    private Course course;

    @Column
    private Date enrollmentDate;

    @Column
    private String status;

    @Column
    private String grade;

}

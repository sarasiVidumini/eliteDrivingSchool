package lk.ijse.orm_final_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Builder

@Entity
@Table(name = "lessons")
public class Lessons {
    @Id
    @Column
    private String lessonId;

    @ManyToOne
    @JoinColumn(name = "student_id" , referencedColumnName ="studentId" )
//    private String studentId;
    private Student student;

    @ManyToOne
    @JoinColumn(name = "instructor_id" , referencedColumnName = "instructorId")
//    private String instructorId;
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "course_id" , referencedColumnName = "course_id")
//    private String courseId;
    private Course course;

    @Column
    private Date lessonDate;

    @Column
    private Time startTime;

    @Column
    private Time endTime;

    @Column
    private String status;


}

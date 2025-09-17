package lk.ijse.orm_final_coursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
//import org.hibernate.mapping.List;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Builder
@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "course_id")
    private String courseId;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private double fee;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructor_id" , referencedColumnName = "instructor_id")
    private Instructor instructor;
//    private String instructorId;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<StudentCourseDetail> studentsCourseDetail;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )

    private List<Lessons> lessons;

//    public Object getInstructorId() {
//    }
}

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructorId" , referencedColumnName = "instructorId" , nullable = true)
    private Instructor instructor;
//    private String instructorId;

//    @OneToMany(
//            mappedBy = "course",
//            cascade = CascadeType.ALL
//    )
//    private List<StudentCourseDetail> studentsCourseDetail;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )

    private List<Lessons> lessons;

    @Transient
    private int enrollmentCount;

//    public Object getInstructorId() {
//    }

    public String getInstructorId() {
        return instructor != null ? instructor.getInstructorId() : null;
    }
}

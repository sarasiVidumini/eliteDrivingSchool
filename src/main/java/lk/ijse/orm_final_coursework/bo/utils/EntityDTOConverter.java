package lk.ijse.orm_final_coursework.bo.utils;

import lk.ijse.orm_final_coursework.dto.*;
import lk.ijse.orm_final_coursework.entity.*;

import java.sql.Date;

public class EntityDTOConverter {

    public CourseDTO getCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(course.getCourseId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setDuration(course.getDuration());
        courseDTO.setFee(course.getFee());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setInstructorId(course.getInstructor().getInstructorId());
        return courseDTO;
    }

    public Course getCourse(CourseDTO courseDTO) {
        Course course = new Course();
        Instructor instructor = new Instructor();
        course.setCourseId(courseDTO.getCourseId());
        course.setCourseName(courseDTO.getCourseName());
        course.setDuration(courseDTO.getDuration());
        course.setFee(courseDTO.getFee());
        course.setDescription(courseDTO.getDescription());
        instructor.setInstructorId(courseDTO.getInstructorId());
        course.setInstructor(instructor);
        return course;
    }

    public InstructorDTO getInstructorDTO(Instructor instructor) {
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setInstructorId(instructor.getInstructorId());
        instructorDTO.setFirstName(instructor.getFirstName());
        instructorDTO.setLastName(instructor.getLastName());
        instructorDTO.setEmail(instructor.getEmail());
        instructorDTO.setPhone(instructor.getPhone());
        instructorDTO.setSpecialization(instructor.getSpecialization());
        instructorDTO.setAvailability_schedule(instructor.getAvailability_schedule());
        return instructorDTO;
    }

    public Instructor getInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(instructorDTO.getInstructorId());
        instructor.setFirstName(instructorDTO.getFirstName());
        instructor.setLastName(instructorDTO.getLastName());
        instructor.setEmail(instructorDTO.getEmail());
        instructor.setPhone(instructorDTO.getPhone());
        instructor.setSpecialization(instructorDTO.getSpecialization());
        instructor.setAvailability_schedule(instructorDTO.getAvailability_schedule());
        return instructor;

    }

    public LessonsDTO getLessonsDTO(Lessons lessons) {
        LessonsDTO lessonsDTO = new LessonsDTO();
        lessonsDTO.setLessonId(lessons.getLessonId());
        lessonsDTO.setLessonDate(lessons.getLessonDate());
        lessonsDTO.setStartTime(lessons.getStartTime());
        lessonsDTO.setEndTime(lessons.getEndTime());
        lessonsDTO.setStatus(lessons.getStatus());
        lessonsDTO.setStudentId(lessons.getStudent().getStudentId());
        lessonsDTO.setCourseId(lessons.getCourse().getCourseId());
        lessonsDTO.setInstructorId(lessons.getInstructor().getInstructorId());
        return lessonsDTO;
    }

    public Lessons getLessons(LessonsDTO lessonsDTO) {
        Lessons lessons = new Lessons();
        Instructor instructor = new Instructor();
        Course course = new Course();
        Student student = new Student();
        lessons.setLessonId(lessonsDTO.getLessonId());
        lessons.setLessonDate(lessonsDTO.getLessonDate());
        lessons.setStartTime(lessonsDTO.getStartTime());
        lessons.setEndTime(lessonsDTO.getEndTime());
        lessons.setStatus(lessonsDTO.getStatus());
        student.setStudentId(lessonsDTO.getStudentId());
        lessons.setStudent(student);
        course.setCourseId(lessonsDTO.getCourseId());
        lessons.setCourse(course);
        instructor.setInstructorId(lessonsDTO.getInstructorId());
        lessons.setInstructor(instructor);
        return lessons;
    }

    public PaymentDTO getPaymentDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(payment.getPaymentId());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setStudentId(payment.getStudent().getStudentId());
        return paymentDTO;
    }

    public Payment getPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        Student student = new Student();
        payment.setPaymentId(paymentDTO.getPaymentId());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        student.setStudentId(paymentDTO.getStudentId());
        payment.setStudent(student);
        return payment;
    }

    public StudentDTO getStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPhone(student.getPhone());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setDob(student.getDob());
        studentDTO.setRegistrationDate(student.getRegistrationDate());
        return studentDTO;
    }

    public Student getStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setStudentId(studentDTO.getStudentId());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setAddress(studentDTO.getAddress());
        student.setDob(studentDTO.getDob());
        student.setRegistrationDate(studentDTO.getRegistrationDate());
        return student;
    }

    public StudentCourseDetailDTO getStudentCourseDetailDTO(StudentCourseDetail studentCourseDetail) {
        StudentCourseDetailDTO studentCourseDetailDTO = new StudentCourseDetailDTO();
        studentCourseDetailDTO.setStudentCourseId(studentCourseDetail.getStudentCourseId());
        studentCourseDetailDTO.setEnrollmentDate(studentCourseDetail.getEnrollmentDate());
        studentCourseDetailDTO.setStatus(studentCourseDetail.getStatus());
        studentCourseDetailDTO.setGrade(studentCourseDetail.getGrade());
        studentCourseDetailDTO.setStudentId(studentCourseDetail.getStudent().getStudentId());
        studentCourseDetailDTO.setCourseId(studentCourseDetail.getCourse().getCourseId());
        return studentCourseDetailDTO;
    }

    public StudentCourseDetail getStudentCourseDetail(StudentCourseDetailDTO studentCourseDetailDTO) {
        StudentCourseDetail studentCourseDetail = new StudentCourseDetail();
        Student student = new Student();
        Course course = new Course();
        studentCourseDetail.setStudentCourseId(studentCourseDetailDTO.getStudentCourseId());
        studentCourseDetail.setEnrollmentDate((Date) studentCourseDetailDTO.getEnrollmentDate());
        studentCourseDetail.setStatus(studentCourseDetailDTO.getStatus());
        studentCourseDetail.setGrade(studentCourseDetailDTO.getGrade());
        student.setStudentId(studentCourseDetailDTO.getStudentId());
        studentCourseDetail.setStudent(student);
        course.setCourseId(studentCourseDetailDTO.getCourseId());
        studentCourseDetail.setCourse(course);
        return studentCourseDetail;
    }

    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setStatus(user.getStatus());
        return userDTO;
    }

    public User getUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        user.setStatus(userDTO.getStatus());
        return user;
    }
}

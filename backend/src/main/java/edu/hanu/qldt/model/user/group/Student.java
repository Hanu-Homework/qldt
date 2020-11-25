package edu.hanu.qldt.model.user.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.hanu.qldt.model.*;
import edu.hanu.qldt.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Student model class to contains student data and build a relationship model.
 * The student cannot creatable without user instance.
 */
@Entity
@Table(name = "students")
public class Student {

    /**
     * Id field [GENERATED AUTOMATICALLY].
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User object to student. This property connects the student to
     * the account.
     */
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    /**
     * Student Date of Birth.
     */
    @Column(name = "dob", nullable = false)
    private LocalDate dateOfBirth;

    /**
     * Gender.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    /**
     * Year when student started.
     */
    @Column(name = "start_year", nullable = false)
    private int startYear;

    /**
     * Student address.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * Student education id.
     */
    @Column(name = "education_id", nullable = false, length = 11)
    private String educationId;

    /**
     * Student healthcare id.
     */
    @Column(name = "health_care_id", length = 16)
    private String healthCareId;

    /**
     * Student father name.
     */
    @Column(name = "father_name", length = 32)
    private String fatherName;

    /**
     * Student mother name.
     */
    @Column(name = "mother_name", length = 32)
    private String motherName;

    /**
     * Student father phone number.
     */
    @Column(name = "father_phone", length = 24)
    private String fatherPhone;

    /**
     * Student mother phone number.
     */
    @Column(name = "mother_phone", length = 24)
    private String motherPhone;

    /**
     * Empty constructor.
     */
    public Student() {

    }

    /**
     * Constructor to make a new instance.
     *
     * @param student      User object to student.
     * @param dateOfBirth  Student Date of Birth.
     * @param startYear   Year when student started.
     * @param address      Student address.
     * @param gender       Gender.
     * @param educationId  Student education id.
     * @param healthCareId Student healthcare id.
     * @param fatherName  Student "first" parent name.
     * @param motherName  Student "second" parent name.
     * @param fatherPhone  Student "first" parent phone number.
     * @param motherPhone  Student "second" parent phone number.
     * @param classroom    Class where student learn.
     */
    public Student(
            User student,
            LocalDate dateOfBirth,
            int startYear,
            String address,
            Gender gender,
            String educationId,
            String healthCareId,
            String fatherName,
            String motherName,
            String fatherPhone,
            String motherPhone,
            Classroom classroom
    ) {
        this.student = student;
        this.dateOfBirth = dateOfBirth;
        this.startYear = startYear;
        this.address = address;
        this.gender = gender;
        this.educationId = educationId;
        this.healthCareId = healthCareId;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.fatherPhone = fatherPhone;
        this.motherPhone = motherPhone;
        this.classroom = classroom;
    }

    /**
     * Student courses. (Only the current year courses.)
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id"),
            }
    )
    private List<Course> courses = new ArrayList<>();


    /**
     * Student reports. (Only the current year reports.)
     */
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Report> reports = new ArrayList<>();

    /**
     * Student exams. (Only the current year exams.)
     */
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Exam> exams = new ArrayList<>();

    /**
     * Student attendances. (Only the current year attendances.)
     */
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances = new ArrayList<>();

    /**
     * Class where student learn.
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int start_year) {
        this.startYear = start_year;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getHealthCareId() {
        return healthCareId;
    }

    public void setHealthCareId(String healthCareId) {
        this.healthCareId = healthCareId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String parent1Name) {
        this.fatherName = parent1Name;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String parent2Name) {
        this.motherName = parent2Name;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String parent1Phone) {
        this.fatherPhone = parent1Phone;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String parent2Phone) {
        this.motherPhone = parent2Phone;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return "Student<" + this.student.getFullName() + "," + this.student.getId() + ">";
    }
}

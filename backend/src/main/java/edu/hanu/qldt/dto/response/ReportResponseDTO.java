package edu.hanu.qldt.dto.response;

public class ReportResponseDTO {

    private int year;

    private int semester;

    private int mark;

    private Long studentId;

    private Long courseId;

    public ReportResponseDTO() {
    }

    public ReportResponseDTO(int year, int semester, int mark, Long studentId, Long courseId) {
        this.year = year;
        this.semester = semester;
        this.mark = mark;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}

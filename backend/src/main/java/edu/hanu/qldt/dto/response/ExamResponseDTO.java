package edu.hanu.qldt.dto.response;

import java.time.LocalDate;

public class ExamResponseDTO {

    private int mark;

    private LocalDate writtenAt;

    private String examType;

    private Long courseId;

    private Long studentId;

    public ExamResponseDTO() {
    }

    public ExamResponseDTO(int mark, LocalDate writtenAt, String examType, Long courseId, Long studentId) {
        this.mark = mark;
        this.writtenAt = writtenAt;
        this.examType = examType;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public LocalDate getWrittenAt() {
        return writtenAt;
    }

    public void setWrittenAt(LocalDate writtenAt) {
        this.writtenAt = writtenAt;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

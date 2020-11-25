package edu.hanu.qldt.dto;

import edu.hanu.qldt.model.user.group.Student;

import java.time.LocalDate;

public class ExamDTO {

    private Student student;

    private int mark;

    private LocalDate writtenAt;

    private String examType;

    public ExamDTO() {
    }

    public ExamDTO(Student student, LocalDate writtenAt, String examType) {
        this.student = student;
        this.mark = 0;
        this.writtenAt = writtenAt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
}

package edu.hanu.qldt.dto.response;

import java.time.LocalDate;

public class AttendanceResponseDTO {

    private Long studentId;

    private boolean miss;

    private int lesson;

    private LocalDate dateOfMiss;

    public AttendanceResponseDTO() {
    }

    public AttendanceResponseDTO(Long studentId, boolean miss, int lesson, LocalDate dateOfMiss) {
        this.studentId = studentId;
        this.miss = miss;
        this.lesson = lesson;
        this.dateOfMiss = dateOfMiss;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public boolean isMiss() {
        return miss;
    }

    public void setMiss(boolean miss) {
        this.miss = miss;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public LocalDate getDateOfMiss() {
        return dateOfMiss;
    }

    public void setDateOfMiss(LocalDate dateOfMiss) {
        this.dateOfMiss = dateOfMiss;
    }
}

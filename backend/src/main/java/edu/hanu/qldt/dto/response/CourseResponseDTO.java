package edu.hanu.qldt.dto.response;

public class CourseResponseDTO {

    private String title;

    private int year;

    private Long teacherId;

    public CourseResponseDTO() {
    }

    public CourseResponseDTO(String title, int year, Long teacherId) {
        this.title = title;
        this.year = year;
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}

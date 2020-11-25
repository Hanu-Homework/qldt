package edu.hanu.qldt.dto.response;

public class RemarkResponseDTO {

    private String text;

    private Long studentId;

    public RemarkResponseDTO(String text, Long studentId) {
        this.text = text;
        this.studentId = studentId;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

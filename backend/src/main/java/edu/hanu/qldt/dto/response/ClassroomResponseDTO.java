package edu.hanu.qldt.dto.response;

public class ClassroomResponseDTO {

    private int startYear;

    private int endYear;

    private int year;

    private char letter;

    private Long headTeacherId;

    public ClassroomResponseDTO() {
    }

    public ClassroomResponseDTO(int startYear, int endYear, int year, char letter, Long headTeacherId) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.year = year;
        this.letter = letter;
        this.headTeacherId = headTeacherId;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Long getHeadTeacherId() {
        return headTeacherId;
    }

    public void setHeadTeacherId(Long headTeacherId) {
        this.headTeacherId = headTeacherId;
    }
}

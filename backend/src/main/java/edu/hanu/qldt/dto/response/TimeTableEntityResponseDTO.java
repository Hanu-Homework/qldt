package edu.hanu.qldt.dto.response;

public class TimeTableEntityResponseDTO {

    private int day;

    private int lessonNumber;

    private Long roomId;

    private Long courseId;

    private Long classroomId;

    public TimeTableEntityResponseDTO() {

    }

    public TimeTableEntityResponseDTO(int day, int lessonNumber, Long roomId, Long courseId, Long classroomId) {
        this.day = day;
        this.lessonNumber = lessonNumber;
        this.roomId = roomId;
        this.courseId = courseId;
        this.classroomId = classroomId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }
}

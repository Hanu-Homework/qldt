package edu.hanu.qldt.dto.response;

public class TeacherPreferenceResponseDTO {

    private Long teacherId;

    private double testWeight;

    private double topicTestWeight;

    private double repetitionWeight;

    private double homeworkWeight;

    public TeacherPreferenceResponseDTO(
            Long teacherId,
            double testWeight,
            double topicTestWeight,
            double repetitionWeight,
            double homeworkWeight
    ) {
        this.teacherId = teacherId;
        this.testWeight = testWeight;
        this.topicTestWeight = topicTestWeight;
        this.repetitionWeight = repetitionWeight;
        this.homeworkWeight = homeworkWeight;

    }

    public TeacherPreferenceResponseDTO() {
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public double getTestWeight() {
        return testWeight;
    }

    public void setTestWeight(double testWeight) {
        this.testWeight = testWeight;
    }

    public double getTopicTestWeight() {
        return topicTestWeight;
    }

    public void setTopicTestWeight(double topicTestWeight) {
        this.topicTestWeight = topicTestWeight;
    }

    public double getRepetitionWeight() {
        return repetitionWeight;
    }

    public void setRepetitionWeight(double repetitionWeight) {
        this.repetitionWeight = repetitionWeight;
    }

    public double getHomeworkWeight() {
        return homeworkWeight;
    }

    public void setHomeworkWeight(double homeworkWeight) {
        this.homeworkWeight = homeworkWeight;
    }
}

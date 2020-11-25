package edu.hanu.qldt.dto.response;

import java.time.LocalDate;

public class StudentResponseDTO {

    private String username;

    private LocalDate dateOfBirth;

    private int startYear;

    private String address;

    private String gender;

    private String educationId;

    private String healthCareId;

    private String fatherName;

    private String motherName;

    private String fatherPhone;

    private String motherPhone;

    private Long classroomId;

    public StudentResponseDTO() {}

    public StudentResponseDTO(
            String username,
            LocalDate dateOfBirth,
            int startYear,
            String address,
            String gender,
            String educationId,
            String healthCareId,
            String fatherName,
            String motherName,
            String fatherPhone,
            String motherPhone,
            Long classroomId
    ) {
        this.username = username;
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
        this.classroomId = classroomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }
}

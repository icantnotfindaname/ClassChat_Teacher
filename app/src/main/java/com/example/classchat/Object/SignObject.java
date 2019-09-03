package com.example.classchat.Object;

public class SignObject {
    private String studentName;
    private String studentId;
    private String signTime;
    private String shouldSignTime;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getShouldSignTime() {
        return shouldSignTime;
    }

    public void setShouldSignTime(String shouldSignTime) {
        this.shouldSignTime = shouldSignTime;
    }

    public SignObject(String studentName, String studentId, String signTime, String shouldSignTime) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.signTime = signTime;
        this.shouldSignTime = shouldSignTime;
    }
}

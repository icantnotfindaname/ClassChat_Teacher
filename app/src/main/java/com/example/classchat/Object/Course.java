package com.example.classchat.Object;

public class Course {
    private String courseName;
    private String courseId;
    private String proUni;
    private String shouldSignTime;

    public Course(String courseName, String courseId, String proUni, String shouldSignTime) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.proUni = proUni;
        this.shouldSignTime = shouldSignTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getProUni() {
        return proUni;
    }

    public void setProUni(String proUni) {
        this.proUni = proUni;
    }

    public String getShouldSignTime() {
        return shouldSignTime;
    }

    public void setShouldSignTime(String shouldSignTime) {
        this.shouldSignTime = shouldSignTime;
    }
}

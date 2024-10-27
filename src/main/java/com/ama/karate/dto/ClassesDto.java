package com.ama.karate.dto;

import java.util.List;

public class ClassesDto {

    private String classesName;
    private String instructorName;
    private String classesAddress;
    private String classesTiming;
    private int totalStudents;
    private Long classesLid;

    public String getClassesName() {
        return classesName;
    }
    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }
    public String getInstructorName() {
        return instructorName;
    }
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    public String getClassesAddress() {
        return classesAddress;
    }
    public void setClassesAddress(String classesAddress) {
        this.classesAddress = classesAddress;
    }
    public String getClassesTiming() {
        return classesTiming;
    }
    public void setClassesTiming(String classesTiming) {
        this.classesTiming = classesTiming;
    }
    public int getTotalStudents() {
        return totalStudents;
    }
    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }
    public Long getClassesLid() {
        return classesLid;
    }
    public void setClassesLid(Long classesLid) {
        this.classesLid = classesLid;
    }
    @Override
    public String toString() {
        return "[classesName=" + classesName + ", instructorName=" + instructorName + ", classesAddress="
                + classesAddress + ", classesTiming=" + classesTiming + ", totalStudents=" + totalStudents
                + ", classesLid=" + classesLid + "]";
    }

    

    
}

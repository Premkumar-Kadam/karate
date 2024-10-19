package com.ama.karate.dto;

import java.util.Arrays;

public class SessionDto {
    
    private int id;
    private String phoneNo;
    private ClassesDto[] classes;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public ClassesDto[] getClasses() {
        return classes;
    }
    public void setClasses(ClassesDto[] classes) {
        this.classes = classes;
    }
    @Override
    public String toString() {
        return "Session [id=" + id + ", phoneNo=" + phoneNo + ", classes=" + Arrays.toString(classes) + "]";
    }

    

}

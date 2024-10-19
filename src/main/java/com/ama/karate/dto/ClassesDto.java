package com.ama.karate.dto;

public class ClassesDto {
    
    private int classLid;
    private String className;
    private int classCount;
    private String classAddress;
    private String classCity;
    private String classFees;
    private String admissionFees;
    private Boolean isMain;

    public int getClassLid() {
        return classLid;
    }
    public void setClassLid(int classLid) {
        this.classLid = classLid;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public int getClassCount() {
        return classCount;
    }
    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }
    public String getClassAddress() {
        return classAddress;
    }
    public void setClassAddress(String classAddress) {
        this.classAddress = classAddress;
    }
    public String getClassCity() {
        return classCity;
    }
    public void setClassCity(String classCity) {
        this.classCity = classCity;
    }
    public String getClassFees() {
        return classFees;
    }
    public void setClassFees(String classFees) {
        this.classFees = classFees;
    }
    public String getAdmissionFees() {
        return admissionFees;
    }
    public void setAdmissionFees(String admissionFees) {
        this.admissionFees = admissionFees;
    }
    public Boolean getIsMain() {
        return isMain;
    }
    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }
    
    @Override
    public String toString() {
        return "Classes [classLid=" + classLid + ", className=" + className + ", classCount=" + classCount
                + ", classAddress=" + classAddress + ", classCity=" + classCity + ", classFees=" + classFees
                + ", admissionFees=" + admissionFees + ", isMain=" + isMain + "]";
    }
    
    


}

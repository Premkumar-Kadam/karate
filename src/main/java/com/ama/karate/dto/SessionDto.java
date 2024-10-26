package com.ama.karate.dto;


public class SessionDto {
    
    private Long userLid;
    private String phoneNo;
    private String userRole;
    private String title;
    private String profileUrl;
    private String userBelt;
    private String email;
    private String fullName;
    private String address;
    private Integer totalClasses;
    private Integer totalStudents;

    public Long getUserLid() {
        return userLid;
    }
    public void setUserLid(Long userLid) {
        this.userLid = userLid;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getProfileUrl() {
        return profileUrl;
    }
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
    public String getUserBelt() {
        return userBelt;
    }
    public void setUserBelt(String userBelt) {
        this.userBelt = userBelt;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Integer getTotalClasses() {
        return totalClasses;
    }
    public void setTotalClasses(Integer totalClasses) {
        this.totalClasses = totalClasses;
    }
    public Integer getTotalStudents() {
        return totalStudents;
    }
    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }
    @Override
    public String toString() {
        return "[userLid=" + userLid + ", phoneNo=" + phoneNo + ", userRole=" + userRole + ", title=" + title
                + ", profileUrl=" + profileUrl + ", userBelt=" + userBelt + ", email=" + email + ", fullName="
                + fullName + ", address=" + address + ", totalClasses=" + totalClasses + ", totalStudents="
                + totalStudents + "]";
    }




    

}

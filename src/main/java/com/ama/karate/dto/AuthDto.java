package com.ama.karate.dto;

public class AuthDto {

    public AuthDto() {}

    public AuthDto(String phoneNo1, String password1) {
    }
    
    private String phoneNo;
    private String password;

    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phone_no) {
        this.phoneNo = phone_no;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "auth [phone_no=" + phoneNo + ", password=" + password + "]";
    }

}

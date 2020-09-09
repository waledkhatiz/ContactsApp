package com.example.contacts;

public class ContactModel {
    private int id;
    private byte[] avatar;
    private String fName;
    private String lName;
    private String mobile;
    private String home;
    private String email;

    public ContactModel(int id, byte[] avatar, String fName, String lName,
                        String mobile, String home, String email) {
        this.id = id;
        this.avatar = avatar;
        this.fName = fName;
        this.lName = lName;
        this.mobile = mobile;
        this.home = home;
        this.email = email;
    }

    public ContactModel() {
    }

    @Override
    public String toString() {
        return "ContactModel{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", home='" + home + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String displayName() {
        return fName + ' ' + lName;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getAvatar() { return avatar;}

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.aptech.user;

import com.aptech.anotations.ColumnName;

import java.util.Date;


public class User {
    @ColumnName(name = "id")
    private long id;
    @ColumnName(name = "username")
    private String username;
    @ColumnName(name = "password")
    private String password;
    @ColumnName(name = "mobile")
    private String mobile;
    @ColumnName(name = "email")
    private String email;
    @ColumnName(name = "address")
    private String address;
    @ColumnName(name = "created_date")
    private Date createdDate;
    @ColumnName(name = "modified_date")
    private Date modifiedDate;
    @ColumnName(name = "status")
    private int status;

    public User() {
    }

    public User(String username, String password, String mobile,
                String email, String address) {
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

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
    @ColumnName(name = "name")
    private String name;
    @ColumnName(name = "created_date")
    private Date createdDate;
    @ColumnName(name = "modified_date")
    private Date modifiedDate;
    @ColumnName(name = "status")
    private int status;
    @ColumnName(name = "photo")
    private String photo;
    @ColumnName(name = "last_failed_login_date")
    private Date lastFailedLoginDate;
    @ColumnName(name = "last_login_date")
    private Date lastLoginDate;
    @ColumnName(name = "lockout_date")
    private Date lockoutDate;
    @ColumnName(name = "login_fail_count")
    private int loginFailCount;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getLastFailedLoginDate() {
        return lastFailedLoginDate;
    }

    public void setLastFailedLoginDate(Date lastFailedLoginDate) {
        this.lastFailedLoginDate = lastFailedLoginDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLockoutDate() {
        return lockoutDate;
    }

    public void setLockoutDate(Date lockoutDate) {
        this.lockoutDate = lockoutDate;
    }

    public int getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }
}

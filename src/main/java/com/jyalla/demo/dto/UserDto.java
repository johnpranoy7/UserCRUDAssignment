package com.jyalla.demo.dto;

import java.util.Date;
import java.util.UUID;


public class UserDto {

    private UUID id;
    private String username;
    private String email;
    private String phoneNo;
    private String profilePic;
    private boolean status;
    private String createdBy;
    private Date createdOn;
    private String encodedPassword;
    private String role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDto(UUID id, String username, String email, String phoneNo, String profilePic, boolean status, String createdBy, Date createdOn, String encodedPassword,
            String role) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.profilePic = profilePic;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.encodedPassword = encodedPassword;
        this.role = role;
    }

    public UserDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", username=" + username + ", email=" + email + ", phoneNo=" + phoneNo + ", profilePic=" + profilePic + ", status=" + status + ", createdBy="
                + createdBy + ", createdOn=" + createdOn + ", encodedPassword=" + encodedPassword + ", role=" + role + "]";
    }



}

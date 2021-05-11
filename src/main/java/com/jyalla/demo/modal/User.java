package com.jyalla.demo.modal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyalla.demo.config.NumericBooleanDeserializer;

@Entity(name = "user")
@Table(name = "\"user\"") // , uniqueConstraints = @UniqueConstraint(columnNames = {"phone_no",
                          // "mail"}))
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "org.hibernate.id.UUIDGenerator", strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @NotBlank(message = "username is mandatory")
    private String username;
    @Column(name = "mail", unique = true)
    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "phone_no is mandatory")
    @Column(name = "phone_no", unique = true)
    private String phoneNo;
    @Column(name = "profile_pic")
    private String profilePic;
    @JsonSerialize(using = NumericBooleanSerializer.class)
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean status;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_on")
    // @PastOrPresent
    private Date createdOn;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_on")
    // @FutureOrPresent
    private Date updatedOn;

    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @RestResource(path = "articles", rel = "articles")
    private List<Blog> articles;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password")
    private String password = "default";

    @Column(name = "role")
    private int role = 2;

    // @JsonIgnore()
    public String getPassword() {
        return password;
    }

    // @JsonProperty(access = Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
        super();
    }

    public List<Blog> getArticles() {
        return articles;
    }

    public void setArticles(List<Blog> articles) {
        this.articles = articles;
    }


    public User(UUID id, @NotBlank(message = "username is mandatory") String username,
            @NotBlank(message = "email is mandatory") @Email(message = "email should be valid") String email, @NotBlank(message = "phone_no is mandatory") String phoneNo,
            String profilePic, boolean status, String createdBy, Date createdOn, String password, int role) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.profilePic = profilePic;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.password = password;
        this.role = role;
    }


    public User(@NotBlank(message = "username is mandatory") String username, @NotBlank(message = "email is mandatory") @Email(message = "email should be valid") String email,
            @NotBlank(message = "phone_no is mandatory") String phoneNo, String profilePic, boolean status, String password, int role) {
        super();
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.profilePic = profilePic;
        this.status = status;
        this.password = password;
        this.role = role;
    }



    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID();
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

    public boolean getStatus() {
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + ", phoneNo=" + phoneNo + ", profilePic=" + profilePic + ", status=" + status + ", createdBy="
                + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", articles=" + articles + ", password=" + password + ", role="
                + role + "]";
    }

}



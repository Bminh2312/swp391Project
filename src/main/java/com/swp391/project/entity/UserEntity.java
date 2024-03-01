package com.swp391.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity(name = "user")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=4, max=20)
    @Column(name = "username")
    private String username;

    @Size(min=4, max=20)
    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "avt", columnDefinition = "TEXT")
    private String avt;

    @Pattern(regexp="\\d{10}")
    @Column(name = "phone")
    private String phone;

    @Column(name = "accesstoken")
    private String accessToken;

    @Column(name = "address")
    private String address;


    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<OrderProjectEntity> sampleProjects;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private String status;
}

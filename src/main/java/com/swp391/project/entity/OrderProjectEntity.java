//package com.swp391.project.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.Date;
//
//@Entity(name = "order_project")
//@Getter
//@Setter
//public class OrderProjectEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne
//    @JoinColumn(name = "id_user")
//    private UserEntity user;
//
//    @ManyToOne
//    @JoinColumn(name = "id_project")
//    private ProjectEntity project;
//
//    @Column(name = "cost")
//    private double cost;
//
//    @Column(name = "description",columnDefinition = "TEXT")
//    private String description;
//
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//
//    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
//    private String status;
//}

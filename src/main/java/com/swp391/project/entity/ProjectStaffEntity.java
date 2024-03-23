//package com.swp391.project.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.Serializable;
//
//@Entity(name = "project_staff")
//@Getter
//@Setter
//public class ProjectStaffEntity implements Serializable {
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
//
//
//}

package com.swp391.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "design_style")
@Getter
@Setter

public class DesignStyleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "img",columnDefinition = "TEXT")
    private String img;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_design")
    private double priceDesign;

    @OneToMany(mappedBy = "designStyle")
    private List<ProjectEntity> projectEntities;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private String status;

}

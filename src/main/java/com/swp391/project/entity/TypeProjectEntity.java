package com.swp391.project.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "type_project")
@Getter
@Setter
public class TypeProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price_type")
    private double priceType;

    @OneToMany(mappedBy = "typeProject")
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

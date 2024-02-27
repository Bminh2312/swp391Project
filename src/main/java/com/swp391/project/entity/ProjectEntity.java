package com.swp391.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity(name = "project")
@Getter
@Setter
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "location",columnDefinition = "NVARCHAR(255)")
    private String location;

    @Column(name = "is_sample")
    private boolean isSample;

    @OneToMany(mappedBy = "project")
    private List<OrderProjectEntity> orderProject;

    @OneToMany(mappedBy = "projectRoom")
    private List<RoomEntity> room;

    @OneToMany(mappedBy = "projectQuote")
    List<QuoteEntity> quotes;

    @ManyToOne
    @JoinColumn(name = "id_design")
    private DesignStyleEntity designStyle;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private String status;
}

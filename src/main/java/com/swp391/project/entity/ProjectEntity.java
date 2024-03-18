package com.swp391.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "project")
@Getter
@Setter
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "location",columnDefinition = "TEXT")
    private String location;

    @Column(name = "img",columnDefinition = "TEXT")
    private String img;

    @Column(name = "is_sample")
    private boolean isSample;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @OneToMany(mappedBy = "projectRoom")
    private List<RoomEntity> room;

    @JsonManagedReference
    @OneToMany(mappedBy = "projectQuote")
    List<QuoteEntity> quotes;

    @ManyToOne
    @JoinColumn(name = "id_design")
    private DesignStyleEntity designStyle;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private TypeProjectEntity typeProject;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private String status;

    public boolean isSample() {
        return isSample;
    }

    public void setSample(boolean sample) {
        isSample = sample;
    }
}

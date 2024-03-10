package com.swp391.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "quote")
@Getter
@Setter
public class QuoteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "quote")
    List<QuoteDetailEntity> quoteDetailEntityList;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_project")
    private ProjectEntity projectQuote;


    @ManyToOne
    @JoinColumn(name = "id_room")
    private RoomEntity roomQuote;

    @Column(name = "quote_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quoteDate;

    @Column(name = "area")
    private double area;

    @Column(name = "total")
    private double total;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private String status;
}

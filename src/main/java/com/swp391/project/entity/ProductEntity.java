package com.swp391.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity(name = "product")
@Getter
@Setter

public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String image;

    @Column(name = "description", columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "design_type")
    private String designType;

    @Column(name = "height")
    private double height;

    @Column(name = "width")
    private double width;

    @Column(name = "length")
    private double length;

    @Column(name = "price_per_m2")
    private double pricePerM2;

    @OneToMany(mappedBy = "product")
    List<QuoteDetailEntity> quoteDetailEntityList;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private String status;
}

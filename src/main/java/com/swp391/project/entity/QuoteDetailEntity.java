package com.swp391.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "quote_detail")
@Getter
@Setter
public class QuoteDetailEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "price_change")
    private double priceChange;

    @Column(name = "note")
    private String note;

    @Column(name = "area")
    private double area;

    @ManyToOne
    @JoinColumn(name = "id_quote")
    private QuoteEntity quote;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "id_raw_material")
    private RawMaterialEntity rawMaterial;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "status",columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private String status;
}

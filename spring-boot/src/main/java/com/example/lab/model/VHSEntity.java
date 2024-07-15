package com.example.lab.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "vhs")
public class VHSEntity {
    @Id
    @Column(name = "vhs_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_title")
    private String title;

    // not sure about rent if it should be on rental entity or here...
    @Column(name = "vhs_rent")
    private Float rent;

    @OneToMany(mappedBy = "vhsEntity")
    private List<RentalEntity> rentals;

    public VHSEntity() {

    }

    public VHSEntity(String title, Float rent) {
        this.title = title;
        this.rent = rent;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Float getRent() {
        return this.rent;
    }

    public List<RentalEntity> getRentals() {
        return this.rentals;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }
}

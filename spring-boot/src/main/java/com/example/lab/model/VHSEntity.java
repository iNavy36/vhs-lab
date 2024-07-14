package com.example.lab.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
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
    private Set<RentalEntity> rentals;

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

    public Set<RentalEntity> getRentals() {
        return this.rentals;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }
}

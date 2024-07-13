package com.example.lab.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rentals")
public class RentalEntity {
    @Id
    @Column(name = "rental_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_date", nullable = false)
    private LocalDate rentDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vhs_id", nullable = false)
    private VHSEntity vhsEntity;

    public RentalEntity() {

    }

    public RentalEntity(LocalDate rentDate, UserEntity userEntity, VHSEntity vhsEntity) {
        this.rentDate = rentDate;
        this.userEntity = userEntity;
        this.vhsEntity = vhsEntity;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDate getRentDate() {
        return this.rentDate;
    }

    public UserEntity getUser() {
        return this.userEntity;
    }

    public VHSEntity getVHS() {
        return this.vhsEntity;
    }

    public void setEndDate(LocalDate endDate) {
        this.rentDate = endDate;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setVHS(VHSEntity vhsEntity) {
        this.vhsEntity = vhsEntity;
    }
}

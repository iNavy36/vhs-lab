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

    @Column(name = "rental_begin_date", nullable = false)
    private LocalDate beginDate;

    @Column(name = "rental_end_date")
    private LocalDate endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vhs_id", nullable = false)
    private VHSEntity vhsEntity;

    public RentalEntity() {

    }

    public RentalEntity(LocalDate beginDate, UserEntity userEntity, VHSEntity vhsEntity) {
        this.beginDate = beginDate;
        this.userEntity = userEntity;
        this.vhsEntity = vhsEntity;
    }

    public Long getId() {
        return this.id;
    }

    public LocalDate getBeginDate() {
        return this.beginDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public UserEntity getUser() {
        return this.userEntity;
    }

    public VHSEntity getVHS() {
        return this.vhsEntity;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setVHS(VHSEntity vhsEntity) {
        this.vhsEntity = vhsEntity;
    }
}

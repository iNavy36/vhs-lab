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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rentals")
public class RentalEntity {
    @Id
    @Column(name = "rental_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_date")
    @NotNull(message = "Date of rent cannot be null.")
    @FutureOrPresent
    private LocalDate rentDate;

    @Column(name = "return_date")
    @FutureOrPresent
    private LocalDate returnDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User ID cannot be null.")
    private UserEntity userEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vhs_id", nullable = false)
    @NotNull(message = "VHS ID cannot be null.")
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

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public UserEntity getUser() {
        return this.userEntity;
    }

    public VHSEntity getVHS() {
        return this.vhsEntity;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setVHS(VHSEntity vhsEntity) {
        this.vhsEntity = vhsEntity;
    }
}

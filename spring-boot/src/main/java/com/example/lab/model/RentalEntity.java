package com.example.lab.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedNativeQuery(name = "rental_entity_mapping", query = "select rentals.rental_id, rentals.rental_date, rentals.return_date, vhs.movie_title, users.user_name from vhs inner join rentals on rentals.vhs_id = vhs.vhs_id inner join users on rentals.user_id = users.user_id", resultSetMapping = "rental_mapped")
@SqlResultSetMapping(name = "rental_mapped", classes = @ConstructorResult(targetClass = RentalMapped.class, columns = {
        @ColumnResult(name = "rental_id", type = Long.class),
        @ColumnResult(name = "rental_date", type = LocalDate.class),
        @ColumnResult(name = "return_date", type = LocalDate.class),
        @ColumnResult(name = "movie_title", type = String.class),
        @ColumnResult(name = "user_name", type = String.class),
}))
@NamedNativeQuery(name = "user_unret_rental_entity_mapping", query = "select rentals.rental_id, rentals.rental_date, rentals.return_date, vhs.movie_title, users.user_name from vhs inner join rentals on rentals.vhs_id = vhs.vhs_id inner join users on rentals.user_id = users.user_id where rentals.return_date is null and users.user_id = ?1", resultSetMapping = "user_unret_rental_mapped")
@SqlResultSetMapping(name = "user_unret_rental_mapped", classes = @ConstructorResult(targetClass = RentalMapped.class, columns = {
        @ColumnResult(name = "rental_id", type = Long.class),
        @ColumnResult(name = "rental_date", type = LocalDate.class),
        @ColumnResult(name = "return_date", type = LocalDate.class),
        @ColumnResult(name = "movie_title", type = String.class),
        @ColumnResult(name = "user_name", type = String.class),
}))
@Table(name = "rentals")
public class RentalEntity {
    @Id
    @Column(name = "rental_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_date")
    @NotNull(message = "{date.notnull}")
    private LocalDate rentDate;

    @Column(name = "return_date")
    @FutureOrPresent(message = "{date.notpast}")
    private LocalDate returnDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @NotNull(message = "{user.notnull}")
    private UserEntity userEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vhs_id")
    @NotNull(message = "{vhs.notnull}")
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

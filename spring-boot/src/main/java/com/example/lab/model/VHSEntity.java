package com.example.lab.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@NamedNativeQuery(name = "vhs_entity_mapping", query = "select vhs.vhs_id, vhs.movie_title, vhs.vhs_rent from vhs", resultSetMapping = "vhs_mapped")
@SqlResultSetMapping(name = "vhs_mapped", classes = @ConstructorResult(targetClass = VHSMapped.class, columns = {
        @ColumnResult(name = "vhs_id", type = Long.class),
        @ColumnResult(name = "movie_title", type = String.class),
        @ColumnResult(name = "vhs_rent", type = Float.class),
}))
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

    @OneToMany(mappedBy = "vhsEntity", targetEntity = RentalEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

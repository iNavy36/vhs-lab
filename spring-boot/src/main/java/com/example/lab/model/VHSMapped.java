package com.example.lab.model;

public class VHSMapped {
    private Long id;
    private String title;
    private Float rent;

    public Long getId() {
        return this.id;
    }

    VHSMapped(Long id, String title, Float rent) {
        this.id = id;
        this.title = title;
        this.rent = rent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getRent() {
        return this.rent;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }

}

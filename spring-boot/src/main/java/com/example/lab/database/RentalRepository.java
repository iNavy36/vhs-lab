package com.example.lab.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab.model.RentalEntity;

public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

}

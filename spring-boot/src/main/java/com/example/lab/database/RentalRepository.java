package com.example.lab.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.lab.model.RentalEntity;
import com.example.lab.model.RentalMapped;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

    @Query(nativeQuery = true, name = "rental_entity_mapping")
    List<RentalMapped> findAllMapped();

    @Query(nativeQuery = true, name = "user_unret_rental_entity_mapping")
    List<RentalMapped> findUserUnreturnedMapped(Long id);
}

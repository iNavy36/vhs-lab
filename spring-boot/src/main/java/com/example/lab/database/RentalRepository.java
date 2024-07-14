package com.example.lab.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lab.model.RentalEntity;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
    RentalEntity findByVhsEntityId(Long vhs_id);
}

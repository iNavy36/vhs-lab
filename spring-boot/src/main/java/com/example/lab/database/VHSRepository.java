package com.example.lab.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.lab.model.VHSMapped;
import com.example.lab.model.VHSEntity;

@Repository
public interface VHSRepository extends JpaRepository<VHSEntity, Long> {
    @Query(nativeQuery = true, name = "vhs_entity_mapping")
    List<VHSMapped> findAllMapped();
}
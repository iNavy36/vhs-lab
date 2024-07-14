package com.example.lab.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lab.model.VHSEntity;

@Repository
public interface VHSRepository extends JpaRepository<VHSEntity, Long> {

}
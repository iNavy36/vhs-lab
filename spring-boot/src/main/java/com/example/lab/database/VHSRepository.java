package com.example.lab.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab.model.VHSEntity;

public interface VHSRepository extends JpaRepository<VHSEntity, Long> {

}
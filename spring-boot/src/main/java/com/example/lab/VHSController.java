package com.example.lab;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.lab.database.*;
import com.example.lab.model.*;

@RestController
@RequestMapping("/api/vhs")
class VHSController {
    private final VHSRepository repository;
    private static final Logger log = LoggerFactory.getLogger(VHSController.class);

    public VHSController(VHSRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<VHSEntity> all() {
        log.info("Getting all VHS tapes...");
        return this.repository.findAll();
    }
}

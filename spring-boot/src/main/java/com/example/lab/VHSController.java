package com.example.lab;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.lab.database.*;
import com.example.lab.exceptions.*;
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

    @GetMapping("/{id}")
    VHSEntity one(@PathVariable Long id) {
        log.info("Getting VHS by id=" + id);
        return repository.findById(id)
                .orElseThrow(() -> new VHSNotFoundException(id));
    }

    @PostMapping
    VHSEntity newVHS(@RequestBody VHSEntity newVHS) {
        log.info("Creating new VHS...");
        return repository.save(newVHS);
    }
}

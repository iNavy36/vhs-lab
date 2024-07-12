package com.example.lab;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab.database.*;
import com.example.lab.exceptions.*;
import com.example.lab.model.VHSEntity;

@RestController
class VHSController {
    private final VHSRepository repository;

    public VHSController(VHSRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/vhs")
    List<VHSEntity> all() {
        return this.repository.findAll();
    }

    @GetMapping("/api/vhs/{id}")
    VHSEntity one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new VHSNotFoundException(id));
    }

    @PostMapping("/api/vhs")
    VHSEntity newVHS(@RequestBody VHSEntity newVHS) {
        return repository.save(newVHS);
    }
}

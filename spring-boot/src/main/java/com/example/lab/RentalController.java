package com.example.lab;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.lab.database.*;
import com.example.lab.exceptions.*;
import com.example.lab.model.*;

@RestController
@RequestMapping("/api/rental")
public class RentalController {
    private final RentalRepository rentalRepository;
    private final VHSRepository vhsRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(VHSController.class);

    public RentalController(RentalRepository rentalRepository, VHSRepository vhsRepository,
            UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.vhsRepository = vhsRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    List<RentalEntity> all() {
        log.info("Getting all rentals...");
        return this.rentalRepository.findAll();
    }

    @GetMapping("/{id}")
    RentalEntity one(@PathVariable Long id) {
        log.info("Getting rental by id=" + id);
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));
    }

    @PostMapping
    RentalEntity newRental(@RequestBody Long user_id, @RequestBody Long vhs_id, @RequestBody LocalDate date) {
        log.info("Creating new rental, user_id=" + user_id + ", vhs_id=" + vhs_id + ", date=" + date);
        return rentalRepository
                .save(new RentalEntity(date, userRepository.getReferenceById(user_id),
                        vhsRepository.getReferenceById(vhs_id)));
    }

    @PutMapping("/{id}")
    RentalEntity replaceVhsInRental(@PathVariable Long id, @RequestBody Long vhs_id) {
        log.info("Updating rental by id=" + id + " with new vhs id=" + vhs_id);
        return rentalRepository.findById(id)
                .map(rental -> {
                    rental.setVHS(vhsRepository.getReferenceById(vhs_id));
                    return rentalRepository.save(rental);
                })
                .orElseThrow(() -> new RentalNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    void deleteRental(@PathVariable Long id) {
        log.info("Deleting rental by id=" + id);
        rentalRepository.deleteById(id);
    }
}

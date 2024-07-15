package com.example.lab;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.lab.database.*;
import com.example.lab.exceptions.*;
import com.example.lab.model.*;

import jakarta.validation.Valid;

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
        return rentalRepository.findAll();
    }

    @GetMapping("/{id}")
    RentalEntity one(@PathVariable Long id) {
        log.info("Getting rental by id=" + id);
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));
    }

    @PostMapping
    RentalEntity newRental(@Valid @RequestParam Long user_id, @Valid @RequestParam Long vhs_id,
            @Valid @RequestParam LocalDate date) {
        log.info("Checking user ID...");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException(user_id);
        log.info("Checking VHS ID...");
        if (!vhsRepository.existsById(vhs_id))
            throw new VHSNotFoundException(vhs_id);
        List<RentalEntity> existingRentals = vhsRepository.getReferenceById(vhs_id).getRentals();
        // RentalEntity existingRental = rentalRepository.findByVhsEntityId(vhs_id);
        if (existingRentals != null) {
            for (RentalEntity existingRental : existingRentals) {
                long daysBetween = Duration
                        .between(existingRental.getRentDate().atStartOfDay(), date.atStartOfDay()).toDays();
                if (existingRental.getRentDate() == date || daysBetween > 0 && existingRental.getReturnDate() == null) {
                    throw new RentalExistingForVHSException(vhs_id);
                }
            }
        }
        log.info("Creating new rental, user_id=" + user_id + ", vhs_id=" + vhs_id + ", date=" + date);
        return rentalRepository
                .save(new RentalEntity(date, userRepository.getReferenceById(user_id),
                        vhsRepository.getReferenceById(vhs_id)));
    }

    @PutMapping("/{id}")
    Float endRental(@PathVariable Long id) {
        log.info("Ended rental by id=" + id);
        RentalEntity returned = rentalRepository.findById(id)
                .map(rental -> {
                    rental.setReturnDate(LocalDate.now());
                    return rentalRepository.save(rental);
                })
                .orElseThrow(() -> new RentalNotFoundException(id));
        long daysBetween = Duration
                .between(returned.getRentDate().atStartOfDay(), returned.getReturnDate().atStartOfDay()).toDays();
        if (daysBetween > 0)
            return returned.getVHS().getRent() * daysBetween;
        else
            return 0.0f;
    }

    @DeleteMapping("/{id}")
    void deleteRental(@PathVariable Long id) {
        log.info("Deleting rental by id=" + id);
        rentalRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

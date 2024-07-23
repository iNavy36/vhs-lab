package com.example.lab;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.lab.database.*;
import com.example.lab.exceptions.*;
import com.example.lab.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("${UI_LOCAL}")
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

    @PostMapping(consumes = "application/json")
    RentalEntity newRental(@Valid @RequestBody RentalForm rental) {
        System.out.println(rental);
        log.info("Checking user ID...");
        if (!userRepository.existsById(rental.getUserId()))
            throw new UserNotFoundException(rental.getUserId());
        log.info("Checking VHS ID...");
        if (!vhsRepository.existsById(rental.getVhsId()))
            throw new VHSNotFoundException(rental.getVhsId());
        List<RentalEntity> existingRentals = vhsRepository.getReferenceById(rental.getVhsId()).getRentals();
        if (existingRentals != null && existingRentals.size() > 0) {
            for (RentalEntity existingRental : existingRentals) {
                long daysBetween = Duration
                        .between(existingRental.getRentDate().atStartOfDay(), rental.getRentDate().atStartOfDay())
                        .toDays();
                if (existingRental.getRentDate().equals(rental.getRentDate())
                        || daysBetween > 0 && existingRental.getReturnDate() == null) {
                    throw new RentalExistingForVHSException(rental.getVhsId());
                }
            }
        }
        log.info("Creating new rental, user_id=" + rental.getUserId() + ", vhs_id=" + rental.getVhsId()
                + ", date=" + rental.getRentDate());
        return rentalRepository
                .save(new RentalEntity(rental.getRentDate(), userRepository.getReferenceById(rental.getUserId()),
                        vhsRepository.getReferenceById(rental.getVhsId())));
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

class RentalForm {
    @JsonProperty("user_id")
    private Long user_id;

    @JsonProperty("vhs_id")
    private Long vhs_id;

    @JsonProperty("rent_date")
    private LocalDate rent_date;

    RentalForm(Long user_id, Long vhs_id, LocalDate rent_date) {
        this.user_id = user_id;
        this.vhs_id = vhs_id;
        this.rent_date = rent_date;
    }

    public Long getUserId() {
        return this.user_id;
    }

    public Long getVhsId() {
        return this.vhs_id;
    }

    public LocalDate getRentDate() {
        return this.rent_date;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public void setVhsId(Long vhs_id) {
        this.vhs_id = vhs_id;
    }

    public void setRentDate(LocalDate rent_date) {
        this.rent_date = rent_date;
    }
}

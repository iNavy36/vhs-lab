package com.example.lab.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.lab.model.VHSEntity;
import com.example.lab.model.UserEntity;

@Configuration
public class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(VHSRepository vhsRepository, UserRepository userRepository) {

    return args -> {
      log.info("Preloading " + vhsRepository.save(new VHSEntity("Back To The Future 2", 0)));
      log.info("Preloading " + vhsRepository.save(new VHSEntity("Back To The Future 2", 0)));
      log.info("Preloading " + vhsRepository.save(new VHSEntity("The Lion King", 0)));
      log.info("Preloading " + vhsRepository.save(new VHSEntity("The Lion King", 0)));
      log.info("Preloading " + vhsRepository.save(new VHSEntity("The Exterminator", 0)));

      log.info("Preloading " + userRepository.save(new UserEntity("Admin Admin", "admin@mail.com")));
      log.info("Preloading " + userRepository.save(new UserEntity("First User", "user1@mail.com")));
    };
  }
}
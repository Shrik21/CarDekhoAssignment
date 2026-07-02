package com.cardekho.advisor.car;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Override
    @EntityGraph(attributePaths = "features")
    List<Car> findAll();

    @Override
    @EntityGraph(attributePaths = "features")
    Optional<Car> findById(Long id);
}

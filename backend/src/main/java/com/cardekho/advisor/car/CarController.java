package com.cardekho.advisor.car;

import com.cardekho.advisor.common.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public List<CarDto> getCars() {
        return carRepository.findAll().stream()
                .map(CarDto::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(CarDto::from)
                .orElseThrow(() -> new NotFoundException("Car not found with id " + id));
    }
}

package com.cardekho.advisor.config;

import com.cardekho.advisor.car.Car;
import com.cardekho.advisor.car.CarRepository;
import com.cardekho.advisor.car.FuelType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedCars(CarRepository carRepository) {
        return args -> {
            if (carRepository.count() > 0) {
                return;
            }

            carRepository.saveAll(List.of(
                    new Car("Maruti Suzuki", "Baleno", "Alpha AMT", BigDecimal.valueOf(980000), FuelType.PETROL, 22.9, 3.0, 5, 7,
                            List.of("360 camera", "head-up display", "six airbags", "touchscreen", "cruise control")),
                    new Car("Tata", "Nexon", "Creative Plus", BigDecimal.valueOf(1270000), FuelType.PETROL, 17.4, 5.0, 5, 8,
                            List.of("six airbags", "sunroof", "ventilated seats", "connected car tech", "rear camera", "ESP")),
                    new Car("Hyundai", "Creta", "SX Tech", BigDecimal.valueOf(1680000), FuelType.DIESEL, 21.8, 3.0, 5, 8,
                            List.of("ADAS", "panoramic sunroof", "dual-zone AC", "digital cluster", "Bose audio", "six airbags")),
                    new Car("Mahindra", "XUV700", "AX7", BigDecimal.valueOf(2150000), FuelType.DIESEL, 16.6, 5.0, 7, 9,
                            List.of("ADAS", "seven seats", "panoramic sunroof", "dual screens", "six airbags", "drive modes")),
                    new Car("Toyota", "Urban Cruiser Hyryder", "V Hybrid", BigDecimal.valueOf(1980000), FuelType.HYBRID, 27.9, 4.0, 5, 7,
                            List.of("strong hybrid", "panoramic sunroof", "wireless charger", "head-up display", "six airbags")),
                    new Car("Honda", "City", "ZX CVT", BigDecimal.valueOf(1620000), FuelType.PETROL, 18.4, 5.0, 5, 8,
                            List.of("ADAS", "lane watch camera", "six airbags", "sunroof", "premium cabin")),
                    new Car("Kia", "Carens", "Luxury Plus", BigDecimal.valueOf(1780000), FuelType.DIESEL, 21.0, 3.0, 7, 7,
                            List.of("six airbags", "ventilated seats", "captain seats", "sunroof", "Bose audio")),
                    new Car("Tata", "Punch", "Accomplished CNG", BigDecimal.valueOf(945000), FuelType.CNG, 26.9, 5.0, 5, 6,
                            List.of("sunroof", "twin-cylinder CNG", "touchscreen", "rear camera", "cruise control")),
                    new Car("MG", "Comet EV", "Plush", BigDecimal.valueOf(920000), FuelType.ELECTRIC, 0.0, 3.0, 4, 5,
                            List.of("electric drivetrain", "dual screens", "connected car tech", "compact city size")),
                    new Car("Hyundai", "Venue", "SX(O)", BigDecimal.valueOf(1350000), FuelType.PETROL, 18.0, 4.0, 5, 7,
                            List.of("six airbags", "sunroof", "connected car tech", "dashcam", "air purifier")),
                    new Car("Skoda", "Slavia", "Style 1.5 DSG", BigDecimal.valueOf(1890000), FuelType.PETROL, 18.7, 5.0, 5, 10,
                            List.of("six airbags", "ventilated seats", "sunroof", "digital cluster", "premium audio")),
                    new Car("Maruti Suzuki", "Ertiga", "ZXI Plus CNG", BigDecimal.valueOf(1280000), FuelType.CNG, 26.1, 3.0, 7, 6,
                            List.of("seven seats", "CNG", "touchscreen", "cruise control", "rear camera"))
            ));
        };
    }
}

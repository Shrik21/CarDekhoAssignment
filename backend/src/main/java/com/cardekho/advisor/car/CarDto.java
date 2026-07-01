package com.cardekho.advisor.car;

import java.math.BigDecimal;
import java.util.List;

public record CarDto(
        Long id,
        String brand,
        String model,
        String variant,
        BigDecimal price,
        FuelType fuelType,
        double mileage,
        double safetyRating,
        int seatingCapacity,
        int performanceScore,
        List<String> features
) {
    public static CarDto from(Car car) {
        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getVariant(),
                car.getPrice(),
                car.getFuelType(),
                car.getMileage(),
                car.getSafetyRating(),
                car.getSeatingCapacity(),
                car.getPerformanceScore(),
                car.getFeatures()
        );
    }
}

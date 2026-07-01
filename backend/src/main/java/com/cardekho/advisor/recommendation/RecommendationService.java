package com.cardekho.advisor.recommendation;

import com.cardekho.advisor.car.Car;
import com.cardekho.advisor.car.CarDto;
import com.cardekho.advisor.car.CarRepository;
import com.cardekho.advisor.car.FuelType;
import com.cardekho.advisor.car.Priority;
import com.cardekho.advisor.car.UsageType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationService {
    private static final double MAX_EXPECTED_MILEAGE = 30.0;
    private static final double MAX_SAFETY_RATING = 5.0;
    private static final double MAX_PERFORMANCE_SCORE = 10.0;
    private static final double MAX_FEATURE_COUNT = 8.0;

    private final CarRepository carRepository;

    public RecommendationService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<RecommendationResponse> recommend(RecommendationRequest request) {
        return carRepository.findAll().stream()
                .map(car -> toRecommendation(car, request))
                .sorted(Comparator.comparingDouble(RecommendationResponse::score).reversed())
                .limit(5)
                .toList();
    }

    private RecommendationResponse toRecommendation(Car car, RecommendationRequest request) {
        double score = 0;
        score += budgetScore(car, request) * 30;
        score += fuelScore(car.getFuelType(), request.fuelType()) * 20;
        score += passengerScore(car, request.passengers()) * 10;
        score += usageScore(car, request.usageType()) * 10;
        score += priorityScore(car, request.priority()) * 30;

        double roundedScore = BigDecimal.valueOf(score)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();

        return new RecommendationResponse(CarDto.from(car), roundedScore, explain(car, request));
    }

    private double budgetScore(Car car, RecommendationRequest request) {
        if (car.getPrice().compareTo(request.minBudget()) >= 0 && car.getPrice().compareTo(request.maxBudget()) <= 0) {
            return 1.0;
        }

        BigDecimal tolerance = request.maxBudget().multiply(BigDecimal.valueOf(0.15));
        BigDecimal upperTolerance = request.maxBudget().add(tolerance);
        if (car.getPrice().compareTo(request.maxBudget()) > 0 && car.getPrice().compareTo(upperTolerance) <= 0) {
            return 0.45;
        }

        return 0.0;
    }

    private double fuelScore(FuelType carFuel, FuelType requestedFuel) {
        if (carFuel == requestedFuel) {
            return 1.0;
        }

        boolean efficientAlternative = requestedFuel == FuelType.PETROL
                && (carFuel == FuelType.HYBRID || carFuel == FuelType.CNG);
        return efficientAlternative ? 0.45 : 0.0;
    }

    private double passengerScore(Car car, int passengers) {
        if (car.getSeatingCapacity() >= passengers) {
            return 1.0;
        }
        return car.getSeatingCapacity() >= passengers - 1 ? 0.35 : 0.0;
    }

    private double usageScore(Car car, UsageType usageType) {
        return switch (usageType) {
            case CITY -> normalize(car.getMileage(), MAX_EXPECTED_MILEAGE);
            case HIGHWAY -> (normalize(car.getMileage(), MAX_EXPECTED_MILEAGE) * 0.55)
                    + (normalize(car.getPerformanceScore(), MAX_PERFORMANCE_SCORE) * 0.45);
            case MIXED -> (normalize(car.getMileage(), MAX_EXPECTED_MILEAGE) * 0.5)
                    + (normalize(car.getSafetyRating(), MAX_SAFETY_RATING) * 0.25)
                    + (normalize(car.getPerformanceScore(), MAX_PERFORMANCE_SCORE) * 0.25);
        };
    }

    private double priorityScore(Car car, Priority priority) {
        return switch (priority) {
            case MILEAGE -> normalize(car.getMileage(), MAX_EXPECTED_MILEAGE);
            case SAFETY -> normalize(car.getSafetyRating(), MAX_SAFETY_RATING);
            case PERFORMANCE -> normalize(car.getPerformanceScore(), MAX_PERFORMANCE_SCORE);
            case FEATURES -> normalize(car.getFeatures().size(), MAX_FEATURE_COUNT);
        };
    }

    private double normalize(double value, double max) {
        return Math.min(value / max, 1.0);
    }

    private String explain(Car car, RecommendationRequest request) {
        String budgetFit = car.getPrice().compareTo(request.maxBudget()) <= 0
                ? "fits your budget"
                : "is slightly above budget but still close enough to consider";
        String fuelFit = car.getFuelType() == request.fuelType()
                ? "matches your preferred fuel type"
                : "offers an efficient alternative to your preferred fuel type";
        String priorityFit = switch (request.priority()) {
            case MILEAGE -> "strong mileage for lower running costs";
            case SAFETY -> "a reassuring safety rating";
            case PERFORMANCE -> "better performance for confident highway drives";
            case FEATURES -> "a useful feature set for daily comfort";
        };

        return "This car suits you because it " + budgetFit + ", " + fuelFit
                + ", seats your passengers, and provides " + priorityFit + ".";
    }
}

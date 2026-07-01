package com.cardekho.advisor.recommendation;

import com.cardekho.advisor.car.Car;
import com.cardekho.advisor.car.CarRepository;
import com.cardekho.advisor.car.FuelType;
import com.cardekho.advisor.car.Priority;
import com.cardekho.advisor.car.UsageType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(RecommendationService.class)
class RecommendationServiceTest {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RecommendationService recommendationService;

    @Test
    void recommendsTopFiveByUserPreferences() {
        carRepository.saveAll(List.of(
                new Car("Safe", "Family", "Top", BigDecimal.valueOf(1200000), FuelType.PETROL, 18.0, 5.0, 5, 7, List.of("airbags", "esp")),
                new Car("Budget", "Runner", "Base", BigDecimal.valueOf(900000), FuelType.PETROL, 24.0, 3.0, 5, 5, List.of("camera")),
                new Car("Large", "Diesel", "Plus", BigDecimal.valueOf(1400000), FuelType.DIESEL, 20.0, 4.0, 7, 6, List.of("captain seats")),
                new Car("Sport", "Sedan", "GT", BigDecimal.valueOf(1500000), FuelType.PETROL, 16.0, 4.0, 5, 10, List.of("sunroof")),
                new Car("Feature", "SUV", "Max", BigDecimal.valueOf(1300000), FuelType.PETROL, 17.0, 4.0, 5, 7, List.of("adas", "sunroof", "audio")),
                new Car("Costly", "SUV", "Ultra", BigDecimal.valueOf(2500000), FuelType.PETROL, 12.0, 5.0, 5, 9, List.of("adas"))
        ));

        RecommendationRequest request = new RecommendationRequest(
                BigDecimal.valueOf(800000),
                BigDecimal.valueOf(1600000),
                FuelType.PETROL,
                UsageType.MIXED,
                5,
                Priority.SAFETY
        );

        List<RecommendationResponse> recommendations = recommendationService.recommend(request);

        assertThat(recommendations).hasSize(5);
        assertThat(recommendations.getFirst().car().brand()).isEqualTo("Safe");
        assertThat(recommendations).extracting(RecommendationResponse::explanation)
                .allMatch(explanation -> explanation.contains("This car suits you because"));
    }
}

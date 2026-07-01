package com.cardekho.advisor.recommendation;

import com.cardekho.advisor.car.CarDto;

public record RecommendationResponse(
        CarDto car,
        double score,
        String explanation
) {
}

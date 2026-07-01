package com.cardekho.advisor.recommendation;

import com.cardekho.advisor.car.FuelType;
import com.cardekho.advisor.car.Priority;
import com.cardekho.advisor.car.UsageType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RecommendationRequest(
        @NotNull @DecimalMin("0.0") BigDecimal minBudget,
        @NotNull @DecimalMin("1.0") BigDecimal maxBudget,
        @NotNull FuelType fuelType,
        @NotNull UsageType usageType,
        @Min(1) int passengers,
        @NotNull Priority priority
) {
}

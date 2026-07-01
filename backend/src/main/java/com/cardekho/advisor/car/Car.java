package com.cardekho.advisor.car;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String variant;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private double mileage;

    @Column(nullable = false)
    private double safetyRating;

    @Column(nullable = false)
    private int seatingCapacity;

    @Column(nullable = false)
    private int performanceScore;

    @ElementCollection
    @CollectionTable(name = "car_features", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "feature", nullable = false)
    private List<String> features = new ArrayList<>();

    protected Car() {
    }

    public Car(String brand, String model, String variant, BigDecimal price, FuelType fuelType,
               double mileage, double safetyRating, int seatingCapacity, int performanceScore,
               List<String> features) {
        this.brand = brand;
        this.model = model;
        this.variant = variant;
        this.price = price;
        this.fuelType = fuelType;
        this.mileage = mileage;
        this.safetyRating = safetyRating;
        this.seatingCapacity = seatingCapacity;
        this.performanceScore = performanceScore;
        this.features = features;
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getVariant() {
        return variant;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public double getMileage() {
        return mileage;
    }

    public double getSafetyRating() {
        return safetyRating;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public List<String> getFeatures() {
        return features;
    }
}

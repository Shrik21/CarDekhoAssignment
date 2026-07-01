export type FuelType = 'PETROL' | 'DIESEL' | 'CNG' | 'ELECTRIC' | 'HYBRID';
export type UsageType = 'CITY' | 'HIGHWAY' | 'MIXED';
export type Priority = 'MILEAGE' | 'SAFETY' | 'PERFORMANCE' | 'FEATURES';

export interface Car {
  id: number;
  brand: string;
  model: string;
  variant: string;
  price: number;
  fuelType: FuelType;
  mileage: number;
  safetyRating: number;
  seatingCapacity: number;
  performanceScore: number;
  features: string[];
}

export interface RecommendationRequest {
  minBudget: number;
  maxBudget: number;
  fuelType: FuelType;
  usageType: UsageType;
  passengers: number;
  priority: Priority;
}

export interface Recommendation {
  car: Car;
  score: number;
  explanation: string;
}

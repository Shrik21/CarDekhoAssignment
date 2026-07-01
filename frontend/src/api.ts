import type { Car, Recommendation, RecommendationRequest } from './types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options?.headers,
    },
    ...options,
  });

  if (!response.ok) {
    throw new Error(`Request failed with ${response.status}`);
  }

  return response.json() as Promise<T>;
}

export function getCars(): Promise<Car[]> {
  return request<Car[]>('/api/cars');
}

export function getRecommendations(payload: RecommendationRequest): Promise<Recommendation[]> {
  return request<Recommendation[]>('/api/recommendations', {
    method: 'POST',
    body: JSON.stringify(payload),
  });
}

import { useMemo, useState } from 'react';
import {
  ArrowRight,
  BadgeIndianRupee,
  CarFront,
  Check,
  Fuel,
  Gauge,
  ShieldCheck,
  Sparkles,
  Users,
} from 'lucide-react';
import { getRecommendations } from './api';
import type { FuelType, Priority, Recommendation, RecommendationRequest, UsageType } from './types';

const fuelTypes: FuelType[] = ['PETROL', 'DIESEL', 'CNG', 'ELECTRIC', 'HYBRID'];
const usageTypes: UsageType[] = ['CITY', 'HIGHWAY', 'MIXED'];
const priorities: Priority[] = ['MILEAGE', 'SAFETY', 'PERFORMANCE', 'FEATURES'];

const formatLabel = (value: string) => value.charAt(0) + value.slice(1).toLowerCase();
const formatPrice = (price: number) =>
  new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 0 }).format(price);

const initialForm: RecommendationRequest = {
  minBudget: 700000,
  maxBudget: 1600000,
  fuelType: 'PETROL',
  usageType: 'MIXED',
  passengers: 5,
  priority: 'SAFETY',
};

function App() {
  const [form, setForm] = useState<RecommendationRequest>(initialForm);
  const [recommendations, setRecommendations] = useState<Recommendation[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const compared = useMemo(() => recommendations.slice(0, 3), [recommendations]);

  const updateForm = <K extends keyof RecommendationRequest>(key: K, value: RecommendationRequest[K]) => {
    setForm((current) => ({ ...current, [key]: value }));
  };

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsLoading(true);
    setError('');

    try {
      const results = await getRecommendations(form);
      setRecommendations(results);
    } catch {
      setError('Could not load recommendations. Check that the Spring Boot API is running.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <main className="min-h-screen bg-cloud">
      <section className="border-b border-slate-200 bg-white">
        <div className="mx-auto grid max-w-7xl gap-8 px-4 py-8 md:grid-cols-[1.1fr_0.9fr] md:px-8 lg:py-12">
          <div className="flex flex-col justify-center">
            <div className="mb-5 flex items-center gap-3 text-sm font-semibold uppercase tracking-wide text-mint">
              <Sparkles className="h-4 w-4" />
              AI-native shortlist assistant
            </div>
            <h1 className="max-w-3xl text-4xl font-bold leading-tight text-ink md:text-6xl">
              AI Car Advisor
            </h1>
            <p className="mt-5 max-w-2xl text-lg leading-8 text-graphite">
              Answer a few buying questions and get a ranked shortlist with practical reasons, scores,
              and a quick comparison for the final three.
            </p>
            <div className="mt-8 grid gap-3 sm:grid-cols-3">
              {[
                ['12', 'seed cars'],
                ['5', 'ranked picks'],
                ['100', 'point score'],
              ].map(([value, label]) => (
                <div className="border-l-4 border-coral bg-cloud p-4" key={label}>
                  <div className="text-2xl font-bold text-ink">{value}</div>
                  <div className="text-sm text-graphite">{label}</div>
                </div>
              ))}
            </div>
          </div>
          <div className="min-h-[320px] overflow-hidden rounded-lg bg-ink shadow-soft">
            <img
              className="h-full w-full object-cover opacity-90"
              src="https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=1200&q=80"
              alt="Car showroom front profile"
            />
          </div>
        </div>
      </section>

      <section className="mx-auto grid max-w-7xl gap-6 px-4 py-8 md:grid-cols-[420px_1fr] md:px-8">
        <form className="h-fit rounded-lg border border-slate-200 bg-white p-5 shadow-sm" onSubmit={submit}>
          <div className="mb-5 flex items-center gap-3">
            <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-mint text-white">
              <CarFront className="h-5 w-5" />
            </div>
            <div>
              <h2 className="text-xl font-bold text-ink">Requirements</h2>
              <p className="text-sm text-graphite">Tune the shortlist inputs</p>
            </div>
          </div>

          <div className="grid gap-4">
            <label className="grid gap-2">
              <span className="text-sm font-semibold text-ink">Minimum budget</span>
              <input
                className="rounded-md border border-slate-300 px-3 py-2 outline-none focus:border-mint"
                min={0}
                step={50000}
                type="number"
                value={form.minBudget}
                onChange={(event) => updateForm('minBudget', Number(event.target.value))}
              />
            </label>

            <label className="grid gap-2">
              <span className="text-sm font-semibold text-ink">Maximum budget</span>
              <input
                className="rounded-md border border-slate-300 px-3 py-2 outline-none focus:border-mint"
                min={100000}
                step={50000}
                type="number"
                value={form.maxBudget}
                onChange={(event) => updateForm('maxBudget', Number(event.target.value))}
              />
            </label>

            <SegmentedControl
              label="Fuel type"
              options={fuelTypes}
              value={form.fuelType}
              onChange={(value) => updateForm('fuelType', value)}
            />

            <SegmentedControl
              label="Usage"
              options={usageTypes}
              value={form.usageType}
              onChange={(value) => updateForm('usageType', value)}
            />

            <label className="grid gap-2">
              <span className="text-sm font-semibold text-ink">Passengers</span>
              <input
                className="rounded-md border border-slate-300 px-3 py-2 outline-none focus:border-mint"
                min={1}
                max={8}
                type="number"
                value={form.passengers}
                onChange={(event) => updateForm('passengers', Number(event.target.value))}
              />
            </label>

            <SegmentedControl
              label="Priority"
              options={priorities}
              value={form.priority}
              onChange={(value) => updateForm('priority', value)}
            />
          </div>

          {error && <p className="mt-4 rounded-md bg-red-50 p-3 text-sm text-red-700">{error}</p>}

          <button
            className="mt-6 flex w-full items-center justify-center gap-2 rounded-md bg-coral px-4 py-3 font-semibold text-white transition hover:bg-[#c94f39] disabled:cursor-not-allowed disabled:opacity-60"
            disabled={isLoading}
            type="submit"
          >
            {isLoading ? 'Finding cars...' : 'Get recommendations'}
            <ArrowRight className="h-4 w-4" />
          </button>
        </form>

        <div className="grid gap-6">
          <section className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
            <div className="mb-5 flex items-center justify-between gap-3">
              <div>
                <h2 className="text-xl font-bold text-ink">Recommendations</h2>
                <p className="text-sm text-graphite">Top 5 cars ranked for your buying context</p>
              </div>
              <BadgeIndianRupee className="h-6 w-6 text-mint" />
            </div>

            {recommendations.length === 0 ? (
              <div className="grid min-h-[260px] place-items-center rounded-lg border border-dashed border-slate-300 bg-cloud p-8 text-center text-graphite">
                Submit the form to generate a ranked shortlist.
              </div>
            ) : (
              <div className="grid gap-4">
                {recommendations.map((item, index) => (
                  <article className="rounded-lg border border-slate-200 p-4" key={item.car.id}>
                    <div className="flex flex-col gap-4 lg:flex-row lg:items-start lg:justify-between">
                      <div>
                        <div className="mb-2 flex items-center gap-2">
                          <span className="flex h-7 w-7 items-center justify-center rounded-full bg-mint text-sm font-bold text-white">
                            {index + 1}
                          </span>
                          <h3 className="text-lg font-bold text-ink">
                            {item.car.brand} {item.car.model}
                          </h3>
                        </div>
                        <p className="text-sm font-medium text-graphite">{item.car.variant}</p>
                        <p className="mt-3 max-w-2xl text-sm leading-6 text-graphite">{item.explanation}</p>
                      </div>
                      <div className="min-w-28 rounded-lg bg-ink p-3 text-center text-white">
                        <div className="text-2xl font-bold">{item.score}</div>
                        <div className="text-xs uppercase tracking-wide text-slate-300">match score</div>
                      </div>
                    </div>
                    <CarStats recommendation={item} />
                  </article>
                ))}
              </div>
            )}
          </section>

          {compared.length > 0 && (
            <section className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
              <h2 className="text-xl font-bold text-ink">Compare shortlisted cars</h2>
              <div className="mt-5 overflow-x-auto">
                <table className="w-full min-w-[720px] border-collapse text-left text-sm">
                  <thead>
                    <tr className="border-b border-slate-200 text-graphite">
                      <th className="py-3 pr-4">Metric</th>
                      {compared.map((item) => (
                        <th className="py-3 pr-4" key={item.car.id}>
                          {item.car.brand} {item.car.model}
                        </th>
                      ))}
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-slate-100">
                    <CompareRow label="Price" values={compared.map((item) => formatPrice(item.car.price))} />
                    <CompareRow label="Fuel" values={compared.map((item) => formatLabel(item.car.fuelType))} />
                    <CompareRow label="Mileage" values={compared.map((item) => `${item.car.mileage || 'EV'} km/l`)} />
                    <CompareRow label="Safety" values={compared.map((item) => `${item.car.safetyRating}/5`)} />
                    <CompareRow label="Seats" values={compared.map((item) => `${item.car.seatingCapacity}`)} />
                    <CompareRow label="Features" values={compared.map((item) => item.car.features.slice(0, 3).join(', '))} />
                  </tbody>
                </table>
              </div>
            </section>
          )}
        </div>
      </section>
    </main>
  );
}

interface SegmentedControlProps<T extends string> {
  label: string;
  options: T[];
  value: T;
  onChange: (value: T) => void;
}

function SegmentedControl<T extends string>({ label, options, value, onChange }: SegmentedControlProps<T>) {
  return (
    <fieldset className="grid gap-2">
      <legend className="text-sm font-semibold text-ink">{label}</legend>
      <div className="grid grid-cols-2 gap-2 sm:grid-cols-3">
        {options.map((option) => (
          <button
            className={`rounded-md border px-3 py-2 text-sm font-semibold transition ${
              value === option
                ? 'border-mint bg-mint text-white'
                : 'border-slate-300 bg-white text-graphite hover:border-mint'
            }`}
            key={option}
            type="button"
            onClick={() => onChange(option)}
          >
            {formatLabel(option)}
          </button>
        ))}
      </div>
    </fieldset>
  );
}

function CarStats({ recommendation }: { recommendation: Recommendation }) {
  const { car } = recommendation;
  const stats = [
    [BadgeIndianRupee, formatPrice(car.price)],
    [Fuel, formatLabel(car.fuelType)],
    [Gauge, car.mileage ? `${car.mileage} km/l` : 'EV city use'],
    [ShieldCheck, `${car.safetyRating}/5 safety`],
    [Users, `${car.seatingCapacity} seats`],
  ] as const;

  return (
    <div className="mt-4 grid gap-2 sm:grid-cols-2 xl:grid-cols-5">
      {stats.map(([Icon, label]) => (
        <div className="flex min-h-12 items-center gap-2 rounded-md bg-cloud px-3 py-2 text-sm font-medium text-graphite" key={label}>
          <Icon className="h-4 w-4 shrink-0 text-mint" />
          <span>{label}</span>
        </div>
      ))}
      <div className="sm:col-span-2 xl:col-span-5">
        <div className="mt-2 flex flex-wrap gap-2">
          {car.features.map((feature) => (
            <span className="inline-flex items-center gap-1 rounded-full bg-slate-100 px-3 py-1 text-xs font-semibold text-graphite" key={feature}>
              <Check className="h-3 w-3 text-mint" />
              {feature}
            </span>
          ))}
        </div>
      </div>
    </div>
  );
}

function CompareRow({ label, values }: { label: string; values: string[] }) {
  return (
    <tr>
      <td className="py-3 pr-4 font-semibold text-ink">{label}</td>
      {values.map((value, index) => (
        <td className="py-3 pr-4 text-graphite" key={`${label}-${index}`}>
          {value}
        </td>
      ))}
    </tr>
  );
}

export default App;

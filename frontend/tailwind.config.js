/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{ts,tsx}'],
  theme: {
    extend: {
      colors: {
        ink: '#172026',
        graphite: '#3d4a52',
        mint: '#0f766e',
        coral: '#db5a42',
        cloud: '#f6f8f9',
      },
      boxShadow: {
        soft: '0 16px 42px rgba(23, 32, 38, 0.10)',
      },
    },
  },
  plugins: [],
};

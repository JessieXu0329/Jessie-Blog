import typography from '@tailwindcss/typography'

/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts}'],
  theme: {
    extend: {
      colors: {
        space: {
          950: '#020617',
          900: '#070f1e',
          800: '#0d1f3c',
        },
      },
      keyframes: {
        flowDown: {
          '0%':   { transform: 'translateY(-150px)', opacity: '0' },
          '8%':   { opacity: '1' },
          '92%':  { opacity: '1' },
          '100%': { transform: 'translateY(calc(100vh + 150px))', opacity: '0' },
        },
        scanLine: {
          '0%, 100%': { top: '5%',  opacity: '0' },
          '20%':      { opacity: '1' },
          '80%':      { opacity: '1' },
          '99%':      { top: '95%', opacity: '0' },
        },
        glowPulse: {
          '0%, 100%': {
            boxShadow:
              '0 0 6px rgba(59,130,246,0.25), inset 0 0 6px rgba(59,130,246,0.06)',
          },
          '50%': {
            boxShadow:
              '0 0 18px rgba(59,130,246,0.55), inset 0 0 12px rgba(59,130,246,0.12)',
          },
        },
        fadeSlideUp: {
          '0%':   { opacity: '0', transform: 'translateY(20px)' },
          '100%': { opacity: '1', transform: 'translateY(0)' },
        },
        blinkCursor: {
          '0%, 100%': { opacity: '1' },
          '50%':      { opacity: '0' },
        },
      },
      animation: {
        'flow-down':      'flowDown linear infinite',
        'scan-line':      'scanLine 12s ease-in-out infinite',
        'glow-pulse':     'glowPulse 3s ease-in-out infinite',
        'fade-slide-up':  'fadeSlideUp 0.5s ease-out forwards',
        'blink-cursor':   'blinkCursor 1s step-end infinite',
      },
    },
  },
  plugins: [typography],
}

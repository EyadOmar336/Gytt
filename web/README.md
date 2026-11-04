# GeneLab AI — Responsive Web Application

A modern, responsive, AI-powered genetics and molecular biology educational web application.

## Features
- **Responsive Layout:** Adaptive desktop sidebar navigation and mobile bottom bar.
- **Bilingual Support:** Arabic (RTL) primary and English (LTR) with instant language toggle.
- **Punnett Square Simulator:** Monohybrid & Dihybrid crosses with real-time grid and statistics.
- **DNA Explorer:** Animated Canvas Double Helix, complementary strand calculation, transcription, and codon translation into 20 amino acids.
- **Breeding Laboratory:** Monte Carlo stochastic breeding simulation with Chi-Square (χ²) goodness-of-fit.
- **Mutation Explorer:** Point mutations (silent, missense, nonsense) & frameshifts with protein chain comparisons.
- **Interactive Quiz:** Dynamic biology practice with instant feedback.
- **AI Genetics Assistant:** Intelligent biology chat assistant.

## How to Run / Deploy

### Option 1: Live Static Preview (Any Static Host)
Simply upload the `web/` folder to:
- **Vercel:** `vercel deploy`
- **Netlify:** Drag and drop the `web/` folder
- **GitHub Pages:** Push to `gh-pages` branch
- **Firebase Hosting:** `firebase deploy --only hosting`

### Option 2: Local Development Server
```bash
# Using python
cd web && python3 -m http.server 8080

# Using Node.js (npx)
npx serve web
```

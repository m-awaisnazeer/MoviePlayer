# 🎬 Modular Movie App

Welcome to **Modular Movie App** – an Android application designed with a clean modular architecture to showcase modern development practices. This app allows users to **search movies**, **view detailed information**, and **play trailers or clips** via a built-in video player.

---

## 🏗️ Modular Architecture Overview

This project follows **multi-module clean architecture**, which offers:

- Separation of concerns
- Easier testability
- Reusability of modules
- Faster build times

---

## 📂 Project Structure

<pre lang="markdown"> ## 📂 Project Structure ``` ├── app/ # Main application module (entry point) ├── core/ │ ├── data/ # Implements data sources and repository logic │ ├── domain/ # Defines domain models and repository interfaces │ ├── ui/ # Reusable Jetpack Compose UI components │ └── utils/ # Common utility classes and extensions ├── feature/ │ ├── moviedetail/ # Displays selected movie details │ ├── movieplayer/ # Video player for playing trailers/clips │ └── searchmovie/ # Functionality to search and list movies ``` </pre>

---

## 🧠 Core Modules

### `core:data`

Responsible for managing data sources:

- Remote data sources (e.g., REST API using Retrofit)
- Local data sources (e.g., Room database)
- Repository implementations
- DTO to domain model mapping

**Example responsibilities:**

- API service integration
- Caching logic
- Data transformation

---

### `core:domain`

This is the **business logic layer**:

- Contains interfaces for repositories (used by `core:data`)
- Holds domain models and use cases
- Free from Android framework dependencies

**Benefits:**

- Fully testable with plain Kotlin
- Decoupled from data and UI

---

### `core:ui`

Reusable **Jetpack Compose UI components** that can be shared across all feature modules.

Examples:

- Buttons
- Loading indicators
- Custom UI elements

Keeps your UI consistent and DRY (Don't Repeat Yourself).

---

### `core:utils`

A collection of utility classes, constants, and extensions commonly used throughout the app.

Examples:

- Date formatters
- Resource helpers
- Result wrappers

---

## 🎯 Feature Modules

### `feature:searchmovie`

This module handles:

- Movie search via text input
- Displaying a list of matching results
- UI state management and interactions

Uses:

- ViewModel with Compose
- Paging (if implemented)
- Navigation to `moviedetail`

---

### `feature:moviedetail`

Responsible for showing:

- Movie synopsis
- Poster, ratings, cast, etc.
- "Play Trailer" button linking to `movieplayer`

Built using:

- Jetpack Compose for UI
- Shared domain models via `core:domain`

---

### `feature:movieplayer`

Embeds a video player within the app for playing trailers or clips.

Possible integrations:

- ExoPlayer or Media3
- Fullscreen toggle
- Playback controls

Handles:

- Player lifecycle
- Playback UI logic

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Kotlin 1.9+
- JDK 17
- Gradle 8+

### Build & Run

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/modular-movie-app.git
   cd modular-movie-app

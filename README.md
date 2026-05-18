# Hariku

## 📱 Project Overview
Hariku is a modern Android wellness app built with **Kotlin**, **Jetpack Compose**, and **Clean Architecture**. It offers a suite of features such as articles, a chatbot, meditation guides, mood tracking, and a personal profile, all wrapped in a sleek dark‑mode UI with glass‑morphism touches.

## ✨ Features
- **Feature‑rich modules**: 15 feature packages (article, chat, meditation, etc.) each following a `data → domain → presentation` layered pattern.
- **Jetpack Compose UI** with a custom `HariKuTheme` (dark & light modes, smooth transitions).
- **Koin DI** for easy, testable dependency management.
- **Room database** integration for offline persistence (e.g., mood logs, journal entries).
- **Navigation‑Compose** powered routing with a centralized `NavGraph`.
- **Modular architecture** that scales effortlessly and enables isolated unit testing.

## 🏗️ Architecture
The app follows a **Clean Architecture** approach:

- **Presentation** – Compose screens, ViewModels, UI state handling (found under `feature_* /presentation`).
- **Domain** – Use‑cases and business rules (`feature_* /domain`).
- **Data** – Repositories, remote API clients, Room DAOs (`feature_* /data`).
- **DI** – Koin modules (`feature_* /di` and core `di`).
- **Core** – Shared UI components, theme, utilities (`core/`).

```
Hariku/
├─ app/
│  ├─ src/main/java/com/hariku/
│  │  ├─ HariKuApp.kt        # Application class – starts Koin
│  │  ├─ MainActivity.kt     # Entry point – sets Compose theme & NavGraph
│  │  ├─ core/               # Shared UI, DI, utilities
│  │  └─ feature_* /         # 15 feature modules (article, chat, …)
│  └─ build.gradle.kts
├─ build.gradle.kts          # Top‑level Gradle config
└─ settings.gradle.kts
```

## 🛠️ Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose, Material 3
- **DI**: Koin
- **Database**: Room
- **Networking**: Retrofit / Kotlin Coroutines (if applicable)
- **Build**: Gradle Kotlin DSL
- **Testing**: JUnit, MockK (unit tests) & Compose UI tests

## 🚀 Getting Started
### Prerequisites
- Android Studio Flamingo (or newer) with **JDK 17**
- Android SDK 34 (or the SDK version defined in `compileSdk`)

### Clone & Open
```bash
git clone <repository‑url>
cd Hariku
```
Open the `HariKu` folder in Android Studio.

### Build & Run
1. Let Android Studio sync Gradle.
2. Connect an Android device or start an emulator.
3. Click **Run** ( ▶️ ) or use the command line:
```bash
./gradlew installDebug
```

## 📊 Testing
Unit tests are located alongside each feature module.
```bash
./gradlew testDebugUnitTest
```
Compose UI tests can be executed with:
```bash
./gradlew connectedDebugAndroidTest
```

## 🤝 Contributing
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/awesome-feature`).
3. Follow the existing clean‑architecture pattern.
4. Write accompanying unit tests.
5. Submit a Pull Request with a clear description.

## 📄 License
This project is licensed under the **MIT License** – see the `LICENSE` file for details.
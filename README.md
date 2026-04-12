# 📱 **Tutorly** - Learn Android Development Seamlessly

**Tutorly** is a modern Android learning platform designed to help developers master Android fundamentals through structured video tutorials. Built with the latest Android technologies, Tutorly provides an intuitive, responsive experience for consuming educational content.

## ✨ **Key Features**

- 🎥 **Multi-Format Video Platform** - Support for both long-form educational content and short-form vertical videos
- 📹 **Long-Form Content** - Curated feed of in-depth tutorials and educational videos with detailed explanations
- 📱 **Short-Form Video Feed** - TikTok-style vertical video player for quick, bite-sized learning content with auto-play functionality
- 🔐 **Secure Authentication** - Seamless Google Sign-In powered by Firebase Authentication
- 👤 **User Profiles** - Personalized user profiles with profile pictures and authentication persistence
- 🎬 **Advanced Video Player** - Full-featured ExoPlayer integration with smooth playback, controls, and optimized streaming
- 🌙 **Dark Mode Support** - Beautiful Material 3 design with automatic light/dark theme switching
- 💾 **Persistent Data** - Secure local storage of user preferences and authentication data with DataStore
- 📐 **Responsive UI** - Adaptive layouts that work seamlessly across different screen sizes and orientations

## 🛠 **Tech Stack**

### **Core Android**
- **Android SDK**: API 24+ (Android 7.0 and above)
- **Language**: Kotlin 2.0.10
- **Build System**: Gradle 9.0.1 (KTS)

### **UI & Presentation**
- **Jetpack Compose** - Modern declarative UI framework
- **Material 3** - Latest Material Design guidelines
- **Navigation Component** - Fragment-based navigation
- **ExoPlayer** - Professional video playback (Media3 1.10.0)

### **Architecture & DI**
- **MVVM + MVI Pattern** - Clean separation of concerns with intent-based state management
- **Hilt** - Dependency injection for clean, testable code
- **Lifecycle & ViewModel** - Lifecycle-aware components with intent handlers
- **StateFlow** - Reactive state management with sealed class hierarchies
- **Intent-Based UI** - Type-safe user action handling through sealed class intents

### **Data & Storage**
- **Firebase Authentication** - Secure user authentication
- **DataStore** - Preferences for encrypted, async data storage
- **Paging 3** - Efficient list pagination
- **Credential Manager** - Modern credential handling with Google ID

### **Utilities**
- **KSP** - Kotlin Symbol Processing for compile-time code generation
- **Glide** - Image loading and caching
- **CircleImageView** - Circular image components
- **Edge-to-Edge** - Modern edge-to-edge display support

## 📂 **Project Structure**

```
Tutorly/
├── app/src/main/java/com/sahilm/tutorly/
│   ├── core/              # Core utilities, DI modules, constants
│   ├── data/              # Data layer - repositories, models
│   ├── domain/            # Domain layer - interfaces, business models
│   └── ui/                # UI layer
│       ├── login/         # Authentication screens
│       ├── home/          # Main app screens
│       └── theme/         # App theming
├── res/                   # Resources (layouts, strings, styles)
└── build.gradle.kts       # Dependencies and build config
```

## 🚀 **Getting Started**

### **Prerequisites**
- Android Studio Arctic Fox or newer
- Android SDK 36 (latest)
- Gradle 9.0+
- Java/Kotlin development environment

### **Setup Instructions**

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/Tutorly.git
   cd Tutorly
   ```

2. **Configure Firebase**
   - Place your `google-services.json` in the `app/` directory
   - This file contains your Firebase project credentials

3. **Build and Run**
   ```bash
   ./gradlew build
   ```
   - Open in Android Studio and run on an emulator or physical device

## 📖 **Usage**

1. **Launch the App** - Tutorly starts with the login screen
2. **Sign In with Google** - Tap the "Sign in with Google" button to authenticate
3. **Browse Videos** - Once logged in, browse through the video feed on the home screen
4. **Watch Tutorials** - Tap any video to open the full-screen player and learn at your own pace
5. **View Profile** - Access your profile with your user picture and name

## 🏗 **Architecture Highlights**

**MVVM + MVI (Model-View-Intent) + Clean Architecture**
- **UI Layer**: Compose activities and fragments for presentation
- **Domain Layer**: Business logic and use cases
- **Data Layer**: Repository pattern for data access

**Intent-Based State Management (MVI Pattern)**
- ViewModels handle user intents through `handleIntent()` methods
- Sealed class hierarchies for type-safe intents:
  - `LoginIntent.GoogleSignIn` - User authentication flow
  - `SignOutIntent.SignOutUser` - Sign out operation
  - `ShortsIntent.LoadShorts`, `PlayVideo`, `PauseVideo` - Video playback control
- State updates are immutable and predictable

**Reactive State Management (MVVM Pattern)**
- `MutableStateFlow<State>` for reactive state exposure
- View states as data classes (e.g., `ShortsViewState`, `LoginState`)
- Sealed classes for state representation with exhaustive when expressions
- `StateFlow.asStateFlow()` for read-only state exposure to UI

**Dependency Injection**
- Uses Hilt for automatic dependency management
- Modular, testable component architecture with `@HiltViewModel`

**Data Flow**
- UI sends intents → ViewModel processes intents → State updates → UI reacts

## 🎨 **Design System**

Tutorly uses a vibrant Material 3 color palette:
- **Primary**: Elegant Indigo (#6366F1)
- **Secondary**: Fresh Green (#10B981)
- **Tertiary**: Warm Amber (#F59E0B)
- Complete dark mode support for reduced eye strain

## 📋 **Content**

Currently features tutorials on:
- Activities & Activity Lifecycle
- Tasks, Back Stack & Launch Modes
- ViewModels & Configuration Changes
- Context in Android
- URIs (Unique Resource Identifiers)
- ...and more!

## 🧪 **Testing**

The project includes unit tests and instrumented tests:
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 📝 **License**

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 **Contributing**

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

## 📞 **Support**

For issues, questions, or suggestions, please open an issue on the GitHub repository.

---

**Built with ❤️ using modern Android development best practices**

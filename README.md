# 🪑 Chares

**Chares** is a simple Android application built with **Jetpack Compose** to help users manage chores and tasks.  
It provides an intuitive interface for adding, tracking, and managing daily chores, with localization support.

---

## ✨ Features
- ✅ Chore management
- 🌍 Localization (English and Turkish)
- 🎨 Theme switching (System, Light, Dark)

---

## 🛠 Technologies
- **Kotlin**
- **Jetpack Compose**
- **Android Architecture Components** (ViewModel, LiveData, Room)
- **DataStore**

---

## 📌 Upcoming Features
- **Localization Improvements**: More robust language support  
- **Welcome Screen**: Guide for first-time users  

---

## ⚠️ Known Issues
- **Translation bug**: some UI strings may not appear localized  
- **Package name warning**: debug builds use `com.example.chares`, which will switch to `com.samm.chares` on final release  

---

## 🚀 Building

### Requirements
- JDK 17+
- Android SDK (check required API levels in `build.gradle`)
- Gradle (included via `gradlew`)
- (Optional) Android Studio for IDE development

### Command Line
```bash
chmod +x ./gradlew
./gradlew clean assembleDebug
````

* APK output: `app/build/outputs/apk/debug/app-debug.apk`
* Install with ADB:

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Android Studio

1. Open the project in Android Studio
2. Let Gradle sync finish
3. Build APK: **Build → Build Bundle(s) / APK(s) → Build APK(s)**
4. Or run directly with **Shift + F10**

---

## 📜 License

Chares is licensed under the **GNU General Public License v3.0**.
See the [LICENSE](LICENSE) file for full details.

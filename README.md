# Chares

Chares is a Simple Android application built with Jetpack Compose to help users manage their chores and tasks. It provides features for adding, tracking, and managing daily chores, with localization support.

## Features

- Chore management
- Localization (English and Turkish)
- Theme switching (System, Light, Dark)

## Technologies Used

- Kotlin
- Jetpack Compose
- Android Architecture Components (ViewModel, LiveData, Room)
- DataStore

## Upcoming Features

- **Fixed Theme**: The application's theme handling has been refactored to ensure consistent and reliable theme switching across the app, addressing previous inconsistencies.
- **Localization Improvements**: Enhanced the localization system to provide more robust and consistent language support, ensuring all UI elements correctly adapt to selected languages.
- **Import/Export Functionality**: Planned feature to allow users to import and export their chore data, enabling easy backup and transfer of information.
- **Welcome Screen**: A new introductory screen to guide first-time users through the app's features and setup process.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. Instructions will be added later.

## Building

To build the project, navigate to the root directory of the project and run:

```bash
./gradlew build
```

### Full Building Instructions

#### Requirements

- JDK 17+
- Android SDK (Make sure required API levels are installed — check `build.gradle`)
- Gradle (comes bundled with the project via `gradlew`)
- (Optional) Android Studio for IDE-based development

#### Build with Gradle Wrapper (Command Line)

1. Make sure the Gradle wrapper is executable:
```bash
chmod +x ./gradlew
```
2. Clean the project and build the debug APK:
```bash
./gradlew clean assembleDebug
```
3. The output APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`
4. You can install it on a connected device using:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```


#### Open and Build with Android Studio (Optional)

1. Launch Android Studio
2. Choose **"Open an Existing Project"** and select the project folder
3. Let Gradle sync complete (automatically or via **File → Sync Project with Gradle Files**)
4. To build the APK, go to **Build → Build Bundle(s) / APK(s) → Build APK(s)**
5. Or run the app directly using **Shift + F10**

## License

Chares
Copyright (C) 2025 Samm

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
[GNU General Public License](LICENSE) for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

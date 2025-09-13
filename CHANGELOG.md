# CHANGELOG

## 2025-09-13

### Added

- **Chore Editing:** Implemented chore editing functionality. Users can now long-press on a chore to open an edit dialog, where they can update the chore's title or delete it.
- **Delete Confirmation:** Added a confirmation dialog when deleting a chore from the edit screen to prevent accidental deletions.

### Changed

- **Chore Interaction:** Changed the long-press gesture on a chore item to open the new edit dialog instead of directly showing a delete confirmation.

---

## 2025-09-12

### Added

- **Configurable Completed Chore Display Duration:** Introduced a new setting to allow users to customize how long completed chores remain visible on the home screen (e.g., 1 hour, 24 hours, 7 days, never).
- **Chore Editing Feature Placeholder:** Added "Chore Editing" to the "Upcoming Features" section in `README.md`.

### Changed

- **Refactored SettingsViewModel Usage:** Centralized `SettingsViewModel` instantiation in `MainActivity.kt` and passed it down the composable hierarchy, improving efficiency and consistency.
- **Implemented Chore Loading Indicator:** Added a `CircularProgressIndicator` to `HomeScreen.kt` to provide visual feedback while chores are being loaded.

### Fixed

- **Compilation Errors in SettingsScreen.kt and AllHistoryScreen.kt:** Resolved type inference and unresolved reference errors related to `CompletedChoreDisplayManager` and `SettingsViewModel` instantiation.
- **Dynamic TopAppBar Titles:** Implemented dynamic titles for the `TopAppBar` based on the current navigation route.

---

## 2025-09-11

### Changed

- **Animation Feature Completion:** Finalized the implementation and integration of animation controls and transitions, ensuring smooth user experience across the application.

---

## 2025-09-10

### Added

- **Enhanced Haptic Feedback System:** Introduced a sophisticated haptic feedback system, providing subtle tactile confirmations for key user interactions such as checking off chores, adding new tasks, and deleting completed items. This feature can be toggled on or off within the settings for personalized user experience.
- **Dynamic Animation Controls:** Implemented a new setting to empower users with control over in-app animations. This allows for a smoother, more modern feel or a simplified, performance-optimized experience based on individual preference.
- **Seamless Navigation Transitions:** Integrated fluid and visually appealing navigation animations, including elegant slide and fade effects, to enhance the user's journey between different screens within the application.
- **Intuitive Item Movement Animations:** Introduced subtle yet effective animations for list items, particularly noticeable when chores are marked as complete and gracefully reposition themselves within the list, providing clear visual feedback.

### Fixed

- **Animation API Compatibility:** Addressed and resolved several critical compilation errors and deprecation warnings stemming from the integration of advanced animation APIs, ensuring robust and future-proof animation functionality.
- **Modular Screen Definitions:** Refactored the application's architecture by extracting the `Screen` sealed class into its own dedicated file, significantly improving code organization, maintainability, and reducing inter-file dependencies.

---

## 2025-09-09

### Changed

- Updated application package name from `com.example.chares` to `com.samm8g.chares`.
- Addressed Kotlinx Serialization opt-in requirement by adding `@OptIn(kotlinx.serialization.InternalSerializationApi::class)` to `Chore.kt`.
- Migrated System UI Controller implementation from deprecated Accompanist library to `androidx.activity.enableEdgeToEdge` API, removing associated dependencies.

---

## 2025-09-08

### Fixed

- Disabled dynamic color to prevent the status bar from turning pink in light mode.
- Adjusted the modal navigation drawer to prevent it from overlapping with the status bar.

### Added

- Implemented immersive mode for an edge-to-edge display.
- Added a "No Chores Found" message to the "All History" screen when it's empty.

---
## 2025-09-07

- No Changelog

---

## 2025-09-06

### Fixed

- Resolved compilation errors related to conflicting `Context.dataStore` declarations.
- Fixed `ClassCastException` crash by aligning `MainActivity` with `AppCompatActivity` and using a `.NoActionBar` theme.
- Corrected theme type mismatch in `MainActivity.kt` (`String` vs. `Int`).
- Implemented robust localization handling by moving locale setting to `ChoreApplication.kt` to correctly manage asynchronous `DataStore` preferences.

### Added

- Initial `README.md` file.
- Initial `CHANGELOG.md` file.
- Initial `BUGS.md` file.
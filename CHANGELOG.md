# CHANGELOG

## 2025-09-08

### Fixed

- Disabled dynamic color to prevent the status bar from turning pink in light mode.
- Adjusted the modal navigation drawer to prevent it from overlapping with the status bar.

### Added

- Implemented immersive mode for an edge-to-edge display.
- Added a "No Chores Found" message to the "All History" screen when it's empty.

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

## 2025-09-09

### Changed

- Updated application package name from `com.example.chares` to `com.samm8g.chares`.
- Addressed Kotlinx Serialization opt-in requirement by adding `@OptIn(kotlinx.serialization.InternalSerializationApi::class)` to `Chore.kt`.
- Migrated System UI Controller implementation from deprecated Accompanist library to `androidx.activity.enableEdgeToEdge` API, removing associated dependencies.
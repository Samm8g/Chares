# CHANGELOG

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

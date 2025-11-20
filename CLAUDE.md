# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

YetAnotherNote is an Android note-taking application built with Kotlin and Jetpack Compose. The project requirements (from Task.md) specify a full-featured notes app with:
- View, add, edit, and delete notes (single and bulk operations)
- Multi-window navigation architecture
- Local data persistence using SQLite + Room
- Permission handling (file access, contacts, geolocation)
- Gesture support (long press for multi-select mode)
- Dark theme and screen rotation support

## Build Commands

```bash
# Build the project
./gradlew build

# Run on connected device/emulator
./gradlew installDebug

# Run tests
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Instrumented tests

# Clean build
./gradlew clean

# Assemble release APK
./gradlew assembleRelease

# Run specific test class
./gradlew test --tests com.disah.yetanothernote.ExampleUnitTest

# Run lint
./gradlew lint
```

## Project Configuration

- **Package**: `com.disah.yetanothernote`
- **Min SDK**: 31
- **Target SDK**: 36
- **Compile SDK**: 36
- **Kotlin Version**: 2.0.21
- **AGP Version**: 8.13.1
- **Java Version**: 11

## Architecture Notes

### Current State
The project is in initial state with a basic Jetpack Compose setup. MainActivity contains a simple greeting screen.

### Planned Architecture (per Task.md requirements)
- **UI Layer**: Jetpack Compose with Material3 for all screens
- **Navigation**: Multi-screen architecture (list view, detail view, edit view)
- **Data Layer**: Room database for local persistence (needs to be implemented)
- **Permission Management**: Runtime permission handling for files, contacts, and location
- **Theme**: Material3 with dark theme support

### Source Structure
- Main source: `app/src/main/java/com/disah/yetanothernote/`
- UI theme files: `app/src/main/java/com/disah/yetanothernote/ui/theme/`
- Tests: `app/src/test/` (unit) and `app/src/androidTest/` (instrumented)

## Dependencies Management

Dependencies are managed using Gradle version catalogs in `gradle/libs.versions.toml`. To add new dependencies, update the versions file rather than directly modifying build.gradle.kts files.

Current key dependencies:
- Jetpack Compose BOM (2024.09.00)
- Material3
- AndroidX Core KTX, Lifecycle, Activity Compose
- JUnit and Espresso for testing

## Development Notes

When implementing the notes app requirements:
1. Room database setup will need dependencies added to `gradle/libs.versions.toml` and `app/build.gradle.kts`
2. Navigation component will be needed for multi-screen architecture
3. Permission handling should follow Android 12+ (API 31+) runtime permission model
4. Gesture detection should be implemented using Compose gesture modifiers
5. Dark theme is already scaffolded in `ui/theme/Theme.kt` and should follow Material3 dynamic color patterns

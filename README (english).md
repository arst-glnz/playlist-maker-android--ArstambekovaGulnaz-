# Playlist Maker

## Project Overview

Playlist Maker is a mobile application for creating and managing music playlists.

The application allows users to:

- search for music tracks via the internet;
- view search results;
- add tracks to favorites;
- create custom playlists;
- add tracks to playlists;
- view and manage existing playlists.

Favorite tracks are stored locally and available in the “Favorites” section.

---

## Application Interface

### Track Search
The application provides track search functionality via the internet with real-time result display.

<img width="630" height="1391" alt="Search screen" src="https://github.com/user-attachments/assets/5c4524e4-2242-4311-a4e7-d7fc8d54d1aa" />

---

### Favorites
Users can add tracks to favorites for quick access. Data is stored locally on the device.

<img width="633" height="1393" alt="Favorites screen" src="https://github.com/user-attachments/assets/ae86c096-55e6-459f-b69e-a35e0bd4bf20" />
<img width="633" height="1401" alt="Favorites details" src="https://github.com/user-attachments/assets/bcec7efb-f2b9-4a16-93c2-b9a40283f387" />

---

### Playlists
Users can create and manage playlists:

- create new playlists;
- add tracks to existing playlists;
- view playlist contents;
- edit and manage playlists.

<img width="635" height="1398" alt="Playlists screen" src="https://github.com/user-attachments/assets/59f3902c-df41-4bf8-a8d8-a2345de73d89" />
<img width="630" height="1395" alt="Playlist details" src="https://github.com/user-attachments/assets/5e0a3cf8-df68-41bd-9536-98b3aa5bb2f2" />
<img width="618" height="1393" alt="Playlist edit" src="https://github.com/user-attachments/assets/4a64c40e-4c7d-440c-995a-0d406e2ba485" />
<img width="620" height="1398" alt="Playlist management" src="https://github.com/user-attachments/assets/8e1fba14-5dab-448b-a88f-92d02a276443" />

---

### Settings
Users can:

- switch between light and dark themes;
- share an application download link;
- manage additional preferences.

<img width="631" height="1393" alt="Settings screen" src="https://github.com/user-attachments/assets/640a2d69-6430-4d21-aad2-c7f81ba7ddb2" />

---

## Architecture

The application is built using the **MVVM (Model–View–ViewModel)** architectural pattern, which separates business logic, data handling, and UI components.

### Model
The data layer responsible for fetching, storing, and processing information.

Technologies used:

- Retrofit — network communication;
- Room Database — local data storage;
- DataStore — user preferences storage.

### View
The UI layer implemented using Jetpack Compose.

Responsible for rendering data and handling user interactions.

### ViewModel
Acts as a bridge between the UI and data layers.

Responsibilities:

- retrieving data from repositories;
- handling user actions;
- managing UI state;
- preparing data for presentation.

---

## Tech Stack

### UI
- Jetpack Compose
- Material 3
- Navigation Compose

### Networking
- Retrofit
- Gson Converter

### Local Storage
- Room Database
- DataStore Preferences

### Image Loading
- Coil

### Development Tools
- Kotlin 2.2.21
- KSP (Kotlin Symbol Processing)
- Android Studio 2025.1.3.7

---

## Requirements

- min SDK: 29
- target SDK: 36

---

## Build and Run

1. Clone the repository:
```bash
git clone <repository_url>
```
2. Open the project in Android Studio.
3. Wait for Gradle sync to complete.
4. Run the application on a physical device or emulator (API 29+).
5. Click Run or use Shift + F10.

# Playlist Maker

## Описание проекта

Playlist Maker — мобильное приложение для создания и управления музыкальными плейлистами.

Приложение позволяет:

- искать музыкальные треки через интернет;
- просматривать результаты поиска;
- добавлять треки в избранное;
- создавать собственные плейлисты;
- добавлять треки в плейлисты;
- просматривать и редактировать созданные плейлисты.

Избранные треки сохраняются локально и доступны во вкладке «Избранное».

---

## Интерфейс приложения

### Поиск треков
Реализован поиск музыкальных треков через интернет с отображением результатов.

<img width="630" height="1391" alt="Search screen" src="https://github.com/user-attachments/assets/5c4524e4-2242-4311-a4e7-d7fc8d54d1aa" />

---

### Избранные треки
Поддерживается добавление треков в избранное для быстрого доступа. Данные сохраняются локально.

<img width="633" height="1393" alt="Favorites screen" src="https://github.com/user-attachments/assets/ae86c096-55e6-459f-b69e-a35e0bd4bf20" />
<img width="633" height="1401" alt="Favorites details" src="https://github.com/user-attachments/assets/bcec7efb-f2b9-4a16-93c2-b9a40283f387" />

---

### Плейлисты
Пользователь может создавать и управлять плейлистами:

- создавать новые плейлисты;
- добавлять треки в существующие плейлисты;
- просматривать содержимое плейлистов;
- редактировать плейлисты.

<img width="635" height="1398" alt="Playlists screen" src="https://github.com/user-attachments/assets/59f3902c-df41-4bf8-a8d8-a2345de73d89" />
<img width="630" height="1395" alt="Playlist details" src="https://github.com/user-attachments/assets/5e0a3cf8-df68-41bd-9536-98b3aa5bb2f2" />
<img width="618" height="1393" alt="Playlist edit" src="https://github.com/user-attachments/assets/4a64c40e-4c7d-440c-995a-0d406e2ba485" />
<img width="620" height="1398" alt="Playlist management" src="https://github.com/user-attachments/assets/8e1fba14-5dab-448b-a88f-92d02a276443" />

---

### Настройки
Пользователь может:

- изменять тему приложения (светлая / тёмная);
- делиться ссылкой на приложение;
- управлять дополнительными настройками.

<img width="631" height="1393" alt="Settings screen" src="https://github.com/user-attachments/assets/640a2d69-6430-4d21-aad2-c7f81ba7ddb2" />

---

## Архитектура проекта

Приложение построено с использованием архитектурного паттерна **MVVM (Model–View–ViewModel)**.

### Model
Слой данных, отвечающий за получение, хранение и обработку информации.

Используются:

- Retrofit — работа с сетью;
- Room Database — локальное хранение данных;
- DataStore — хранение пользовательских настроек.

### View
Пользовательский интерфейс, реализованный с помощью Jetpack Compose.

Отвечает за отображение данных и обработку пользовательских действий.

### ViewModel
Связывает UI и слой данных.

Отвечает за:

- получение данных из репозиториев;
- обработку пользовательских действий;
- управление состоянием экранов;
- подготовку данных для отображения в UI.

---

## Технологический стек

### UI
- Jetpack Compose
- Material 3
- Navigation Compose

### Работа с сетью
- Retrofit
- Gson Converter

### Локальное хранение данных
- Room Database
- DataStore Preferences

### Работа с изображениями
- Coil

### Инструменты разработки
- Kotlin 2.2.21
- KSP (Kotlin Symbol Processing)
- Android Studio 2025.1.3.7

---

## Требования

- min SDK: 29
- target SDK: 36

---

## Сборка и запуск проекта

1. Склонируйте репозиторий:
```bash
git clone <repository_url>
```
2. Откройте проект в Android Studio.
3. Дождитесь завершения синхронизации Gradle.
4. Запустите приложение на устройстве или эмуляторе (API 29+).
5. Нажмите Run или используйте Shift + F10.

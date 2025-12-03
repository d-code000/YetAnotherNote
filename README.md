# YetAnotherNote

Android приложение для заметок, созданное с использованием Kotlin и Jetpack Compose.

## Требования

- **Android Studio**: Ladybug | 2024.2.1 или новее
- **JDK**: Java 11 или выше
- **Android SDK**:
  - Compile SDK: 36
  - Min SDK: 31
  - Target SDK: 36
- **Gradle**: 8.9 (используется через wrapper)

## Настройка проекта

### 1. Клонирование репозитория

```bash
git clone <repository-url>
cd YetAnotherNote2
```

### 2. Открытие проекта в Android Studio

1. Запустите Android Studio
2. Выберите **File → Open**
3. Найдите папку проекта `YetAnotherNote2` и нажмите **OK**
4. Android Studio автоматически начнет синхронизацию Gradle

### 3. Настройка Android SDK

1. Откройте **File → Settings** (или **Android Studio → Preferences** на macOS)
2. Перейдите в **Languages & Frameworks → Android SDK**
3. Убедитесь, что установлены следующие компоненты:
   - **SDK Platforms**: Android 14.0 (API 36)
   - **SDK Tools**:
     - Android SDK Build-Tools 36
     - Android Emulator
     - Android SDK Platform-Tools

4. Если компоненты не установлены, выберите их и нажмите **Apply**

### 4. Настройка Java JDK

1. Откройте **File → Project Structure** (Ctrl+Alt+Shift+S)
2. В разделе **SDK Location** убедитесь, что выбран JDK 11 или выше
3. Если JDK не настроен:
   - Нажмите на выпадающий список **Gradle JDK**
   - Выберите подходящую версию или скачайте через **Download JDK**

### 5. Синхронизация Gradle

После открытия проекта Android Studio должна автоматически синхронизировать зависимости. Если этого не произошло:

1. Нажмите на иконку слона (Gradle) в правом верхнем углу
2. Или выберите **File → Sync Project with Gradle Files**
3. Дождитесь завершения синхронизации

## Запуск проекта

### Запуск на эмуляторе

1. Создайте виртуальное устройство:
   - Откройте **Tools → Device Manager**
   - Нажмите **Create Device**
   - Выберите устройство (например, Pixel 5)
   - Выберите System Image с API 31 или выше
   - Завершите настройку

2. Запустите приложение:
   - Выберите эмулятор в списке устройств (Device dropdown)
   - Нажмите зеленую кнопку **Run** (▶) или нажмите Shift+F10

### Запуск на физическом устройстве

1. Включите режим разработчика на Android устройстве:
   - **Настройки → О телефоне → Номер сборки** (нажмите 7 раз)

2. Включите отладку по USB:
   - **Настройки → Для разработчиков → Отладка по USB**

3. Подключите устройство к компьютеру через USB

4. Разрешите отладку по USB на устройстве (появится диалог)

5. Выберите ваше устройство в списке и нажмите **Run**

### Команды Gradle (альтернативный способ)

#### Windows (PowerShell/CMD):

```bash
# Сборка проекта
.\gradlew.bat build

# Установка на устройство
.\gradlew.bat installDebug

# Запуск тестов
.\gradlew.bat test

# Очистка проекта
.\gradlew.bat clean
```

#### Linux/macOS:

```bash
# Сборка проекта
./gradlew build

# Установка на устройство
./gradlew installDebug

# Запуск тестов
./gradlew test

# Очистка проекта
./gradlew clean
```

## Возможные проблемы

### Ошибка "SDK not found"

Убедитесь, что Android SDK установлен и путь указан корректно в **File → Project Structure → SDK Location**.

### Ошибка "Unsupported Java version"

Проверьте, что используется JDK 11 или выше в **File → Project Structure → SDK Location → Gradle JDK**.

### Ошибка синхронизации Gradle

1. Выберите **File → Invalidate Caches → Invalidate and Restart**
2. После перезапуска выполните синхронизацию заново

### Эмулятор не запускается

Убедитесь, что:
- Виртуализация включена в BIOS (Intel VT-x или AMD-V)
- Установлен Intel HAXM (для процессоров Intel) или Hyper-V настроен правильно

## Конфигурация проекта

- **Package**: `com.disah.yetanothernote`
- **Kotlin**: 2.0.21
- **Android Gradle Plugin**: 8.13.1
- **Compose BOM**: 2024.09.00

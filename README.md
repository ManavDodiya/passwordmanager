# 🔐 Password Manager – Android App (Jetpack Compose)

A modern and secure **Android Password Manager** built using **Jetpack Compose**, **MVVM architecture**, and **AES-GCM encryption**.  
This application allows users to securely store, view, edit, and delete account credentials locally on their device.

---

## 📥 Download

👉 [Download latest APK](https://github.com/ManavDodiya/passwordmanager/releases/latest)


## 📱 Features

- Secure password storage using **AES-GCM encryption**
- Clean and modern UI with **Jetpack Compose (Material 3)**
- Add, edit, view, and delete saved accounts
- Password visibility toggle
- Bottom sheet–based user interactions
- Offline-first (local database)
- Preview-friendly Compose UI components

---

## 🏗️ Architecture

The project follows **MVVM (Model–View–ViewModel)** architecture to ensure scalability and maintainability.


### Architecture Highlights
- UI layer is **stateless and reactive**
- Business logic handled inside ViewModels
- Encryption and decryption handled **outside Composables**
- Database stores **only encrypted passwords**

---

## 🧰 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Architecture:** MVVM
- **Database:** Room
- **State Management:** StateFlow
- **Encryption:** AES-GCM
- **Build System:** Gradle (Kotlin DSL)

---

## 🔐 Security Details

- Passwords are encrypted using **AES-GCM**
- Only encrypted values are stored in the database
- Decryption is performed outside the UI layer
- No sensitive data is logged
- Designed to avoid cryptographic issues caused by recomposition

> ⚠️ This project is intended for learning and personal use.  
> For production usage, additional security layers such as **Android Keystore**, **Biometric authentication**, and **secure backups** should be implemented.




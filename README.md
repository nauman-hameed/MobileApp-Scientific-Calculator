**📱 MobileApp Scientific Calculator**

A fully functional Scientific Calculator Android App built using Kotlin in Android Studio, designed to perform both basic and advanced mathematical operations with a clean, modern interface inspired by the iPhone calculator.

**🧮 Features
🔢 Basic Operations**

Addition +
Subtraction −
Multiplication ×
Division ÷

**🧠 Scientific Functions**

Trigonometric: sin, cos, tan
Logarithmic: log, ln
Square root: √
Power / Exponentiation: xʸ
Factorial: n!
Pi and e constants
💡 Extra Features
User-friendly interface with real-time input display
Error handling (e.g., division by zero or invalid inputs)
Portrait + landscape orientation support
Modern material UI design
**
🏗️ Tech Stack

Language: Kotlin
IDE: Android Studio
Minimum SDK: 24 (Android 7.0)
UI Layout: XML
Architecture: Simple MVC**

**🧰 Folder Structure**
app/
 ├── manifests/              # AndroidManifest.xml
 ├── java/                   # Kotlin source code (MainActivity, logic)
 ├── res/
 │   ├── layout/             # XML layout files (activity_main.xml)
 │   ├── values/             # colors.xml, strings.xml, themes.xml
 │   └── drawable/           # app icons and assets
 └── build.gradle

**🚨 Error Handling**

Displays “Invalid Input” for malformed expressions.
Division by zero shows a friendly “Cannot divide by zero” message.
Prevents crashes with input validation.

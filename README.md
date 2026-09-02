# 🧮 Calculator Suite

### Java Desktop Application

**Calculator Suite** is a Java-based desktop application that combines multiple calculation tools into a single, easy-to-use platform. Built using **Java Swing**, the application provides a clean graphical user interface with a modular structure.

It is designed for students and everyday users who need quick access to essential mathematical and academic calculation tools.

---

## ✨ Features

### 🧮 Basic Calculator

* Addition, subtraction, multiplication, and division
* Decimal number calculations
* Percentage calculations
* Clear and backspace functionality
* Keyboard input support
* Basic error handling

### 🔬 Scientific Calculator

* Trigonometric functions
* Logarithmic calculations
* Square root
* Power and exponential operations
* Advanced mathematical expressions

### 🎓 CGPA Calculator

* Subject-wise grade input
* Credit-based calculation
* Automatic grade-point conversion
* Automatic CGPA calculation
* Support for multiple subjects
* **F Grade = 2 Grade Points**

### 🖥️ Dashboard

* Centralized application navigation
* Calculator module cards
* Live date and time
* Application information
* University-themed interface

### 🚀 Splash Screen

* Application startup screen
* Smooth transition to the main dashboard
* Professional application launch experience

---

## 📌 Project Overview

Calculator Suite provides a centralized dashboard from which users can launch different calculator modules.

The application currently consists of:

| Module                   | Description                                 |
| ------------------------ | ------------------------------------------- |
| 🧮 Basic Calculator      | Performs everyday arithmetic calculations   |
| 🔬 Scientific Calculator | Performs advanced mathematical operations   |
| 🎓 CGPA Calculator       | Calculates credit-weighted CGPA             |
| 🖥️ Dashboard            | Provides centralized application navigation |
| 🚀 Splash Screen         | Displays the application startup interface  |

The project focuses on combining functionality with a simple and modern desktop user interface.

---

## 🛠️ Technologies Used

| Technology     | Purpose                           |
| -------------- | --------------------------------- |
| **Java**       | Core programming language         |
| **Java Swing** | Graphical User Interface          |
| **AWT**        | GUI components and event handling |
| **VS Code**    | Development environment           |
| **Git**        | Version control                   |
| **GitHub**     | Source code hosting               |

---

## 📂 Project Structure

```text
Calculator-Suite-VSCode
│
├── src
│   ├── Main.java
│   ├── SplashScreen.java
│   ├── Dashboard.java
│   ├── BasicCalculator.java
│   ├── ScientificCalculator.java
│   └── CGPACalculator.java
│
├── screenshots
│   ├── dashboard.png
│   ├── basic-calculator.png
│   ├── scientific-calculator.png
│   └── cgpa-calculator.png
│
└── README.md
```

---

## ▶️ How to Run

### Prerequisites

Make sure the following are installed:

* **Java JDK**
* **Visual Studio Code** or any Java-supported IDE
* **Git** (optional)

### 1. Clone the Repository

```bash
git clone https://github.com/bhardwajkrish868-design/Calculator.git
```

### 2. Navigate to the Project

```bash
cd Calculator
```

Then navigate to the source directory:

```bash
cd src
```

### 3. Compile the Java Files

```bash
javac *.java
```

### 4. Run the Application

```bash
java Main
```

The Calculator Suite application should now launch on your desktop.

---

## 🎓 CGPA Calculation

The CGPA Calculator uses a **credit-weighted calculation**.

### Formula

```text
CGPA = Σ(Credit × Grade Point) / Σ(Credits)
```

### Grade Point System

| Grade | Grade Point |
| :---: | :---------: |
|   O   |      10     |
|   E   |      9      |
|   A   |      8      |
|   B   |      7      |
|   C   |      6      |
|   D   |      5      |
|   F   |      2      |

> **Note:** In this application, the **F grade is assigned 2 grade points**.

### Example

Suppose a student has:

| Subject     | Credit | Grade | Grade Point |
| ----------- | -----: | :---: | ----------: |
| Mathematics |      4 |   A   |           8 |
| Programming |      4 |   O   |          10 |
| Physics     |      3 |   B   |           7 |

Then:

```text
CGPA = (4×8 + 4×10 + 3×7) / (4+4+3)

     = (32 + 40 + 21) / 11

     = 93 / 11

     = 8.45
```

---

## 📸 Application Screenshots

### 🖥️ Dashboard

![Dashboard](screenshots/dashboard.png)

The dashboard provides centralized access to all calculator modules along with application information and live date/time.

---

### 🧮 Basic Calculator

![Basic Calculator](screenshots/basic-calculator.png)

The Basic Calculator supports common arithmetic operations such as addition, subtraction, multiplication, division, decimal calculations, and percentage calculations.

---

### 🔬 Scientific Calculator

![Scientific Calculator](screenshots/scientific-calculator.png)

The Scientific Calculator provides advanced mathematical operations including trigonometric, logarithmic, exponential, square-root, and power calculations.

---

### 🎓 CGPA Calculator

![CGPA Calculator](screenshots/cgpa-calculator.png)

The CGPA Calculator calculates the student's CGPA based on subject credits and corresponding grade points.

---

## 🎯 Objectives

The main objectives of the Calculator Suite project are:

* Apply Java programming concepts in a real-world application.
* Understand Java Swing GUI development.
* Implement event-driven programming.
* Practice Object-Oriented Programming concepts.
* Build reusable calculator modules.
* Develop a structured desktop application.
* Improve problem-solving and programming skills.
* Learn Git and GitHub project management.
* Create an easy-to-use graphical application.

---

## 🧠 Concepts Demonstrated

The project demonstrates several important Java programming concepts:

* Object-Oriented Programming
* Classes and Objects
* Methods and Constructors
* Encapsulation
* Event Handling
* GUI Programming
* Java Swing
* AWT Components
* Exception Handling
* Mathematical Operations
* Modular Programming
* User Input Validation

---

## 🔮 Future Scope

The application can be expanded with additional utilities and features such as:

* 📅 Date Difference Calculator
* 🎂 Age Calculator
* 📊 Percentage Calculator
* 📏 Unit Converter
* 💰 Financial Calculator
* 🧮 Statistics Calculator
* 🕘 Calculation History
* 🌙 Dark Mode
* 💾 Save Calculation Results
* 📱 Improved User Interface
* ⌨️ Extended Keyboard Shortcuts

---

## 👨‍💻 Developer

### Krish Bhardwaj

**B.Tech — Computer Science & Engineering**

* **GitHub:** [bhardwajkrish868-design](https://github.com/bhardwajkrish868-design)
* **LinkedIn:** [Krish Bhardwaj](https://www.linkedin.com/in/krish-bhardwaj-972828379/)

---

## 🔗 Project Repository

### Calculator Suite — GitHub

[View Project Repository](https://github.com/bhardwajkrish868-design/Calculator)

---

## ⭐ Support

If you find this project useful, consider giving the repository a ⭐ **Star** on GitHub.

---

<div align="center">

# 🧮 Calculator Suite

### Java Desktop Application

**Developed by Krish Bhardwaj**

⭐ **Built with Java Swing**

</div>

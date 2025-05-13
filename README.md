# Automation Testing Project
Graduation Project for DEPI 

## Overview
This project is an automated testing framework designed to validate web applications using Selenium WebDriver with Java. It follows the Page Object Model (POM) design pattern for better maintainability and scalability.

## Key Features
- **Page Object Model**: Implemented for clean separation between test logic and page-specific code
- **Cross-Browser Testing**: Supports Chrome and MS Edge browsers
- **TestNG Integration**: For test execution and reporting
- **Logging**: Integrated logging for better test execution tracking

## Technologies Used
- Java 8+
- Selenium WebDriver
- TestNG
- Maven
- WebDriverManager (for automated driver management)

## Project Structure
```
Automation-Testing-Project/
├── src/main/java/
│   ├── pages/          # Page object classes
│   ├── utilities/      # Helper classes and utilities
│   └── constants/      # Constant values used across tests
├── src/test/java/
│   ├── tests/          # Test classes
│   └── listeners/      # TestNG listeners
├── src/test/resources/
│   ├── testdata/       # Test data files
│   └── config.properties # Configuration file
├── pom.xml            # Maven dependencies
└── testng.xml         # TestNG configuration
```

## Setup Instructions

### Prerequisites
- Java JDK 8 or higher
- Maven 3.6.0 or higher
- Chrome/Firefox browsers installed

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/DoaaTawfik0/Automation-Testing-Project.git
   ```
2. Navigate to project directory:
   ```bash
   cd Automation-Testing-Project
   ```
3. Install dependencies:
   ```bash
   mvn clean install
   ```

## Running Tests
To run all tests:
```bash
mvn test
```

To run specific test suite:
```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

## Configuration
Edit `src/test/resources/config.properties` to configure:
- Browser type (chrome/firefox)
- Base URL
- Timeout settings
- Other environment-specific parameters

## Best Practices Followed
1. Implemented Page Object Model for maintainable code
2. Used explicit waits instead of thread.sleep()
3. Created utility classes for common functions
4. Implemented proper logging mechanism
5. Used WebDriverManager for automated driver management
6. Followed consistent naming conventions

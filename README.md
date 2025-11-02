# Comprehensive Text Editor Connected with jenkins-agent and jenkins-master
Path: Git->Jenkins->SonarQube->Docker->Kubernets
## Overview
The Comprehensive Text Editor is a Java-based web application designed to provide a rich text editing experience. It leverages modern web technologies and follows best practices in software development.

## Features
- User-friendly interface for text editing.
- Support for various text formats.
- Configuration options for customization.
- Unit tests to ensure code quality.

## Project Structure
```
Comprehensive-Text-Editor
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── texteditor
│   │   │               └── App.java
│   │   ├── resources
│   │   │   └── application.properties
│   │   └── webapp
│   │       ├── WEB-INF
│   │       │   └── web.xml
│   │       └── index.jsp
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── texteditor
│                       └── AppTest.java
├── Dockerfile
├── Jenkinsfile
├── pom.xml
├── .gitignore
└── README.md
```

## Getting Started

### Prerequisites
- Java 17
- Maven 3
- Docker (for containerization)

### Installation
1. Clone the repository:
   ```
   git clone https://github.com/Neel-29/Comprehensive-Text-Editor.git
   ```
2. Navigate to the project directory:
   ```
   cd Comprehensive-Text-Editor
   ```
3. Build the project using Maven:
   ```
   mvn clean package
   ```

### Running the Application
To run the application locally, use the following command:
```
mvn spring-boot:run
```

### Docker
To build and run the application in a Docker container, use the following commands:
1. Build the Docker image:
   ```
   docker build -t comprehensive-text-editor .
   ```
2. Run the Docker container:
   ```
   docker run -p 8080:8080 comprehensive-text-editor
   ```

### Testing
To run the unit tests, execute:
```
mvn test
```

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.
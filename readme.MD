# Discussion Board

## Overview
This is a simple discussion board application built with Spring Boot and MongoDB. Users can view and create discussions.

## Features
- View all discussions
- Create new discussions

## Technologies Used
- Java
- Spring Boot
- Thymeleaf
- MongoDB
- Gradle

## Getting Started

### Prerequisites
- Java 11 or higher
- MongoDB
- Gradle

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/discussion-board.git
    cd discussion-board
    ```

2. Update the MongoDB URI in `src/main/resources/application.properties` if necessary:
    ```ini
    spring.data.mongodb.uri=mongodb://localhost:27017/springdiscussionboard
    ```

3. Build the project:
    ```sh
    ./gradlew build
    ```

4. Run the application:
    ```sh
    ./gradlew bootRun
    ```

### Usage
- Access the application at `http://localhost:8080`.
- Navigate to `/dashboard` to view all discussions.
- Click on "Create new discussion" to add a new discussion.

## Project Structure
- `src/main/java/com/discussionboard/db` - Contains the main application and controller classes.
- `src/main/resources/templates` - Contains Thymeleaf templates.
- `src/main/resources/application.properties` - Configuration properties.

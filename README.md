# Quizzler

Quizzer is a web application designed for teachers to create and manage quizzes and for students to take quizzes and receive feedback. The project follows Scrum methodology and is developed in three sprints. The project is built with Java Spring Boot (backend) and JavaScript React (frontend).

https://software-development-project-1-git-quizzer.2.rahtiapp.fi 

## 🧩 Features
### For Teachers:

- Create and manage quizzes with questions
- Assign quizzes to specific classes
- View quiz results and analytics for student performance

### For Students:

- Take assigned quizzes with real-time feedback
- View results
- Review quizzes


## 🛠️ Tech Stack

| Layer     | Technology       |
|-----------|------------------|
| Backend   | Java, Spring Boot|
| Build Tool| Maven            |
| Frontend  | React, HTML, CSS, JavaScript|
| Database  | H2 / PostgreSQL |

## Contributors

- [Markus Mäntylä](https://github.com/MarkusMant)
- [Robbie Winter](https://github.com/robbiewinter)
- [Meeri Klemola](https://github.com/MeeriKlemola)
- [Kaisla Kudjoi](https://github.com/kaislakudjoi)
- [Noora Laakso](https://github.com/nooralaakso)

## Backlog
https://github.com/orgs/commit-ment-issues/projects/1

## Retrospective 1
https://edu.flinga.fi/s/ENTN72M

## Retrospective 2
https://edu.flinga.fi/s/ECXCXME

## Developer guide

This section provides instructions on how to get the backend application running locally via the command-line interface.

## 🚀 Getting Started
### Requirement

- Java 17+
- Maven 3.6+

### Backend

1. **Clone the repository**
   ```bash
   git clone (https://github.com/commit-ment-issues/software-development-project-1.git)

   git clone (git@github.com:commit-ment-issues/software-development-project-1.git)

   cd 'repository-directory'

2. **Build the project**
    ```bash
    ./mvnw clean install

3. **Start application**
    ```bash
    ./mvnw spring-boot:run

4. **Once the application has started**

    visit http://localhost:8080/

### Frontend

1. **Navigate to the frontend folder**
    
    ```bash
    cd Quizzer
2. **Install dependencies**
    
    ```bash
    npm install
3. **Start the frontend application**
    
    ```bash
    npm run dev
4. **Access the frontend**
    
    Open your browser and visit the URL displayed in the terminal (e.g. http://localhost:5173/).


## Data model

```mermaid
erDiagram
    QUIZ ||--o{ QUESTION : 1N

    CATEGORY ||--o{ QUIZ : 1N

    ANSWERS o{--|| QUESTION : N1

    REVIEW o{--|| QUIZ : N1

    REVIEW {
        long reviewId
        string reviewerNickname
        int rating
        string reviewText
        localDate date
    }
    USER {
        int id
        string name
        string email
        string password
    }
    QUIZ {
        long quizId
        string name
        string description
        string courseCode
        int publishedStatus
        LocalDate creationDate
    }
    QUESTION {
        long questionId
        string questionText
        string difficulty
        int totalAnswers
        int correctAnswers
    }
    ANSWERS {
        long id
        string text
        int status
    }
    CATEGORY {
        long categoryid
        string name
        string description
    }
```
### Entities

#### USER
- Represents the user of the application
- Attributes:
    - id: Unique identifier for user
    - name: Name of the user
    - email: Email of the user
    - password: Password for user authentication
- Relationships:
    - There are currently no relationship between user or any other entity

#### QUIZ
- Represents a quiz created by a user
- Attributes:
    - quizId: Unique identifier for the quiz
    - name: Name of the quiz
    - description: Description of the quiz
    - courseCode: Code for the course associated with the quiz
    - publishedStatus: Status that indicates whether the quiz is published or not
    - creationDate: Date for when the quiz was created
- Relationships:
    - One-to-Many relationship with QUESTION
    - Many-to-One relationship with CATEGORY
    - One-to-Many relationship with REVIEW

#### QUESTION
- Represents a question in a quiz
- Attributes:
    - id: Unique identifier for the question
    - questionText: The text of the question
    - difficulty: Difficulty level for the question
    - totalAnswers: Number of total answers of the quiz
    - correctAnswers: Number of correct answers of the quiz
- Relationships:
    - Many-to-One with QUIZ
    - One-to-Many with ANSWERS

#### ANSWERS
- Represents possible answers to a question
- Attributes:
    - id: Unique identifier for the answer
    - text: The text of the answer
    - status: Indicates whether the answer is correct or not
- Relationships:
    - Many-to-One with QUESTION

#### CATEGORY

- Represents a category to which quizzes can belong
- Attributes:
    - id: Unique identifier for the category
    - name: Name of the category
    - description: Description of the category
- Relationships: One-to-Many with QUIZ

#### REVIEW

- Represents the reviews of a quiz
- Attributes:
    - id: Unique identifier for the review
    - reviewerNickname: Nickname for the reviewer
    - rating: Rating given to the quiz
    - text: Review text given to the quiz
    - date: Creation date for the review
- Relationships: Many-to-One with QUIZ

## License
This project is licensed under the terms of the [MIT license.](./LICENSE.txt)


## How to run the tests on the command-line

### Run the backend tests:

   ```bash
    ./mvnw test


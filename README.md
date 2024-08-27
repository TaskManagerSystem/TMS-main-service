# Task Manager Service

## Overview
This project is a web-based project management system designed to
help users manage projects, tasks, comments, attachments, labels,
and notifications. The system is broken down into several
microservices, each responsible for a specific domain of
the application. The system is scalable, secure, and integrates
with third-party services like Dropbox for attachment storage and
email/Telegram for notifications.

## Microservices Overview:

1. **Main Service**:

   &nbsp;&nbsp;&nbsp;&nbsp;*Responsibilities*:
   - User management (registration, authentication, roles, and permissions)
   - Project management (CRUD operations)
   - Task management (CRUD operations, assignment, status tracking)
   - Comment management (adding, viewing, deleting comments on tasks)
   - Label management (creating, modifying, deleting labels and assigning them to tasks)
2. **[Attachment Service](https://github.com/TaskManagerSystem/TMS-attachment-service)**:

   &nbsp;&nbsp;&nbsp;&nbsp;*Responsibilities*:
   - Managing file attachments for tasks 
   - Storing files on Dropbox and saving metadata in the database 
   - Retrieving and deleting attachments
3. **[Notification Service](https://github.com/TaskManagerSystem/TMS-notification-service)**:

   &nbsp;&nbsp;&nbsp;&nbsp;*Responsibilities*:
   - Sending notifications to users about task deadlines, new comments, and other relevant updates 
   - Integrating with external communication channels like email and Telegram

## Technologies and 
This project utilizes a variety of technologies and tools to ensure
a robust, scalable, and maintainable car sharing service.
Below is an overview of the main technologies and tools used:

### Backend
- **Java**: The primary programming language used for the backend services.
- **Spring Boot**: A powerful framework for building production-ready
applications quickly, providing features such as
dependency injection, web frameworks, data access, and security.
- **Spring Security**: Used to handle authentication and authorization.
- **Spring Data JPA**: Simplifies data access and management using
Java Persistence API.
- **Liquibase**: An open-source library for tracking,
managing, and applying database schema changes.
- **Apache Kafka**: A distributed event streaming platform used as 
a message broker to handle asynchronous communication between
microservices.
- **JavaMail API**: Used to send email notifications to users about important updates.
- **Telegram API**: Integrated to send notifications via 
a Telegram bot to users for task-related
### Database
- **MySQL**: A widely-used open-source relational database system,
chosen for its reliability, ease of use, and support for complex
queries and transactions.
### CI/CD:
- **GitHub Actions**: Provides CI capabilities to automate building 
and testing the application.
### Containerization:
- **Docker**: Used to create, deploy, and run application
in containers.
- **Docker Compose**: Used for defining and running multi-container
Docker application.
### API Documentation:
- **Swagger/OpenApi**: Tools for generating and visualizing API 
documentation, making it easier to understand and use the API 
- endpoints.
### Code Quantity:
- **Checkstyle**: A development tool to help ensure that Java code 
adheres a coding standard.
- **Maven**: A build automation tool used primary for Java projects.
It simplifies the build process and dependency management.
### Version Control:
- **Git**: Version control system for managing source code.
- **GitHub**: Platform for hosting the repository and managing issues,
branches, and pull requests.
### Projects Management:
- **Trello**: A task management tool used for tracking project progress and managing tasks.
### Development Environment:
- **IntelliJIdea**: An integrated development environment for Java development.
- **Postman**: A tool for testing APIs by sending request and receiving response.
### Logging and Monitoring:
- **SLF4J with Logback**: For logging application activities.
- **Spring Boot Actuator**: Provides production-ready features like
monitoring, metrics, and health checks.
### Environment managing:
- **dotenv (.env)**: A module that loads environment variables from a '.env'
file into the application, ensuring sensitive information is kept secure and not
pushed to version control.

## Feature and functionality:
### User management:
1. **Registration**: Users can register on the platform by providing their email, first name, last name, and password.
    - [Registers a new user - demo.](https://drive.google.com/file/d/1V78I2bf0_0jdg136h_TWa6mlFnaiI3Cm/view?usp=sharing)
    ```bash
   POST: api/auth/register
   ``` 

2. **Authentication**: Secure login functionality using JWT tokens to manage sessions.Users can log in using their credentials (email and password).
    - [Sign in for existing user - demo.](https://drive.google.com/file/d/1Dwyg90Y1mwMzB_VOF0zFdXJDFy4wr7dF/view?usp=drive_link)
   ```bash
   POST: api/auth/login
   ```

3. **User Profiles**: Users can view and updated their pro
file information.
    - [View profile - demo](https://drive.google.com/file/d/1o0v-JUnE_YOnaHM8NhMZgkzYLas2J4yc/view?usp=drive_link)
   ```bash
   GET: api/user/me
   ```
    - [Update profile - demo](https://drive.google.com/file/d/1m8lhnajRDTp_ADoLdreP7jswQwkZcwC9/view?usp=drive_link)
   ```bash
   PUT: api/users/me
   ```
4. **Role Management**: Admins can assign roles (ADMIN or USER) to users,
controlling access to certain features.
### Project Controller:
1. **Create a new project**: Admin can create a new project.
   - [Create project - demo](https://drive.google.com/file/d/1fN2DhyKExDO2KhIt4uXxhIbXjyWCBHDL/view?usp=sharing)
   ```bash
    POST: /api/projects
   ```
2. **Find project**: Authorized user can find project by specific Id:
   - [Find project - demo](https://drive.google.com/file/d/1u2MwWuAlH3DgS5cKRXgj6FI9bv-uvYYO/view?usp=sharing)
   ```bash
   GET: /api/projects/{projectId}
   ```
3. **Find projects for user**: Admin can find all his projects
   - [Find projects for user - demo](https://drive.google.com/file/d/1B6DEVsFpq532Hk2ME2TBiCdUb07ePFMx/view?usp=sharing)
   ```bash
   GET: /api/projects
   ```
4. **Add members to project**: Admin can add new users to project   
   - [Add members - demo](https://drive.google.com/file/d/1Vsk4mXxnSVDt-3JSP-w5UhWHBKHqs2a2/view?usp=sharing)
   ```bash
   PUT: api/projects/{projectId}/members
   ```
5. **Delete members**: Admin can delete members
   - [Delete members-demo](https://drive.google.com/file/d/106PKQWpZooD1lZcuSiwc3HScFN41JWcL/view?usp=sharing)
   ```bash
   DELETE: /api/projects/{projectId}/members
   ```
6. **Find projects for authorized user**: Return list of projects for user
   - [Find user's projects](https://drive.google.com/file/d/1a2x3CeLy55u8Oy9R5eGMNjv4lKUjI64a/view?usp=sharing)
   ```bash
   GET: api/projects/
   ```
   
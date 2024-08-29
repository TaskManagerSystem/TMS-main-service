# Task Manager Service

## Overview
This project is a web-based project management system designed to
help users manage projects, tasks, comments, attachments, labels,
and notifications. The system is broken down into several
microservices, each responsible for a specific domain of
the application. The system is scalable, secure, and integrates
with third-party services like Dropbox for attachment storage and
email/Telegram for notifications.
---
## Microservices Overview:

1. **Main Service**:

   &nbsp;&nbsp;&nbsp;&nbsp;*Responsibilities*:
   - User management (registration, authentication, roles, and permissions)
   - Project management (CRUD operations)
   - Task management (CRUD operations, assignment, status tracking)
   - Comment management (adding, viewing, deleting comments on tasks)
   - Label management (creating, modifying, deleting labels and assigning them to tasks)
2. **[TMS-attachment Service](https://github.com/TaskManagerSystem/TMS-attachment-service)**:

   &nbsp;&nbsp;&nbsp;&nbsp;*Responsibilities*:
   - Managing file attachments for tasks 
   - Storing files on Dropbox and saving metadata in the database 
   - Retrieving and deleting attachments
3. **[TMS-notification Service](https://github.com/TaskManagerSystem/TMS-notification-service)**:

   &nbsp;&nbsp;&nbsp;&nbsp;*Responsibilities*:
   - Sending notifications to users about task deadlines, new comments, and other relevant updates 
   - Integrating with external communication channels like email and Telegram

### Auxiliary repositories:
   1. **[TMS-common-dto](https://github.com/TaskManagerSystem/TMS-common-dto)**
    
      *Responsibilities*:
      - This service is responsible for saving DTO (Data Transfer Object) objects, which are 
used to forward data through Kafka.

   2. **[TMS-docker-compose](https://github.com/TaskManagerSystem/TMS-docker-compose)**
   
      *Responsibilities*:
      - This service stores and manages Docker Compose configurations to
      automate the deployment of services. 
---     
## Technologies and Tools
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
---
## Feature and functionality:
### User management:
1. **Registration**: Users can register on the platform by providing their email, first name, last name, and password.
    - [Registers a new user - demo.](https://drive.google.com/file/d/1V78I2bf0_0jdg136h_TWa6mlFnaiI3Cm/view?usp=sharing)
    ```bash
   POST: /api/auth/register
   ``` 

2. **Authentication**: Secure login functionality using JWT tokens to manage sessions.Users can log in using their credentials (email and password).
    - [Sign in for existing user - demo.](https://drive.google.com/file/d/1Dwyg90Y1mwMzB_VOF0zFdXJDFy4wr7dF/view?usp=drive_link)
   ```bash
   POST: /api/auth/login
   ```

3. **User Profiles**: Users can view and updated their pro
file information.
    - [View profile - demo](https://drive.google.com/file/d/1o0v-JUnE_YOnaHM8NhMZgkzYLas2J4yc/view?usp=drive_link)
   ```bash
   GET: /api/user/me
   ```
    - [Update profile - demo](https://drive.google.com/file/d/1m8lhnajRDTp_ADoLdreP7jswQwkZcwC9/view?usp=drive_link)
   ```bash
   PUT: /api/users/me
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
   PUT: /api/projects/{projectId}/members
   ```
5. **Delete members**: Admin can delete members
   - [Delete members-demo](https://drive.google.com/file/d/106PKQWpZooD1lZcuSiwc3HScFN41JWcL/view?usp=sharing)
   ```bash
   DELETE: /api/projects/{projectId}/members
   ```
6. **Find projects for authorized user**: Return list of projects for user
   - [Find user's projects](https://drive.google.com/file/d/1a2x3CeLy55u8Oy9R5eGMNjv4lKUjI64a/view?usp=sharing)
   ```bash
   GET: /api/projects/
   ```
### Task Controller 
1. **Create a new task**: User with role Admin can create a new task for project
   - [Create a task - demo](https://drive.google.com/file/d/1GAz5y5RpdHz5ID9HzS2zzieM9neF9PEH/view?usp=sharing)
   ```bash
   POST: /api/tasks
   ```
2. **Get task by id**: Authorized user can find task by specific id
   - [Get task By id - demo](https://drive.google.com/file/d/1ZgjGu4r5103gvU-u_r1yoga_0jfj9YJa/view?usp=sharing)
   ```bash
   GET: /api/tasks/{id}
   ```
3. **Delete task by id**: User with role Admin can delete tasks
   - [Delete task - demo](https://drive.google.com/file/d/1JESYe-N8s0a5Pj4M19s7uQ6IqiaCnY0C/view?usp=sharing)
   ```bash
   DELETE: /api/tasks/id
   ```
4. **Update task by id**: User with role Admin can update tasks
   - [Update task by id](https://drive.google.com/file/d/1WRCN52Yc_pDNyIz48npGWs-nDWMCmJjt/view?usp=sharing)
   ```bash
   PUT: /api/tasks/id
   ```
### Comment Controller
1. **Add comment to task**: User can add comments to task
   - [Add comment - demo](https://drive.google.com/file/d/1YIvoQ9HfWzfODd_xSAec8RZzYzaG46Kr/view?usp=sharing)
   ```bash
   POST: /api/comments
   ```
2. **Return all comments**: User can look at all his comments by task id
   ```bash
   GET: /api/comments
   ```
### Label Controller
1. **Create a new label**: User can create new label
   - [Create new label - demo](https://drive.google.com/file/d/1nNO2M6DQktxMAPUJR-QPU2fZ0kuOo41E/view?usp=sharing)
   ```bash
   POST: /api/labels
   ```
2. **Update label**: User can update labels
   - [Update label - demo](https://drive.google.com/file/d/1A_Ss7gM4FdaYXZXrc7xZb3f06a6Dvjps/view?usp=sharing)
   ```bash
   PUT: /api/labels/{id}
   ```
3. **Get All labels**: User gets list of labels
   - [Get all labels - demo](https://drive.google.com/file/d/126qptQU26Gqmy-xAGHPpasUG0M8bAyt7/view?usp=sharing)
   ```bash
   GET: /api/labels
   ```
4. **Delete label**: User can delete label by id
   - [Delete label - demo](https://drive.google.com/file/d/11a-EPb2WcqQ43dUCOAvEb491S4W4cvHh/view?usp=sharing)
   ```bash
   DELETE: /api/labels/{id}
   ```
---
### [Install instruction](https://github.com/TaskManagerSystem/TMS-docker-compose)

---
### Challenges and Solutions
1. **Microservices Architecture**:

   **Challenges**: Transitioning to a microservices architecture was one of the most significant challenges.
    Splitting the application into independent services—such as task management,
    notification handling, and others—introduced complexity in ensuring these services could
    effectively communicate and remain cohesive as a single system. Managing data consistency 
    across distributed databases, handling potential network failures, and maintaining system
    integrity while allowing for independent service scaling were all challenges that had to
    be addressed.

   **Solutions**: To overcome the challenges of microservices architecture, we implemented
    Kafka for reliable event streaming and asynchronous communication between services. This
    approach allowed us to decouple the components while maintaining system integrity, ensuring 
    that the services could effectively communicate and scale independently.


2. **Telegram Notification System**:
    
    **Challenges**:  Implementing a notification system that sends messages via 
    Telegram according to task deadlines was challenging due to the need for timely and 
    reliable delivery of messages. This required accommodating various scenarios such as 
    overdue tasks, upcoming deadlines, or task updates, while ensuring the system remained
    responsive and accurate.

    **Solution**: To ensure timely and reliable delivery of notifications via Telegram, we
    developed a dedicated notification service. This service listens to Kafka topics 
    for deadline-related events, constructs detailed messages, and sends them to users on 
    Telegram. This solution keeps users informed about their tasks, helping them stay on track
    and engaged with the system.
---
## Authors:
- [Oleksandr Farion](https://github.com/ReamFOX)
- [Dmytro Hadiuchko](https://github.com/DmytroHadiuchko)

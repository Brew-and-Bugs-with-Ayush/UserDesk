# UserDesk

[![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/SpringBoot-3.2-green?logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9-red?logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**UserDesk** is a secure and robust user management system with role-based access control (RBAC). It provides a scalable solution for managing users, roles, and permissions while ensuring security with JWT authentication.

---

## 🔐 Features

- **User Management:** Create, read, update, and delete users.
- **Role-Based Access Control (RBAC):** Assign roles and permissions to control access.
- **Secure Authentication:** JWT-based authentication for secure login and API access.
- **Database:** Persist user and role data in PostgreSQL.
- **RESTful APIs:** Fully functional APIs for integration with front-end or other services.

---

## 🚀 Getting Started

## 🛠 Tech Stack

| Backend | Security | Database | Build Tool |
|---------|----------|----------|------------|
| ![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white) | ![JWT](https://img.shields.io/badge/JWT-Auth-orange) | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql&logoColor=white) | ![Maven](https://img.shields.io/badge/Maven-3.9-red?logo=apache-maven&logoColor=white) |
| ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.2-green?logo=spring&logoColor=white) | | | |

---

### Installation

1. Clone the repository:

```bash
git clone https://github.com/your-username/UserDesk.git
cd UserDesk
```

2. Configure PostgreSQL database:

 ```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/userdesk
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

3. Build and run the project:

 ```bash
mvn clean install
mvn spring-boot:run
 ```

4. Access the APIs

---

🔑 Security

JWT token-based authentication for secure API access.

Role-based access control to restrict endpoints based on user roles.

Passwords are securely hashed before storing in the database.

---

🤝 Contributing

Contributions are welcome! Please fork the repo and create a pull request for bug fixes, improvements, or new features.
---

🧑‍💻 Author

Ayush Gupta
💼 GitHub: https://github.com/Brew-and-Bugs-with-Ayush

🌐 LinkedIn: https://www.linkedin.com/in/ayush-gupta004

📧 Email: ayushgupta.Codex@gmail.com

---

📝 License

This project is licensed under the MIT License — feel free to use, learn, and build upon it.

---

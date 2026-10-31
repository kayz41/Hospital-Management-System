# Hospital Management System (HMS)

A Java-based desktop Hospital Management System developed using Java Swing, Maven, and Microsoft SQL Server. The project brings together hospital administration modules, database management, user access control, reporting, and supporting healthcare workflows.

**Developer:** Joel Kayz  
**Institution:** ISBAT University  
**Programme:** Bachelor of Science in Applied Information Technology  
**Project Type:** Academic and practical software development project

**Repository:** https://github.com/kayz41/Hospital-Management-System

---

## Project Overview

The Hospital Management System (HMS) is a desktop application designed to support common hospital administration activities through a graphical interface connected to Microsoft SQL Server.

The application includes modules for patient management, doctor management, appointments, billing, staff administration, pharmacy operations, medical records, laboratory services, and reporting.

It also incorporates security-related functionality such as BCrypt password hashing, login verification, role-based dashboard permissions, audit logging, and session management.

The project is being developed with the long-term objective of improving its reliability, usability, security, and potential for future web-based access.

## Key Features

### Application and administration
- Java Swing desktop interface
- Dashboard and operational statistics
- Patient management
- Doctor management
- Staff management
- Appointment management
- User account management

### Financial and reporting
- Billing management
- Invoice functionality
- Revenue dashboard
- Report generation
- Report export and printing functionality

### Security
- User authentication
- BCrypt password hashing and verification
- Role-based dashboard access
- Failed-login tracking and account locking
- Audit logging
- Session management

### Additional modules
- Medical records
- Laboratory management
- Pharmacy management
- Pharmacy dispensing
- Barcode and QR-code utilities
- Notification-related service components

Feature availability and completeness differ between modules. External integrations and end-to-end workflows require individual testing.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 25 | Application development |
| Java Swing | Desktop user interface |
| Apache Maven | Build and dependency management |
| Microsoft SQL Server | Relational database |
| JDBC | Database connectivity |
| Apache NetBeans IDE | Development environment |
| Git | Version control |
| GitHub | Source code hosting |
| BCrypt | Password hashing |
| JFreeChart | Charting and analytics |
| Apache POI | Office document processing |
| ZXing | Barcode and QR-code utilities |

## Application Architecture

The current desktop application follows a layered structure:

```text
Java Swing User Interface
          |
          v
Application Logic
          |
          v
Models, Data Access and Services
          |
          v
JDBC Connectivity
          |
          v
Microsoft SQL Server
```

The codebase contains interface classes, database-related components, supporting services, and utility classes.

## Main Application Modules

| Module | Purpose |
|---|---|
| Login | User authentication |
| Dashboard | Statistics, navigation and activity |
| Patients | Patient record management |
| Doctors | Doctor record management |
| Appointments | Appointment administration |
| Billing | Billing and payment records |
| Staff | Staff administration |
| Users | User account administration |
| Reports | Report generation and output |
| Pharmacy | Pharmacy-related operations |
| Pharmacy Dispensing | Dispensing-related workflows |
| Medical Records | Medical record administration |
| Laboratory | Laboratory-related workflows |

## Requirements

Before running the project, ensure that you have:

1. Java Development Kit (JDK) 25.
2. Apache NetBeans IDE or a compatible Java IDE.
3. Apache Maven.
4. Microsoft SQL Server.
5. A database schema compatible with the application.
6. Git, if cloning the repository.

## Installation

### 1. Clone the repository

Open PowerShell:

```powershell
git clone https://github.com/kayz41/Hospital-Management-System.git
cd Hospital-Management-System
```

### 2. Open the project

Open the cloned Maven project in NetBeans.

Allow Maven to download and resolve the dependencies listed in `pom.xml`.

### 3. Configure the database

Install and start Microsoft SQL Server.

Create or restore the database required by the application and ensure its tables and columns match the SQL queries used by the project.

### 4. Configure database credentials

The application reads database connection details from environment variables:

| Variable | Description |
|---|---|
| `HMS_DB_URL` | SQL Server JDBC connection URL |
| `HMS_DB_USER` | Database username |
| `HMS_DB_PASSWORD` | Database password |

Example for the current PowerShell session:

```powershell
$env:HMS_DB_URL = "YOUR_SQL_SERVER_JDBC_URL"
$env:HMS_DB_USER = "YOUR_DATABASE_USERNAME"
$env:HMS_DB_PASSWORD = "YOUR_DATABASE_PASSWORD"
```

Replace the example values with your actual development configuration.

Never publish database passwords or private credentials in source code or GitHub.

### 5. Build the application

Run:

```powershell
mvn clean compile
```

To create the packaged application:

```powershell
mvn clean package
```

The Maven configuration includes a packaging setup for an executable JAR. The resulting artifact should be tested in the target environment before distribution.

### 6. Run the application

Launch the configured main class through NetBeans:

```text
com.mycompany.userauthentication.Login
```

Sign in using a valid account configured in the database.

## Security Considerations

The application includes several security-related mechanisms:

- BCrypt password hashing and verification.
- User authentication.
- Role-based dashboard permissions.
- Failed-login tracking and account locking.
- Audit logging.
- Session management.

These mechanisms form part of the project's security foundation. However, a complete production deployment requires additional verification of authorization across all sensitive operations, database permissions, privacy controls, secure configuration, and application reliability.

The system should not be used to process real patient information without appropriate security, privacy, regulatory, and clinical workflow assessments.

## Testing and Verification

Testing should cover:

- Login and authentication.
- Password verification.
- Role-based access.
- Account-locking behaviour.
- Database connectivity.
- Patient and doctor record operations.
- Appointment workflows.
- Billing and invoice operations.
- Audit logging.
- Reports and exports.
- Pharmacy and laboratory workflows.
- Session behaviour.

Successful compilation alone does not establish that every application feature works correctly.

Use synthetic demonstration data when testing and preparing screenshots.

## Development Roadmap

Planned development and improvement areas include:

1. Further testing and stabilization of existing desktop modules.
2. Improvements to interface consistency and usability.
3. Further security and authorization testing.
4. Verification of notification integrations.
5. Improved reporting and analytics.
6. Exploration of a secure web-based interface and REST API.
7. Improved deployment, backup, and recovery procedures.

Roadmap items are planned work and should not be interpreted as completed features.

## Author

**Joel Kayz**

Bachelor of Science in Applied Information Technology  
ISBAT University

GitHub: https://github.com/kayz41

## License

No separate open-source license is currently specified. Unless a license is added, permission to reuse, modify, or redistribute this project should not be assumed.

# 🏥 Hospital Management System (HMS)

A desktop-based Hospital Management System developed using **Java, Java Swing, Maven, and Microsoft SQL Server** to support hospital administration, patient management, clinical workflows, billing, pharmacy operations, and reporting.

The project is being developed as an academic and practical software engineering project at ISBAT University. Its current desktop application is the foundation for planned notification enhancements and a future web-based interface.

**Repository:** https://github.com/kayz41/Hospital-Management-System

---

## 📖 Table of Contents

- [Project Overview](#-project-overview)
- [Key Features](#-key-features)
- [Technology Stack](#-technology-stack)
- [Project Modules](#-project-modules)
- [Project Architecture](#-project-architecture)
- [Project Structure](#-project-structure)
- [Requirements](#-requirements)
- [Installation and Setup](#-installation-and-setup)
- [Environment Configuration](#-environment-configuration)
- [Database Configuration](#-database-configuration)
- [Running the Application](#-running-the-application)
- [Notifications and SMS Integration](#-notifications-and-sms-integration)
- [Security Considerations](#-security-considerations)
- [Testing and Verification](#-testing-and-verification)
- [Known Limitations](#-known-limitations)
- [Development Roadmap](#-development-roadmap)
- [Contributing](#-contributing)
- [Author](#-author)
- [License](#-license)

---

## 📖 Project Overview

The Hospital Management System is designed to organize common hospital administration activities through a desktop application connected to a Microsoft SQL Server database.

The system brings together patient and doctor management, appointments, medical records, pharmacy operations, laboratory services, billing, staff management, and reporting.

The project also includes supporting components for audit logging, validation, session management, barcode and QR-code generation, expiry alerts, and notification services.

The long-term objective is to evolve the project into a broader hospital information system while preserving the existing desktop application and introducing a web interface in a later development phase.

## ✨ Key Features

- 🔐 User login and authentication
- 🛡️ Role-Based Access Control (RBAC)
- 📝 Audit trail logging
- 👥 Patient, doctor, and staff management
- 📅 Appointment management
- 🩺 Medical records management
- 🧪 Laboratory management
- 💊 Pharmacy and dispensing support
- 💳 Billing and invoice management
- 📊 Reports and revenue dashboard
- 📦 Pharmacy expiry-alert support
- 🔔 Notification service components
- 📱 SMS integration using the Africa's Talking sandbox client
- 📧 Email notification components
- 🧾 Barcode and QR-code generation utilities
- 🔍 Data validation and session-management utilities
- 🗄️ Microsoft SQL Server database integration
- 🖥️ Desktop graphical interface built with Java Swing

Feature availability and completeness may vary by module. The full clinical and financial workflows require end-to-end testing before the system is considered production-ready.

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Application logic |
| Java Swing | Desktop graphical user interface |
| Apache Maven | Dependency management and build |
| Microsoft SQL Server | Relational database |
| JDBC | Database connectivity |
| Apache NetBeans IDE | Development environment |
| Git | Version control |
| GitHub | Source code hosting |
| Africa's Talking API | SMS sandbox integration |
| HTML, CSS, JavaScript | Planned web-interface technologies |
| Spring Boot / REST API | Potential backend for the future web application |

The existing desktop application uses Java Swing. HTML, CSS, JavaScript, and a web API are part of the planned web expansion; they should not be interpreted as an already completed web interface.

## 🧩 Project Modules

### 1. Authentication and access control

- Login interface
- User account management
- Role-based access control support
- Session management
- Audit logging

### 2. Patient and doctor management

- Patient records
- Doctor records
- Staff records
- Appointment management
- Input validation

### 3. Medical records and laboratory

- Medical records management
- International medical records module
- Laboratory management
- Supporting data-access and model classes

### 4. Pharmacy and inventory support

- Pharmacy management
- Pharmacy dispensing interface and supporting classes
- Pharmacy data-access components
- Expiry-alert services and scheduler

Stock deductions, dispensing rules, and expiry-alert behaviour must be verified against the actual database and application workflows.

### 5. Billing and financial reporting

- Billing management
- Billing data-access and model classes
- Invoice interface
- Revenue dashboard
- Report generation

The billing and clinical workflows should be tested together to verify that charges, invoices, and payment records remain consistent.

### 6. Notifications and supporting utilities

- SMS service components
- Africa's Talking sandbox client
- SMS history and supporting database-access classes
- Email notification components
- Notification configuration and management
- WhatsApp notification service component
- Barcode generation
- QR-code generation
- Validation and session utilities

The presence of a service class does not by itself guarantee that its external provider integration is fully configured or operational.

## 🏗️ Project Architecture

The current application is a Java desktop system using Java Swing for its user interface and JDBC for database access.

The source code includes interface classes, model classes, data-access objects (DAOs), service classes, and supporting utilities.

A simplified view of the current architecture is:

```text
Java Swing Desktop Interface
            |
            v
Application Logic and Validation
            |
            v
Models, DAOs and Service Classes
            |
            v
JDBC Database Connectivity
            |
            v
Microsoft SQL Server
```

External notification providers, such as the Africa's Talking SMS API, are accessed through separate service components.

### Planned web architecture

The proposed web expansion may use the following structure:

```text
Web Browser
HTML + CSS + JavaScript
            |
            v
REST API
Spring Boot / Java
            |
            v
Business Logic and Security
            |
            v
Data Access Layer
            |
            v
Microsoft SQL Server
```

This is a proposed architecture, not a claim that the web application or its deployment is complete. The API, authentication, authorization, database configuration, and integration with the desktop application's data model must be reviewed before public deployment.

## 📂 Project Structure

The main desktop project is organized as a Maven application.

```text
PROJECT UPGRADE/
├── src/
│   └── main/
│       └── java/
│           └── com/mycompany/userauthentication/
│               ├── Login.java
│               ├── DASHBOARD.java
│               ├── Patients.java
│               ├── Doctors.java
│               ├── Appointments.java
│               ├── MedicalRecords.java
│               ├── InternationalMedicalRecords.java
│               ├── Laboratory.java
│               ├── Pharmacy.java
│               ├── PharmacyDispensing.java
│               ├── Billing.java
│               ├── Invoice.java
│               ├── DoctorPerformance.java
│               ├── RevenueDashboard.java
│               ├── SMSHistory.java
│               ├── DatabaseConnection.java
│               ├── NotificationConfigLoader.java
│               ├── AfricaTalkingSmsClient.java
│               └── dao/
├── pom.xml
├── .gitignore
└── README.md
```

This is a simplified illustration of the main source tree. Additional Java classes, NetBeans `.form` files, models, DAOs, and service components are present in the repository.

## 💻 Requirements

Before running the application, install and configure:

1. **Java JDK** compatible with the project's Maven compiler settings.
2. **Apache NetBeans IDE** or another compatible Java IDE.
3. **Apache Maven**, either through NetBeans or as a standalone installation.
4. **Microsoft SQL Server** and an appropriate SQL Server management tool.
5. The database schema and tables required by the application.
6. Git, if you intend to clone or contribute to the repository.

## ⚙️ Installation and Setup

### Step 1: Clone the repository

Open PowerShell or a terminal:

```powershell
git clone https://github.com/kayz41/Hospital-Management-System.git
cd Hospital-Management-System
```

### Step 2: Open the project

Open the cloned project in Apache NetBeans IDE.

Allow Maven to resolve the dependencies defined in `pom.xml`.

### Step 3: Configure SQL Server

Install and start Microsoft SQL Server, then create or restore the database used by the project.

The database name used in the current project documentation is:

```text
HospitalManagementDB
```

Create or import the required tables and schema before launching the application. The repository's Java classes depend on the database structure, so the schema must match the queries and data-access classes in the code.

### Step 4: Configure database environment variables

The database connection class reads its settings from environment variables rather than embedding the database password in source code.

Set the appropriate values for:

- `HMS_DB_URL`
- `HMS_DB_USER`
- `HMS_DB_PASSWORD`

The JDBC URL must match your SQL Server host, port, database, and connection requirements.

### Step 5: Build the application

From the project directory, run:

```powershell
mvn clean compile
```

If Maven is not available on your system PATH, use the Maven executable configured in NetBeans or the full path to your local Maven installation.

### Step 6: Launch the application

Run the project's configured main class through NetBeans, or use the Maven execution configuration if the project defines one.

Log in with a valid account configured in the database.

## 🔐 Environment Configuration

The application uses environment variables for database and notification configuration.

| Variable | Purpose |
|---|---|
| `HMS_DB_URL` | SQL Server JDBC connection URL |
| `HMS_DB_USER` | Database username |
| `HMS_DB_PASSWORD` | Database password |
| `HMS_EMAIL_ADDRESS` | Email sender address |
| `HMS_EMAIL_PASSWORD` | Email account or provider password |
| `HMS_EMAIL_ENABLED` | Enables or disables email notification support |
| `HMS_SMS_ENABLED` | Enables or disables SMS notification support |
| `HMS_SMS_API_KEY` | Africa's Talking sandbox API key |
| `HMS_SMS_ALERT_RECIPIENT` | Recipient configured for SMS alerts |
| `HMS_WHATSAPP_ENABLED` | Enables or disables WhatsApp notification support |

Set only the variables required for the functionality you intend to use. Optional notification features may remain disabled during initial setup.

### Example for the current PowerShell session

Replace the example placeholders with your own configuration values:

```powershell
$env:HMS_DB_URL = "jdbc:sqlserver://YOUR_SERVER:1433;databaseName=HospitalManagementDB;encrypt=true;trustServerCertificate=false"
$env:HMS_DB_USER = "YOUR_DATABASE_USER"
$env:HMS_DB_PASSWORD = "YOUR_DATABASE_PASSWORD"
```

Use the correct JDBC URL for your SQL Server installation. Certificate settings depend on your environment; do not disable certificate validation on a production connection just to bypass a TLS problem.

Environment variables set this way apply only to the current PowerShell session and its child processes. Configure persistent variables through Windows settings or a secure secrets-management mechanism if needed.

Never commit actual passwords, API keys, access tokens, or private environment files to GitHub.

## 🗄️ Database Configuration

The application uses Microsoft SQL Server through JDBC.

Before running the system:

1. Confirm that SQL Server is running and accepting connections.
2. Verify the database name and connection URL.
3. Confirm that the configured account has the necessary database permissions.
4. Create or restore the schema required by the application.
5. Check that table names and columns match the queries used by the Java classes.
6. Test database connectivity before testing individual modules.

Database schema changes should be made through controlled scripts or backups. Avoid destructive changes to an existing hospital database without a verified backup and a migration plan.

## ▶️ Running the Application

A typical development workflow is:

1. Configure Java, Maven, and SQL Server.
2. Set the required environment variables.
3. Open the project in NetBeans.
4. Build the project.
5. Start the application.
6. Sign in using a valid configured account.
7. Navigate through the dashboard to access the available modules.
8. Test each workflow using safe test data.
9. Review application logs and database records when troubleshooting.

Do not use real patient information for development testing unless you have the appropriate authorization and safeguards.

## 📲 Notifications and SMS Integration

The project includes notification-related service components for email, SMS, and WhatsApp.

### SMS integration

The current `AfricaTalkingSmsClient` targets the Africa's Talking sandbox messaging endpoint and reads the API key from `HMS_SMS_API_KEY`.

Before testing:

1. Obtain valid sandbox credentials from Africa's Talking.
2. Set the API key in your development environment.
3. Confirm that SMS is enabled in the relevant configuration.
4. Use the provider's supported sandbox recipient and message setup.
5. Check the provider dashboard and response to confirm acceptance.

A successful HTTP response alone does not prove that a real SMS was delivered. Sandbox testing is separate from production SMS delivery, and production access requires its own configuration and verification.

### Email integration

Email notification components are present in the project. Configure the sender address and password using the environment variables described above, and verify provider requirements such as SMTP settings and application-password policies.

### WhatsApp integration

A WhatsApp notification service component is present. A working production integration requires a supported provider, valid credentials, appropriate recipient consent, and verification of the sending workflow.

Do not assume that all notification channels are fully operational simply because their Java classes exist.

## 🔒 Security Considerations

Security is particularly important because a hospital system may process sensitive patient and financial information.

Development and deployment should follow these principles:

- Never publish database passwords, email passwords, API keys, or access tokens.
- Store secrets outside source control.
- Use least-privilege database accounts.
- Validate user input before processing or storing it.
- Enforce authentication and authorization on sensitive operations.
- Protect patient information and restrict access to authorized personnel.
- Review audit logging to avoid recording passwords, tokens, or unnecessary sensitive data.
- Use encrypted database and API connections with appropriate certificate validation.
- Back up the database and test restoration procedures.
- Review the security of any REST API before exposing it to a network or the public internet.
- Use synthetic data during development wherever possible.

This is an academic development project and should not be treated as a production-ready clinical system without a separate security, privacy, reliability, and workflow assessment.

## 🧪 Testing and Verification

The desktop project has previously passed a Maven `clean compile` operation during development. Compilation confirms that the code builds; it does not establish that every feature or external integration works correctly.

Recommended verification includes:

- Login and role-based access checks
- Database connectivity and error handling
- Patient and doctor record operations
- Appointment creation, updating, and retrieval
- Medical-record access and validation
- Pharmacy dispensing and stock consistency
- Billing and invoice calculations
- Expiry-alert behaviour
- Audit logging
- SMS sandbox acceptance
- Email configuration and delivery testing
- Backup and restoration procedures

Clinical and financial workflows should be tested end to end using controlled test data before being relied on for real operations.

## ⚠️ Known Limitations

- The main application is currently a Java Swing desktop application.
- The HTML/CSS/JavaScript web interface is planned and is not documented here as completed.
- SMS integration currently targets the Africa's Talking sandbox.
- Live SMS delivery and production notification credentials require separate verification.
- Email and WhatsApp service components require configuration and testing.
- The complete clinical workflow—from consultation and prescription through dispensing and billing—requires end-to-end verification.
- The separate API and future web interface require security and integration review before public deployment.

These limitations should be updated as features are implemented and tested.

## 🚀 Development Roadmap

### Phase 1: Stabilize the desktop HMS

- Verify existing modules and database operations.
- Test login, permissions, and audit logging.
- Validate appointment and medical-record workflows.
- Verify billing and invoice calculations.
- Test pharmacy dispensing, stock updates, and expiry alerts.
- Improve error handling and data validation.

### Phase 2: Complete notifications

- Verify SMS sandbox operation.
- Configure and test email notifications.
- Verify notification history and failure handling.
- Evaluate a supported WhatsApp provider integration.
- Add safe configuration examples without exposing credentials.

### Phase 3: Introduce the web interface

- Design the user interface using HTML and CSS.
- Add client-side behaviour using JavaScript.
- Review and secure the Spring Boot REST API.
- Define API contracts for patients, doctors, appointments, records, billing, and pharmacy.
- Integrate the web interface with the backend and SQL Server.
- Implement server-side authentication, authorization, validation, and error handling.
- Test the web application independently from the desktop interface.

### Phase 4: Advanced capabilities

- Dashboard analytics and operational reports
- Two-factor authentication
- Improved audit and access monitoring
- Automated and verified database backups
- Online payment integration
- Mobile-friendly user interface
- Multilingual support
- Deployment, monitoring, and recovery procedures

Roadmap items are planned improvements, not promises that the corresponding functionality is already available.

## 🤝 Contributing

Contributions, bug reports, and suggestions are welcome.

Before proposing changes:

1. Review the existing implementation.
2. Create a separate branch for your work.
3. Avoid committing credentials, generated build output, or private patient data.
4. Test changes before submitting them.
5. Document database or configuration changes.
6. Include appropriate verification steps with your proposed changes.

Changes to database structures or security-sensitive features should be reviewed carefully.

## 👨‍💻 Author

**Joel Kayz**

Bachelor of Science in Information Technology
ISBAT University

GitHub: https://github.com/kayz41

## 📄 License

This project was developed for academic purposes at ISBAT University.

No separate open-source license is specified here. Unless a license is added to the repository, permission to reuse, redistribute, or modify the code should not be assumed.

# MongoDB Setup Guide for VIT Student Results

## Prerequisites

- MongoDB installed locally (version 4.0 or higher)
- Java 17+
- Maven 3.6+

## Installation Steps

### 1. Install MongoDB

**Windows:**
- Download from: https://www.mongodb.com/try/download/community
- Run the installer and follow the installation wizard
- MongoDB will be installed as a Windows Service by default

**macOS:**
```bash
brew install mongodb-community
brew services start mongodb-community
```

**Linux (Ubuntu):**
```bash
sudo apt-get install -y mongodb
sudo systemctl start mongodb
```

### 2. Verify MongoDB Installation

Test if MongoDB is running:
```bash
mongo
# or for newer versions
mongosh
```

You should see the MongoDB shell prompt.

### 3. Create Database and Collection

```bash
# Connect to MongoDB
mongosh

# Use the database (it will be created automatically if it doesn't exist)
use vit_results

# Create the Students collection
db.createCollection("Students")

# Verify
db.getCollectionNames()
```

### 4. Configure Spring Boot Application

The application is already configured in `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/vit_results
spring.data.mongodb.database=vit_results
spring.data.mongodb.auto-index-creation=true
```

### 5. Build and Run the Backend

```bash
cd Backend

# Clean and build
./mvnw.cmd clean package

# Or just run directly
./mvnw.cmd spring-boot:run
```

### 6. Verify Data Initialization

When the application starts, it will:
1. Check if the student with PRN "12420262" exists
2. If not, automatically insert the sample data
3. Display confirmation messages in the console

Expected console output:
```
✓ Student data initialized successfully!
  PRN: 12420262
  Name: Omkar Gondkar
  CGPA: 6.56
```

## API Endpoints

### Base URL: `http://localhost:8080/api/students`

#### 1. Get All Students
```
GET /api/students
```

**Response:**
```json
[
  {
    "id": "...",
    "prnNo": "12420262",
    "name": "Omkar Gondkar",
    "semester": 5,
    "subjects": [...],
    "cgpa": 6.56
  }
]
```

#### 2. Get Student by PRN Number (Most Important)
```
GET /api/students/prn/{prnNo}
```

**Example:**
```
GET /api/students/prn/12420262
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "prnNo": "12420262",
  "name": "Omkar Gondkar",
  "semester": 5,
  "subjects": [
    {
      "code": "CC",
      "name": "Cloud Computing",
      "mse": 30,
      "ese": 65,
      "total": 64.5,
      "grade": "C"
    },
    {
      "code": "CN",
      "name": "Computer Network",
      "mse": 30,
      "ese": 70,
      "total": 69,
      "grade": "B"
    },
    {
      "code": "DAA",
      "name": "Design & Analyze of Algorithm",
      "mse": 25,
      "ese": 68,
      "total": 63.9,
      "grade": "C"
    },
    {
      "code": "ANN",
      "name": "Artificial Neural Network",
      "mse": 28,
      "ese": 68,
      "total": 65.6,
      "grade": "C"
    }
  ]
}
```

#### 3. Get Student by ID
```
GET /api/students/{id}
```

#### 4. Create New Student
```
POST /api/students
Content-Type: application/json

{
  "prnNo": "12345678",
  "name": "Student Name",
  "semester": 5,
  "subjects": [...]
}
```

#### 5. Update Student
```
PUT /api/students/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "subjects": [...]
}
```

#### 6. Delete Student
```
DELETE /api/students/{id}
```

## Database Schema

### Collections

#### Students Collection

```json
{
  "_id": ObjectId("..."),
  "prnNo": "12420262",
  "name": "Omkar Gondkar",
  "semester": 5,
  "subjects": [
    {
      "code": "CC",
      "name": "Cloud Computing",
      "mse": 30,
      "ese": 65
    },
    {
      "code": "CN",
      "name": "Computer Network",
      "mse": 30,
      "ese": 70
    },
    {
      "code": "DAA",
      "name": "Design & Analyze of Algorithm",
      "mse": 25,
      "ese": 68
    },
    {
      "code": "ANN",
      "name": "Artificial Neural Network",
      "mse": 28,
      "ese": 68
    }
  ]
}
```

## Verify Data in MongoDB

```bash
# Connect to MongoDB
mongosh

# Select database
use vit_results

# View all students
db.Students.find()

# Find specific student by PRN
db.Students.findOne({ prnNo: "12420262" })

# Count students
db.Students.countDocuments()
```

## Troubleshooting

### MongoDB Connection Error
- Ensure MongoDB service is running: `net start MongoDB`
- Check if port 27017 is accessible
- Verify connection string in `application.properties`

### Data Not Initializing
- Delete the existing `vit_results` database
- Restart the application
- Check console logs for error messages

### Port Already in Use
- MongoDB default port: 27017
- Spring Boot default port: 8080
- Change in `application.properties` if needed

## File Structure

```
Backend/
├── src/main/
│   ├── java/com/example/demo/
│   │   ├── controller/
│   │   │   └── StudentController.java      # REST API endpoints
│   │   ├── service/
│   │   │   └── StudentService.java         # Business logic
│   │   ├── repository/
│   │   │   └── StudentRepository.java      # MongoDB queries
│   │   ├── model/
│   │   │   ├── Student.java                # Student entity
│   │   │   └── Subject.java                # Subject entity
│   │   ├── config/
│   │   │   └── DataInitializer.java        # Initial data setup
│   │   └── DemoApplication.java            # Main Spring Boot app
│   └── resources/
│       └── application.properties          # Configuration
├── pom.xml                                 # Maven dependencies
└── MONGODB_SETUP.md                        # This file
```

## Next Steps

1. ✅ Install MongoDB
2. ✅ Start MongoDB service
3. ✅ Build and run the Spring Boot application
4. ✅ Test API endpoints with Postman or curl
5. ✅ Connect frontend to backend API

## Support

For issues or questions, check:
- Spring Boot docs: https://spring.io/projects/spring-boot
- MongoDB docs: https://docs.mongodb.com
- Spring Data MongoDB: https://spring.io/projects/spring-data-mongodb
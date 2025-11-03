# VIT Student Results API - Endpoint Reference

## Base URL
```
http://localhost:8080/api/students
```

## Quick Test Commands (PowerShell)

### 1. Get All Students
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Get
$response | ConvertTo-Json | Write-Host
```

### 2. Search Student by PRN Number (Important!)
```powershell
# Replace with actual PRN
$prnNo = "12420262"
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/students/prn/$prnNo" -Method Get
$response | ConvertTo-Json -Depth 10 | Write-Host
```

### 3. Create New Student
```powershell
$body = @{
    prnNo = "12345678"
    name = "John Doe"
    semester = 5
    subjects = @(
        @{
            code = "CC"
            name = "Cloud Computing"
            mse = 28
            ese = 65
        },
        @{
            code = "CN"
            name = "Computer Network"
            mse = 30
            ese = 70
        },
        @{
            code = "DAA"
            name = "Design & Analyze of Algorithm"
            mse = 25
            ese = 68
        },
        @{
            code = "ANN"
            name = "Artificial Neural Network"
            mse = 28
            ese = 68
        }
    )
} | ConvertTo-Json -Depth 10

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/students" -Method Post -ContentType "application/json" -Body $body
$response | ConvertTo-Json -Depth 10 | Write-Host
```

### 4. Get Student by ID
```powershell
# Replace with actual ID from response
$id = "607f1f77bcf86cd799439011"
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/students/$id" -Method Get
$response | ConvertTo-Json -Depth 10 | Write-Host
```

### 5. Update Student
```powershell
$id = "607f1f77bcf86cd799439011"
$body = @{
    name = "Updated Name"
    semester = 6
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/students/$id" -Method Put -ContentType "application/json" -Body $body
$response | ConvertTo-Json -Depth 10 | Write-Host
```

### 6. Delete Student
```powershell
$id = "607f1f77bcf86cd799439011"
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/students/$id" -Method Delete
$response | ConvertTo-Json | Write-Host
```

## Test Commands using cURL

### 1. Get All Students
```bash
curl -X GET http://localhost:8080/api/students
```

### 2. Search Student by PRN (Most Important!)
```bash
curl -X GET http://localhost:8080/api/students/prn/12420262
```

### 3. Create New Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "prnNo": "12345678",
    "name": "John Doe",
    "semester": 5,
    "subjects": [
      {
        "code": "CC",
        "name": "Cloud Computing",
        "mse": 28,
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
  }'
```

### 4. Get Student by ID
```bash
curl -X GET http://localhost:8080/api/students/607f1f77bcf86cd799439011
```

### 5. Update Student
```bash
curl -X PUT http://localhost:8080/api/students/607f1f77bcf86cd799439011 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Name",
    "semester": 6
  }'
```

### 6. Delete Student
```bash
curl -X DELETE http://localhost:8080/api/students/607f1f77bcf86cd799439011
```

## Response Examples

### Success Response (Get Student by PRN)
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

### Error Response (Student Not Found)
```json
{
  "error": "Student with PRN 99999999 not found"
}
```

### Status Codes
- **200 OK** - Request successful
- **201 Created** - Student created successfully
- **404 Not Found** - Student not found
- **400 Bad Request** - Invalid request data

## Calculated Fields

The Student and Subject objects automatically calculate:

### Subject
- **Total Marks**: `(MSE × 0.3) + (ESE × 0.7)`
- **Grade**: A+/A/B/C/D/F based on total

### Student
- **CGPA**: Average of all subject totals ÷ 10
- **Overall Grade**: Based on average marks

## Testing with Postman

1. Import the collection
2. Set base URL to `http://localhost:8080`
3. Use the GET request: `{{base_url}}/api/students/prn/12420262`
4. View the JSON response

## Integration with Frontend

The frontend can call the backend API:
```javascript
const response = await fetch('http://localhost:8080/api/students/prn/12420262');
const student = await response.json();
```

Note: CORS is enabled for `http://localhost:5173` (React frontend)
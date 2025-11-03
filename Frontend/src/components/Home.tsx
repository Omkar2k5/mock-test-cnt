import './Home.css'

interface Subject {
  code: string
  name: string
  mse: number
  ese: number
}

interface StudentResult {
  rollNo: string
  name: string
  semester: number
  subjects: Subject[]
}

export function Home() {
  // Sample student data
  const student: StudentResult = {
    rollNo: 'VIT22001',
    name: 'Aman Kumar',
    semester: 5,
    subjects: [
      {
        code: 'CN',
        name: 'Computer Network',
        mse: 28,
        ese: 65
      },
      {
        code: 'DAAOA',
        name: 'Design and Analysis of Algorithms',
        mse: 26,
        ese: 58
      },
      {
        code: 'CC',
        name: 'Cloud Computing',
        mse: 29,
        ese: 70
      },
      {
        code: 'ANN',
        name: 'Artificial Neural Network',
        mse: 25,
        ese: 62
      }
    ]
  }

  const calculateTotal = (subject: Subject): number => {
    return parseFloat(((subject.mse * 0.3) + (subject.ese * 0.7)).toFixed(2))
  }

  const getGrade = (marks: number): string => {
    if (marks >= 90) return 'A+'
    if (marks >= 80) return 'A'
    if (marks >= 70) return 'B'
    if (marks >= 60) return 'C'
    if (marks >= 50) return 'D'
    return 'F'
  }

  const calculateCGPA = (): number => {
    const totalMarks = student.subjects.reduce((sum, subject) => sum + calculateTotal(subject), 0)
    const averageMarks = totalMarks / student.subjects.length
    return parseFloat((averageMarks / 10).toFixed(2))
  }

  return (
    <div className="home-container">
      <div className="header">
        <h1>VIT Student Result Portal</h1>
        <p className="subtitle">Semester Result Display</p>
      </div>

      <div className="student-info-card">
        <div className="student-info">
          <div className="info-item">
            <label>Roll Number:</label>
            <span>{student.rollNo}</span>
          </div>
          <div className="info-item">
            <label>Name:</label>
            <span>{student.name}</span>
          </div>
          <div className="info-item">
            <label>Semester:</label>
            <span>{student.semester}</span>
          </div>
          <div className="info-item cgpa-item">
            <label>CGPA:</label>
            <span className="cgpa-value">{calculateCGPA()}</span>
          </div>
        </div>
      </div>

      <div className="results-container">
        <h2>Subject-wise Marks</h2>
        <table className="results-table">
          <thead>
            <tr>
              <th>Subject Code</th>
              <th>Subject Name</th>
              <th>MSE (30%)</th>
              <th>ESE (70%)</th>
              <th>Total Marks</th>
              <th>Grade</th>
            </tr>
          </thead>
          <tbody>
            {student.subjects.map((subject, index) => {
              const total = calculateTotal(subject)
              const grade = getGrade(total)
              return (
                <tr key={index} className={`grade-${grade.toLowerCase()}`}>
                  <td className="code">{subject.code}</td>
                  <td className="name">{subject.name}</td>
                  <td className="marks">{subject.mse}/30</td>
                  <td className="marks">{subject.ese}/70</td>
                  <td className="total-marks">
                    <strong>{total}/100</strong>
                  </td>
                  <td className="grade">
                    <span className={`grade-badge grade-${grade.toLowerCase()}`}>
                      {grade}
                    </span>
                  </td>
                </tr>
              )
            })}
          </tbody>
        </table>
      </div>

      <div className="summary-section">
        <div className="summary-card">
          <h3>Result Summary</h3>
          <div className="summary-stats">
            <div className="stat">
              <span className="label">Total Subjects:</span>
              <span className="value">{student.subjects.length}</span>
            </div>
            <div className="stat">
              <span className="label">Average Marks:</span>
              <span className="value">
                {parseFloat(
                  (student.subjects.reduce((sum, subject) => sum + calculateTotal(subject), 0) / student.subjects.length).toFixed(2)
                )}
              </span>
            </div>
            <div className="stat">
              <span className="label">Overall Grade:</span>
              <span className="value grade-badge">
                {getGrade(student.subjects.reduce((sum, subject) => sum + calculateTotal(subject), 0) / student.subjects.length)}
              </span>
            </div>
          </div>
        </div>
      </div>

      <div className="footer-note">
        <p><strong>Grading Scale:</strong> A+ (90-100), A (80-89), B (70-79), C (60-69), D (50-59), F (Below 50)</p>
        <p><strong>Note:</strong> Total Marks = (MSE × 30%) + (ESE × 70%)</p>
      </div>
    </div>
  )
}
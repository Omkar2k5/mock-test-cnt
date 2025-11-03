import { useState } from 'react'
import './Home.css'

interface Subject {
  code: string
  name: string
  mse: number
  ese: number
}

interface StudentResult {
  prnNo: string
  name: string
  semester: number
  subjects: Subject[]
}

export function Home() {
  const [prnNo, setPrnNo] = useState('')
  const [student, setStudent] = useState<StudentResult | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const fetchStudentData = async () => {
    if (!prnNo.trim()) {
      setError('Please enter a PRN number')
      return
    }

    setLoading(true)
    setError('')
    setStudent(null)

    try {
      const response = await fetch(`http://localhost:8080/api/students/prn/${prnNo}`)
      if (response.ok) {
        const data = await response.json()
        setStudent(data)
      } else {
        setError('Result not found')
      }
    } catch (err) {
      setError('Failed to fetch data. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  const calculateTotal = (subject: Subject): number => {
    return parseFloat(((subject.mse + subject.ese) / 100).toFixed(2))
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

      <div className="search-card">
        <div className="search-container">
          <input
            type="text"
            placeholder="Enter PRN Number"
            value={prnNo}
            onChange={(e) => setPrnNo(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && fetchStudentData()}
            className="prn-input"
          />
          <button onClick={fetchStudentData} disabled={loading} className="fetch-btn">
            {loading ? 'Fetching...' : 'Fetch Result'}
          </button>
        </div>
        {error && <p className="error-message">{error}</p>}
      </div>

      {student && (
        <>
          <div className="student-info-card">
            <div className="student-info">
              <div className="info-item">
                <label>PRN Number:</label>
                <span>{student.prnNo}</span>
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
                  <th>MSE</th>
                  <th>ESE</th>
                  <th>Total</th>
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
                      <td className="marks">{subject.mse}</td>
                      <td className="marks">{subject.ese}</td>
                      <td className="total-marks">
                        <strong>{total}</strong>
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
            <p><strong>Note:</strong> Total = (MSE + ESE) / 100</p>
          </div>
        </>
      )}
    </div>
  )
}
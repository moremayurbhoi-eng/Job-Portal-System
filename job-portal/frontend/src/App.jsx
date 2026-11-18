import { Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';
import Jobs from './pages/Jobs';
import JobDetail from './pages/JobDetail';
import Login from './pages/Login';
import Register from './pages/Register';
import MyApplications from './pages/MyApplications';
import Dashboard from './pages/Dashboard';
import JobForm from './pages/JobForm';
import Applicants from './pages/Applicants';

export default function App() {
  return (
    <>
      <Navbar />
      <main className="wrap">
        <Routes>
          <Route path="/" element={<Jobs />} />
          <Route path="/jobs/:id" element={<JobDetail />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route path="/applications" element={<ProtectedRoute role="CANDIDATE"><MyApplications /></ProtectedRoute>} />

          <Route path="/dashboard" element={<ProtectedRoute role="EMPLOYER"><Dashboard /></ProtectedRoute>} />
          <Route path="/dashboard/jobs/new" element={<ProtectedRoute role="EMPLOYER"><JobForm /></ProtectedRoute>} />
          <Route path="/dashboard/jobs/:id/edit" element={<ProtectedRoute role="EMPLOYER"><JobForm /></ProtectedRoute>} />
          <Route path="/dashboard/jobs/:id/applicants" element={<ProtectedRoute role="EMPLOYER"><Applicants /></ProtectedRoute>} />

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
    </>
  );
}

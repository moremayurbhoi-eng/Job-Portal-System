import { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { errMsg } from '../api';

export default function Login() {
  const { login } = useAuth();
  const nav = useNavigate();
  const { state } = useLocation();
  const [f, setF] = useState({ email: '', password: '' });
  const [error, setError] = useState('');

  const submit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const u = await login(f.email, f.password);
      nav(state?.from || (u.role === 'EMPLOYER' ? '/dashboard' : '/'), { replace: true });
    } catch (err) {
      setError(errMsg(err));
    }
  };

  return (
    <form className="auth stack" onSubmit={submit}>
      <h1>Sign in</h1>
      <label>Email<input type="email" required autoComplete="email" value={f.email} onChange={(e) => setF({ ...f, email: e.target.value })} /></label>
      <label>Password<input type="password" required autoComplete="current-password" value={f.password} onChange={(e) => setF({ ...f, password: e.target.value })} /></label>
      {error && <p className="error">{error}</p>}
      <button className="btn">Sign in</button>
      <p className="muted">New here? <Link to="/register">Create an account</Link></p>
      <p className="hint">Demo: candidate@demo.com or employer@demo.com, password123</p>
    </form>
  );
}

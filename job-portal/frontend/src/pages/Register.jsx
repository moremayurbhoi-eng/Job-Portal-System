import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { errMsg } from '../api';

export default function Register() {
  const { register } = useAuth();
  const nav = useNavigate();
  const [f, setF] = useState({ name: '', email: '', password: '', role: 'CANDIDATE', company: '' });
  const [error, setError] = useState('');
  const set = (k) => (e) => setF({ ...f, [k]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const u = await register({ ...f, company: f.role === 'EMPLOYER' ? f.company : null });
      nav(u.role === 'EMPLOYER' ? '/dashboard' : '/', { replace: true });
    } catch (err) {
      setError(errMsg(err));
    }
  };

  return (
    <form className="auth stack" onSubmit={submit}>
      <h1>Create your account</h1>
      <fieldset className="roles">
        <legend>I want to</legend>
        <label><input type="radio" name="role" checked={f.role === 'CANDIDATE'} onChange={() => setF({ ...f, role: 'CANDIDATE' })} /> Find a job</label>
        <label><input type="radio" name="role" checked={f.role === 'EMPLOYER'} onChange={() => setF({ ...f, role: 'EMPLOYER' })} /> Hire people</label>
      </fieldset>
      <label>Full name<input required value={f.name} onChange={set('name')} /></label>
      {f.role === 'EMPLOYER' && <label>Company<input required value={f.company} onChange={set('company')} /></label>}
      <label>Email<input type="email" required autoComplete="email" value={f.email} onChange={set('email')} /></label>
      <label>Password<input type="password" required minLength="6" autoComplete="new-password" value={f.password} onChange={set('password')} /></label>
      {error && <p className="error">{error}</p>}
      <button className="btn">Create account</button>
      <p className="muted">Already registered? <Link to="/login">Sign in</Link></p>
    </form>
  );
}

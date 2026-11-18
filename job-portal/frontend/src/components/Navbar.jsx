import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const nav = useNavigate();

  return (
    <header className="nav">
      <div className="wrap nav-inner">
        <Link to="/" className="brand">Harbor Jobs</Link>
        <nav>
          <NavLink to="/" end>Browse jobs</NavLink>
          {user?.role === 'CANDIDATE' && <NavLink to="/applications">My applications</NavLink>}
          {user?.role === 'EMPLOYER' && <NavLink to="/dashboard">Dashboard</NavLink>}
          {user ? (
            <>
              <span className="who">{user.name}</span>
              <button className="link" onClick={() => { logout(); nav('/'); }}>Sign out</button>
            </>
          ) : (
            <>
              <NavLink to="/login">Sign in</NavLink>
              <Link to="/register" className="btn small">Create account</Link>
            </>
          )}
        </nav>
      </div>
    </header>
  );
}

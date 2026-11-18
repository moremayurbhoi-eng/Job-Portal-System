import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api, { errMsg } from '../api';
import { timeAgo } from '../utils';

export default function MyApplications() {
  const [apps, setApps] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    api.get('/applications/mine').then((r) => setApps(r.data)).catch((e) => setError(errMsg(e)));
  }, []);

  if (error) return <p className="error">{error}</p>;
  if (!apps) return <p className="muted">Loading…</p>;

  return (
    <>
      <h1>My applications</h1>
      {apps.length === 0 ? (
        <p className="empty">You haven't applied to anything yet. <Link to="/">Browse open jobs</Link></p>
      ) : (
        <ul className="job-list">
          {apps.map((a) => (
            <li className="job-row" key={a.id}>
              <Link to={`/jobs/${a.jobId}`}>
                <div>
                  <h3>{a.jobTitle}</h3>
                  <p className="muted">{a.company} · Applied {timeAgo(a.appliedAt)}</p>
                </div>
                <span className={`status ${a.status}`}>{a.status.toLowerCase()}</span>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </>
  );
}

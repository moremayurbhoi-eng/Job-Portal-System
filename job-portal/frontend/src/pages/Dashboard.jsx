import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api, { errMsg } from '../api';
import { JOB_TYPES, timeAgo } from '../utils';

export default function Dashboard() {
  const [jobs, setJobs] = useState(null);
  const [error, setError] = useState('');

  const load = () => api.get('/jobs/mine').then((r) => setJobs(r.data)).catch((e) => setError(errMsg(e)));
  useEffect(() => { load(); }, []);

  const remove = async (job) => {
    if (!window.confirm(`Delete "${job.title}" and its ${job.applicantCount} application(s)?`)) return;
    try { await api.delete(`/jobs/${job.id}`); load(); } catch (e) { setError(errMsg(e)); }
  };

  return (
    <>
      <div className="row-between">
        <h1>Your job postings</h1>
        <Link className="btn" to="/dashboard/jobs/new">Post a job</Link>
      </div>
      {error && <p className="error">{error}</p>}
      {!jobs && !error && <p className="muted">Loading…</p>}
      {jobs?.length === 0 && <p className="empty">You haven't posted any jobs. Post your first role to start receiving applications.</p>}
      {jobs?.length > 0 && (
        <ul className="job-list">
          {jobs.map((j) => (
            <li className="job-row dash" key={j.id}>
              <div>
                <h3><Link to={`/jobs/${j.id}`}>{j.title}</Link></h3>
                <p className="muted">{JOB_TYPES[j.type]} · {j.location} · Posted {timeAgo(j.postedAt)}</p>
                {!j.active && <span className="tag closed">Closed</span>}
              </div>
              <div className="actions">
                <Link to={`/dashboard/jobs/${j.id}/applicants`}><strong>{j.applicantCount}</strong> applicants</Link>
                <Link to={`/dashboard/jobs/${j.id}/edit`}>Edit</Link>
                <button className="link danger" onClick={() => remove(j)}>Delete</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </>
  );
}

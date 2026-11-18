import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import api, { errMsg } from '../api';
import { STATUSES, timeAgo } from '../utils';

export default function Applicants() {
  const { id } = useParams();
  const [apps, setApps] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    api.get(`/jobs/${id}/applications`).then((r) => setApps(r.data)).catch((e) => setError(errMsg(e)));
  }, [id]);

  const setStatus = async (app, status) => {
    try {
      const { data } = await api.patch(`/applications/${app.id}/status`, { status });
      setApps(apps.map((a) => (a.id === app.id ? data : a)));
    } catch (e) { setError(errMsg(e)); }
  };

  return (
    <>
      <Link to="/dashboard" className="back">Back to dashboard</Link>
      <h1>Applicants</h1>
      {error && <p className="error">{error}</p>}
      {apps?.length === 0 && <p className="empty">No applications yet. Share the job link to get candidates in.</p>}
      <ul className="job-list">
        {apps?.map((a) => (
          <li className="applicant" key={a.id}>
            <div className="row-between">
              <div>
                <h3>{a.candidateName}</h3>
                <p className="muted"><a href={`mailto:${a.candidateEmail}`}>{a.candidateEmail}</a> · Applied {timeAgo(a.appliedAt)}</p>
              </div>
              <select value={a.status} onChange={(e) => setStatus(a, e.target.value)} aria-label={`Status for ${a.candidateName}`}>
                {STATUSES.map((s) => <option key={s} value={s}>{s.charAt(0) + s.slice(1).toLowerCase()}</option>)}
              </select>
            </div>
            <p className="prose">{a.coverLetter}</p>
            {a.resumeUrl && <p><a href={a.resumeUrl} target="_blank" rel="noreferrer">Open resume</a></p>}
          </li>
        ))}
      </ul>
    </>
  );
}

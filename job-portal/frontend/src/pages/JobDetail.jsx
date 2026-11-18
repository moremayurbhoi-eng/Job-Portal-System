import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import api, { errMsg } from '../api';
import { useAuth } from '../AuthContext';
import { JOB_TYPES, salary, timeAgo } from '../utils';

export default function JobDetail() {
  const { id } = useParams();
  const { user } = useAuth();
  const [job, setJob] = useState(null);
  const [error, setError] = useState('');
  const [form, setForm] = useState({ coverLetter: '', resumeUrl: '' });
  const [msg, setMsg] = useState({ type: '', text: '' });
  const [busy, setBusy] = useState(false);

  useEffect(() => {
    api.get(`/jobs/${id}`).then((r) => setJob(r.data)).catch((e) => setError(errMsg(e)));
  }, [id]);

  const apply = async (e) => {
    e.preventDefault();
    setBusy(true);
    setMsg({ type: '', text: '' });
    try {
      await api.post(`/jobs/${id}/apply`, form);
      setMsg({ type: 'ok', text: 'Application sent. Track its status under My applications.' });
    } catch (err) {
      setMsg({ type: 'error', text: errMsg(err) });
    } finally {
      setBusy(false);
    }
  };

  if (error) return <p className="error">{error}</p>;
  if (!job) return <p className="muted">Loading…</p>;
  const pay = salary(job);
  const applied = msg.type === 'ok';

  return (
    <article className="detail">
      <Link to="/" className="back">Back to all jobs</Link>
      <h1>{job.title}</h1>
      <p className="muted">{job.company} · {job.location} · Posted {timeAgo(job.postedAt)}</p>
      <p className="facts">
        <span className="tag">{JOB_TYPES[job.type]}</span>
        {pay && <span className="pay">{pay}</span>}
        {!job.active && <span className="tag closed">Closed</span>}
      </p>

      <h2>About the role</h2>
      <p className="prose">{job.description}</p>
      {job.requirements && (
        <>
          <h2>What we're looking for</h2>
          <ul className="reqs">{job.requirements.split('\n').filter(Boolean).map((r, i) => <li key={i}>{r}</li>)}</ul>
        </>
      )}

      <section className="apply">
        {!user && <p><Link to="/login" state={{ from: `/jobs/${id}` }}>Sign in</Link> or <Link to="/register">create a candidate account</Link> to apply.</p>}
        {user?.role === 'EMPLOYER' && user.id === job.employerId && (
          <p><Link className="btn ghost" to={`/dashboard/jobs/${id}/applicants`}>View applicants</Link></p>
        )}
        {user?.role === 'CANDIDATE' && job.active && !applied && (
          <form onSubmit={apply} className="stack">
            <h2>Apply for this job</h2>
            <label>Cover letter
              <textarea rows="6" required maxLength="3000" value={form.coverLetter}
                onChange={(e) => setForm({ ...form, coverLetter: e.target.value })}
                placeholder="Tell the team why you're a good fit." />
            </label>
            <label>Resume link (optional)
              <input type="url" value={form.resumeUrl} placeholder="https://"
                onChange={(e) => setForm({ ...form, resumeUrl: e.target.value })} />
            </label>
            {msg.type === 'error' && <p className="error">{msg.text}</p>}
            <button className="btn" disabled={busy}>{busy ? 'Sending…' : 'Send application'}</button>
          </form>
        )}
        {applied && <p className="success">{msg.text}</p>}
      </section>
    </article>
  );
}

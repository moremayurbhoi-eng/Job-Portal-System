import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api, { errMsg } from '../api';
import { useAuth } from '../AuthContext';
import { JOB_TYPES } from '../utils';

const empty = { title: '', company: '', location: '', type: 'FULL_TIME', description: '', requirements: '', salaryMin: '', salaryMax: '', active: true };

export default function JobForm() {
  const { id } = useParams();
  const { user } = useAuth();
  const nav = useNavigate();
  const [f, setF] = useState({ ...empty, company: user?.company || '' });
  const [error, setError] = useState('');
  const set = (k) => (e) => setF({ ...f, [k]: e.target.value });

  useEffect(() => {
    if (!id) return;
    api.get(`/jobs/${id}`)
      .then((r) => setF({ ...empty, ...r.data, salaryMin: r.data.salaryMin ?? '', salaryMax: r.data.salaryMax ?? '', requirements: r.data.requirements ?? '' }))
      .catch((e) => setError(errMsg(e)));
  }, [id]);

  const submit = async (e) => {
    e.preventDefault();
    setError('');
    const body = {
      title: f.title, company: f.company, location: f.location, type: f.type,
      description: f.description, requirements: f.requirements, active: f.active,
      salaryMin: f.salaryMin === '' ? null : Number(f.salaryMin),
      salaryMax: f.salaryMax === '' ? null : Number(f.salaryMax),
    };
    try {
      if (id) await api.put(`/jobs/${id}`, body); else await api.post('/jobs', body);
      nav('/dashboard');
    } catch (err) {
      setError(errMsg(err));
    }
  };

  return (
    <form className="stack form-wide" onSubmit={submit}>
      <h1>{id ? 'Edit job' : 'Post a job'}</h1>
      <label>Job title<input required value={f.title} onChange={set('title')} /></label>
      <div className="two">
        <label>Company<input required value={f.company} onChange={set('company')} /></label>
        <label>Location<input required value={f.location} onChange={set('location')} placeholder="City, or Remote" /></label>
      </div>
      <label>Job type
        <select value={f.type} onChange={set('type')}>
          {Object.entries(JOB_TYPES).map(([k, v]) => <option key={k} value={k}>{v}</option>)}
        </select>
      </label>
      <div className="two">
        <label>Minimum salary<input type="number" min="0" value={f.salaryMin} onChange={set('salaryMin')} /></label>
        <label>Maximum salary<input type="number" min="0" value={f.salaryMax} onChange={set('salaryMax')} /></label>
      </div>
      <label>Description<textarea rows="7" required maxLength="5000" value={f.description} onChange={set('description')} /></label>
      <label>Requirements (one per line)<textarea rows="5" maxLength="3000" value={f.requirements} onChange={set('requirements')} /></label>
      <label className="check"><input type="checkbox" checked={f.active} onChange={(e) => setF({ ...f, active: e.target.checked })} /> Accepting applications</label>
      {error && <p className="error">{error}</p>}
      <div className="actions">
        <button className="btn">{id ? 'Save changes' : 'Publish job'}</button>
        <button type="button" className="btn ghost" onClick={() => nav('/dashboard')}>Cancel</button>
      </div>
    </form>
  );
}

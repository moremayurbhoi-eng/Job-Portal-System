import { useEffect, useState } from 'react';
import api, { errMsg } from '../api';
import JobRow from '../components/JobRow';
import { JOB_TYPES } from '../utils';

export default function Jobs() {
  const [form, setForm] = useState({ q: '', location: '', type: '' });
  const [filters, setFilters] = useState(form);
  const [page, setPage] = useState(0);
  const [data, setData] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    setError('');
    api.get('/jobs', { params: { ...filters, type: filters.type || undefined, page, size: 8 } })
      .then((r) => setData(r.data))
      .catch((e) => setError(errMsg(e)));
  }, [filters, page]);

  const submit = (e) => {
    e.preventDefault();
    setPage(0);
    setFilters(form);
  };
  const set = (k) => (e) => setForm({ ...form, [k]: e.target.value });

  return (
    <>
      <section className="hero">
        <h1>Open roles from teams hiring now</h1>
        <form className="search" onSubmit={submit}>
          <input placeholder="Job title or company" value={form.q} onChange={set('q')} aria-label="Job title or company" />
          <input placeholder="Location" value={form.location} onChange={set('location')} aria-label="Location" />
          <select value={form.type} onChange={set('type')} aria-label="Job type">
            <option value="">Any type</option>
            {Object.entries(JOB_TYPES).map(([k, v]) => <option key={k} value={k}>{v}</option>)}
          </select>
          <button className="btn">Search</button>
        </form>
      </section>

      {error && <p className="error">{error}</p>}
      {!data && !error && <p className="muted">Loading jobs…</p>}
      {data && (
        <>
          <p className="muted count">{data.totalElements} {data.totalElements === 1 ? 'job' : 'jobs'} found</p>
          {data.content.length === 0 ? (
            <p className="empty">No jobs match those filters. Try a broader title or clear the location.</p>
          ) : (
            <ul className="job-list">{data.content.map((j) => <JobRow key={j.id} job={j} />)}</ul>
          )}
          {data.totalPages > 1 && (
            <div className="pager">
              <button className="btn ghost" disabled={page === 0} onClick={() => setPage(page - 1)}>Previous</button>
              <span className="muted">Page {data.page + 1} of {data.totalPages}</span>
              <button className="btn ghost" disabled={page + 1 >= data.totalPages} onClick={() => setPage(page + 1)}>Next</button>
            </div>
          )}
        </>
      )}
    </>
  );
}

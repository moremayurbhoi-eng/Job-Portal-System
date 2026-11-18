import { Link } from 'react-router-dom';
import { JOB_TYPES, salary, timeAgo } from '../utils';

export default function JobRow({ job }) {
  const pay = salary(job);
  return (
    <li className="job-row">
      <Link to={`/jobs/${job.id}`}>
        <div>
          <h3>{job.title}</h3>
          <p className="muted">{job.company} · {job.location}</p>
        </div>
        <div className="job-meta">
          <span className="tag">{JOB_TYPES[job.type]}</span>
          {pay && <span className="pay">{pay}</span>}
          <span className="muted">{timeAgo(job.postedAt)}</span>
        </div>
      </Link>
    </li>
  );
}
